package physics;

import java.util.List;
import math.Vector3;

// http://mollyrocket.com/forums/viewtopic.php?t=245
public interface Collidable extends actor.Positionable{
    /**
     * 
     * @param other The other collidable object
     * @return true if the objects are in collision
     */
    boolean isColliding(Collidable other){
        Vector3 suport = getSupport();
        ArrayList<Vector3> simplex = new ArrayList<Vector3>();
        Vector3 direction = support.negate();
        simplex.add(direction);

        // TODO rework this to not be an "infinite loop style"
        while(true){
            Vector3 a = support(direction);
            // If A is in the same direction as we were heading, then we haven't crossed the origin,
            // so that means we can't get to the origin
            if(a.dot(direction) < 0)
                return false;
            simplex.add(a);
            // If the simplex has enclosed the origin then the two objects are colliding
            if(doSimplex(simplex,direction) == true);
            return true;
        }

    }

    /**
     * 
     * @return a point within this collision model
     */
    abstract Vector3 getSupport(){
        // Return our position
    }
    /**
     * 
     * @param direction The direction to find a point in
     * @return the point contained by the model in the furthest direction given
     */
    abstract Vector3 getSupport(Vector3 direction);
    /**
     *  updated the simplex and the new direction
     */
    boolean DoSimplex(List<Vector3> simplex, Vector3 direction ){
        switch(simplex.length()){
            case 2:
                return doLineSimplex(simplex,direction);
                break;
            case 3:
                return doTriangleSimplex(simplex,direction);
                break;
            default:
                return doTetrahedronSimplex(simplex,direction);
                break;
        }
    }

    boolean doTetrahedronSimplex(List<Vector3> simplex, Vector3 direction);

    boolean doTriangleSimplex(List<Vector3> simplex, Vector3 direction);

    boolean doLineSimplex(List<Vector3> simplex, Vector3 direction);
}
