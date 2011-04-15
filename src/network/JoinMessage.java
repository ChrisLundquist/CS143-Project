package network;

import game.Player;

/**
 * This messages is sent by the client in response to the servers HelloMessage
 * @author Dustin Lundquist <dustin@null-ptr.net>
 */
public class JoinMessage extends Message {
    private static final long serialVersionUID = 3427534415413817486L;
    private Player player;
    
    public JoinMessage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
