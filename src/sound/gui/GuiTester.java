package sound.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import sound.Piece;
import sound.Theme;



public class GuiTester extends JFrame {   

    private String[] nameList = new String[0];
    private File[] currentFiles;

    private Theme playedTheme = new Theme(new ArrayList<Piece>());
    
    private final JButton addSongBtn = new JButton("Select Files");
    private JList songList = new JList(nameList);
    private final JPanel bottomPanel = new JPanel();
    
    private final JFileChooser fc = new JFileChooser();

    private ConsoleWindow cw;
    private Thread themeThread;
    
    public GuiTester(){
        cw = new ConsoleWindow();
        
        addSongBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
              addSong();
            }
          });
        this.add(addSongBtn,BorderLayout.NORTH);

        this.add(songList,BorderLayout.CENTER);
        
        
            JButton playBtn = new JButton("Play");
            playBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    play();
                  }
                });
            bottomPanel.add(playBtn,BorderLayout.WEST);
            
            JButton stopBtn = new JButton("Stop");
            stopBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    themeThread.stop();                    
                }});
            bottomPanel.add(stopBtn,BorderLayout.EAST);
        this.add(bottomPanel,BorderLayout.SOUTH);
        
        //Don't mess with this stuff.
        this.setSize(250, 400);
        this.setMinimumSize(new Dimension(250,400));
        this.setTitle("Song Player");
        cw.align(this);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
              System.exit(0);
            }
          });

    }
    
    public void addSong(){
        fc.setCurrentDirectory(new File("."));
        fc.setMultiSelectionEnabled(true);
        int status =  fc.showOpenDialog(this);
        
        if(status == JFileChooser.APPROVE_OPTION){

            this.currentFiles = fc.getSelectedFiles();
            String[] names = new String[this.currentFiles.length];
            for(int i = 0;i<names.length;i++){
                names[i] = this.currentFiles[i].getName();
                cw.print("Added Song: "+names[i]);
            }
            this.remove(songList);
            this.songList = new JList(names);
            this.add(songList);
            hack();
        }
    }
    
    public void play(){
        cw.print("Making new theme");
        for(File f: this.currentFiles){
            if(f.getName().endsWith(".wav")){
                playedTheme.addPiece(new Piece(f.getPath()));
                cw.print("Added "+f.getName()+" to the theme");
            }
            else{
                cw.print("Could not add"+f.getName()+".  Not a wav file");
            }
        }
        this.themeThread.start();
    }
    
    public void hack(){
        this.setSize(this.getWidth()+1, this.getHeight());
    }
    
    public static void main(String args[]) {
        new GuiTester();
    }
    
}
