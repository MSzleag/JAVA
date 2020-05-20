
package moj;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Moj extends JFrame
{
    Moj()
    {
        initComponents();
        
    }
    
   public void initComponents()
   {
        this.setTitle("Moj zegarek");
        this.setBounds(400,400,400,250);
        
        panel.add(etykieta);
        
        panel2.add(godziny);
        panel2.add(minuty);
        panel2.add(sekundy);
        
        ActionListener stoper = new Zegar();
        
        godziny.addActionListener(new CheckBoxHandler());
        minuty.addActionListener(new CheckBoxHandler());
        sekundy.addActionListener(new CheckBoxHandler());
        
        Timer zegar = new Timer(1000,stoper);
        zegar.start();
        
        getContentPane().add(panel,BorderLayout.NORTH);
        getContentPane().add(panel1,BorderLayout.CENTER);
        getContentPane().add(panel2,BorderLayout.SOUTH);

        this.setDefaultCloseOperation(3);
   }
   
   class Zegar implements ActionListener
   {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (godziny.isSelected()) etykieta.setText(pobierzGodzine());
            if (minuty.isSelected())
            {
                godziny.setSelected(true);
                etykieta.setText(pobierzGodzine() + " : " + pobierzMinute());
            }
            if (sekundy.isSelected()) 
            {
                minuty.setSelected(true);
                etykieta.setText( pobierzGodzine()+ " : " + pobierzMinute() + " : " + pobierzSekunde());
            }
            
           
        }
       
   }
       
   JPanel panel = new JPanel();
   JPanel panel1 = new JPanel();
   JPanel panel2 = new JPanel();
   JLabel etykieta = new JLabel();
   JCheckBox godziny = new JCheckBox("Godzina");
   JCheckBox minuty = new JCheckBox("Minuta");
   JCheckBox sekundy = new JCheckBox("Sekunda");
   
       
           class CheckBoxHandler implements ActionListener
    {

        
        public void actionPerformed(ActionEvent e) 
        {
            
            if (godziny.isSelected()) etykieta.setText(pobierzGodzine());
            if (minuty.isSelected())
            {
                godziny.setSelected(true);
                etykieta.setText(pobierzGodzine() + " : " + pobierzMinute());
            }
            if  (sekundy.isSelected()) 
            {
                minuty.setSelected(true);
                etykieta.setText( pobierzGodzine()+ " : " + pobierzMinute() + " : " + pobierzSekunde());
            }
            
           
        }
        
       
   }
   
       public String pobierzGodzine()
    {
           GregorianCalendar kalendarz  = new GregorianCalendar();
           String h = ""+kalendarz.get(Calendar.HOUR_OF_DAY);
           
           if (Integer.parseInt(h) < 10)
                   h = "0" + h;

           
        return h;
    }
    public String pobierzMinute()
    {
           GregorianCalendar kalendarz  = new GregorianCalendar();

           String m = ""+kalendarz.get(Calendar.MINUTE);

           if (Integer.parseInt(m) < 10)
                   m = "0" + m;

           
           
        return  m;
    }
    public String pobierzSekunde()
    {
           GregorianCalendar kalendarz  = new GregorianCalendar();

           String s = ""+kalendarz.get(Calendar.SECOND);

           if (Integer.parseInt(s) < 10)
                   s = "0" + s;
           
           
        return s;
    }
      


    public static void main(String[] args) {
       new Moj().setVisible(true);
    }
    
}
