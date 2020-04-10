package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper extends JFrame
{
        Zipper()
        {
            this.setTitle("Zipper");
            this.setBounds(2500,500,400,300);
            this.setJMenuBar(pasekMenu);

            JMenu menuPlik = pasekMenu.add(new JMenu("Plik"));

            Action akcjaDodawania = new Akcja("Dodaj", "Dodaj nowy wpis do archiwu,","ctrl d", new ImageIcon("dodaj.png") );
            Action akcjaUsuwania = new Akcja("Usuń", "Usun z archiwum","ctrl u", new ImageIcon("usun.png"));
            Action akcjaZippowania = new Akcja("Zip", "Zippuj,","ctrl z");

            JMenuItem menuOtworz = menuPlik.add(akcjaDodawania);
            JMenuItem menuUsun = menuPlik.add(akcjaUsuwania);
            JMenuItem menuZip = menuPlik.add(akcjaZippowania);

            JScrollPane scrollPane = new JScrollPane(lista);

            bDodaj = new JButton(akcjaDodawania);
            bUsun = new JButton(akcjaUsuwania);
            bZip = new JButton(akcjaZippowania);

            lista.setBorder(BorderFactory.createEtchedBorder());
            GroupLayout layout = new GroupLayout(this.getContentPane());

            layout.setAutoCreateContainerGaps(true);
            layout.setAutoCreateGaps(true);

            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                    .addComponent(scrollPane, 100,150,Short.MAX_VALUE)
                            .addContainerGap(0,Short.MAX_VALUE)
                            .addGroup(
                                    layout.createParallelGroup().addComponent(bDodaj).addComponent(bUsun).addComponent(bZip)
                    )
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup()
                            .addComponent(scrollPane, 100,150,Short.MAX_VALUE)
                            .addGroup(
                                    layout.createSequentialGroup().addComponent(bDodaj).addComponent(bUsun).addGap(5,40,Short.MAX_VALUE).addComponent(bZip)
                    )
            );

            this.getContentPane().setLayout(layout);
            this.pack();
            this.setDefaultCloseOperation(3);

        }

        private DefaultListModel modelListy = new DefaultListModel()
        {
            @Override
            public void addElement(Object object) {
                lista.add(object);
                super.addElement(((File)object).getName());
            }
            ArrayList lista = new ArrayList();
            public Object get(int index)
            {
                return lista.get(index);
            }

            public Object remove(int index) {
                lista.remove(index);
                return super.remove(index);
            }
        };
        private JList lista = new JList(modelListy);
        private JButton bDodaj;
        private JButton bUsun;
        private JButton bZip;
        private JMenuBar pasekMenu = new JMenuBar();
        private JFileChooser wybieracz = new JFileChooser();
        public static final int BUFFOR = 1024;


        private class Akcja extends AbstractAction
        {

            public Akcja(String nazwa, String opis, String Skrot)
            {
                this.putValue(Action.NAME, nazwa);
                this.putValue(Action.SHORT_DESCRIPTION, opis);
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(Skrot));
            }

            public Akcja(String nazwa, String opis, String Skrot, ImageIcon ikona)
            {
                this.putValue(Action.NAME, nazwa);
                this.putValue(Action.SHORT_DESCRIPTION, opis);
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(Skrot));
                this.putValue(Action.SMALL_ICON, ikona);
            }
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("Dodaj"))
                    dodajWpisyDoArchiwum();
                else if (e.getActionCommand().equals("Usuń"))
                    usuwanieWpisowZListy();
                else if (e.getActionCommand().equals("Zip"))
                    stworzArchiwumZip();
            }
        }
        private void dodajWpisyDoArchiwum()
        {

            wybieracz.setCurrentDirectory(new File(System.getProperty("user.dir")));
            wybieracz.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            wybieracz.setMultiSelectionEnabled(true);
            int tmp = wybieracz.showDialog(rootPane,"Dodaj do archiwum");

            if (tmp == 0 )
            {
                File[] sciezki = wybieracz.getSelectedFiles();

                for (int i = 0 ; i < sciezki.length ; i++)
                    if (!czyWpisSiePowtarza(sciezki[i].getPath()))
                        modelListy.addElement(sciezki[i]);
            }

        }

    private boolean czyWpisSiePowtarza(String testowanyWpis)
    {
        for (int i = 0 ; i < modelListy.getSize() ; i++)
            if(  ((File)modelListy.get(i)).getPath().equals(testowanyWpis))
                return true;
        return false;
    }

    private void usuwanieWpisowZListy()
    {
        int[] tmp = lista.getSelectedIndices();
        for (int i = 0; i < tmp.length ; i++)
            modelListy.remove(tmp[i] - i);
    }

    private void stworzArchiwumZip()
    {
        wybieracz.setCurrentDirectory(new File(System.getProperty("user.dir")));
        wybieracz.setSelectedFile(new File(System.getProperty("user.dir") + File.separator + "NowyZip.zip"));
        int tmp = wybieracz.showDialog(rootPane, "Kompresuj");

        if (tmp == JFileChooser.APPROVE_OPTION )
        {
            byte tmpData[] = new byte[BUFFOR];
            try {
                ZipOutputStream zOutS = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(wybieracz.getSelectedFile()), BUFFOR));

                for (int i = 0; i < modelListy.getSize(); i++)
                {
                    if(!((File)modelListy.get(i)).isDirectory())
                    zipuj(zOutS, (File)modelListy.get(i),tmpData,((File)modelListy.get(i)).getPath());
                    else
                    {
                        wypiszSciezki((File)modelListy.get(i));

                        for (int j = 0 ; j < listaSciezek.size(); j++)
                            zipuj(zOutS, (File)listaSciezek.get(j), tmpData,((File)modelListy.get(i)).getPath());
                        listaSciezek.removeAll(listaSciezek);
                    }

                }

                zOutS.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void zipuj(ZipOutputStream zOutS, File sciezkaPliku, byte[] tmpData, String sciezkaBazowa)
            throws IOException
    {
        BufferedInputStream inS = new BufferedInputStream(new FileInputStream(sciezkaPliku), BUFFOR);

        zOutS.putNextEntry(new ZipEntry(sciezkaPliku.getPath().substring(sciezkaBazowa.lastIndexOf(File.separator)+1)));


        int counter;
        while ((counter = inS.read(tmpData, 0, BUFFOR)) != -1)
            zOutS.write(tmpData, 0, counter);


        zOutS.closeEntry();

        inS.close();
    }

    private void wypiszSciezki(File nazwaSciezki)
    {
        String[] nazwyPlikowiKatalogow = nazwaSciezki.list();

        for (int i = 0 ; i < nazwyPlikowiKatalogow.length ; i ++)
        {
            File p = new File(nazwaSciezki.getPath(), nazwyPlikowiKatalogow[i]);

                    if (p.isFile())
                        listaSciezek.add(p);

                    if (p.isDirectory())
                        wypiszSciezki(new File(p.getPath()));

        }
    }
    ArrayList listaSciezek = new ArrayList();
}
