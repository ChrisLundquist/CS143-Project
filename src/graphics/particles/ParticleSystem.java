package graphics.particles;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.*;

import graphics.Texture;

/**
 * Fire particle, to use call setParameters() with certain arguments, and then draw(gl), 
 * just calling draw(gl) will set parameters to default 
 * 
 * To create a nice fountain of particles set the parameters to  
 * lifetime = 100f
 *  decay = 1f
 *  size = 0.4f;

 * @author Tim Mikeladze, Chris Lundquist
 *
 */
public class ParticleSystem {
    //Max amount of particles
    private static int MAX_PARTICLES = 250;    
    private static List<Particle> particles = new LinkedList<Particle>();

    //paths to textures
    protected static final String TEXTURE_FOLDER = "assets/images/particles/";
    protected static final String RED    = TEXTURE_FOLDER + "redParticle.jpg";
    protected static final String ORANGE = TEXTURE_FOLDER + "orangeParticle.jpg";
    protected static final String YELLOW = TEXTURE_FOLDER + "yellowParticle.jpg";
    protected static final String WHITE  = TEXTURE_FOLDER + "whiteParticle.jpg";

    public static void initialize( GL2 gl){
        // Particles are transparent.
        gl.glEnable( GL.GL_BLEND );    
        gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE );
        gl.glEnable( GL.GL_TEXTURE_2D );
        Texture.findOrCreateByName(YELLOW);
        Texture.findOrCreateByName(ORANGE);
        Texture.findOrCreateByName(RED);
        Texture.findOrCreateByName(WHITE);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    }

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
    }
}