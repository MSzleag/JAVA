package com.company;
import javax.swing.*;
import java.awt.*;


public class Main //extends JFrame
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        Gameplay gameplay = new Gameplay();

        frame.setTitle("Snake Game");
        frame.setVisible(true);

        frame.setBounds(100,100,905,700);
        frame.setBackground(Color.DARK_GRAY);

        frame.setResizable(false);
        frame.setVisible(true);

        frame.add(gameplay,BorderLayout.CENTER);
        gameplay.setFocusable(true);
        gameplay.requestFocusInWindow();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
