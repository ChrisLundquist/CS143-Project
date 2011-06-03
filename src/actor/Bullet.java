package actor;

import graphics.Model;
import graphics.particles.FireParticle;
import graphics.particles.ParticleSystem;
import math.*;

public class Bullet extends Projectile {
    private static final long serialVersionUID = -3860927022451699968L;
    protected static int BULLET_DAMAGE = 5;

    protected static final String MODEL_NAME = Model.Models.BULLET;
    private static final String SOUND_EFFECT = "Gun1.wav";
    private static final float EFFECT_VOLUME = 0.5f;
    private static final float BULLET_SPEED = 1.0f;
    private static final long DEFAULT_DELAY = 100;

    public Bullet(Actor actor){
        super(actor);
        damage = BULLET_DAMAGE;
        velocity.timesEquals(BULLET_SPEED);

        sound.Event effect = new sound.Event(actor.getPosition(), actor.getVelocity(),sound.Library.findByName(SOUND_EFFECT));
        effect.gain = EFFECT_VOLUME;
        sound.Manager.addEvent(effect);
    }
    
    public void handleCollision(Actor other){
        // Don't shoot our parents
        if (parentId.equals(other.getId()))
            return;
        
        if(ParticleSystem.isEnabled()){
            for(int i = 0; i < 50; i++){
                ParticleSystem.addParticle( new FireParticle(this,Vector3f.newRandom(1)));
            }
        }
        die();
    }
    
    @Override
    public void onFirstUpdate(){
        
    }
    public static long getShotCoolDown() {
        return DEFAULT_DELAY;
    }
}
