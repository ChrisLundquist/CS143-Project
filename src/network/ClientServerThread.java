package network;

import java.io.*;
import java.net.*;
import java.util.Queue;
import javax.swing.JOptionPane;
import actor.Actor;
import actor.ActorSet;
import game.Player;

/**
 * This thread runs on the client and handles synchronizing the client state with the server
 * @author Dustin Lundquist <dustin@null-ptr.net>
 */
public class ClientServerThread extends AbstractConnectionThread {
    private static final int NOT_CONNECTED = 1001;
    private static final int CONNECTING = 1002;
    private static final int JOINING_GAME = 1003;
    private static final int CONNECTED = 1004;
    private static int UPDATE_RATE = 1000 / 15; // 15 updates per second;

    private Player player;
    private long lastUpdate; // time in milliseconds of last update
    private int status;
    private ActorSet actors;
    private HelloMessage hello;
    private Queue<Actor> newActorsQueue;


    public ClientServerThread(String host, Player player) throws IOException {
        this(new Socket(host, DedicatedServer.SERVER_PORT), player);
    }

    public ClientServerThread(Socket socket, Player player) {
        super(socket);
        this.player = player;
        status = NOT_CONNECTED;
        lastUpdate = System.currentTimeMillis();   
    }

    protected synchronized Message handleMessage(Message msg) throws IOException, InterruptedException {
        if (msg instanceof HelloMessage) {
            hello = (HelloMessage)msg;
            status = CONNECTING;
            // This is kind of annoying, we have to create fields for every variable we want to present to the rest of the client
            actors = new ActorSet(hello.playerId);
            newActorsQueue = new java.util.concurrent.ConcurrentLinkedQueue<Actor>();
            actors.addNewActorConsumer(newActorsQueue);
            player.setPlayerId(actors.playerId);

            this.notifyAll(); 
            while (status == CONNECTING)
                wait(); // wait for player to decide if they want to join this map

            return new JoinMessage(player);
        } if (msg instanceof UpdateMessage) {
            UpdateMessage update = (UpdateMessage) msg;
            status = CONNECTED;
            update.applyTo(actors);
        }

        rate_limit();

        return new UpdateMessage(player, newActorsQueue);
    }

    public int getStatus() {
        return status;
    }

    /**
     * The server provides the playerId we use for initializing the ActorSet
     * @return
     */
    public ActorSet getActors() {
        return actors;
    }

    public HelloMessage getHello() {
        return hello;
    }

    /**
     * Rate limit updates to a reasonable rate
     */
    private void rate_limit() {
        try {
            int delay = UPDATE_RATE - (int) (System.currentTimeMillis() - lastUpdate);
            if (delay > 0)

                Thread.sleep(delay);

            lastUpdate = System.currentTimeMillis();
        } catch (InterruptedException e) {
            // someone interrupted our thread, silently fail
        }
    }

    public static ClientServerThread joinServer(String host, Player player) {
        try {
            ClientServerThread connection = new ClientServerThread(host, player);
            connection.start();

            synchronized(connection) {
                while (connection.getStatus() != CONNECTING)
                    connection.wait();          
            }
            /*
             * In a more verbose connection routine we probably want to display the list of players and server map
             */
            connection.joinGame();
            return connection;     
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Unable to resolve server name " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Network error occured: " + e.getMessage());
        } catch (InterruptedException e) {
            // someone interrupted our thread, silently die
        }  
        return null;

    }

    private synchronized void joinGame() {
        status = JOINING_GAME;
        notifyAll();
    }

    public static void main(String[] args) {
        System.out.println("Starting client");
        joinServer("localhost", new Player());
        
    }
}
