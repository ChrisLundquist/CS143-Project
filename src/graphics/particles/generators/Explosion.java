package graphics.particles.generators;

import graphics.particles.Particle;
import actor.interfaces.Movable;
import math.Vector3f;

public class Explosion<T extends Particle> extends ParticleGenerator<T> {
    public float scale;
    public Explosion(Class<? extends T> impl, Movable source) {
        super(impl, source);
        intensity = 8;
    }
    @Override
    protected T configureParticule(T particle) {
        particle.setPosition(new Vector3f(source.getPosition()));
        particle.setVelocity(Vector3f.newRandom(1).normalize().timesEquals(intensity / 512.0f));
        return particle;
    }
}
