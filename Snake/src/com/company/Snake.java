package com.company;

import javax.swing.*;
import java.io.*;
import java.awt.*;

public class Snake
{

    private int speed = 4;

    public static Image getImg()
    {
        return Snake.image;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void snakeMovement (JPanel container)
    {
        if (movingRight)
        {
            image = rightmouth;
            x += speed;
            y += 0;
        }
        if (movingLeft)
        {
            image = leftmouth;
            x += -speed;
            y += 0;
        }
        if (movingUp)
        {
            image = upmouth;
            x += 0;
            y += -speed;
        }
        if (movingDown)
        {
            image = downmouth;
            x += 0;
            y += speed;
        }
    }


    static final String directory = "assets" + File.separator;

    private static Image rightmouth = new ImageIcon(directory + "rightmouth.png").getImage();
    private static Image leftmouth = new ImageIcon(directory + "leftmouth.png").getImage();
    private static Image upmouth = new ImageIcon(directory + "upmouth.png").getImage();
    private static Image downmouth = new ImageIcon(directory + "downmouth.png").getImage();
    private static Image image = (Image)rightmouth;

    private static Image food = new ImageIcon(directory + "enemy.png").getImage();

    private static int x = 200;
    private static int y = 200;

    private int mouthWidth = rightmouth.getWidth(null);
    private int mouthHeight = rightmouth.getHeight(null);

    private boolean movingRight = true;
    private boolean movingLeft = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

    public boolean isMovingUp() {
        return movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

}
