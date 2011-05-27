package graphics.particles;

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
    private static List<Particle> particles = new LinkedList<Particle>();
    private static Queue<Particle> newParticles = new java.util.concurrent.ConcurrentLinkedQueue<Particle>(); 
    public static boolean enabled = true;

    public static boolean addParticle(Particle particle){
        // We can't add a particle if we have too many
        if(particles.size() > MAX_PARTICLES)
            return false;

        newParticles.offer(particle);
        return true;
    }

    /**
     * Draws the particles
     * @param gl
     */
    public static void render( GL2 gl ){
        Particle particle;
        
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
}
