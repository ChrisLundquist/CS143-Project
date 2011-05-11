
package graphics.particles;

import javax.media.opengl.*;

import math.Vector3;
/**
 * Particle System for each particle
 * @author Tim Mikeladze
 *
 */
public class ParticleSystem extends Particle implements actor.Velocitable, actor.Positionable {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;

    private float lifetime;// = 100;
    private float decay;// = 5f;

    // private float size = 0.005f;
    private float size;// = 2f;
   // private float pos[] = {10.0f, 20.0f, -30f};
    private float pos[] = {0.0f, 0.0f, 0f};
    private float speed[] = {0.0f, 0.0f, 0.0f};
    
    /**
     * Defaults position to 0,0,0
     * @param lifetime
     * @param decay
     * @param size
     */
    ParticleSystem(float lifetime, float decay, float size)
    {
        colorG = 1;
        colorB = 1f;
        colorA = 1f;
        
        if( lifetime != 0) { this.lifetime = lifetime; }
        if( decay != 0) { this.decay = decay; }
        if( size != 0) { this.size = size; }

        pos[X] = pos[Y] = pos[Z] = 0f;
    }
 
    public ParticleSystem(float x, float y, float z, float lifetime, float decay, float size) {
        colorG = 1;
        colorB = 1f;
        colorA = 1f;
        
        if( lifetime != 0) { this.lifetime = lifetime; }
        if( decay != 0) { this.decay = decay; }
        if( size != 0) { this.size = size; }
        
        pos[X] = x;
        pos[Y] = y;
        pos[Z] = z;
    }

    protected void updateColor() {
        colorR -= 0.015f;
        colorG -= 0.05f;
        colorB -= 0.2f;
    }

    public float getLifetime() { return lifetime; }

    public float getPosX() { return pos[X]; }
    public float getPosY() { return pos[Y]; }
    public float getPosZ() { return pos[Z]; }

    public float getSpeedX() { return speed[X]; }
    public float getSpeedY() { return speed[Y]; }
    public float getSpeedZ() { return speed[Z]; }

    public void setSpeed( float sx, float sy, float sz ) 
    { 
        speed[X] = sx;
        speed[Y] = sy;
        speed[Z] = sz;
    }
    public void incSpeedX( float ds ) { speed[X] += ds; }
    public void incSpeedY( float ds ) { speed[Y] += ds; }
    public void incSpeedZ( float ds ) { speed[Z] += ds; }
    public boolean isAlive() { return (lifetime > 0.0); }
    public void evolve()
    {
        lifetime -= decay;
        // Update locaton.
        for(int i=0; i<3; i++)
            pos[i] += speed[i];
    }

    public void draw( GL2 gl )
    { 
        final float halfSize = size / 2f;
        final float x = pos[X]-halfSize;
        final float y = pos[Y]-halfSize;
        final float xs = pos[X]+halfSize;
        final float ys = pos[Y]+halfSize;
        // Particle as small rectangle.
        gl.glBegin(GL2.GL_QUADS); {
            gl.glTexCoord2f( 0f, 0f );
            gl.glVertex3f( x, y, pos[Z] );
            gl.glTexCoord2f( 1f, 0f );
            gl.glVertex3f( xs, y, pos[Z] );
            gl.glTexCoord2f( 1f, 1f );
            gl.glVertex3f( xs, ys, pos[Z] );
            gl.glTexCoord2f( 0f, 1f );
            gl.glVertex3f( x, ys, pos[Z] );
        } gl.glEnd();
    }

    @Override
    public Vector3 getVelocity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector3 getPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object setVelocity(Vector3 vel) {
        // TODO Auto-generated method stub
        return null;
    }

   
}