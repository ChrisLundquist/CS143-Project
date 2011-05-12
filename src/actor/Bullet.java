package actor;

import graphics.particles.ParticleFire;

import javax.media.opengl.GL2;

import math.*;

public class Bullet extends Projectile {
    private static final long serialVersionUID = -3860927022451699968L;
    private static final int MAX_AGE = 60 * 5; /* 60 fps * 5 seconds = 300 frames */
    protected static final float BULLET_SPEED = 1.0f;
    
    protected static final String MODEL_NAME = "bullet";
    ParticleFire particle;

    public Bullet(Actor actor){
        super(actor);  
        particle = new ParticleFire();
        velocity.times(BULLET_SPEED);
    }

    /**
     * 
     * @param actor
     * @param positionOffset the offset relative to the actor
     * @param direction
     */
    public Bullet(Actor actor, Vector3 positionOffset){
        this(actor);
        particle = new ParticleFire();
        position.plusEquals(positionOffset);

    }

    public void render(GL2 gl) {
        super.render(gl);
        addParticles(gl);
    }

    public void addParticles(GL2 gl) {
        particle.setParameters(this,100f, 5f, .1f);
        particle.draw(gl);
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
