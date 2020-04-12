package com.company;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.Random;

public class Snake
{

    private int speed = 5;
    static int score = 0;

    public static Image getImg()
    {
        return Snake.image;
    }
    public static Image getFood() { return Snake.food; }

    public int getX() { return x; }
    public int getY() { return y; }

    public static int getFoodX() { return foodX; }
    public static int getFoodY() { return foodY; }

    public void snakeEat()
    {
        Rectangle foodArea = new Rectangle(foodX+5,foodY+5,food.getWidth(null)-10,food.getHeight(null)-10);
        Rectangle snakeArea = new Rectangle(x, y ,image.getWidth(null),image.getHeight(null));

        if (snakeArea.intersects(foodArea))
        {
             foodX = random.nextInt(700);
             foodY = random.nextInt(500);

            score += 10;
            System.out.println(score);
        }
    }

    public void snakeMovement (JPanel container)
    {
        Rectangle rectangle = container.getBounds();

        if (movingRight)
        {
            image = rightmouth;
            x += speed;
            y += 0;
            if (x >= rectangle.getMaxX()) x = (int)rectangle.getMinX();
        }

        if (movingLeft)
        {
            image = leftmouth;
            x += -speed;
            y += 0;
            if (x <= rectangle.getMinX()) x = (int)rectangle.getMaxX();
        }

        if (movingUp)
        {
            image = upmouth;
            x += 0;
            y += -speed;
            if (y <= rectangle.getMinY()) y = (int)rectangle.getMaxY();
        }

        if (movingDown)
        {
            image = downmouth;
            x += 0;
            y += speed;
            if (y >= rectangle.getMaxY()) y = (int)rectangle.getMinY();
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

    static Random random = new Random();
    private static int foodX = random.nextInt(700);
    private static int foodY = random.nextInt(500);

    private int mouthWidth = rightmouth.getWidth(null);
    private int mouthHeight = rightmouth.getHeight(null);

    private volatile boolean movingRight = true;
    private volatile boolean movingLeft = false;
    private volatile boolean movingUp = false;
    private volatile boolean movingDown = false;

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
