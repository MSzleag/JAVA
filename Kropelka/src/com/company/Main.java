
package com.company;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Main extends JFrame
{
    Main()
    {
        this.setTitle("Checkbox");
        this.setBounds(400,400,400,250);
        panelAnimacji.setBackground(Color.gray);

        JButton buttonStart = (JButton)panelButtonow.add(new JButton("Start"));
        buttonStart.addActionListener(e -> startAnimation());

        JButton buttonStop = (JButton)panelButtonow.add(new JButton("Stop"));
        buttonStop.addActionListener(e -> stopAnimation());

        JButton buttonDodaj = (JButton)panelButtonow.add(new JButton("Dodaj"));
        buttonDodaj.addActionListener(e -> dodajAnimation());

        this.getContentPane().add(panelAnimacji);
        this.getContentPane().add(panelButtonow,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(3);
    }

    public void startAnimation ()
    {
        panelAnimacji.startAnimation();
    }

    public void stopAnimation ()
    {
        panelAnimacji.stop();
    }

    public void dodajAnimation() {panelAnimacji.addKropelka();};

    private JPanel panelButtonow = new JPanel();
    private PanelAnimacji panelAnimacji = new PanelAnimacji();



    public static void main(String[] args)
    {
        new Main().setVisible(true);
    }

    class PanelAnimacji extends JPanel
    {
        private volatile boolean zatrzymany = false;
        private Object lock = new Object();

        public void addKropelka()
        {
            listaKropelek.add(new Kropelka());
            watek = new Thread(grupaWatkow,new KropelkaRunnable((Kropelka)listaKropelek.get(listaKropelek.size()-1)));
            watek.start();
        }

        public void stop()
        {
            zatrzymany = true;
        }

        public void startAnimation()
        {
            if (zatrzymany)
            {
                zatrzymany = false;
                synchronized (lock)
                {
                    lock.notifyAll();
                }
            }
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            for (int i = 0; i < listaKropelek.size(); i++)
            {
                g.drawImage(Kropelka.getImg(), ((Kropelka)listaKropelek.get(i)).x, ((Kropelka)listaKropelek.get(i)).y, null);
            }
        }

        ArrayList listaKropelek = new ArrayList();
        JPanel ten = this;
        Thread watek;
        ThreadGroup grupaWatkow = new ThreadGroup("Grupa Kropelek");


        public class KropelkaRunnable implements Runnable
        {
            public KropelkaRunnable(Kropelka kropelka)
            {
                this.kropelka = kropelka;
            }

            @Override
            public void run()
            {

                    while (true)
                    {
                        synchronized (lock)
                        {
                            while (zatrzymany)
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
                        }
                        this.kropelka.ruszKropelka(ten);
                        repaint();
                        try
                        {
                            Thread.sleep(2);
                        }
                        catch (InterruptedException e)
                        {
                            System.out.println(e.getMessage());
                        }
                    }
            }
            Kropelka kropelka;
        }
    }
}

class Kropelka
{

    public static Image getImg()
    {
        return Kropelka.kropelka;
    }

    public void ruszKropelka (JPanel pojemnik)
    {
        Rectangle granicePanelu = pojemnik.getBounds();
        x += dx;
        y += dy;

        if (y + yKropelki >= granicePanelu.getMaxY())
        {
            y = (int)granicePanelu.getMaxY() - yKropelki;
            dy = -dy;
        }
        if (x + xKropelki >= granicePanelu.getMaxX())
        {
            x = (int)granicePanelu.getMaxX() - xKropelki;
            dx = -dx;
        }
        if (y + yKropelki <= granicePanelu.getMinY())
        {
            y = (int)granicePanelu.getMinY();
            dy = -dy;
        }
        if (x + xKropelki <= granicePanelu.getMinX())
        {
            x = (int)granicePanelu.getMinX();
            dx = -dx;
        }

    }

    public static Image kropelka = new ImageIcon("kropelka.gif").getImage();

    int x = 0;
    int y = 0;

    int dx = 1;
    int dy = 1;

    int xKropelki = kropelka.getWidth(null);
    int yKropelki = kropelka.getHeight(null);

}