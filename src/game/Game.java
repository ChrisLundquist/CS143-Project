package game;
 
import actor.Player;

 
/**
 * ported to JOGL 2.0 by Julien Gouesse (http://tuer.sourceforge.net)
 */
public class Game { 

    private static graphics.Renderer renderer;
    
    public static void main(String[] args) {
        renderer = new graphics.Renderer();
        renderer.start();
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

    public static void exit() {
        renderer.exit();
    }

}