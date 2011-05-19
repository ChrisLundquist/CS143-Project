package actor;

import math.*;

public class Bullet extends Projectile {
    private static final long serialVersionUID = -3860927022451699968L;
    private static final int MAX_AGE = 60 * 5; /* 60 fps * 5 seconds = 300 frames */
    protected final float BULLET_SPEED;
    protected static final int BULLET_DAMAGE = 5;
    
    protected static final String MODEL_NAME = "bullet";
    private static final String SOUND_EFFECT = "Gun1.wav";

    public Bullet(Actor actor,float speed){
        super(actor);
        this.BULLET_SPEED = speed;
        graphics.particles.ParticleSystem.addParticle(new graphics.particles.FireParticle(this,velocity.negate()));
        sound.Manager.addEvent(new sound.Event(actor.getPosition().toFloatArray(), actor.getVelocity().toFloatArray(),sound.Library.findByName(SOUND_EFFECT)));
        velocity.times(BULLET_SPEED);
        damage = BULLET_DAMAGE;
    }

    /**
     * @param actor
     * @param positionOffset the offset relative to the actor
     * @param direction
     */
    public Bullet(Actor actor, float speed, Vector3f positionOffset){
        this(actor,speed);
        position.plusEquals(positionOffset);
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);

        if (other instanceof ship.PlayerShip)
            return;
        bounce(other);
    }

    public void update() {
        super.update();

        if (age > MAX_AGE)
            delete();   
    }
}
