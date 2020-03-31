package etykiety;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public final class Etykiety extends JFrame
{
    Etykiety()
    {
        super("Etykiety");
        this.setBounds(400,400,400,250);
        
        InitComponents();
        
        this.setDefaultCloseOperation(3);
    }
    
    public void InitComponents()
    {
//        panel.add(etykieta);
        panel.add(czas);
        ActionListener stoper = new Zegar();
        
        
        Timer zegar = new Timer(1000,stoper);
        
        zegar.start();
        
        this.getContentPane().add(panel);
        pack();
    }
    JPanel panel = new JPanel();
    JLabel etykieta = new JLabel("czas");
    JLabel czas = new JLabel(pobierzCzas());
    
    private class Zegar implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
           
            GregorianCalendar kalendarz  = new GregorianCalendar();
           String h = ""+kalendarz.get(Calendar.HOUR_OF_DAY);
           String m = ""+kalendarz.get(Calendar.MINUTE);
           String s = ""+kalendarz.get(Calendar.SECOND);
           
           if (Integer.parseInt(h) < 10)
                   h = "0" + h;
           if (Integer.parseInt(m) < 10)
                   m = "0" + m;
           if (Integer.parseInt(s) < 10)
                   s = "0" + s;
           
           czas.setText(pobierzCzas());
        }
        
    }
    static int i = 1;
    
    public String pobierzCzas()
    {
           GregorianCalendar kalendarz  = new GregorianCalendar();
           String h = ""+kalendarz.get(Calendar.HOUR_OF_DAY);
           String m = ""+kalendarz.get(Calendar.MINUTE);
           String s = ""+kalendarz.get(Calendar.SECOND);
           
           if (Integer.parseInt(h) < 10)
                   h = "0" + h;
           if (Integer.parseInt(m) < 10)
                   m = "0" + m;
           if (Integer.parseInt(s) < 10)
                   s = "0" + s;
           
           
        return h + ":" + m + ":" + s;
    }
    
    public static void main(String[] args) 
    {
        new Etykiety().setVisible(true);
    }
    
}
