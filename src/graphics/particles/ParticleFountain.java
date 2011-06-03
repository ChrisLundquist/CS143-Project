package graphics.particles;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import actor.Movable;
/**
 * 
 * @author Durandal
 * based on http://stackoverflow.com/questions/299998/instantiating-object-of-type-parameter
 * @param <T>
 */
public class ParticleFountain <T extends Particle >{
    private static final float SLOW_FACTOR = 0.5f;
    private Constructor<? extends T> ctor;
    Movable source;
    public int intensity;


    public ParticleFountain(Class<? extends T> impl, actor.Movable source) {
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
        particle.setPosition(new math.Vector3f(source.getPosition()));
        particle.setVelocity(source.getVelocity().times(SLOW_FACTOR).plus(math.Vector3f.newRandom(0.001f)));
        
        return particle;
    }
    
    public void update(){
        generateParticles();
    }
}