package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * Main menu, the OpenGL portion of the game will be started from here
 * @author tim
 *
 */
public class MainMenu extends JPanel implements ActionListener {
    private static final long serialVersionUID = -3569270152514757138L;
    //background image
    BufferedImage background;
    int y, x;
    //paths to all images used
    
    private static final String BACKGROUND1_PATH = "assets/images/mainmenu/background1.jpg";
    private static final String BACKGROUND2_PATH = "assets/images/mainmenu/background2.jpg";
    private static final String BACKGROUND3_PATH = "assets/images/mainmenu/background3.jpg";
    private static final String BACKGROUND4_PATH = "assets/images/mainmenu/background4.jpg";
    private static final String BACKGROUND5_PATH = "assets/images/mainmenu/background5.jpg";
    ImageIcon play, joinGame,joinGame_selected, play_selected, settings, settings_selected, quit, quit_selected;
    

    private static final String PLAY_PATH  = "assets/images/play.png";
    private static final String JOINGAME_PATH = "assets/images/joingame.png";
    private static final String SETTINGS_PATH = "assets/images/settings.png";
    private static final String QUIT_PATH = "assets/images/quit.png";
    
    private static final String PLAY_PATH_SELECTED  = "assets/images/play_selected.png";
    private static final String JOINGAME_PATH_SELECTED = "assets/images/joingame_selected.png";
    private static final String SETTINGS_PATH_SELECTED = "assets/images/settings_selected.png";
    private static final String QUIT_PATH_SELECTED = "assets/images/quit_selected.png";

    //buttons
    JButton playButton, joinGameButton, settingsButton, quitButton, backButton;
    JCheckBox enableController, enableSound, enableParticles;
    public boolean controllerEnabled = false;
    public boolean soundEnabled = true;
    public boolean particlesEnabled = true;
    public boolean menuVisible;

    public MainMenu() throws IOException {        
        //loads all images from assets/images/
        try {
            background = ImageIO.read(new File(getRandomImage()));
            play = new ImageIcon(PLAY_PATH);
            joinGame = new ImageIcon(JOINGAME_PATH);
            settings = new ImageIcon(SETTINGS_PATH);
            quit = new ImageIcon(QUIT_PATH);
            
            play_selected = new ImageIcon(PLAY_PATH_SELECTED);
            joinGame_selected = new ImageIcon(JOINGAME_PATH_SELECTED);
            settings_selected = new ImageIcon(SETTINGS_PATH_SELECTED);
            quit_selected = new ImageIcon(QUIT_PATH_SELECTED);
        } catch (IOException e) {
            System.out.println("Can't find image in assets");
            e.printStackTrace();
        }

        //creates new frame
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.setSize(900,900);

        //adds gui elements
        createGUI();

        //sets the menu visibility as true
        f.setVisible(true);
        menuVisible = true;
    }
    public String getRandomImage() {
        int random = (int)(5*Math.random())+1;
        switch(random) {
            case 1:
                return BACKGROUND1_PATH;
            case 2:
                return BACKGROUND2_PATH;
            case 3:
                return BACKGROUND3_PATH;
            case 4:
                return BACKGROUND4_PATH;
            case 5:
                return BACKGROUND5_PATH;
        }
        return null;
    }
    /**
     * creates and adds gui components
     * 
     */
    private void createGUI() {
        this.setLayout(null);

        playButton = new JButton(play);   
        playButton.setOpaque(false);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorder(null);
        playButton.setRolloverIcon(play_selected);
        playButton.addActionListener(this);
        playButton.setBounds(50,50,100,50);

        joinGameButton = new JButton(joinGame);
        joinGameButton.addActionListener(this);
        joinGameButton.setOpaque(false);
        joinGameButton.setBorderPainted(false);
        joinGameButton.setContentAreaFilled(false);
        joinGameButton.setBorder(null);
        joinGameButton.setRolloverIcon(joinGame_selected);
        joinGameButton.addActionListener(this);
        joinGameButton.setBounds(50,100,230,50);

        settingsButton = new JButton(settings);
        settingsButton.setOpaque(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setBorder(null);
        settingsButton.setRolloverIcon(settings_selected);
        settingsButton.addActionListener(this);
        settingsButton.setBounds(50, 150, 180, 50);

        quitButton = new JButton(quit);
        quitButton.setOpaque(false);
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorder(null);
        quitButton.setRolloverIcon(quit_selected);
        quitButton.addActionListener(this);
   
     
        
        enableController = new JCheckBox(play, controllerEnabled);
        enableController.setOpaque(false);
        enableController.setBorderPainted(false);
        enableController.setContentAreaFilled(false);
        enableController.setBorder(null);
        enableController.setRolloverIcon(play_selected);
        enableController.addActionListener(this);
        enableController.setVisible(false);
        enableController.setBounds(50, 50, 100, 50);

        enableSound = new JCheckBox("Enable Sound", soundEnabled);
        enableSound.setVisible(false);
        enableSound.addActionListener(this);
        enableSound.setBounds(50, 100, 100, 50);

        enableParticles = new JCheckBox("Enable Particles", particlesEnabled);
        enableParticles.setVisible(false);
        enableParticles.addActionListener(this);
        enableParticles.setBounds(50, 150, 100, 50);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setVisible(false);
        backButton.setBounds(50, 200, 100, 50);

        add(playButton);
        add(joinGameButton);
        add(settingsButton);
        add(enableController);
        add(enableSound);
        add(enableParticles);
        add(quitButton);
        add(backButton);
    }
    /**
     * Paints the background image to the panel
     * It is scaled in relation to the screen size
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        //scales image
        int w = getWidth();
        int h = getHeight();
        int iw = background.getWidth();
        int ih = background.getHeight();
        double xScale = (double)w/iw;
        double yScale = (double)h/ih;
        double scale = //Math.min(xScale, yScale);    // scale to fit
            Math.max(xScale, yScale);  // scale to fill
        int  width = (int)(scale*iw);
        int  height = (int)(scale*ih);
        x = (w - width)/2;
        y = (h - height)/2;
        //paints image
        g2.drawImage(background, x, y, width, height, this);
        g2.drawImage(background, x, y, width, height, this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playButton) {
            Game.init();
            this.setVisible(false);
            menuVisible = false;
            Game.start();
        }
        if(e.getSource() == joinGameButton) {
            // TODO a better way to populate the server list and an option to enter a custom address
            String[] servers = {"localhost", "openspace.overthere.org"};
            String server = (String)JOptionPane.showInputDialog(this,
                    "Select a server to join",
                    "Join a Network Game",
                    JOptionPane.PLAIN_MESSAGE,
                    null, // Icon
                    servers,
                    null // initial value
            );

            if (server == null) // user hit cancel
                return;

            Game.joinServer(server);
            this.setVisible(false);
            menuVisible = false;
            Game.start(); 
        }
        if(e.getSource() == settingsButton) {
            playButton.setVisible(false);
            settingsButton.setVisible(false);
            quitButton.setVisible(false);
            joinGameButton.setVisible(false);
            enableController.setVisible(true);
            enableSound.setVisible(true);
            enableParticles.setVisible(true);
            backButton.setVisible(true);

        }
       
        if(e.getSource() == enableController) {
            if(controllerEnabled == true) {
                controllerEnabled = false;
                enableController.setEnabled(controllerEnabled);
            }
            if(controllerEnabled == false) {
                controllerEnabled = true;
                enableController.setEnabled(controllerEnabled);
            }

        }
        if(e.getSource() == enableSound) {
            if(soundEnabled == true) {
                soundEnabled = false;
                enableSound.setEnabled(soundEnabled);
            }
            if(soundEnabled == false) {
                soundEnabled = true;
                enableSound.setEnabled(soundEnabled);
            }


        }
        if(e.getSource() == enableParticles) {
            if(particlesEnabled == true) {
                particlesEnabled = false;
                enableParticles.setEnabled(particlesEnabled);
            }
            if(particlesEnabled == false) {
                particlesEnabled = true;
                enableParticles.setEnabled(particlesEnabled);
            }

        }
        if(e.getSource() == backButton) {
            playButton.setVisible(true);
            settingsButton.setVisible(true);
            quitButton.setVisible(true);
            joinGameButton.setVisible(true);
            enableController.setVisible(false);
            enableSound.setVisible(false);
            enableParticles.setVisible(false);
            backButton.setVisible(false);
        }
        if(e.getSource() == quitButton) {
            System.exit(0);
        }

    }
    public static void main (String []args) throws IOException {
        @SuppressWarnings("unused")
        MainMenu menu = new MainMenu();
        //Game.init();
        //Game.start(); 
    }
}