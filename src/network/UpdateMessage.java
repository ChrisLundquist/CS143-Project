package network;

import game.Player;

import java.util.List;
import java.util.Queue;
import actor.Actor;
import actor.ActorSet;

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
        for (Actor a: server.getActors())
            if (a.getId().getPlayerId() != player.getPlayerId())
                actors.add(a);
        
//        actors = server.getActors().getCopyList();
//        actors.remove(player.getShip());
    }

    /**
     * Constructor for client updates (from client to server)
     * @param player
     */
    public UpdateMessage(Player player, Queue<Actor> newActors) {
        actors = new java.util.ArrayList<Actor>();
        this.player = player;

        Actor a;
        while((a = newActors.poll()) != null)
            actors.add(a);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void applyTo(ActorSet actors) {
        /*
        System.err.println("Network Update");
        for (Actor a: this.actors)
            System.err.println(a);

        System.err.println("Applying to local actors");
        for (Actor a: actors)
            System.err.println(a);
         */
        for (Actor a: this.actors)
            actors.addOrReplace(a);
    }
}
