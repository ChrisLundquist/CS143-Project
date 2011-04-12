package network;

import java.net.*;
import java.io.*;

public class NetworkConnectionThread extends Thread {
    private Socket server;

    public NetworkConnectionThread(InetAddress addr, String name) throws IOException {
        server = new Socket(addr, NetworkProtocol.SERVER_PORT);
    }
    
    // This is the main body of the network connection thread
    public void run() {
        NetworkProtocol msg;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            out = new ObjectOutputStream(server.getOutputStream());
            in = new ObjectInputStream(server.getInputStream());
            
            // TODO join game
            
            // Main loop to receive updates and respond
            while ((msg = (NetworkProtocol)in.readObject()) != null) {
                msg.apply(actor.Actor.actors);
                out.writeObject(new NetworkProtocol());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid network message");
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                server.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
