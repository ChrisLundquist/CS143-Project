package graphics.particles;

import graphics.particles.generators.ParticleGenerator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.media.opengl.*;


/**
 * @author Tim Mikeladze, Chris Lundquist
 *
 */
public class ParticleSystem {
    //Max amount of particles
    private static int MAX_PARTICLES = 4096;
    static List<Particle> particles = new LinkedList<Particle>();
    static Queue<Particle> newParticles = new java.util.concurrent.ConcurrentLinkedQueue<Particle>();
    static Queue<ParticleGenerator<? extends Particle>> generators = new java.util.concurrent.ConcurrentLinkedQueue<ParticleGenerator<? extends Particle>>();
    static Queue<ParticleGenerator<? extends Particle>> events = new java.util.concurrent.ConcurrentLinkedQueue<ParticleGenerator<? extends Particle>>();

    public static boolean enabled = true, fountainsEnabled = true;

    public static boolean addParticle(Particle particle){
        // We can't add a particle if we have too many
        if(particles.size() > MAX_PARTICLES)
            return false;

        return newParticles.offer(particle);
    }

    public static boolean addGenerator(ParticleGenerator<? extends Particle> generator){
        return generators.add(generator);
    }
    
    public static boolean addEvent(ParticleGenerator<? extends Particle> event){
        return events.add(event);
    }

    /**
     * Draws the particles
     * @param gl
     */
    public static void render( GL2 gl ){
        Particle particle;

        for(ParticleGenerator<? extends Particle> event : events)
            event.update();
        events.clear();
        if(fountainsEnabled)
            for(ParticleGenerator<? extends Particle> fountain : generators)
                fountain.update();

        // Check for new particles
        while ((particle = newParticles.poll())!= null)
            particles.add(particle);


        // Particles are transparent.
        gl.glEnable( GL2.GL_BLEND );
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glBlendFunc( GL2.GL_SRC_ALPHA, GL2.GL_ONE );
        gl.glDepthMask( false );

        gl.glEnable(GL2.GL_POINT_SMOOTH);
        //gl.glPointSize(size);
        gl.glBegin(GL2.GL_POINTS);

        // Loop over particles.
        Iterator<Particle> it = particles.iterator();
        while(it.hasNext()){
            particle = it.next();
            if (particle.isAlive()) {
                particle.update();
                particle.draw(gl);
            } else {
                it.remove();
            }
        }

        gl.glEnd();
        gl.glDepthMask( true );
        gl.glDisable( GL2.GL_BLEND );
        gl.glEnable(GL2.GL_LIGHTING);

    }
    public static boolean isEnabled() {
        return enabled;
    }

    public static synchronized void removeGenerator(ParticleGenerator<? extends Particle> particleFountain) {
        generators.remove(particleFountain);
    }
}
