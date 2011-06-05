
package game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import actor.ActorSet;

public class GameMultiThread extends GameThread {
    private static final long COLLISION_TIME = FRAME_RATE * 9 /10; // arbitrarily 90% maximum
    private static final int CORE_SUBSCRIPTION_FACTOR = 1;
    protected int maxThreads;
    protected physics.CollisionSolverThread[] collisionThreads;
    protected ExecutorService pool;

    public GameMultiThread(ActorSet actors) {
        super(actors);
        maxThreads = Runtime.getRuntime().availableProcessors() * CORE_SUBSCRIPTION_FACTOR;
        pool = java.util.concurrent.Executors.newFixedThreadPool(maxThreads);
        collisionThreads = new physics.CollisionSolverThread[maxThreads];
    }

    public void run() {
        for(int i = 0; i < collisionThreads.length; ++i)
            collisionThreads[i] = new physics.CollisionSolverThread(actors, i, collisionThreads.length);

        super.run();
        // Shutdown collision thread pool so we can exit cleanly.
        pool.shutdown();
    }

    @Override
    public void checkCollision(){
        for(int i = 0; i < collisionThreads.length; ++i)
            pool.execute(collisionThreads[i]);

        //Give the collision threads as much time as we can, but this may cull them prematurely
        try {
            pool.awaitTermination(COLLISION_TIME, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

