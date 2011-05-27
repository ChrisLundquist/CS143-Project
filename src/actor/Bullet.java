package actor;

import graphics.Model;
import graphics.particles.FireParticle;
import graphics.particles.ParticleSystem;
import math.*;

public class Bullet extends Projectile {
    private static final long serialVersionUID = -3860927022451699968L;
    private static final int MAX_AGE = 60 * 5; /* 60 fps * 5 seconds = 300 frames */
    protected final float BULLET_SPEED;
    protected int BULLET_DAMAGE = 5;

    protected static final String MODEL_NAME = Model.Models.BULLET;
    private static final String SOUND_EFFECT = "Gun1.wav";
    private static final float EFFECT_VOLUME = 0.5f;

    public Bullet(Actor actor,float speed,int multiplier){
        super(actor);
        this.BULLET_SPEED = speed;

        if(multiplier != 0) {
            BULLET_DAMAGE = BULLET_DAMAGE * multiplier;
        }
        damage = BULLET_DAMAGE;

        sound.Event effect = new sound.Event(actor.getPosition(), actor.getVelocity(),sound.Library.findByName(SOUND_EFFECT));
        effect.gain = EFFECT_VOLUME;
        sound.Manager.addEvent(effect);
        velocity.timesEquals(BULLET_SPEED);
    }


    /**
     * @param actor
     * @param positionOffset the offset relative to the actor
     * @param direction
     */
    public Bullet(Actor actor, float speed, int damage, Vector3f positionOffset){
        this(actor,speed,damage);
        position.plusEquals(positionOffset);
    }

    @Override
    public void handleCollision(Actor other) {
        if (other instanceof ship.PlayerShip)
            return;
        else{
            die();
        }

        if(ParticleSystem.isEnabled()){
            for(int i = 0; i < 50; i++){
                ParticleSystem.addParticle( new FireParticle(this,Vector3f.newRandom(1)));
            }
        }

        bounce(other);
    }

    public void update() {
        super.update();

        if (age > MAX_AGE){
            die();   
        }
    }

    public void die(){
        delete();
    }
}
