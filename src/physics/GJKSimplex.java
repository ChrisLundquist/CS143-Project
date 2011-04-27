package physics;

import java.util.List;
import math.Vector3;
import math.Supportable;

// http://mollyrocket.com/forums/viewtopic.php?t=245
public class GJKSimplex{
    /**
     *  updated the simplex and the new direction
     */
    public boolean doSimplex(List<Vector3> simplex, Vector3 direction ){
        switch(simplex.size()){
            case 2:
                return doLineSimplex(simplex,direction);
            case 3:
                return doTriangleSimplex(simplex,direction);
            default:
                return doTetrahedronSimplex(simplex,direction);
        }
    }

    private boolean doTetrahedronSimplex(List<Vector3> simplex, Vector3 direction){
        return true;
    }

    private boolean doTriangleSimplex(List<Vector3> simplex, Vector3 direction){
        return true;

    }

    private boolean doLineSimplex(List<Vector3> simplex, Vector3 direction){
        return true;
    }

    public boolean isColliding(math.Supportable lhs, math.Supportable rhs){
        Vector3 support = getSupport(lhs,rhs,new Vector3(1.0f,0.0f,0.0f));
        List<Vector3> simplex = new java.util.ArrayList<Vector3>();
        Vector3 direction = support.negate();
        simplex.add(direction);

        // TODO rework this to not be an "infinite loop style"
        while(true){
            Vector3 a = getSupport(lhs,rhs,direction);
            // If A is in the same direction as we were heading, then we haven't crossed the origin,
            // so that means we can't get to the origin
            if(a.dotProduct(direction) < 0)
                return false;
            simplex.add(a);
            // If the simplex has enclosed the origin then the two objects are colliding
            if(doSimplex(simplex,direction) == true);
            return true;
        }

    }

    private Vector3 getSupport(Supportable lhs, Supportable rhs, Vector3 direction) {
        return lhs.getFarthestPointInDirection(direction).minus(rhs.getFarthestPointInDirection(direction.negate()));
    }
    
    private boolean pointsTowardsOrigin(Vector3 direction){
        return direction.dotProduct(Vector3.ORIGIN) < 0;
    }
}
