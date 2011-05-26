package actor;

import graphics.Model;

public class Missile extends Projectile {
    private static final long serialVersionUID = -8381240274687476481L;
    protected static final float MISSILE_SPEED = 0.5f;
    protected static final String MODEL_NAME = Model.Models.MISSILE;

    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);
    }
    
    public Missile(Actor actor){
        super(actor);
        modelName = MODEL_NAME;
        velocity.times(MISSILE_SPEED);
    }
}
