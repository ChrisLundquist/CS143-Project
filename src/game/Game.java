package game;
 
import input.InputHandler;
import actor.Player;

public class Game { 

    private static graphics.Renderer renderer;
    private static input.InputHandler input;
    private static boolean paused;
    private static Player player;
    
    public static void main(String[] args) {
        player = new Player();
        renderer = new graphics.Renderer();
        input = new InputHandler();
        renderer.start();
      
    }
    
    public static InputHandler getInputHandler(){
        return input;
    }

    public static Player getPlayer() {
        return player;
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

    public static void exit() {
        System.err.println("Exiting");
        // FIXME This generates an exception when some threaded callback tries
        //       to redraw the window
        renderer.exit();
    }

}