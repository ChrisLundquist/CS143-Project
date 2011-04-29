package actor;

import math.*;

public class Bullet extends Actor {
    private static final long serialVersionUID = -3860927022451699968L;

    public Bullet(){
        super();
        angularVelocity = new Quaternion(Vector3.UNIT_Y, 1);
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("DEBUG: Collision Detected");
    }
}
