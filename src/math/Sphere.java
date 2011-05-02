package math;

import actor.Positionable;

public class Sphere implements Supportable, Positionable{
    Vector3 position;
    float radius;

    public Sphere(Vector3 position, float radius){
        this.radius = radius;
        this.position = position;
    }

    @Override
    public Vector3 getFarthestPointInDirection(Vector3 direction) {
        return getPosition().plus(direction.normalize().times(radius));
    }

    @Override
    public Vector3 getPosition() {
        return position;
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
}
