package actor;

import graphics.Model;
import math.Quaternion;
import math.Vector3f;

public class Asteroid extends Actor {
    private static final long serialVersionUID = 916554544709785597L;
    private static final String MODEL_NAME = Model.Models.ASTEROID;

    public Asteroid(){
        super();
        angularVelocity = new Quaternion(Vector3f.UNIT_Y, 1);
        modelName = MODEL_NAME;
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);
    }
}
