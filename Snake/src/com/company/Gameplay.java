package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class Gameplay extends JPanel implements ActionListener
{

    private ImageIcon titleImage;
    private ImageIcon rightmouth = new ImageIcon("rightmouth.png");
    private ImageIcon leftmouth = new ImageIcon("leftmouth.png");
    private ImageIcon upmouth = new ImageIcon("upmouth.png");
    private ImageIcon downmouth = new ImageIcon("downmouth.png");
    private ImageIcon snakeTail = new ImageIcon("snakeimage.png");
    private ImageIcon food = new ImageIcon("enemy.png");

    private Timer timer;
    private int delay = 100;

    private int lengthOfSnake = 3;

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


        titleImage = new ImageIcon("snaketitle.jpg");
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
        timer.start();
        snakeEating();
        while(!dead) {
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
                    if (snakeY[i] < 75) snakeY[i] = 575;
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
    }
    public void snakeEating()
    {
        Rectangle foodArea = new Rectangle(foodX + 5 ,foodY + 5 ,food.getIconWidth() - 5,food.getIconHeight() - 5);
        Rectangle snakeArea = new Rectangle(snakeX[0] + 5 , snakeY[0] + 5 ,rightmouth.getIconWidth() - 5, rightmouth.getIconHeight() - 5);

        if (snakeArea.intersects(foodArea))
        {
            foodX = random.nextInt(850 - 25) + 25;
            foodY = random.nextInt(575 - 75) + 75;
            lengthOfSnake++;
        }
    }

}



