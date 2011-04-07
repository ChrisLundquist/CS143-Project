package sound.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsoleWindow extends JFrame {
    private JTextArea textConsole = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textConsole);
    public ConsoleWindow()
    {
        textConsole.setEditable(false);
        this.add(scrollPane,BorderLayout.CENTER);
        
        this.print("Console up and running");
        
        //don't mess with this stuff
        
        this.setSize(250, 400);
        this.setMinimumSize(new Dimension(250,400));
        this.setTitle("Console");
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
              System.exit(0);
            }
        });
    }
    
    public void print(Object o){
        textConsole.setText(textConsole.getText()+o.toString()+"\n");
    }
    public void clear(){
        textConsole.setText("");
    }
    
    public void align(JFrame parent){
        this.setLocation(parent.getX()+parent.getWidth(), parent.getY());
    }
    public void hack(){
        this.setSize(this.getWidth()+1, this.getHeight());
    }
}
