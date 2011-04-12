package network;

import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class DedicatedServer {
    private static final long FRAME_RATE = 1000 / 60; // Frame rate in ms
    
    private ServerSocket socket;
    private boolean running;
    private Timer timer;


    public static void main(String[] args) {
        DedicatedServer server = new DedicatedServer();
        server.run();
    }
    

    private void startup() {
        running = true;
        try {
            socket = new ServerSocket(NetworkProtocol.SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + NetworkProtocol.SERVER_PORT);
            System.exit(-1);
        }
        
        /* Start a timer to handle our per frame updates */
        timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateTask(), 0, FRAME_RATE);
        
        new ListenerThread(this).start();
    }

    private void shutdown() {
        /* Clean up */
        timer.cancel();
        
        try {
            /* Close the socket so the listener thread will stop blocking */
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void run() {
        startup();
        
 
        while (running) {
            // do stuff
        }
        
        
        shutdown();
    }


    // Main server update code
    private void update() {
        // TODO Auto-generated method stub
        
    }
    
    private class ListenerThread extends Thread {
        private DedicatedServer server;
        
        public ListenerThread(DedicatedServer server) {
            // We have to pass this through so we can then pass it to ServerClientThread
            this.server = server;
        }

        public void run() {
            while (running) {
                try {
                    Socket client = socket.accept();
                    new ServerClientThread(client, server).start();
                } catch (SocketException e) {
                    if (e.getMessage().equals("Socket closed") && running == false) {
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
}
