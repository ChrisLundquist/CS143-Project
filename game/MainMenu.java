package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This is the 2nd Window(Main menu) with the Start Game & how to play & high score in it. A class within a class.
 * @author GUI team
 */
public class MainMenu extends JFrame {
    private static final long serialVersionUID = -6930053717837454204L;

    //These are the fields
    private JPanel titlePanel;
    private JPanel buttonsPanel;
    private JButton highScores;
    private JButton startGame;
    private JButton settings;
    private JButton howToPlay;
    private JButton quitButton;
    private JLabel title;
    private JLabel imageLabel;

    //constructor
    public MainMenu() {
        setLayout(new BorderLayout());

        ImageIcon titleImage = new ImageIcon("data/title.png");
        imageLabel = new JLabel();
        imageLabel.setIcon(titleImage);

        buttonsPanel = new JPanel();
        titlePanel = new JPanel();


        title = new JLabel();
        title.setIcon(titleImage);

        JPanel blackSpace = new JPanel();

        add(titlePanel, BorderLayout.NORTH);
        add(blackSpace, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.CENTER);
        add(blackSpace, BorderLayout.SOUTH);
        add(blackSpace, BorderLayout.AFTER_LAST_LINE);


        startGame = new JButton(Game.isStarted() ? "Resume Game" : "Start Game");
        howToPlay = new JButton("How to play");
        settings = new JButton("Settings");
        highScores = new JButton("High scores");
        quitButton = new JButton("Quit");

        startGame.addActionListener(new StartGameListener());
        howToPlay.addActionListener(new HowToPlayListener());
        settings.addActionListener(new SettingsListener());
        highScores.addActionListener(new HighScoresListener());
        quitButton.addActionListener(new QuitButtonListener());

        titlePanel.add(title);
        buttonsPanel.add(startGame);
        buttonsPanel.add(howToPlay);
        buttonsPanel.add(settings);
        buttonsPanel.add(highScores);
        buttonsPanel.add(quitButton);

        GUI.colorize(buttonsPanel);
        GUI.colorize(titlePanel);
        GUI.colorize(blackSpace);

        pack();
        setVisible(true);
    }

    private class StartGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Game.showGame();
        }
    }

    private class HowToPlayListener implements ActionListener {
        public void actionPerformed(ActionEvent f) {
            String instructions= "<html>" +
            "<body>" +
            "<table>" +
            "<tr><th colspan=2>How to PLAY</th></tr>" +
            "<tr><td>pause game</td><td>pause or p</td></tr>" +
            "<tr><td>menu</td><td>escape or q</td></tr>" +
            "<tr><td>forward thrusters</td><td>up arrow</td></tr>" +
            "<tr><td>reverse thrusters</td><td>down arrow</td></tr>" +
            "<tr><td>turn left</td><td>left arrow</td></tr>" +
            "<tr><td>turn right</td><td>right arrow</td></tr>" +
            "<tr><td>emergency stop</td><td>up and down arrows</td></tr>" +
            "<tr><td>flip 180</td><td>s</td></tr>" +
            "<tr><td>warp</td><td>w</td></tr>" +
            "</table>" +
            "</body>" +
            "</html>";
            JOptionPane.showMessageDialog(null, instructions);
        }
    }

    private class SettingsListener implements ActionListener {
        public void actionPerformed(ActionEvent f) {
            new Settings();
        }
    }

    private class HighScoresListener implements ActionListener {
        public void actionPerformed(ActionEvent f) {
            Game.showHighScores();
        }
    }

    private class QuitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent f) {
            System.exit(0);
        }
    }
}
