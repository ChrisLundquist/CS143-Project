package network;

import game.Player;

import java.util.List;
import actor.Actor;

public class UpdateMessage extends Message {
    private static final long serialVersionUID = 6956652733302442322L;
    private List<Actor> actors;
    private Player player;
    
    /**
     * Constructor for server updates (from server to client)
     * @param player
     * @param server 
     */
    public UpdateMessage(DedicatedServer server, Player p) {
        player = p;
        actors = new java.util.ArrayList<Actor>();
        actors.addAll(Actor.getActors());
        actors.remove(player.getShip());
    }

    /**
     * Constructor for client updates (from client to server)
     * @param player
     */
    public UpdateMessage(Player p) {
        player = p;
        actors = new java.util.ArrayList<Actor>();
        actors.add(player.getShip());
        // TODO iterate through actors looking for Actors with a parentId of the players ship.
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public List<Actor> getActors() {
        return actors;
    }
}
