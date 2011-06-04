package actor;

import graphics.particles.ParticleSystem;
import graphics.particles.Plasma;
import graphics.particles.generators.Explosion;

public class SniperBullet extends Bullet {
    private static final long serialVersionUID = 7453924541312180985L;
    protected static final long DEFAULT_DELAY = 1000;

    public SniperBullet(Actor actor) {
        super(actor);
        damage = 40;
        velocity.timesEquals(5);
    }

    public void handleCollision(Actor other){
        // Don't shoot our parents
        if (parentId.equals(other.getId()))
            return;

        if(ParticleSystem.isEnabled())
            ParticleSystem.addEvent( new Explosion<Plasma>(Plasma.class,this));

        die();
    }

    public static long getShotCoolDown() {
        return DEFAULT_DELAY;
    }
}
