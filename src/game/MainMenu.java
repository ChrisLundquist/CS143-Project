package game;

import game.Player.ShipType;

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

    private static final String BACKGROUND_PATHS[] = {
        "assets/images/mainmenu/background1.jpg",
        "assets/images/mainmenu/background2.jpg",
        "assets/images/mainmenu/background3.jpg",
        "assets/images/mainmenu/background4.jpg",
        "assets/images/mainmenu/background5.jpg",
    };

    ImageIcon play, joinGame,joinGame_selected, play_selected, settings, settings_selected, quit, quit_selected;
    ImageIcon particles_on, particles_off, shaders_on, shaders_off, sound_on, sound_off, controller_on,
    controller_off, back, back_selected;

    private static final String[] NETWORK_SERVERS = {"openspace.overthere.org", "localhost"};

    private static final String PLAY_PATH  = "assets/images/mainmenu/start.png";
    private static final String JOINGAME_PATH = "assets/images/mainmenu/joingame.png";
    private static final String SETTINGS_PATH = "assets/images/mainmenu/settings.png";
    private static final String QUIT_PATH = "assets/images/mainmenu/quit.png";

    private static final String PLAY_PATH_SELECTED  = "assets/images/mainmenu/start_selected.png";
    private static final String JOINGAME_PATH_SELECTED = "assets/images/mainmenu/joingame_selected.png";
    private static final String SETTINGS_PATH_SELECTED = "assets/images/mainmenu/settings_selected.png";
    private static final String QUIT_PATH_SELECTED = "assets/images/mainmenu/quit_selected.png";

    private static final String CONTROLLER_ON = "assets/images/mainmenu/controller_on.png";
    private static final String CONTROLLER_OFF = "assets/images/mainmenu/controller_off.png";

    private static final String SOUND_ON = "assets/images/mainmenu/sound_checked.png";
    private static final String SOUND_OFF = "assets/images/mainmenu/sound_unchecked.png";

    private static final String SHADERS_ON = "assets/images/mainmenu/shaders_checked.png";
    private static final String SHADERS_OFF = "assets/images/mainmenu/shaders_unchecked.png";

    private static final String PARTICLES_ON = "assets/images/mainmenu/particles_checked.png";
    private static final String PARTICLES_OFF = "assets/images/mainmenu/particles_unchecked.png";

    private static final String BACK = "assets/images/mainmenu/back.png";
    private static final String BACK_SELECTED = "assets/images/mainmenu/back_selected.png";

    //buttons
    JButton playButton, joinGameButton, settingsButton, quitButton, backButton;
    JCheckBox enableController, enableSound, enableShaders, enableParticles;

    public boolean controllerEnabled = false;
    public boolean soundEnabled = true;
    public boolean particlesEnabled = true;
    public boolean shadersEnabled = true;
    public boolean menuVisible;

    public MainMenu() throws IOException {        
        //loads all images from assets/images/mainmenu
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

            controller_on = new ImageIcon(CONTROLLER_ON);
            controller_off = new ImageIcon(CONTROLLER_OFF);
            sound_on = new ImageIcon(SOUND_ON);
            sound_off = new ImageIcon(SOUND_OFF);
            shaders_on = new ImageIcon(SHADERS_ON);
            shaders_off = new ImageIcon(SHADERS_OFF);
            particles_on = new ImageIcon(PARTICLES_ON);
            particles_off = new ImageIcon(PARTICLES_OFF);
            back = new ImageIcon(BACK);
            back_selected = new ImageIcon(BACK_SELECTED);

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
        java.util.Random rand = new java.util.Random();

        return BACKGROUND_PATHS[rand.nextInt(BACKGROUND_PATHS.length)];
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
        playButton.setBounds(50,50,200,50);

        joinGameButton = new JButton(joinGame);
        joinGameButton.addActionListener(this);
        joinGameButton.setOpaque(false);
        joinGameButton.setBorderPainted(false);
        joinGameButton.setContentAreaFilled(false);
        joinGameButton.setBorder(null);
        joinGameButton.setRolloverIcon(joinGame_selected);
        joinGameButton.addActionListener(this);
        joinGameButton.setBounds(50,100,200,50);

        settingsButton = new JButton(settings);
        settingsButton.setOpaque(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setBorder(null);
        settingsButton.setRolloverIcon(settings_selected);
        settingsButton.addActionListener(this);
        settingsButton.setBounds(50, 150, 200, 50);

        quitButton = new JButton(quit);
        quitButton.setOpaque(false);
        quitButton.setBorderPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorder(null);
        quitButton.setRolloverIcon(quit_selected);
        quitButton.addActionListener(this);
        quitButton.setBounds(50, 200, 200, 50);



        enableController = new JCheckBox();
        if(controllerEnabled == true) {
            enableController.setIcon(controller_on);
        }
        else {
            enableController.setIcon(controller_off);
        }
        enableController.setOpaque(false);
        enableController.setBorderPainted(false);
        enableController.setContentAreaFilled(false);
        enableController.setBorder(null);
        enableController.addActionListener(this);
        enableController.setVisible(false);
        enableController.setBounds(50, 50, 100, 50);

        enableSound = new JCheckBox();
        if(soundEnabled == true) {
            enableSound.setIcon(sound_on);
        }
        else {
            enableSound.setIcon(sound_off);
        }
        enableSound.setOpaque(false);
        enableSound.setBorderPainted(false);
        enableSound.setContentAreaFilled(false);
        enableSound.setBorder(null);
        enableSound.addActionListener(this);
        enableSound.setVisible(false);
        enableSound.setBounds(50, 100, 100, 50);

        enableShaders = new JCheckBox();
        if(shadersEnabled == true) {
            enableShaders.setIcon(shaders_on);
        }
        else {
            enableShaders.setIcon(shaders_off);
        }
        enableShaders.setOpaque(false);
        enableShaders.setBorderPainted(false);
        enableShaders.setContentAreaFilled(false);
        enableShaders.setBorder(null);
        enableShaders.addActionListener(this);
        enableShaders.setVisible(false);
        enableShaders.setBounds(50, 150, 100, 50);

        enableParticles = new JCheckBox();
        if(particlesEnabled == true) {
            enableParticles.setIcon(particles_on);
        }
        else {
            enableParticles.setIcon(particles_off);
        }
        enableParticles.setOpaque(false);
        enableParticles.setBorderPainted(false);
        enableParticles.setContentAreaFilled(false);
        enableParticles.setBorder(null);
        enableParticles.addActionListener(this);
        enableParticles.setVisible(false);
        enableParticles.setBounds(50, 200, 100, 50);

        backButton = new JButton(back);
        backButton.setVisible(false);
        backButton.addActionListener(this);
        backButton.setOpaque(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorder(null);
        backButton.setRolloverIcon(back_selected);
        backButton.addActionListener(this);
        backButton.setBounds(50,250,230,50);


        add(playButton);
        add(joinGameButton);
        add(settingsButton);
        add(enableController);
        add(enableSound);
        add(enableShaders);
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
            // Do not join game once a game is already in progress
            if (game.Game.getActors() != null)
                return;

            JLabel serverLabel = new JLabel("Select a server to join");
            JComboBox serverInput = new JComboBox(NETWORK_SERVERS);
            serverInput.setEditable(true);

            JLabel playerLabel = new JLabel("Player Name");
            JTextField playerInput = new JTextField(Player.DEFAULT_NAME);

            JLabel shipLabel = new JLabel("Select a ship");
            JComboBox shipInput = new JComboBox(ShipType.values());

            JPanel panel = new JPanel();
            GroupLayout layout = new GroupLayout(panel);
            panel.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(serverLabel)
                            .addComponent(serverInput)
                            .addComponent(playerLabel)
                            .addComponent(playerInput)
                            .addComponent(shipLabel)
                            .addComponent(shipInput)
                    )
            );
            layout.setVerticalGroup(
                    layout.createSequentialGroup()
                    .addComponent(serverLabel)
                    .addComponent(serverInput)
                    .addComponent(playerLabel)
                    .addComponent(playerInput)
                    .addComponent(shipLabel)
                    .addComponent(shipInput)
            );

            int choice = JOptionPane.showConfirmDialog(this, panel, "Join a Network Game", JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION)
                return;

            String server = (String) serverInput.getSelectedItem();
            String playerName = playerInput.getText();
            ShipType ship = (ShipType) shipInput.getSelectedItem();

            if (Game.joinServer(server, playerName, ship) == false)
                return;

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
            enableShaders.setVisible(true);
            enableParticles.setVisible(true);
            backButton.setVisible(true);

        }

        if(e.getSource() == enableController) {
            if(controllerEnabled == true) {
                controllerEnabled = false;
                enableController.setEnabled(controllerEnabled);
                enableController.setIcon(controller_off);
            }
            if(controllerEnabled == false) {
                controllerEnabled = true;
                enableController.setEnabled(controllerEnabled);
                enableController.setIcon(controller_on);
            }

        }
        if(e.getSource() == enableSound) {
            if(soundEnabled == true) {
                soundEnabled = false;
                enableSound.setEnabled(soundEnabled);
                enableSound.setIcon(sound_off);
            }
            if(soundEnabled == false) {
                soundEnabled = true;
                enableSound.setEnabled(soundEnabled);
                enableSound.setIcon(sound_on);
            }


        }
        if(e.getSource() == enableShaders) {
            if(shadersEnabled == true) {
                shadersEnabled = false;
                enableShaders.setEnabled(shadersEnabled);
                enableShaders.setIcon(shaders_off);
            }
            if(shadersEnabled == false) {
                shadersEnabled = true;
                enableShaders.setEnabled(shadersEnabled);
                enableShaders.setIcon(shaders_on);
            }


        }
        if(e.getSource() == enableParticles) {
            if(particlesEnabled == true) {
                particlesEnabled = false;
                enableParticles.setEnabled(particlesEnabled);
                enableParticles.setIcon(particles_off);
            }
            if(particlesEnabled == false) {
                particlesEnabled = true;
                enableParticles.setEnabled(particlesEnabled);
                enableParticles.setIcon(particles_on);
            }

        }
        if(e.getSource() == backButton) {
            playButton.setVisible(true);
            settingsButton.setVisible(true);
            quitButton.setVisible(true);
            joinGameButton.setVisible(true);
            enableController.setVisible(false);
            enableSound.setVisible(false);
            enableShaders.setVisible(false);
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