package network;

import java.io.*;
import java.net.*;
import game.Player;
import actor.Actor;

/**
 * This thread runs on the client and handles synchronizing the client state with the server
 * @author Dustin Lundquist <dustin@null-ptr.net>
 */
public class ClientServerThread extends ConnectionThread {
    private static int UPDATE_RATE = 1000 / 15; // 15 updates per second;

    private Player player; // TODO create a player class to hold the player name and player ship id
    private long lastUpdate; // time in milliseconds of last update

    public ClientServerThread(InetAddress addr, Player player) throws IOException {
        super(new Socket(addr, DedicatedServer.SERVER_PORT));
        this.player = player;
        lastUpdate = System.currentTimeMillis();
    }

    protected Message handleMessage(Message msg) throws IOException {
        if (msg instanceof HelloMessage) {
            game.Game.setMap(((HelloMessage) msg).getMap());
            return new JoinMessage(player);
        } if (msg instanceof UpdateMessage) {
            UpdateMessage update = (UpdateMessage) msg;
            Actor.updateFromNetwork(update.getActors(), player.getShip());
        }

        rate_limit();

        return new UpdateMessage(game.Game.getPlayer());
    }

    /**
     * Rate limit update to a reasonable rate
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
}
