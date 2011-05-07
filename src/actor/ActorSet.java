package actor;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class ActorSet implements Set<Actor> {
    /**
     * All the actors currently in play We use the fully qualified named space
     * for the Vector container so it doesn't clash with our name space. Vectors
     * work like ArrayLists, but are synchronized.
     */
    private List<Actor> actors = Collections.synchronizedList(new java.util.ArrayList<Actor>());
    
    
    /**
     * Thread safe way to remove actors
     * @param idToRemove
     */
    public void removeActorId(int idToRemove) {
        synchronized(actors) {
            for (ListIterator<Actor> it = actors.listIterator(); it.hasNext(); ) {
                Actor a = it.next();
                if (a.getId() == idToRemove)
                    it.remove();
            }
        }
    }

    public void addActor(Actor actor) {
        synchronized(actors) {
            actors.add(actor);
        }
    }

    public void removeActor(Actor actor) {
        synchronized(actors) {
            actors.remove(actor);
        }
    }

    /**
     * 
     * @param frames the number of frames since the last update
     */
    public void updateActors(int frames) {
        synchronized(actors) {
            // Update each actor
            for (Actor a : actors) {
                // Track down actors without ids.
                if (a.getId() == 0)
                    System.err.println("DEBUG: " + a + " actor without ID set");
                a.update();
            }
        }
    }

    // FIXME this is a linear time search
    public static Actor findById(int id) {
        synchronized(actors) {
            for (Actor a: actors)
                if (a.getId() == id)
                    return a;
        }
        return null;
    }
    
    /**
     * Update all the actors
     * @author Dustin Lundquist <dustin@null-ptr.net>
     * @param update a list of updates
     * @param ship the players ship or similar actor that should not be updated
     */
    public static void updateFromNetwork(List<Actor> update, Actor ship) {
        synchronized(actors) {
            // This is n^2 - but I don't have a better way to do it
            // Using ListEterators so we only need to make one pass adding and removing elements
            for (ListIterator<Actor> actors_iter = actors.listIterator(); actors_iter.hasNext(); ) {
                Actor a = actors_iter.next();
                boolean found = false;

                for (ListIterator<Actor> update_iter = update.listIterator(); update_iter.hasNext(); ) {
                    Actor u = update_iter.next();

                    if (a.id != u.id)
                        continue;

                    // Do not update the players ship position from the network
                    if (ship != null && u.id == ship.id)
                        continue;

                    actors_iter.set(u);
                    update_iter.remove();
                    found = true;
                    break;
                }
                // Skip the last step if running on the server
                if (ship == null)
                    continue;

                // Remove actors that where not present in the update, except for the players ship
                if (!found && a != ship)
                    actors_iter.remove();
            }
            // Add the remaining
            actors.addAll(update);
        }

    }
}
