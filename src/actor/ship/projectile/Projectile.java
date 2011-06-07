package actor.ship.projectile;

import actor.Actor;
import math.Quaternion;
import math.Vector3f;

public abstract class Projectile extends Actor{
    private static final long serialVersionUID = 8097256529802244313L;
    private static final int MAX_AGE = 60 * 5; /* 60 fps * 5 seconds = 300 frames */

    protected final float DEFAULT_SIZE = 0.1f;
    protected int damage;
    protected float speed;

    protected static final String MODEL_NAME = "bullet";
    protected static final long DEFAULT_DELAY = 1000;
    protected static final float DEFAULT_SPEED = 1.0f;
    

    public Projectile(Actor actor){
        super();
        speed = DEFAULT_SPEED;
        velocity = actor.getVelocity().plus(actor.getDirection().times(speed));
        position = new Vector3f(actor.getPosition());
        rotation = new Quaternion(actor.getRotation());
        parentId = actor.getId();
        setSize(DEFAULT_SIZE);
        modelName = MODEL_NAME;
    }
    public int getDamage() {
        return damage;
    }
    
    public float getSpeed(){
        return speed;
    }

    public void update() {
        super.update();

        if (age > MAX_AGE){
            die();   
        }
    }
    
    @Override
    public void handleCollision(Actor other) {
        // Don't shoot our parents
        if (parentId.equals(other.getId()))
            return;
        die();
    }
    
    public static long getShotCoolDown() {
        return DEFAULT_DELAY;
    }
}
