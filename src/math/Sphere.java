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
        return getPosition().minus(direction.normalize().times(radius));
    }

    @Override
    public Vector3 getPosition() {
        return position;
    }
}
