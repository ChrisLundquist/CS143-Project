package graphics.particles;

import javax.media.opengl.GL2;

import math.Vector3f;
import math.Vector4f;

public abstract class Particle implements actor.Velocitable, actor.Positionable {
    protected final static int DEFAULT_LIFETIME = 90;
    protected final static float SLOW_FACTOR = 0.05f;
    protected math.Vector4f color;
    protected int age;
    protected math.Vector3f position, velocity;
    protected float size;

    Particle(){
        color = new Vector4f(1,1,1,1);
        position = new Vector3f();
        velocity = new Vector3f();
        size = 1.0f;
        age = 0;
    }

    public void draw( GL2 gl ){
        gl.glColor4f(color.x, color.y, color.z, color.t);
        gl.glVertex3f(position.x,position.y,position.z);
    }

    public float getLifetime() {
        return DEFAULT_LIFETIME - age; 
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
        return (age < DEFAULT_LIFETIME);
    }

    @Override
    public Particle setVelocity(Vector3f vel) {
        this.velocity = vel;
        return this;
    }

    protected void update(){
        age++;
        position.plusEquals(velocity);
    }
}