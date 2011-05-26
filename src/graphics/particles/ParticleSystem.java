package graphics.particles;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.*;


/**
 * @author Tim Mikeladze, Chris Lundquist
 *
 */
public class ParticleSystem {
    //Max amount of particles
    private static int MAX_PARTICLES = 2500;
    private static List<Particle> particles = new LinkedList<Particle>();
    public static boolean enabled = false;

    public static boolean addParticle(Particle particle){
        // We can't add a particle if we have too many
        if(particles.size() > MAX_PARTICLES)
            return false;

        particles.add(particle);
        return true;
    }

    /**
     * Draws the particles
     * @param gl
     */
    public static void render( GL2 gl ){
        // Particles are transparent.
        gl.glEnable( GL2.GL_BLEND );
        gl.glBlendFunc( GL2.GL_SRC_ALPHA, GL2.GL_ONE );
        gl.glDepthMask( false );
        // Loop over particles.
        Iterator<Particle> it = particles.iterator();
        while(it.hasNext()){
            Particle particle = it.next();
            if ( ! particle.isAlive() ) {
                it.remove();
                break;
            }
            particle.update();
            particle.draw( gl );
        }
        gl.glDepthMask( true );
        gl.glDisable( GL2.GL_BLEND );
    }
    public static boolean isEnabled() {
        return enabled;
    }
}
