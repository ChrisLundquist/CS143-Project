package game;

import actor.Player;

public class Game {
    private static GUI gui;
    private static MainMenu menu;
    private static boolean paused;
    private static boolean started; 
    // TODO CL - get rid of this. It is used by the main menu to change the verbage from "start" to "resume"


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
        return paused;
    }

    public static void quitToMenu() {
        // TODO Auto-generated method stub
        
    }

    public static void togglePause() {
        paused = !paused;
    }

    public static void init() {
        graphics.Model.loadModels();                  // Load our models
        sound.SoundEffect.init(false);                // Default to sound off
        graphics.particles.ParticleSystem.init(true); // default to particles on
        graphics.OnscreenMessage.init();              // Initialize Screen messages
    }

    public static void update() {
        // TODO Auto-generated method stub
        
    }

    public static void dispose() {
        // TODO Auto-generated method stub
        
    }

    public static boolean isStarted() {
        return started;
    }

    public static void showGame() {
        menu.dispose();
        menu = null;
        gui.setVisible(true);
        
    }

}
