package graphics.particles;

import javax.media.opengl.*;

import actor.Actor;
import graphics.Texture;

/**
 * Fire particle, to use call setParameters() with certain arguments, and then draw(gl), 
 * just calling draw(gl) will set parameters to default 
 * @author Tim Mikeladze
 *
 */
public class ParticleFire {
    
    //Max amount of particles
    private int MAX_PARTICLES = 250;    
    private ParticleSystem p[] = new ParticleSystem[MAX_PARTICLES];
    
    Texture texture[] = new Texture[4];
    
    //paths to textures
    String RED = "assets/images/particles/redParticle.jpg";
    String ORANGE = "assets/images/particles/orangeParticle.jpg";
    String YELLOW ="assets/images/particles/yellowParticle.jpg";
    String WHITE= "assets/images/particles/whiteParticle.jpg";
   
    //default parameters just in case
    float lifetime=100; float decay=5f; float size=2f;
    float x; float y; float z;
    
    /**
     * Loads the textures
     */
    public ParticleFire() {
        texture[0] = Texture.findOrCreateByName(YELLOW);
        texture[1] = Texture.findOrCreateByName(ORANGE);
        texture[2] = Texture.findOrCreateByName(RED);
        texture[3] = Texture.findOrCreateByName(WHITE);
    }
    
    /**
     * Sets particle's position relative to the actor's, lifetime, decay, size
     * @param actor 
     * @param lifetime
     * @param decay
     * @param size
     */
    public void setParameters(Actor actor, float lifetime, float decay, float size) {
       /* this.x = actor.getPosition().x;
        this.y = actor.getPosition().y;
        this.z = actor.getPosition().z;*/
        
        this.x = actor.getFarthestPointInDirection(actor.getDirection().negate()).x;
        this.y = actor.getFarthestPointInDirection(actor.getDirection().negate()).y;
        this.z = actor.getFarthestPointInDirection(actor.getDirection().negate()).z;
      
        this.lifetime = lifetime;
        this.decay = decay;
        this.size = size;
         
    }
    /**
     * Sets particle's x,y,z coordinates and lifetime,decay,size
     * @param x
     * @param y
     * @param z
     * @param lifetime
     * @param decay
     * @param size
     */
    public void setParameters(float x, float y, float z, float lifetime, float decay, float size) {
        this.x  = x;
        this.y = y;
        this.z = z;
        this.lifetime = lifetime;
        this.decay = decay;
        this.size = size;
         
    }
    /**
     * Sets particle's position relative to the Actor, defaults the other parameters
     * @param actor
     */
    public void setParameters(Actor actor) {
        this.x = actor.getFarthestPointInDirection(actor.getDirection().negate()).x;
        this.y = actor.getFarthestPointInDirection(actor.getDirection().negate()).y;
        this.z = actor.getFarthestPointInDirection(actor.getDirection().negate()).z;
        
         
    }
    /**
     * sets x,y,z position, defaults other parameters
     * @param x
     * @param y
     * @param z
     */
    public void setParameters(float x, float y, float z) {
        this.x  = x;
        this.y = y;
        this.z = z;
        
         
    }
    /**
     * Sets the position given by Vector3, defaults other parameters
     * @param Vector3
     */
    public void setParameters(math.Vector3 Vector3) {
        this.x = Vector3.x;
        this.y = Vector3.y;
        this.z = Vector3.z;
        
         
    }
    /**
     *  Sets the position given by Vector3, sets lifetime, decay, size
     * @param Vector3
     * @param lifetime
     * @param decay
     * @param size
     */
    public void setParameters(math.Vector3 Vector3, float lifetime, float decay, float size) {
        this.x  = Vector3.x;
        this.y = Vector3.y;
        this.z = Vector3.z;
        this.lifetime = lifetime;
        this.decay = decay;
        this.size = size;
         
    }
    
    public void init( GL2 gl)
    {
        // Particles are trasnparent.
        gl.glEnable( GL.GL_BLEND );    
        gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE );
        gl.glEnable( GL.GL_TEXTURE_2D );
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    }
    
    /**
     * Creates a new particle system with given parameters
     * @return
     */
    private ParticleSystem createParticle()
    {
        ParticleSystem p = new ParticleSystem(x, y, z, lifetime, decay, size);
        //ParticleSystem p = new ParticleSystem( 500f, 0f, 0f );
        p.setSpeed( 
                0.001f - (float)Math.random() / 500.0f,
                0.008f - (float)Math.random() / 1000.0f,
                0.001f - (float)Math.random() / 500.0f 
        );
        return p;
    }
    
    /**
     * Draws the particles
     * @param gl
     */
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