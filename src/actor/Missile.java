package actor;

import graphics.Model;
import graphics.particles.*;

import math.*;

public class Missile extends Projectile {
    private static final long serialVersionUID = -8381240274687476481L;
    protected static final int MISSILE_DAMAGE = 100;
    protected static final String MODEL_NAME = Model.Models.MISSILE;
    protected static final String SHOOT_EFFECT = "missile_firing.wav";
    protected static final String DEATH_EFFECT = "explode.wav";
    protected static final float EFFECT_VOLUME = 16f;

    public Missile(Actor actor){
        super(actor);
        damage = MISSILE_DAMAGE;
        modelName = MODEL_NAME;
    }

    public void die(){
        sound.Event effect = new sound.Event(getPosition(), getVelocity(),sound.Library.findByName(DEATH_EFFECT));
        effect.gain = EFFECT_VOLUME;
        sound.Manager.addEvent(effect);

        if(ParticleSystem.isEnabled()){
            for(int i = 0; i < 256; i++){
                ParticleSystem.addParticle( new FireParticle(this,Vector3f.newRandom(1)));
            }
        }

        ParticleSystem.removeFountain(particleFountain);
        delete();
    }
    
    @Override
    public void onFirstUpdate(){
        sound.Event effect = new sound.Event(getPosition(), getVelocity(),sound.Library.findByName(SHOOT_EFFECT));
        effect.gain = EFFECT_VOLUME;
        sound.Manager.addEvent(effect);

        if(ParticleSystem.isEnabled()){
            particleFountain = new graphics.particles.ParticleFountain<PlasmaParticle>(PlasmaParticle.class,this);
            ParticleSystem.addFountain(particleFountain);
        }
    }
    
    public static long getShotCoolDown() {
        return DEFAULT_DELAY;
    }
}

