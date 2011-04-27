package physics;

import java.util.List;
import math.Vector3;
import math.Supportable;

// http://mollyrocket.com/forums/viewtopic.php?t=245
public class GJKSimplex{
    static private boolean containsOrigin(List<Vector3> simplex) {
        // If we don't have 4 points, then we can't enclose the origin in R3
        if(simplex.size() < 4)
            return false;
        // TODO
        return true;
    }

    /**
     *  updated the simplex and the new direction
     */
    static public Vector3 findSimplex(List<Vector3> simplex, Vector3 direction ){
        switch(simplex.size()){
            case 2:
                return findLineSimplex(simplex,direction);
            case 3:
                return findTriangleSimplex(simplex,direction);
            default:
                return findTetrahedronSimplex(simplex,direction);
        }
    }

    static private Vector3 findLineSimplex(List<Vector3> simplex, Vector3 direction){
        Vector3 a = simplex.get(1); // always the end of the list
        Vector3 b = simplex.get(0);

        Vector3 ab = b.minus(a);

        if(sameDirection(ab,Vector3.ORIGIN)){ /* The simplex is on the segment AB*/
            direction = trippleProduct(ab,Vector3.ORIGIN,ab);
        }else { /* The newest point is the simplex */
            direction = Vector3.ORIGIN.minus(a);
        }

        return direction;
    }

    static private Vector3 findTriangleSimplex(List<Vector3> simplex, Vector3 direction){
        return direction;

    }

    static private Vector3 findTetrahedronSimplex(List<Vector3> simplex, Vector3 direction){
        return direction;
    }

    static private Vector3 getSupport(Supportable lhs, Supportable rhs, Vector3 direction) {
        return lhs.getFarthestPointInDirection(direction).minus(rhs.getFarthestPointInDirection(direction.negate()));
    }

    static public boolean isColliding(math.Supportable lhs, math.Supportable rhs){
        Vector3 support = getSupport(lhs,rhs,new Vector3(1.0f,0.0f,0.0f));
        List<Vector3> simplex = new java.util.ArrayList<Vector3>();
        Vector3 direction = support.negate();
        simplex.add(direction);
        Vector3 a;

        // If A is in the same direction as we were heading, then we haven't crossed the origin,
        // so that means we can't get to the origin
        while(sameDirection(a = getSupport(lhs,rhs,direction),direction)){
            simplex.add(a);
            // If the simplex has enclosed the origin then the two objects are colliding
            direction = findSimplex(simplex,direction);

            if(containsOrigin(simplex))
                return true;
        }
        return false;
    }

    static private boolean sameDirection(Vector3 a, Vector3 b){
        return a.dotProduct(b) > 0;
    }

    static private Vector3 trippleProduct(Vector3 i, Vector3 j, Vector3 k){
        return i.cross(j).cross(k);
    }
}
