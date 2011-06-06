package network;

import game.GameMultiThread;
import game.GameThread;
import game.Map;
import game.Player;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Set;
import actor.ActorSet;

public class DedicatedServer extends Thread {
    public static final int SERVER_PORT = 8348;
    
    public static void main(String[] args) {
        DedicatedServer server = new DedicatedServer();
        server.start();
    }
    
    
    private ActorSet actors;
    private Map currentMap;
    private GameThread game;
    private List<ServerClientThread> players;
    private boolean running;
    private ServerSocket socket;
    private int lastPlayerId;

    public DedicatedServer() {
        players = new java.util.concurrent.CopyOnWriteArrayList<ServerClientThread>();
        actors = new ActorSet();
        lastPlayerId = actors.playerId;
    }
    
    public void addPlayer(ServerClientThread player) {
        players.add(player);
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public List<Player> getPlayers() {
        List<Player> players = new java.util.ArrayList<Player>();
        
        for (ServerClientThread t: this.players)
            players.add(t.getPlayer());
        
        return players;
    }

    public boolean isRunning() {
        return running;
    }

    public void removePlayer(ServerClientThread player) {
        players.remove(player);
        actors.remove(player.getPlayer().getShip(actors));
    }

    public void run() {
        startup();
        
        // Calling .run() instead of .start() so we don't start a new thread and we keep blocking until the server shuts down
        new ServerCli(this, System.out, System.in).run();
        
        shutdown();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void shutdown() {
        game.setGameState(GameThread.STATE_STOPPED);
        
        try {
            /* Close the socket so the listener thread will stop blocking */
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startup() {
        setName("Dedicated Server");
        running = true;
        
        graphics.core.Model.loadModels();
        
        try {
            socket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + SERVER_PORT);
            System.exit(-1);
        }
        
        currentMap = Map.load("example_1");
        actors.addAll(currentMap.actors);

        game = new GameMultiThread(actors);
        game.start();
        
        new ListenerThread(socket, this).start();
    }

    public ActorSet getActors() {
        return actors;
    }

    public int getNewPlayerId() {
        return ++lastPlayerId ;
    }

    public Set<String> getModelsInUse() {
        Set<String> models = new java.util.HashSet<String>();

        for (graphics.core.Model m: graphics.core.Model.loaded_models())
            models.add(m.name);
        
        return models;
    }
}
