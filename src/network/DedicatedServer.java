package network;

import game.Map;
import game.Player;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import actor.Actor;

public class DedicatedServer {
    public static final int SERVER_PORT = 8348;
    private static final long FRAME_RATE = 1000 / 60; // Frame rate in ms
    
    private ServerSocket socket;
    private boolean running;
    private Timer timer;
    private List<Player> players;
    private Map currentMap;

    public static void main(String[] args) {
        DedicatedServer server = new DedicatedServer();
        server.run();
    }

    public DedicatedServer() {
        players = new CopyOnWriteArrayList<Player>();
    }
    
    private void startup() {
        setRunning(true);
        try {
            socket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + SERVER_PORT);
            System.exit(-1);
        }
        
        currentMap = Map.load("example_1");
        
        // Sample asteroids
        actor.Asteroid a;
        for (int i = 0; i < 4; i++) {
            a = new actor.Asteroid();
            a.setPosition(new math.Vector3(0.0f, 0.0f, -10.0f * i));
            actor.Actor.addActor(a);
        }

        /* Start a timer to handle our per frame updates */
        timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateTask(), 0, FRAME_RATE);
        
        new ListenerThread(this).start();
    }

    private void shutdown() {
        timer.cancel();
        
        try {
            /* Close the socket so the listener thread will stop blocking */
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        startup();
        
        new ServerCli(this, System.out, System.in).run();
        
        shutdown();
    }

    // Main server update code
    private void update() {
        Actor.updateActors();
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public List<Player> getPlayers() {
        return players;
    }

    private class ListenerThread extends Thread {
        private DedicatedServer server;
        
        public ListenerThread(DedicatedServer server) {
            // We have to pass this through so we can then pass it to ServerClientThread
            this.server = server;
        }

        public void run() {
            while (isRunning()) {
                try {
                    Socket client = socket.accept();
                    new ServerClientThread(client, server).start();
                } catch (SocketException e) {
                    if (e.getMessage().equals("Socket closed") && isRunning() == false) {
                        System.err.println("Server socket closed, shutting down.");
                    } else {
                        e.printStackTrace();                        
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private class UpdateTask extends TimerTask {
        public void run() {
            update();
        }
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        Actor.removeActor(player.getShip());
    }
}
