package network;

import game.Map;

/**
 * This message is sent by the server when the player initially connects
 * @author Dustin Lundquist <dustin@null-ptr.net>
 */
public class HelloMessage extends Message {
    private static final long serialVersionUID = -1554065217547323739L;

    private int playerCount;
    private Map map;

    public HelloMessage(DedicatedServer server) {
        playerCount = server.getPlayers().size();
        map = server.getCurrentMap();
    }

    public int getPlayerCount() {
        return playerCount;
    }
    
    public Map getMap() {
        return map;
    }
}
