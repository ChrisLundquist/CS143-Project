package game;
 
import java.io.IOException;
import java.net.UnknownHostException;

import input.InputHandler;

public class Game { 

    private static graphics.Renderer renderer;
    private static input.InputHandler input;
    private static boolean paused;
    private static Player player;
    private static Map map;
   
    public static void init(){
        map = Map.load("example_1");
        player = new Player();
        
        /*
         * To test networking uncomment this block
        try {
            new network.ClientServerThread(java.net.InetAddress.getLocalHost(), player).start();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        
        renderer = new graphics.Renderer();
        input = new InputHandler();
        graphics.Model.loadModels();
        
        actor.Asteroid a = new actor.Asteroid();
        a.setPosition(new math.Vector3(0.0f,0.0f,-10.0f));
        actor.Actor.addActor(a);
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

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map m) {
            map = m;
    }
}