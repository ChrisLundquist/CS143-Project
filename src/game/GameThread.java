package game;

import java.util.List;

import physics.CollisionSolverThread;
import actor.ActorSet;

public class GameThread extends Thread {
    public static final long FRAME_RATE = 1000 / 60;
    public static final int STATE_RUNNING = 1001;
    public static final int STATE_PAUSED = 1002;
    public static final int STATE_STOPPED = 1003;
    protected long lastUpdate ;
    protected int gameState;

    protected ActorSet actors;
    protected List<Updateable> callbacks;


    public GameThread(ActorSet actors) {
        this.gameState = STATE_RUNNING;
        this.actors = actors;
        callbacks = new java.util.ArrayList<Updateable>();
        lastUpdate = 0;
    }

    public void run() {
        setName(this.getClass().getSimpleName() + " [" + actors.playerId + "]");
        setPriority(NORM_PRIORITY + 2);

        while (gameState != STATE_STOPPED) {
            int frames = waitForNextFrame();

            for (Updateable c: callbacks)
                c.update(gameState == STATE_PAUSED);

            // Don't update the game state if we are paused
            if(gameState == STATE_PAUSED)
                continue;

            checkCollision();

            // Update the actors
            actors.update(frames);
        }
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int state) {
        if (state >= STATE_RUNNING && state <= STATE_STOPPED) // within valid state range
            this.gameState = state;
    }

    public void checkCollision(){
        CollisionSolverThread.checkCollision(actors, 0, 1);
    }

    /**
     * This acts as a frame rate controller for our game logic
     * @return the number of frames elapsed.
     */
    protected int waitForNextFrame() {
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
