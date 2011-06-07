package game;

import game.Player.ShipType;
import game.types.AsteroidField;
import game.types.Bandits;
import input.KeyboardListener;
import input.XboxInputListener;
import java.io.IOException;
import network.ClientServerThread;
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
    private static ClientServerThread networkConnection;

    public static void init() {
        System.out.println(Runtime.getRuntime().availableProcessors() + " available cores detected");
        try {
            Settings.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (networkConnection == null) {
            player = new Player();
            map = Map.load("example_1");

            actors = new ActorSet();
            actors.addAll(map.actors);

            graphics.core.Model.loadModels();
        } else {
            map = networkConnection.getHello().map;
            actors = networkConnection.getActors();

            graphics.core.Model.loadModels(networkConnection.getHello().modelNames);
        }


        //turns on controller if enabled in settings
        if(Settings.Profile.controllerOn) {
            try {
                //starts xbox controller thread
                controller = new XboxInputListener();
                Thread myThread = new Thread(controller);
                myThread.start();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }




        renderer = new graphics.core.Renderer(player.getCamera());
        renderer.setHud(new graphics.Hud(player));
        input = new KeyboardListener();


        // When we pass player.getCamera() the sound doesn't match the player position
        if (sound.Manager.enabled)
            sound.Manager.initialize(player.getCamera());

        game = new GameMultiThread(actors);
        // CL - We need to get input even if the game is paused,
        //      that way we can unpause the game.
        game.addCallback(input);
        game.addCallback(new Updateable() {
            public void update(boolean paused) {
                player.updateCamera();
            }
        });
        if (sound.Manager.enabled) 
            game.addCallback(new Updateable() {
                public void update(boolean paused) {
                    sound.Manager.processEvents();
                }
            });
        // Single player only callbacks
        if (networkConnection == null) {
            game.addCallback(new AsteroidField());
            game.addCallback(new Bandits());
        }
    }

    public static boolean joinServer(String server, String playerName, ShipType ship) {
        player = new Player(playerName, ship);
        networkConnection = ClientServerThread.joinServer(server, player);  
        if (networkConnection == null)
            return false;
            

        init();
        return true;
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
        try {
            renderer.shutdown();
            game.setGameState(GameThread.STATE_STOPPED);
            game.join();
            if (networkConnection != null) {
                networkConnection.close();
                networkConnection.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void togglePause() {
        if (isPaused())
            game.setGameState(GameThread.STATE_RUNNING);
        else
            game.setGameState(GameThread.STATE_PAUSED);
    }

    public static void exit() {
        quitToMenu();
        
        //cleanly exits if on windows or mac
        if(GetOS.isWindows() || GetOS.isMac()) {
            System.exit(0);
        }
        //not so clean, but that is because linux is superior
        else {
            System.exit(0);
        }
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
