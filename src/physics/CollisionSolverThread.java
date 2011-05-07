package physics;

import actor.Actor;
import actor.ActorSet;

public class CollisionSolverThread extends Thread {
    ActorSet actors;
    protected final int stride, start;
    public CollisionSolverThread(ActorSet actors,int start, int stride){
        if(stride <= 0 || start < 0)
            throw new IllegalArgumentException("CollisionSolverThread: Stride must be positive");
        this.actors = actors;
        this.stride = stride;
        this.start = start;
    }


    public void checkCollisions(){
        // Check our guy we stride to against all the others
        for(int i = start; i < actors.size(); i += stride)
            for(int j = i + 1; j < actors.size(); ++j){
                Actor a = actors.get(i);
                Actor b = actors.get(j);

                if(a.isColliding(b)){
                    a.handleCollision(b);
                    b.handleCollision(a);
                }
                
                if (Thread.interrupted()) {
                    System.err.println("CollsionSolverThread: Thread " + this.getId() + " interrupted");
                    return;
                }
            }
    }

    public void run(){
        checkCollisions();
    }
}
