package network;

import java.util.Collection;


/**
 * This message is sent by the server when the player initially connects
 * @author Dustin Lundquist <dustin@null-ptr.net>
 */
public class HelloMessage extends Message {
    private static final long serialVersionUID = -1554065217547323739L;

    public final int playerCount;
    public final int playerId;
    public final game.Map map;
    public final Collection<String> modelNames;

    public HelloMessage(DedicatedServer server) {
        playerCount = server.getPlayers().size();
        playerId = server.getNewPlayerId();
        map = server.getCurrentMap();
        modelNames = server.getModelsInUse();
    }
}
