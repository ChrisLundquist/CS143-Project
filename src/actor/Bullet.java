package actor;

import java.sql.Timestamp;

import math.*;

public class Bullet extends Actor {
    private static final long serialVersionUID = -3860927022451699968L;
    private boolean alive = true;

    public Bullet(){
        super();
        angularVelocity = new Quaternion(Vector3.UNIT_Y, 1);
       /* while(true) {
            if((System.currentTimeMillis() - age) > 2000) {
                actor.Actor.removeActor(this);
            }
        }*/
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("DEBUG: Collision Detected");
    }

    public void setAge() {

    }
}
