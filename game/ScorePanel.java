package game;
import javax.swing.*;
import java.text.DecimalFormat;

public class ScorePanel extends JPanel {
    private static final long serialVersionUID = -3919165149509621102L;
    private static ScorePanel scorePanel; // there should be a better way to do this
    private static DecimalFormat decPlaces = new DecimalFormat("0");
    private int scoreMultiplyer =1;


    /*
     * There should be a better way to do this, but we need various parts
     * of the game to be able to access the score panel to update statistics.
     */
    public static ScorePanel getScorePanel() {
        return scorePanel;
    }


    private int frameCounter;
    private JLabel multiplyer;
    private JLabel score;
    private JLabel accurate;
    private JLabel asteroidNumber;
    private int scoreAmount;
    private int hitAmount;
    private int shotsMissed;

    public ScorePanel() {
        // Fix issue on MacOS where score panel is huge and ScenePanel is postage stamp sized
        // setPreferredSize(new Dimension(100, 500));

        multiplyer = new JLabel(" x"+scoreMultiplyer);
        accurate = new JLabel("Accuracy 0%");
        score = new JLabel("Score: " + scoreAmount);
        add(score);
        add(accurate);
        add(multiplyer);

        /* If this is the first ScorePanel register it as our ScorePanel - there should only be one */
        if (scorePanel == null)
            scorePanel = this;

        GUI.colorize(this);
    }
    public void bulletMissed() {
        shotsMissed ++ ;
    }

    /**
     * Update is called by Asteroids.update() every frame to redraw the scores
     */
    public void updateScorePanel() {
        // Update every 30 frames
        if (frameCounter > 0) {
            frameCounter --;
        } else {
            frameCounter = 30;

            score.setText("Score: " + scoreAmount);
            accurate.setText("Accuracy: " + decPlaces.format(getAccuracy()) + "%");
            multiplyer.setText(" x"+scoreMultiplyer);
        }
    }

    /*
     * helper method to return the accuracy and avoid dividing by zero
     */
    private double getAccuracy() {
        // avoiding divide by zero error
        if (hitAmount + shotsMissed == 0)
            return 0;

        // accuracy formula
        return ((double)hitAmount / (hitAmount + shotsMissed)) * 100;
    }


    public int getScore() {
        return scoreAmount;
    }


    public void reset() {
        scoreAmount = 0;
        hitAmount = 0;
        shotsMissed = 0;
    }
}
