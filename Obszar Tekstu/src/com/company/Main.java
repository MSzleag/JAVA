
package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.BorderLayout.*;
import static java.awt.BorderLayout.SOUTH;

public class Main extends JFrame
{
    Main()
    {

        initComponents();


    }

    public void initComponents()
    {
        this.setTitle("Finding in Text Area");
        this.setBounds(400,400,400,250);

        //obszarTekstowy.selectAll();  Oznacza caly tekst
        //obszarTekstowy.select(0,3); //Oznacza zaznaczone pozycje odkąd do kąd
        //obszarTekstowy.replaceSelection("lala "); zamienia obszar ktory jest zaznaczony
        //obszarTekstowy.replaceRange("lala",0,13); zamienia tekst w zadanym obszarze
        //obszarTekstowy.insert("insert",0); dolacza w zadanym miejscu
        //obszarTekstowy.append("dolaczone do konca");
        //obszarTekstowy.select(obszarTekstowy.getText().indexOf("test"),obszarTekstowy.getText().indexOf("test")+"test".length()); zaznacza tekst od jego poczatku do zadanego momentu dl


        panelPodmiany.add(replace);
        panelPodmiany.add(podmiana);

        panelSzukania.add(Znajdz);
        panelSzukania.add(szukany);

        Znajdz.addActionListener(e -> {

            poczatekSzukanego = obszarTekstowy.getText().indexOf(szukany.getText(), poczatekSzukanego + szukany.getText().length());

            if (poczatekSzukanego >= 0 && szukany.getText().length() > 0)
            {
                obszarTekstowy.requestFocus();
                obszarTekstowy.select(poczatekSzukanego,poczatekSzukanego + szukany.getText().length());
            }

        });
        replace.addActionListener(e ->
                {
                    if (obszarTekstowy.getSelectionStart() == obszarTekstowy.getSelectionEnd())
                    {
                        Znajdz.doClick(0);
                        if ( obszarTekstowy.getSelectionStart() != obszarTekstowy.getSelectionEnd()) {
                            int option = JOptionPane.showConfirmDialog(rootPane, "Are you willing to replace \"" + szukany.getText() + " on \"" + podmiana.getText() + "\"", "Replace Confirmation", JOptionPane.YES_NO_OPTION);

                            if (option == 0) {
                                obszarTekstowy.replaceSelection(podmiana.getText());
                            }
                        }
                    }


                    else if (obszarTekstowy.getSelectionStart() != obszarTekstowy.getSelectionEnd())
                    {
                        obszarTekstowy.requestFocus();
                        int option = JOptionPane.showConfirmDialog(rootPane, "Are you willing to replace \"" + szukany.getText() + " on \"" + podmiana.getText() + "\"","Replace Confirmation", JOptionPane.YES_NO_OPTION);
                        if (option == 0)
                        {
                            obszarTekstowy.replaceSelection(podmiana.getText());
                        }
                    }

                });


        this.getContentPane().add(obszarPrzewijania, NORTH);
        this.getContentPane().add(panelSzukania, CENTER);
        this.getContentPane().add(panelPodmiany,SOUTH);

        this.setDefaultCloseOperation(3);
    }

    private JTextArea obszarTekstowy = new JTextArea("This is test text for testing text areas and finding metods ",7, 10);

    private JScrollPane obszarPrzewijania = new JScrollPane(obszarTekstowy);

    private JPanel panelSzukania = new JPanel();
    private JPanel panelPodmiany = new JPanel();

    private JButton Znajdz = new JButton("Find");
    private JButton replace = new JButton("Replace");

    private JTextField szukany = new JTextField(8);
    private  JTextField podmiana = new JTextField(8);

    private int poczatekSzukanego = 0;


    public static void main(String[] args)
    {
        new Main().setVisible(true);
    }

}
