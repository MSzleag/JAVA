package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gameplay extends JPanel
{
    private Snake snake = new Snake();
    private Thread snakeThread = new Thread();

    private Object lock = new Object();
    private volatile boolean stop = true;

    Gameplay()
    {
        this.setPreferredSize(new Dimension(700 , 500));
        this.setMaximumSize(this.getPreferredSize());
        this.setMinimumSize(this.getPreferredSize());
        this.setBackground(Color.BLACK);

        snakeThread = new Thread(new SnakeRunnable(snake));
        snakeThread.start();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                keyPressedHandler(e);
            }
        });

    }


    public void startAnimation()
    {
        stop = false;

        synchronized (lock)
        {
            lock.notifyAll();
        }

    }

    public void stopAnimation() { stop = true; }

    private void keyPressedHandler(KeyEvent e)
{
    if (e.getKeyCode() == KeyEvent.VK_SPACE)
    {
        if (stop)
            startAnimation();

        else
            stopAnimation();
    }

    if (e.getKeyCode() == KeyEvent.VK_UP)
    {
        if (!snake.isMovingDown())
        {
            snake.setMovingUp(true);
            snake.setMovingDown(false);
            snake.setMovingLeft(false);
            snake.setMovingRight(false);
        }
    }

    if (e.getKeyCode() == KeyEvent.VK_DOWN)
    {
        if(!snake.isMovingUp())
        {
            snake.setMovingUp(false);
            snake.setMovingDown(true);
            snake.setMovingLeft(false);
            snake.setMovingRight(false);
        }

    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
    {
        if (!snake.isMovingRight())
        {
            snake.setMovingUp(false);
            snake.setMovingDown(false);
            snake.setMovingLeft(true);
            snake.setMovingRight(false);
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
    {
        if (!snake.isMovingLeft())
        {
            snake.setMovingUp(false);
            snake.setMovingDown(false);
            snake.setMovingLeft(false);
            snake.setMovingRight(true);
        }
    }
}
    JPanel thisPanel = this;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(Snake.getImg(),snake.getX(),snake.getY(),null);

            g.drawImage(Snake.getFood(),snake.getFoodX(),snake.getFoodY(),null);

    }

    public class SnakeRunnable implements Runnable
    {
        Snake snake;
        public SnakeRunnable(Snake snake)
        {
            this.snake = snake;
        }
        @Override
        public void run()
        {
            while (true) //DEATH TODO
            {

                synchronized (lock)
                {
                    while (stop)
                    {
                        try
                        {
                            lock.wait();
                        }
                        catch (InterruptedException e)
                        {
                            System.out.println(e.getMessage());
                        }
                    }

                    this.snake.snakeMovement(thisPanel);
                    this.snake.snakeEat();

                    repaint();

                    try
                    {
                        Thread.sleep(15);
                    }

                    catch (InterruptedException ex)
                    {
                        System.out.println("Stopped");
                    }

                }
            }
        }
    }

}

