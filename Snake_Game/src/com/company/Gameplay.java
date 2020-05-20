package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;


public class Gameplay extends JPanel implements ActionListener
{
    Object directory = "assets" + File.separator;
    private ImageIcon titleImage = new ImageIcon(directory + "snaketitle.jpg");
    private ImageIcon rightmouth = new ImageIcon(directory + "rightmouth.png");
    private ImageIcon leftmouth = new ImageIcon(directory + "leftmouth.png");
    private ImageIcon upmouth = new ImageIcon(directory + "upmouth.png");
    private ImageIcon downmouth = new ImageIcon(directory + "downmouth.png");
    private ImageIcon snakeTail = new ImageIcon(directory + "snakeimage.png");
    private ImageIcon food = new ImageIcon( directory + "enemy.png");
    private Icon startIcon = new ImageIcon(directory + "snake.png");

    private Timer timer;
    private int delay;

    private Object[] possibilities = {"easy", "medium" , "hard", "insane"};
    private String level;

    private int lengthOfSnake = 3;
    private static int score = 0;

    private int[] snakeX = new int[750];
    private int[] snakeY = new int[750];

    private Random random = new Random();
    private int foodX = random.nextInt(850 - 25) + 25;
    private int foodY = random.nextInt(575 - 75) + 75;

    private volatile boolean movingRight = false;
    private volatile boolean movingLeft = false;
    private volatile boolean movingUp = false;
    private volatile boolean movingDown = false;
    private boolean dead = false;

    int move = 0;

    Gameplay() {

        difficulty();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                keyPressedHandler(e);
            }
        });

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }


    @Override
    public void paint(Graphics g) {

        if (move == 0)
        {
            snakeX[0] = 100;
            snakeX[1] = 75;
            snakeX[2] = 50;

            snakeY[0] = 100;
            snakeY[1] = 100;
            snakeY[2] = 100;
        }

        g.setColor(Color.white);
        g.drawRect(24,10,851,55);



        titleImage.paintIcon(this, g , 25, 11);


        g.setColor(Color.WHITE);
        g.drawRect(24,74,851,577);

        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);


        food.paintIcon(this,g,foodX,foodY);

        rightmouth.paintIcon(this, g, snakeX[0],snakeY[0]);

        for (int i = 0 ; i < lengthOfSnake ; i++)
        {
            if (i == 0 && movingRight)
                rightmouth.paintIcon(this,g, snakeX[i] , snakeY[i]);

            if (i == 0 && movingLeft)
                leftmouth.paintIcon(this,g, snakeX[i] , snakeY[i]);

            if (i == 0 && movingUp)
                upmouth.paintIcon(this,g, snakeX[i] , snakeY[i]);

            if (i == 0 && movingDown)
                downmouth.paintIcon(this,g, snakeX[i] , snakeY[i]);

            if (i != 0)
                snakeTail.paintIcon(this, g , snakeX[i] , snakeY[i]);

        }
        g.dispose();
    }
    private void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            if (dead)
            {
                move = 0;
                lengthOfSnake = 3;
                timer.restart();
                score = 0;
                dead = false;
                repaint();
            }

            else
            {
                if (timer.isRunning())
                    timer.stop();
                else timer.start();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (!movingDown) {
                move = 1;
                movingUp = true;
                movingDown = false;
                movingLeft = false;
                movingRight = false;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!movingUp) {
                move = 1;
                movingUp = false;
                movingDown = true;
                movingLeft = false;
                movingRight = false;
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (!movingRight) {
                move = 1;
                movingUp = false;
                movingDown = false;
                movingLeft = true;
                movingRight = false;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (!movingLeft) {
                move = 1;
                movingUp = false;
                movingDown = false;
                movingLeft = false;
                movingRight = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        snakeEating();
        snakeMoving();
        snakeDeath();
    }

    public void snakeMoving()
    {
        if (movingRight) {
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeY[i + 1] = snakeY[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakeX[i] += 25;
                } else {
                    snakeX[i] = snakeX[i - 1];
                }
                if (snakeX[i] > 850) snakeX[i] = 25;
            }
            repaint();
        }

        if (movingLeft) {

            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeY[i + 1] = snakeY[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakeX[i] -= 25;
                } else {
                    snakeX[i] = snakeX[i - 1];
                }
                if (snakeX[i] < 25) snakeX[i] = 850;
            }
            repaint();
        }

        if (movingUp) {

            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeX[i + 1] = snakeX[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakeY[i] -= 25;
                } else {
                    snakeY[i] = snakeY[i - 1];
                }
                if (snakeY[i] < 75) snakeY[i] = 625;
            }
            repaint();

        }

        if (movingDown) {

            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                snakeX[i + 1] = snakeX[i];
            }
            for (int i = lengthOfSnake; i >= 0; i--) {
                if (i == 0) {
                    snakeY[i] += 25;
                } else {
                    snakeY[i] = snakeY[i - 1];
                }
                if (snakeY[i] > 625) snakeY[i] = 75;
            }
            repaint();
        }
    }

    public void snakeEating()
    {
        Rectangle foodArea = new Rectangle(foodX + 10 ,foodY + 10 ,food.getIconWidth() - 10,food.getIconHeight() - 10);
        Rectangle snakeArea = new Rectangle(snakeX[0] + 5 , snakeY[0] + 5 ,rightmouth.getIconWidth() - 5, rightmouth.getIconHeight() - 5);

        if (snakeArea.intersects(foodArea))
        {
            if (level.equals("insane"))
                lengthOfSnake += 4;

            else if(level.equals("hard"))
                lengthOfSnake += 2;

            else
                lengthOfSnake++;

            foodX = random.nextInt(850 - 25) + 25;
            foodY = random.nextInt(575 - 75) + 75;
            score += 10;
        }
    }

    public void snakeDeath()
    {
        Rectangle snakeArea = new Rectangle(snakeX[0] + 5 , snakeY[0] + 5 ,rightmouth.getIconWidth() - 5, rightmouth.getIconHeight() - 5);
        for (int i = 3; i < lengthOfSnake ; i++)
        {
            Rectangle snakeTailArea = new Rectangle(snakeX[i]+5, snakeY[i]+5,snakeTail.getIconWidth(),snakeTail.getIconHeight() -5);
            if (snakeArea.intersects(snakeTailArea))
            {
                timer.stop();
                dead = true;
                JOptionPane.showMessageDialog(this, "Your Score is: " + score +
                        " on " + level + " level" +  "\n" + "Click Ok and press Space to try again");
            }

        }
    }

    public void difficulty()
    {

        level = (String)JOptionPane.showInputDialog(
                this,"Start the game","Choose diffucult level",
                JOptionPane.PLAIN_MESSAGE,startIcon,possibilities,"easy");

        if (level.equals("easy"))
            delay = 100;

        if(level.equals("medium"))
            delay = 70;

        if(level.equals("hard"))
            delay = 40;

        if(level.equals("insane"))
            delay = 20;
    }
}



