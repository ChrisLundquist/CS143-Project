package network;

import java.io.*;
import java.net.*;

/**
 * This is the network connection thread for the client.
 * 
 * @author Dustin Lundquist
 *
 */
public class ClientServerThread extends ConnectionThread {
    private Object player; // TODO create a player class to hold the player name and player ship id

    public ClientServerThread(InetAddress addr, Object player) throws IOException {
        super(new Socket(addr, DedicatedServer.SERVER_PORT));
        this.player = player;
    }

    protected Message handleMessage(Message msg) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
}
