package graphics.particles;

import javax.media.opengl.*;
import graphics.Texture;


public class ParticleSystem {
    
    private final int MAX_PARTICLES = 250;    
    private ParticleFire p[] = new ParticleFire[MAX_PARTICLES];
    Texture texture[] = new Texture[4];
    String RED = "assets/images/particles/redParticle.jpg";
    String ORANGE = "assets/images/particles/orangeParticle.jpg";
    String YELLOW ="assets/images/particles/yellowParticle.jpg";
    String WHITE= "assets/images/particles/whiteParticle.jpg";
   
    public ParticleSystem() {
        texture[0] = Texture.findOrCreateByName(YELLOW);
        texture[1] = Texture.findOrCreateByName(ORANGE);
        texture[2] = Texture.findOrCreateByName(RED);
        texture[3] = Texture.findOrCreateByName(WHITE);
    }
    
    public void init( GL2 gl)
    {
        // Particles are tranparent.
        gl.glEnable( GL.GL_BLEND );    
        gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE );
        
        gl.glEnable( GL.GL_TEXTURE_2D );
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    }
    
    private ParticleFire createParticle()
    {
        ParticleFire p = new ParticleFire( 500f, 0f, 0f );
        p.setSpeed( 
                0.001f - (float)Math.random() / 500.0f,
                0.008f - (float)Math.random() / 1000.0f,
                0.001f - (float)Math.random() / 500.0f 
        );
        return p;
    }
    
    public void draw( GL2 gl )
    {
        gl.glDepthMask( false );
        // Loop over particles.
        for( int i=0; i < MAX_PARTICLES; i++ )
        {
            // Create new particles for continuous effect.
            if ( p[i] == null ) 
            {
                p[i] = createParticle();
                break; // Create one particle per time step.
            }

            // Kill particle if it hits the ground or died.
            if ( p[i].getPosY() < 0.0f || ! p[i].isAlive() ) 
            {
                p[i] = createParticle();
            }

            // Apply gravity.
            p[i].incSpeedY( -0.00004f );
            p[i].evolve();

           // Select texture and draw.
            if( p[i].getLifetime() > 200 ) {
                gl.glEnable(GL2.GL_TEXTURE_2D);
                texture[0].bind(gl);
            } 
            else if(p[i].getLifetime() > 50) {
                gl.glEnable(GL2.GL_TEXTURE_2D);
                texture[1].bind(gl);
            }
            else {
                gl.glEnable(GL2.GL_TEXTURE_2D);
                texture[2].bind(gl);
            }
            p[i].draw( gl );

        }
        gl.glDepthMask( true );
    }
}