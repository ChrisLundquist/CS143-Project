package network;

import java.io.*;
import java.net.*;

public class ServerClientThread extends Thread {
    private Socket client;
    private DedicatedServer server;

    public ServerClientThread(Socket client, DedicatedServer server) {
        this.client = client;
        this.server = server;
    }
    
    public void run() {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());

            NetworkProtocol msg;

            while ((msg = (NetworkProtocol)in.readObject()) != null) {
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                client.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
