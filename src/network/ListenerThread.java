package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ListenerThread extends Thread {
    private DedicatedServer server;
    private ServerSocket socket;

    public ListenerThread(ServerSocket socket, DedicatedServer server) {
        // We have to pass the DedicatedServer object through so we can then pass it to ServerClientThread
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        setName("Listener " + socket.getLocalSocketAddress() + ":" + socket.getLocalPort());
        
        try {
            while (server.isRunning()) {
                Socket client = socket.accept();
                new ServerClientThread(client, server).start();
            }
        } catch (IOException e) {
            if (e instanceof SocketException && e.getMessage().equals("Socket closed") && server.isRunning() == false) {
                System.err.println("Server socket closed, shutting down.");
            }

            e.printStackTrace();
            return;
        }
    }
}