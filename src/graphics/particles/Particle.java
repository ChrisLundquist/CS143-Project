package graphics.particles;

import javax.media.opengl.GL2;

import math.Vector3f;
import math.Vector4f;

public abstract class Particle implements actor.Velocitable, actor.Positionable {
    protected final static int DEFAULT_LIFETIME = 120;
    protected final static float SLOW_FACTOR = 0.1f;
    protected math.Vector4f color;
    protected int lifetime, age;
    protected math.Vector3f position, velocity;
    protected float size;

    Particle(){
        color = new Vector4f(1,1,1,1);
        position = new Vector3f();
        velocity = new Vector3f();
        size = 1.0f;
        age = 0;
        lifetime = DEFAULT_LIFETIME;
    }
    
    Particle(actor.Actor actor, Vector3f direction){
        super();
        velocity = actor.getVelocity().minus(direction.times(SLOW_FACTOR));
        position = actor.getFarthestPointInDirection(direction);
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
    public Vector3f getPosition() {
        return position;
    }
    
    public Particle setPosition(Vector3f newPosition){
        position = newPosition;
        return this;
    }

    @Override
    public Vector3f getVelocity() {
        return velocity;
    }

    public boolean isAlive() {
        return (lifetime > 0.0);
    }

    @Override
    public Particle setVelocity(Vector3f vel) {
        this.velocity = vel;
        return this;
    }

    abstract protected void update();
}