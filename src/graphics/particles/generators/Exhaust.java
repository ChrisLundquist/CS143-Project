package graphics.particles.generators;

import graphics.particles.Particle;
import actor.interfaces.Movable;

public class Exhaust<T extends Particle> extends ParticleGenerator<T> {
    public Exhaust(Class<? extends T> impl, Movable source) {
        super(impl, source);
    }

    @Override
    protected T configureParticule(T particle) {
        particle.setPosition(new math.Vector3f(source.getPosition()));
        particle.setVelocity(source.getVelocity().negate().plus(math.Vector3f.newRandom(0.001f).times(source.getVelocity().magnitude2())));
        return particle;
    }
}
