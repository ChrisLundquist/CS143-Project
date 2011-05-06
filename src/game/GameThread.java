package game;

import actor.Actor;

public class GameThread extends Thread {
    private static final long FRAME_RATE = 1000 / 60;
    private long lastUpdate = 0;
    private int maxCores = 0;
    
    
    
    
    public void run() {
        maxCores = Runtime.getRuntime().availableProcessors();
        while (true) {
            int frames = waitForNextFrame();
             
            // CL - We need to get input even if the game is paused,
            //      that way we can unpause the game.
            game.Game.getInputHandler().update();
            
            // Don't update the game state if we are paused
            if(game.Game.isPaused())
                continue;

            game.Game.getPlayer().updateCamera();

            for(int i = 0; i < maxCores; i++)
                new physics.CollisionSolverThread(Actor.getActors(),i, maxCores).start();
            // Update the actors
            actor.Actor.updateActors(frames);
        }
        
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
}
