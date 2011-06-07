package graphics.particles.generators;

import graphics.particles.Particle;
import graphics.particles.ParticleSystem;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import actor.interfaces.Movable;
/**
 * 
 * @author Durandal
 * based on http://stackoverflow.com/questions/299998/instantiating-object-of-type-parameter
 * @param <T>
 */
public abstract class ParticleGenerator <T extends Particle > {
    protected Constructor<? extends T> ctor;
    protected Movable source;
    public int intensity;


    public ParticleGenerator(Class<? extends T> impl, actor.interfaces.Movable source) {
        try {
            ctor = impl.getConstructor();
        } catch (SecurityException e) {
            e.printStackTrace();
            return;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        this.source = source;
        intensity = 1;
    }

    public void generateParticles(){
        if (ParticleSystem.enabled)
            for(int i = 0; i < 4 * intensity; ++i)
                try {
                    ParticleSystem.addParticle(newParticle());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
    }
    
    public T newParticle(){
        T particle = null;
        try {
            particle = ctor.newInstance();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return configureParticule(particle);
    }
    
    abstract protected T configureParticule(T particle);

    public void update(){
        generateParticles();
    }
    public ParticleGenerator<? extends Particle> setIntensity(int i) {
        intensity = i;
        return this;
    }
}