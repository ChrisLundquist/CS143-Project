package actor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ActorSet provides a thread safe backing store for our actors
 * @author Dustin Lundquist <dustin@null-ptr.net>
 */
public class ActorSet implements Set<Actor> {

    private Map<ActorId, Actor> actors;
    private final int playerId;

    public ActorSet(int playerId) {
        this.playerId = playerId;
        actors = new java.util.concurrent.ConcurrentHashMap<ActorId, Actor>();
    }

    public ActorSet() {
        this(0);
    }

    @Override
    /**
     * Add an actor to the ActorSet, assigning it an ActorId if one isn't already set
     */
    public synchronized boolean add(Actor a) {     
        if (a.id == null)
            a.id = new ActorId(playerId);

        if (actors.containsKey(a.id))
            return false;
        
        // Set a back reference to this ActorSet for actor.add() and actor.delete()
        a.actors = this;
        
        actors.put(a.id, a);

        return true;
    }

    @Override
    public  boolean addAll(Collection<? extends Actor> list) {
        boolean changed = false;
        
        for (Actor a: list)
            if (add(a))
                changed = true;

        return changed;
    }

    @Override
    public synchronized void clear() {
        actors.clear();
    }

    @Override
    public boolean contains(Object arg0) {
        return false;
    }

    public synchronized boolean contains(Actor a) {
        if (a.id == null)
            return false;
        return actors.containsKey(a.id);
    }

    @Override
    public boolean containsAll(Collection<?> list) {
        for (Object o: list)
            if (contains(o) == false)
                return false;

        return true;
    }

    @Override
    public synchronized boolean isEmpty() {
        return actors.isEmpty();
    }


    @Override
    public synchronized Iterator<Actor> iterator() {
        return new CopyIterator();
    }


    @Override
    public boolean remove(Object arg0) {
        return false;
    }

    public synchronized boolean remove(Actor a) {
        if (a.id == null)
            return false;

        return actors.remove(a.id) != null;
    }

    @Override
    public boolean removeAll(Collection<?> list) {
        boolean changed = false;
        
        for (Object o: list)
            if (remove(o))
                changed = true;

        return changed;
    }


    @Override
    public boolean retainAll(Collection<?> arg0) {
        throw new UnsupportedOperationException();
    }


    @Override
    public synchronized int size() {
        return actors.size();
    }


    @Override
    public synchronized Object[] toArray() {
        return actors.values().toArray();
    }


    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return actors.values().toArray(a);
    }

    /**
     * Returns an Actor from the ActorSet with the provided ActorId if it exists
     * @param id
     * @return
     */
    public synchronized Actor findById(ActorId id) {
        return actors.get(id);
    }

    /**
     * Update all the actors in the ActorSet
     * @param frames the number of frame elapsed since the last update
     */
    public void update(int frames) {
        for (Actor a: this) {
            a.update();
        }
    }
    
    /**
     * Returns a new instance of a List containing all
     * the actors currently in the ActorSet
     * @return
     */
    public synchronized List<Actor> getCopyList() {
        return new CopyList();    
    }

    /**
     * Copy iterator
     * @author dustin
     */
    private class CopyIterator implements Iterator<Actor> {
        List<Actor> copy;
        Iterator<Actor> it;

        public CopyIterator() {
            copy = new java.util.ArrayList<Actor>(actors.values());
            it = copy.iterator();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Actor next() {
            return it.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() not supported by " + this.getClass());
        }
    }

    private class CopyList extends java.util.ArrayList<Actor> {
        private static final long serialVersionUID = -7412443957198423304L;

        public CopyList() {
            super(actors.values());
        }
    }
}
