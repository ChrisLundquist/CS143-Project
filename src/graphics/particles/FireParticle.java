
package graphics.particles;

import graphics.Texture;

import javax.media.opengl.GL2;

import math.Vector3f;
/**
 * Particle System for each particle
 * @author Tim Mikeladze, Chris Lundquist
 *
 */
public class FireParticle extends Particle  {
    /**
     * Creates a new ParticleSystem with the given parameters. lifetime, decay, and size can not be 0
     * @param position
     * @param lifetime
     * @param decay
     * @param size
     */
    public FireParticle(actor.Actor actor,Vector3f direction) {
        super(actor,direction);
    }

    protected void update() {
        age++;

        color.x -= 0.015f;
        color.y -= 0.05f;
        color.z -= 0.2f;
    }
    public void draw( GL2 gl ){ 
        // Select texture and draw.
        gl.glEnable(GL2.GL_TEXTURE_2D);
        if( getLifetime() > 200 ) {
            Texture.findByName(ParticleSystem.RED).bind(gl);
        } 
        else if(getLifetime() > 50) {
            Texture.findByName(ParticleSystem.ORANGE).bind(gl);   
        }
        else {
            Texture.findByName(ParticleSystem.YELLOW).bind(gl);
        }
        super.draw(gl);
    }
}