package com.company;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notebook extends JFrame
{
    Notebook()
    {
        this.setTitle ("NoteBook");

        int frameWidth = width/2;
        int frameHeight = height/2;

        this.setBounds((width - frameWidth)/2,(height - frameHeight)/2, frameWidth,frameHeight);

        initComponents();

        this.setDefaultCloseOperation(3);
}
    private void initComponents()
    {
        this.setJMenuBar(menuBar);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());


        menuOpen = menuFile.add(new JMenuItem("Open"));
        menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
        menuOpen.addActionListener(e -> {
            try
            {
                addFile();
            }

            catch (IOException ex)
            {
                System.out.println(ex.getMessage());;
            }
        });

        menuSave = menuFile.add(new JMenuItem("Save"));
        menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
        menuSave.setEnabled(false);
        menuSave.addActionListener(e ->
        {
            try
            {

                saveFile();

            }
            catch (IOException ex)
            {
                System.out.println(ex.getMessage());;
            }
        });

        menuSaveAs = menuFile.add(new JMenuItem("Save As"));
        menuSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_DOWN_MASK));
        menuSaveAs.addActionListener(e ->
        {
            try
            {

                saveFileAs();

            }
            catch (IOException ex)
            {

                System.out.println(ex.getMessage());;

            }
        });
        panel.add(scrollPane,BorderLayout.CENTER);

        this.getContentPane().add(panel,BorderLayout.LINE_START);

        Filters();


        this.pack();

    }
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuSaveAs;

    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = menuBar.add(new JMenu("File"));

    private TextArea textArea = new TextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);

    private JPanel panel = new JPanel();


    private static File path = null;
    JFileChooser fileChooser = new JFileChooser();

    private void addFile() throws IOException {

            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int tmp = fileChooser.showOpenDialog(rootPane);

            if (tmp == 0)
            {
                path = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(path.getPath()));

                String text;
                textArea.selectAll();
                textArea.replaceRange("",0,textArea.getSelectionEnd());

                while(!((text = reader.readLine()) == null))
                {
                    textArea.append(text);
                    textArea.append("\n");
                }

                menuSave.setEnabled(true);
                reader.close();
            }

    }


    private void saveFile()
            throws IOException
    {
        PrintWriter writer = new PrintWriter(new FileWriter(path.getPath()));
        writer.print(textArea.getText());
        writer.close();
        System.out.println("SAVED");
    }

    private void saveFileAs()
            throws IOException
    {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int tmp = fileChooser.showDialog(rootPane, "Save as");

        if (tmp == 0)
        {

                path = fileChooser.getSelectedFile();

                if (new File(path.getPath()).exists())
                {
                    PrintWriter writer = new PrintWriter(new FileWriter(path.getPath()));
                    int result  = JOptionPane.showConfirmDialog(this,"The File exists, are you sure to overwrite it?");

                    switch (result)
                    {

                        case JOptionPane.YES_OPTION:
                            writer.print(textArea.getText());
                            menuSave.setEnabled(true);
                            writer.close();
                            return;

                        case JOptionPane.CANCEL_OPTION:
                            return;

                        case JOptionPane.NO_OPTION:
                            return;
                    }
                }

                else
                    {
                        PrintWriter writer = new PrintWriter(new FileWriter(path.getPath()));
                        writer.print(textArea.getText());
                        writer.close();
                        menuSave.setEnabled(true);
                        return;
                    }
        }
    }


    private void Filters()
    {
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files","txt"));
    }


}
