package graphics.particles.generators;

import graphics.particles.Particle;
import actor.Movable;

public class Exhaust<T extends Particle> extends ParticleGenerator<T> {
    private static final float SLOW_FACTOR = 0.5f;

    public Exhaust(Class<? extends T> impl, Movable source) {
        super(impl, source);
    }

    @Override
    protected T configureParticule(T particle) {
        particle.setPosition(new math.Vector3f(source.getPosition()));
        particle.setVelocity(source.getVelocity().times(SLOW_FACTOR).plus(math.Vector3f.newRandom(0.001f)));
        return particle;
    }
}
