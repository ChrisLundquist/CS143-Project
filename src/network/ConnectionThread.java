package network;

import java.net.*;
import java.io.*;

/**
 * Abstract base class for Client and Server connection threads
 * manages the input and output stream creation and exception handling
 * @author Dustin Lundquist <dustin@null-ptr.net>
 */
public abstract class ConnectionThread extends Thread {
    private Socket socket;    
    
    protected ConnectionThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * This is the main body of the network connection thread
     */
    public void run() {
        Message msg;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            // Include a hook to send an initial hello message
            msg = helloMessage();
            if (msg != null) {
                out.writeObject(msg);
                out.flush();
            }
            
            // Main loop to receive updates and respond          
            while (true) {
                
                // Having issue with reading partial objects over the wire, so trying mark
                in.mark(64 * 1024);
                try {
                    msg = (Message)in.readObject();
                } catch (java.io.EOFException e) {
                    System.out.println("EOF encountered, trying to retry");
                    e.printStackTrace();
                    Thread.sleep(5);
                    in.reset();
                    continue;
                }
                
                
                msg = handleMessage(msg);
                out.writeObject(msg);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Invalid network message");
            e.printStackTrace();
        } catch (InterruptedException e) {
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
            shutdownHook();
        }
    }

    /**
     * Provides an interface for overriding classes to perform cleanup tasks
     */
    protected void shutdownHook() {
        // Do nothing
    }

    /**
     * Provides a hook for sending an intial message
     * Either the client or server should override this, not both
     * @return an message to send on connection establishment
     */
    protected Message helloMessage() {
        return null;
    }

    protected abstract Message handleMessage(Message msg) throws IOException;
}
