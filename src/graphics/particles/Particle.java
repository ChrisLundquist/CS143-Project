package graphics.particles;

import javax.media.opengl.GL2;

import math.Vector3;
import math.Vector4;

public abstract class Particle implements actor.Velocitable, actor.Positionable {
    protected final static int DEFAULT_LIFETIME = 120;
    protected math.Vector4 color;
    protected int lifetime, age;
    protected math.Vector3 position, velocity;
    protected float size;

    Particle(){
        color = new Vector4(1,1,1,1);
        position = new Vector3();
        velocity = new Vector3();
        size = 1.0f;
        age = 0;
        lifetime = DEFAULT_LIFETIME;
    }

    public void draw( GL2 gl ){ 
        final float halfSize = size / 2f;
        final float x = getPosition().x - halfSize;
        final float y = getPosition().x - halfSize;
        final float xs = getPosition().x + halfSize;
        final float ys = getPosition().y + halfSize;
        // Particle as small rectangle.
        gl.glBegin(GL2.GL_QUADS); {
            gl.glTexCoord2f( 0f, 0f );
            gl.glVertex3f( x, y, getPosition().z );
            gl.glTexCoord2f( 1f, 0f );
            gl.glVertex3f( xs, y, getPosition().z );
            gl.glTexCoord2f( 1f, 1f );
            gl.glVertex3f( xs, ys, getPosition().z );
            gl.glTexCoord2f( 0f, 1f );
            gl.glVertex3f( x, ys, getPosition().z );
        } gl.glEnd();
    }

    public float getLifetime() {
        return lifetime; 
    }

    @Override
    public Vector3 getPosition() {
        return position;
    }
    
    public Particle setPosition(Vector3 newPosition){
        position = newPosition;
        return this;
    }

    @Override
    public Vector3 getVelocity() {
        return velocity;
    }

    public boolean isAlive() {
        return (lifetime > 0.0);
    }

    @Override
    public Particle setVelocity(Vector3 vel) {
        this.velocity = vel;
        return this;
    }

    abstract protected void update();
}