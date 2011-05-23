package actor;

import graphics.Model;
import math.Quaternion;
import math.Vector3f;

public class Asteroid extends Actor {
    private static final long serialVersionUID = 916554544709785597L;
    private static final String MODEL_NAME = "ship_test";

    public Asteroid(){
        super(Model.Model_Enum.ASTEROID);
        angularVelocity = new Quaternion(Vector3f.UNIT_Y, 1);
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);
    }
}
