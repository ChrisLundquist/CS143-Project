package math;

import actor.interfaces.Positionable;
import actor.interfaces.Velocitable;

public class Sphere implements Supportable, Positionable, Velocitable{
    Vector3f position, velocity;
    float radius;

    public Sphere(Vector3f position, float radius){
        this.radius = radius;
        this.position = position;
        velocity = new Vector3f(Vector3f.ZERO);
    }

    @Override
    public Vector3f getFarthestPointInDirection(Vector3f direction) {
        return getPosition().plus(direction.normalize().times(radius));
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }
    
    public Sphere setPosition(Vector3f newPosition){
        position = newPosition;
        return this;
    }

    public boolean isColliding(Sphere other){
        return distance(other) < (radius + other.radius);
    }

    public float distance(Sphere other){
        return  position.minus(other.position).magnitude();
    }

    public String toString(){
        return new String("Position: " + getPosition() + "\nRadius: " + radius);
    }

    @Override
    public Vector3f getVelocity() {
        return velocity;
    }

    @Override
    public Sphere setVelocity(Vector3f vel) {
        velocity = vel;
        return this;
    }
}
