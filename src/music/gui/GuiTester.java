package music.gui;

import java.awt.EventQueue;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JToolBar;
import java.awt.Component;
import javax.swing.Box;

import music.Mixer;
import music.Piece;
import music.Theme;
import music.Mixer.ThemeEnum;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuiTester {
    private JFrame frame;

    private String curPath = ".";

    private Mixer mixer;
    private Theme theme;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    public ConsoleWindow console = new ConsoleWindow();
    private MutableList songList;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GuiTester window = new GuiTester();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GuiTester() {
        initialize();

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                console.align(frame);
            }
            public void componentMoved(ComponentEvent e) {
                console.align(frame);
            }
        });
        frame.setBounds(100, 100, 600, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Song Previewer");
        console.align(frame);

        JButton btnPlay = new JButton("Play");
        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                play();
            }
        });
        btnPlay.setVerticalAlignment(SwingConstants.TOP);
        frame.getContentPane().add(btnPlay, BorderLayout.SOUTH);

        JToolBar toolBar = new JToolBar();
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);

        JButton btnAddSong = new JButton("Add Song");
        btnAddSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                browseForSong();
            }
        });
        toolBar.add(btnAddSong);

        JButton btnClearSongs = new JButton("Clear Songs");
        btnClearSongs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                pieces.clear();
                update();
                console.print("cleared selection");
            }
        });
        toolBar.add(btnClearSongs);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut);

        JButton btnOpenPlaylist = new JButton("Open Playlist");
        toolBar.add(btnOpenPlaylist);

        JButton btnSavePlaylist = new JButton("Save Playlist");
        toolBar.add(btnSavePlaylist);

        songList = new MutableList();

        frame.getContentPane().add(songList, BorderLayout.CENTER);
    }

    private void browseForSong(){
        JFileChooser chooser = new JFileChooser(new File(this.curPath));
        File selectedSong;
        Piece piece;

        int status = chooser.showOpenDialog(frame);
        if(status==JFileChooser.APPROVE_OPTION){
            selectedSong = chooser.getSelectedFile();
            if(selectedSong.isFile()){
                this.curPath = selectedSong.getParent();
                try{
                    piece = new Piece(selectedSong.getAbsolutePath(),selectedSong.getName());
                    pieces.add(piece);
                    

                    console.print("Successfully loaded "+selectedSong.getName());
                    update();
                }
                catch(UnsupportedAudioFileException e){
                    console.print("Could not parse file as audio");
                }
                catch(IOException e){
                    console.print("Could not find the file");
                }
            }
        }
        else{
            console.print("You did not choose a file.");
            return;
        }

    }

    public void update(){
        //rebuild the mixer
        theme = new Theme(pieces);
        Map<ThemeEnum,Theme> themeMap = new HashMap<ThemeEnum,Theme>();
        themeMap.put(ThemeEnum.FIGHT, theme);
        mixer = new Mixer(themeMap);
        
        //update the list
        songList.getContents().clear();
        for(Piece p:pieces){
            songList.getContents().addElement(p.getName());
        }
    }
    
    public void play(){
        mixer.change(ThemeEnum.FIGHT);
        mixer.run();
    }
}
