package network;

import game.Player;

import java.util.Vector;
import actor.Actor;

public class UpdateMessage extends Message {
    private static final long serialVersionUID = 6956652733302442322L;
    Vector<Actor> actors;
    
    /**
     * Constructor for server updates
     */
    public UpdateMessage() {
        actors = new Vector<Actor>();
        actors.addAll(Actor.actors);
    }

    public UpdateMessage(Player player) {
        
    }

}
