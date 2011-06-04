package actor.ship.projectile;

import graphics.particles.Fire;
import graphics.particles.ParticleSystem;
import graphics.particles.generators.Explosion;
import actor.Actor;

public class Nuke extends Missile {
    private static final long serialVersionUID = -1273682307711962750L;
    protected static final int NUKE_DAMAGE = 5000;
    protected static final float EFFECT_VOLUME = 64f;
    protected static final long NUKE_DELAY = 5000;


    public Nuke(Actor actor) {
        super(actor);
        damage = NUKE_DAMAGE;
        velocity.timesEquals(0.6f);
        scale.timesEquals(2);
    }
    
    public void die(){
        sound.Event effect = new sound.Event(getPosition(), getVelocity(),sound.Library.findByName(DEATH_EFFECT));
        effect.gain = EFFECT_VOLUME;
        sound.Manager.addEvent(effect);

        velocity.timesEquals(0);
        if(ParticleSystem.isEnabled())
            ParticleSystem.addEvent((new Explosion<Fire>(Fire.class,this)).setIntensity(1024));

        ParticleSystem.removeGenerator(particleGenerator);
        delete();
    }
    
    public static long getShotCoolDown() {
        return NUKE_DELAY;
    }
}
