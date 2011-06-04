package game;

/**
 * Provides an interface for things that need to be called each game update loop
 * @author dustin
 */
public interface Updateable {

    void update(boolean paused);

}
