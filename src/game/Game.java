package game;

import game.types.*;
import input.KeyboardListener;
import input.XboxInputListener;

import java.io.IOException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import settings.Settings;
import actor.ActorSet;

public class Game {
    private static graphics.core.Renderer renderer;
    private static input.KeyboardListener input;
    private static input.XboxInputListener controller;
    private static Player player;
    private static Map map;
    private static ActorSet actors;
    private static GameThread game;

    public static void init() {
        System.out.println(Runtime.getRuntime().availableProcessors() + " available cores detected");
        try {
            Settings.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map = Map.load("example_1");
        player = new Player();
        actors = new ActorSet();
        actors.addAll(map.actors);
        player.respawn(actors, map.getSpawnPosition());

        renderer = new graphics.core.Renderer(player.getCamera());
        input = new KeyboardListener();
        
        /*
        try {
            //TODO add boolean to check if controller enabled
            //TODO addCallBack
            //starts xbox controller thread
            controller = new XboxInputListener();
            Thread myThread = new Thread(controller);
            myThread.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        graphics.core.Model.loadModels();
        // When we pass player.getCamera() the sound doesn't match the player position
        sound.Manager.initialize(player.getShip());

        game = new GameThread(actors);
        // CL - We need to get input even if the game is paused,
        //      that way we can unpause the game.
        game.addCallback(input);
        game.addCallback(new Updateable() {
            public void update() {
                player.updateCamera();
            }
        });
        game.addCallback(new Updateable() {
            public void update() {
                sound.Manager.processEvents();
            }
        });

        game.addCallback(new AsteroidField());
        game.addCallback(new Bandits());
    }

    public static void joinServer(String server) {
        player = new Player();
        map = Map.load("example_1");

        network.ClientServerThread.joinServer(server, player);

        //renderer = new graphics.Renderer();
        input = new KeyboardListener();
        graphics.core.Model.loadModels();

        //new GameThread().start();
    }

    public static void start() {
        game.start();
        renderer.start();
    }

    public static KeyboardListener getInputHandler(){
        return input;
    }
    
    public static XboxInputListener getControllerHandler(){
        return controller;
    }

    public static Player getPlayer() {
        return player;
    }

    public static boolean isPaused() {
        return game.getGameState() == GameThread.STATE_PAUSED;
    }

    public static void quitToMenu() {
        game.setGameState(GameThread.STATE_STOPPED);
    }

    public static void togglePause() {
        if (isPaused())
            game.setGameState(GameThread.STATE_RUNNING);
        else
            game.setGameState(GameThread.STATE_PAUSED);
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

    public static graphics.core.Renderer getRenderer() {
        return renderer;
    }

    public static ActorSet getActors() {
        return actors;
    }
}
