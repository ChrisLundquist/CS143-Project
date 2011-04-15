package network;

import java.net.*;
import java.io.*;


public abstract class ConnectionThread extends Thread {
    private Socket socket;    
    
    public ConnectionThread(Socket socket) {
        this.socket = socket;
    }

    // This is the main body of the network connection thread
    public void run() {
        Message msg;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            // Include a hook to send an initial hello message
            msg = helloMessage();
            if (msg != null)
                out.writeObject(msg);
            
            // Main loop to receive updates and respond
            while ((msg = (Message)in.readObject()) != null) {
                msg = handleMessage(msg);
                out.writeObject(msg);
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
                socket.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected Message helloMessage() {
        return null;
    }

    protected abstract Message handleMessage(Message msg) throws IOException;
}
