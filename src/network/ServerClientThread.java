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
        return new HelloMessage(server);
    }

    protected Message handleMessage(Message msg) throws IOException {
        if (msg instanceof JoinMessage) {
            server.addPlayer(((JoinMessage) msg).getPlayer());
        } else if (msg instanceof UpdateMessage) {
        }
        return new UpdateMessage();
    }
}
