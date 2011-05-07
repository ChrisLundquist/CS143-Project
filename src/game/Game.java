package game;

import input.KeyboardListener;
import java.io.IOException;

import settings.Settings;
import ship.CapitalShip;
import actor.Actor;
import actor.ActorSet;
import actor.Asteroid;

public class Game {
    private static graphics.Renderer renderer;
    private static input.KeyboardListener input;
    private static boolean paused;
    private static Player player;
    private static Map map;
    private static ActorSet actors;

    //for HUD radar testing, will be removed later
    static actor.Asteroid a;

    public static void init(){
        System.out.println(Runtime.getRuntime().availableProcessors() + " available cores detected");
        try {
            Settings.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map = Map.load("example_1");
        player = new Player();
        actors = new ActorSet();
        Actor ship = player.getNewShip();
        ship.setPosition(map.getSpawnPosition());
        actors.add(ship);
        player.respawn2(actors);

        renderer = new graphics.Renderer();
        input = new KeyboardListener();
        graphics.Model.loadModels();


        
        new GameThread(actors).start();
    }
    //for HUD radar testing, will be removed later
    public static Asteroid getAsteroid() {
        return a;
    }

    public static void joinServer(String server) {
        player = new Player();
        map = Map.load("example_1");

        network.ClientServerThread.joinServer(server, player);

        renderer = new graphics.Renderer();
        input = new KeyboardListener();
        graphics.Model.loadModels();

        
        a.setPosition(new math.Vector3(0.0f,0.0f,-10.0f));
        //actor.Actor.addActor(a);
        
        //new GameThread().start();
    }

    public static void start(){
        renderer.start();
    }

    public static KeyboardListener getInputHandler(){
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

    public static void main (String []args) throws IOException {
        Game.init();
        Game.start();
    }

    public static graphics.Renderer getRenderer() {
        return renderer;
    }
    
    public static ActorSet getActors() {
        return actors;
    }
}
