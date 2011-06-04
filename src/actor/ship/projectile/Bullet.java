package actor.ship.projectile;

import actor.Actor;
import graphics.core.Model;
import graphics.particles.ParticleSystem;
import graphics.particles.Plasma;
import graphics.particles.generators.Explosion;

public class Bullet extends Projectile {
    private static final long serialVersionUID = -3860927022451699968L;
    protected static int BULLET_DAMAGE = 5;

    protected static final String MODEL_NAME = Model.Models.BULLET;
    private static final String SOUND_EFFECT = "Gun1.wav";
    private static final float EFFECT_VOLUME = 0.5f;
    private static final float BULLET_SPEED = 1.0f;
    protected static final long DEFAULT_DELAY = 100;

    public Bullet(Actor actor){
        super(actor);
        damage = BULLET_DAMAGE;
        velocity.timesEquals(BULLET_SPEED);
    }

    public void handleCollision(Actor other){
        // Don't shoot our parents
        if (parentId.equals(other.getId()))
            return;
        
        // This isn't handled in die because we only want to make particles on contact
        velocity.timesEquals(0);
        if(ParticleSystem.isEnabled())
            ParticleSystem.addEvent( new Explosion<Plasma>(Plasma.class,this));

        die();
    }

    @Override
    public void onFirstUpdate(){
        // Our velocity isn't quite accurate, but it will be good enough
        sound.Event effect = new sound.Event(getPosition(), getVelocity(),sound.Library.findByName(SOUND_EFFECT));
        effect.gain = EFFECT_VOLUME;
        sound.Manager.addEvent(effect);
    }

    public static long getShotCoolDown() {
        return DEFAULT_DELAY;
    }
}
