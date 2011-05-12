package math;

import actor.Positionable;
import actor.Velocitable;

public class Sphere implements Supportable, Positionable, Velocitable{
    Vector3 position, velocity;
    float radius;

    public Sphere(Vector3 position, float radius){
        this.radius = radius;
        this.position = position;
        velocity = new Vector3(Vector3.ZERO);
    }

    @Override
    public Vector3 getFarthestPointInDirection(Vector3 direction) {
        return getPosition().plus(direction.normalize().times(radius));
    }

    @Override
    public Vector3 getPosition() {
        return position;
    }
    
    public Sphere setPosition(Vector3 newPosition){
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
    public Vector3 getVelocity() {
        return velocity;
    }

    @Override
    public Sphere setVelocity(Vector3 vel) {
        velocity = vel;
        return this;
    }
}
