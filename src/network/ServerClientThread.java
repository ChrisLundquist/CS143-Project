package network;

import game.Player;
import java.io.IOException;
import java.net.Socket;

import actor.ActorSet;

/**
 * This thread runs as part of the server handling one player's connection
 * @author Dustin Lundquist <dustin@null-ptr.net>
 */
public class ServerClientThread extends AbstractConnectionThread {
    private DedicatedServer server;
    private Player player;
    private ActorSet actors;

    public ServerClientThread(Socket client, DedicatedServer server) {
        super(client);
        this.server = server;
        this.actors = server.getActors();
    }

    protected Message helloMessage() {
        return new HelloMessage(server);
    }

    protected Message handleMessage(Message msg) throws IOException {
        if (msg instanceof JoinMessage) {
            player = ((JoinMessage) msg).getPlayer();
            server.addPlayer(this);
        } else if (msg instanceof UpdateMessage) {
            UpdateMessage update = (UpdateMessage) msg;
            Player playerUpdate = update.getPlayer();
            
            if (player.getShipId() != null && ! player.getShipId().equals(playerUpdate.getShipId())) {
                // Player died or respawned or something
                
                if (player.getPlayerId() != playerUpdate.getPlayerId())
                    throw new RuntimeException("Player tried something sneaky and changed their player id");

                // Easiest way to resolve update
                // TODO this doesn't notify the server process of the new player so we should probably do a member by member copy
                player = playerUpdate;
            }
            update.applyTo(actors);
        }
        return new UpdateMessage(server, player);
    }

    protected void shutdownHook() {
        server.removePlayer(this);
    }

    public Player getPlayer() {
        return player;
    }
}
