package actor;

import math.*;

public class Asteroid extends Actor {
    private static final long serialVersionUID = 916554544709785597L;
    private static final String MODEL_NAME = "cube_cube";

    public Asteroid(){
        super();
        angularVelocity = new Quaternion(Vector3.UNIT_Y, 1);
        modelName = MODEL_NAME;
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);
    }
}
