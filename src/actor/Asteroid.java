package actor;

import math.Quaternion;
import math.Vector3f;

public class Asteroid extends Actor {
    private static final long serialVersionUID = 916554544709785597L;
    private static final String MODEL_NAME = "asteroid";
    protected int hitPoints;

    public Asteroid(){
        super();
        angularVelocity = new Quaternion(Vector3f.randomPosition(1), 1);
        scale = new Vector3f(2,2,2).plus(Vector3f.randomPosition(1));
        hitPoints = (int) scale.magnitude2(); 
        modelName = MODEL_NAME;
    }
    
    public Asteroid(Vector3f position, Vector3f velocity){
        this();
        setPosition(position);
        setVelocity(velocity);
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);
        if(other instanceof Projectile){
            hitPoints -= ((Projectile) other).getDamage();
            if(hitPoints < 0)
                die();
        }
        bounce(other);
    }
    
    @Override
    public void die(){
        // TODO make particles
        delete();
    }
}
