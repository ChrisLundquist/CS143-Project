package actor.ship.projectile;

import actor.Actor;
import graphics.core.Model;
import graphics.particles.*;
import graphics.particles.generators.Explosion;
import graphics.particles.generators.ParticleGenerator;

public class Missile extends Projectile {
    private static final long serialVersionUID = -8381240274687476481L;
    protected static final int MISSILE_DAMAGE = 100;
    protected static final String MODEL_NAME = Model.Models.MISSILE;
    protected static final String SHOOT_EFFECT = "missile_firing.wav";
    protected static final String DEATH_EFFECT = "explode.wav";
    protected static final float EFFECT_VOLUME = 16f;
    protected ParticleGenerator<? extends Particle> particleGenerator;


    public Missile(Actor actor){
        super(actor);
        damage = MISSILE_DAMAGE;
        modelName = MODEL_NAME;
    }

    public void die(){
        if (sound.Manager.enabled) {
            sound.Event effect = new sound.Event(getPosition(), getVelocity(),sound.Library.findByName(DEATH_EFFECT));
            effect.gain = EFFECT_VOLUME;
            sound.Manager.addEvent(effect);
        }

        velocity.timesEquals(0);
        if(ParticleSystem.isEnabled())
            ParticleSystem.addEvent((new Explosion<Fire>(Fire.class,this)).setIntensity(96));

        ParticleSystem.removeGenerator(particleGenerator);
        delete();
    }

    @Override
    public void onFirstUpdate(){
        if (sound.Manager.enabled) {
            sound.Event effect = new sound.Event(getPosition(), getVelocity(),sound.Library.findByName(SHOOT_EFFECT));
            effect.gain = EFFECT_VOLUME;
            sound.Manager.addEvent(effect);
        }
  
        if(ParticleSystem.isEnabled()){
            particleGenerator = new graphics.particles.generators.Exhaust<Plasma>(Plasma.class,this);
            ParticleSystem.addGenerator(particleGenerator);
        }
    }

    public static long getShotCoolDown() {
        return DEFAULT_DELAY;
    }
}

