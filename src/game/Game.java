package game;
 
import input.InputHandler;

public class Game { 

    private static graphics.Renderer renderer;
    private static input.InputHandler input;
    private static boolean paused;
    private static Player player;
   
    public static void init(){
        player = new Player();

        renderer = new graphics.Renderer();
        input = new InputHandler();
        graphics.Model.loadModels();
        
        actor.Asteroid a = new actor.Asteroid();
        a.setPosition(new math.Vector3(0.0f,0.0f,-10.0f));
        actor.Actor.actors.add(a);
    }
    
    public static void start(){
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
        System.exit(0);
    }
}