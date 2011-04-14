package network;

public class HelloMessage extends Message {
    private static final long serialVersionUID = -1554065217547323739L;

    private int playerCount;
    private game.Map map;

    public HelloMessage(DedicatedServer server) {
        playerCount = server.getPlayers().size();
        map = server.getCurrentMap();
    }

    public int getPlayerCount() {
        return playerCount;
    }
    
    public game.Map getMap() {
        return map;
    }
}
