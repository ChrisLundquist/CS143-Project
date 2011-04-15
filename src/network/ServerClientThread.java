package network;

import java.io.IOException;
import java.net.Socket;

public class ServerClientThread extends ConnectionThread {
    private DedicatedServer server;

    public ServerClientThread(Socket client, DedicatedServer server) {
        super(client);
        this.server = server;
    }
    
    protected Message helloMessage() {
        return null;
    }

    protected Message handleMessage(Message msg) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
}
