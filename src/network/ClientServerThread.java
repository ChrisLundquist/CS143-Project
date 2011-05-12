package network;

import java.io.*;
import java.net.*;
import java.util.Queue;
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
    private Queue<Actor> newActors;


    public ClientServerThread(String host, Player player) throws IOException {
        this(new Socket(host, DedicatedServer.SERVER_PORT), player);
    }

    public ClientServerThread(Socket socket, Player player) {
        super(socket);
        this.player = player;
        status = NOT_CONNECTED;
        lastUpdate = System.currentTimeMillis();   
    }

    protected Message handleMessage(Message msg) throws IOException, InterruptedException {
        if (msg instanceof HelloMessage) {
            hello = (HelloMessage)msg;
            // This is kind of annoying, we have to create fields for every variable we want to present to the rest of the client
            actors = new ActorSet(hello.playerId);
            newActors = new java.util.concurrent.ConcurrentLinkedQueue<Actor>();
            actors.addNewActorConsumer(newActors);
            
            this.notifyAll();
            while (status == CONNECTING)
                wait(); // wait for player to decide if they want to join this map
            
            return new JoinMessage(player);
        } if (msg instanceof UpdateMessage) {
            UpdateMessage update = (UpdateMessage) msg;
            status = CONNECTED;
            //Actor.updateFromNetwork(update.getActors(), player.getShip());
        }

        rate_limit();

        return new UpdateMessage(game.Game.getPlayer());
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ActorSet joinServer(String host, Player player) {
        try {
            ClientServerThread connection = new ClientServerThread(host, player);
            connection.start();
   
            synchronized(connection) {
                 connection.wait(30000);
            }
            if (connection.getStatus() != CONNECTING) {
                System.err.println("Connection failed");
                return null;
            }
            ActorSet actors = connection.getActors();
            /*
             * In a more verbose connection routine we probably want to display the list of players and server map
             */
            connection.joinGame();
            return actors;     
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        
    }

    private void joinGame() {
        status = JOINING_GAME;
        notifyAll();
    }
}
