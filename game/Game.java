package game;

import actor.Player;

public class Game {
    private static GUI gui;
    private static MainMenu menu;
    private static boolean paused;


    /**
     * Our main function
     * @param args
     */
    public static void main(String[] args) {
        gui = new GUI();
        menu = new MainMenu();
    }

    public static Player getPlayer() {
        // TODO Auto-generated method stub
        return null;
    }

    public static boolean isPaused() {
        // TODO Auto-generated method stub
        return false;
    }

    public static void quitToMenu() {
        // TODO Auto-generated method stub
        
    }

    public static void togglePause() {
        // TODO Auto-generated method stub
        
    }

    public static void init() {
        // TODO Auto-generated method stub
        graphics.Model.loadModels();
        sound.SoundEffect.init(false);
        graphics.particles.ParticleSystem.init(true);
        graphics.OnscreenMessage.init();
        
    }

    public static void update() {
        // TODO Auto-generated method stub
        
    }

    public static void dispose() {
        // TODO Auto-generated method stub
        
    }

    public static boolean isStarted() {
        // TODO Auto-generated method stub
        return false;
    }

    public static void showGame() {
        menu.dispose();
        menu = null;
        gui.setVisible(true);
        
    }

    public static void showHighScores() {
        // TODO Auto-generated method stub
        
    }

}
