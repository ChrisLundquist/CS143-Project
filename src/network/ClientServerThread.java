package network;

import game.Player;

import java.io.*;
import java.net.*;

/**
 * This is the network connection thread for the client.
 * 
 * @author Dustin Lundquist
 *
 */
public class ClientServerThread extends ConnectionThread {
    private Player player; // TODO create a player class to hold the player name and player ship id

    public ClientServerThread(InetAddress addr, Player player) throws IOException {
        super(new Socket(addr, DedicatedServer.SERVER_PORT));
        this.player = player;
    }

    protected Message handleMessage(Message msg) throws IOException {
        if (msg instanceof HelloMessage) {
            game.Game.setMap(((HelloMessage) msg).getMap());
            return new JoinMessage(player);
        } if (msg instanceof UpdateMessage) {
            
        }
        // TODO Auto-generated method stub
        return new UpdateMessage(game.Game.getPlayer());
    }
}
