package actor;

import math.*;

public class Asteroid extends Actor {
    private static final long serialVersionUID = 916554544709785597L;

    public Asteroid(){
        super();
        angularVelocity = new Quaternion(Vector3.UNIT_Y, 1);
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("DEBUG: Collision Detected");
    }
}
