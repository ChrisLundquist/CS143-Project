package game;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import actor.ActorSet;

public class GameThread extends Thread {
    private static final long FRAME_RATE = 1000 / 60;
    private static final long COLLISION_TIME = FRAME_RATE * 9 /10; // arbitrarily 90% maximum
    private static final int CORE_SUBSCRIPTION_FACTOR = 1;
    public static final int STATE_RUNNING = 1001;
    public static final int STATE_PAUSED = 1002;
    public static final int STATE_STOPPED = 1003;
    private long lastUpdate ;
    private int maxThreads, gameState;
    private physics.CollisionSolverThread[] collisionThreads;
    private ExecutorService pool;
    private ActorSet actors;
    private List<Updateable> callbacks;


    public GameThread(ActorSet actors) {
        this.gameState = STATE_RUNNING;
        this.actors = actors;
        callbacks = new java.util.ArrayList<Updateable>();
        lastUpdate = 0;
        maxThreads = Runtime.getRuntime().availableProcessors() * CORE_SUBSCRIPTION_FACTOR;
        pool = java.util.concurrent.Executors.newFixedThreadPool(maxThreads);
        collisionThreads = new physics.CollisionSolverThread[maxThreads];
    }

    public void run() {
        setName(this.getClass().getSimpleName() + " [" + actors.playerId + "]");
        setPriority(NORM_PRIORITY + 2);
        
        for(int i = 0; i < collisionThreads.length; ++i)
            collisionThreads[i] = new physics.CollisionSolverThread(actors, i, collisionThreads.length);
        

        while (gameState != STATE_STOPPED) {
            int frames = waitForNextFrame();
            
            for (Updateable c: callbacks)
                c.update(gameState == STATE_PAUSED);

            // Don't update the game state if we are paused
            if(gameState == STATE_PAUSED)
                continue;

            for(int i = 0; i < collisionThreads.length; ++i)
                pool.execute(collisionThreads[i]);
            
            
            // Give the collision threads as much time as we can, but this may cull them prematurely
            try {
                pool.awaitTermination(COLLISION_TIME, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the actors
            actors.update(frames);
        }
        // Shutdown collision thread pool so we can exit cleanly.
        pool.shutdown();

    }
    
    public int getGameState() {
        return gameState;
    }
    
    public void setGameState(int state) {
        if (state >= STATE_RUNNING && state <= STATE_STOPPED) // within valid state range
            this.gameState = state;
    }

    /**
     * This acts as a frame rate controller for our game logic
     * @return the number of frames elapsed.
     */
    private int waitForNextFrame() {
        long current = System.currentTimeMillis();
        // handle first frame case
        if (lastUpdate == 0) {
            lastUpdate = current;
            return 1;
        }

        long elapsed = current - lastUpdate;

        int frames = (int)(elapsed / FRAME_RATE) + 1;
        long delay = FRAME_RATE * frames - elapsed;

        try {
            sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return frames;
    }

    public void addCallback(Updateable callback) {
        callbacks.add(callback);
    }
}
