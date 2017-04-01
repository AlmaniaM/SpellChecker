package edu.wou.cs260.SpellChecker;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class Console implements Runnable
{
    TextArea displayPane;
    BufferedReader reader;

    private Console(TextArea displayPane, PipedOutputStream pos)
    {
        this.displayPane = displayPane;

        try
        {
            PipedInputStream pis = new PipedInputStream( pos );
            reader = new BufferedReader( new InputStreamReader(pis) );
        }
        catch(IOException e) {}
    }

    public void run()
    {
        String line = null;

        try
        {
            while ((line = reader.readLine()) != null)
            {
                displayPane.appendText( line + "\n" );
                displayPane.positionCaret( displayPane.getLength());
                try
                {
                    Thread.sleep(10);
                }
                catch(Exception e)
                {

                }
            }
        }
        catch (IOException ioe)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IOException error");
            alert.setHeaderText("Error Redirecting Output");
            alert.setContentText(ioe.getMessage());
        }
    }

    public static void redirectOutput(TextArea displayPane)
    {
        Console.redirectOut(displayPane);
        Console.redirectErr(displayPane);
    }

    public static void redirectOut(TextArea displayPane)
    {
        PipedOutputStream pos = new PipedOutputStream();
        System.setOut( new PrintStream(pos, true) );

        Console console = new Console(displayPane, pos);
        new Thread(console).start();
    }

    public static void redirectErr(TextArea displayPane)
    {
        PipedOutputStream pos = new PipedOutputStream();
        System.setErr( new PrintStream(pos, true) );

        Console console = new Console(displayPane, pos);
        new Thread(console).start();
    }
/*
    public static void main(String[] args)
    {
        TextArea textArea = new TextArea();
        JScrollPane scrollPane = new JScrollPane( textArea );

        JFrame frame = new JFrame("Redirect Output");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add( scrollPane );
        frame.setSize(200, 100);
        frame.setVisible(true);

        Console.redirectOutput( textArea );
        final int i = 0;

        Timer timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println( new java.util.Date().toString() );
                System.err.println( System.currentTimeMillis() );
            }
        });
        timer.start();
    }*/
}