package physics;

import java.util.List;
import math.Vector3;
import math.Supportable;

// http://mollyrocket.com/forums/viewtopic.php?t=245
public class GJKSimplex{
    static public boolean containsOrigin(List<Vector3> simplex) {
        // If we don't have 4 points, then we can't enclose the origin in R3
        if(simplex.size() < 4)
            return false;
        Vector3 a = simplex.get(3); 
        Vector3 b = simplex.get(2); 
        Vector3 c = simplex.get(1); 
        Vector3 d = simplex.get(0); 

        Vector3 ao = Vector3.ORIGIN.minus(a); 
        Vector3 ab = b.minus(a); 
        Vector3 ac = c.minus(a); 
        Vector3 ad = d.minus(a); 
        Vector3 abc = ab.cross(ac); 
        Vector3 acd = ac.cross(ad); 
        Vector3 adb = ad.cross(ab); 

        //the side (positive or negative) of B, C and D relative to the planes of ACD, ADB and ABC respectively 
        int BsideOnACD = acd.dotProduct(ab) > 0.0f ? 1 : 0; 
        int CsideOnADB = adb.dotProduct(ac) > 0.0f ? 1 : 0; 
        int DsideOnABC = abc.dotProduct(ad) > 0.0f ? 1 : 0; 

        //whether the origin is on the same side of ACD/ADB/ABC as B, C and D respectively 
        boolean ABsameAsOrigin = (acd.dotProduct(ao) > 0.0f ? 1 : 0) == BsideOnACD; 
        boolean ACsameAsOrigin = (adb.dotProduct(ao) > 0.0f ? 1 : 0) == CsideOnADB; 
        boolean ADsameAsOrigin = (abc.dotProduct(ao) > 0.0f ? 1 : 0) == DsideOnABC; 

        //if the origin is on the same side as all B, C and D, the origin is inside the tetrahedron and thus there is a collision 
        if (ABsameAsOrigin && ACsameAsOrigin && ADsameAsOrigin) { 
            return true; 
        } 
        return false;
    }

    /**
     *  update the simplex and the new direction
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
        //A is the point added last to the simplex 
        Vector3 a = simplex.get(1); 
        Vector3 b = simplex.get(0); 
        Vector3 ab = b.minus(a); 
        Vector3 ao = Vector3.ORIGIN.minus(a); 

        if (ab.sameDirection(ao)) { 
            direction = ab.cross(ao).cross(ab); 
        } else { 
            direction = ao; 
        } 
        return direction;
    }

    static private Vector3 findTriangleSimplex(List<Vector3> simplex, Vector3 direction){
        //A is the point added last to the simplex 
        Vector3 a = simplex.get(2); 
        Vector3 b = simplex.get(1); 
        Vector3 c = simplex.get(0); 
        Vector3 ao = Vector3.ORIGIN.minus(a); 

        // The AB edge
        Vector3 ab = b.minus(a); 
        // the AC edge
        Vector3 ac = c.minus(a); 
        // The normal to the triangle
        Vector3 abc = ab.cross(ac); 

        if (abc.cross(ac).sameDirection(ao)) {
            // The origin is above
            if (ac.sameDirection(ao)) { 
                simplex.clear(); 
                simplex.add(a); 
                simplex.add(c); 
                direction = ac.cross(ao).cross(ac); 
            } 
            else 
                if (ab.sameDirection(ao)) { 
                    simplex.clear(); 
                    simplex.add(a); 
                    simplex.add(b); 
                    direction = ab.cross(ao).cross(ab); 
                } 
                else { 
                    simplex.clear(); 
                    simplex.add(a); 
                    direction = ao; 
                } 
        } 
        else {
            // The origin is below
            if (ab.cross(abc).sameDirection(ao)) { 
                if (ab.sameDirection(ao)) { 
                    simplex.clear(); 
                    simplex.add(a); 
                    simplex.add(b); 
                    direction = ab.cross(ao).cross(ab); 
                } 
                else { 
                    simplex.clear(); 
                    simplex.add(a); 
                    direction = ao; 
                } 
            } 
            else { 
                if (abc.sameDirection(ao)) { 
                    //the simplex stays A, B, C 
                    direction = abc; 
                } 
                else { 
                    simplex.clear(); 
                    simplex.add(a); 
                    simplex.add(c); 
                    simplex.add(b); 

                    direction = abc.negate(); 
                } 
            } 
        } 
        return direction;
    }

    static private Vector3 findTetrahedronSimplex(List<Vector3> simplex, Vector3 direction){
        //A is the point added last to the simplex 
        Vector3 a = simplex.get(3); 
        Vector3 b = simplex.get(2); 
        Vector3 c = simplex.get(1); 
        Vector3 d = simplex.get(0); 

        Vector3 ao = a.negate(); 
        Vector3 ab = b.minus(a); 
        Vector3 ac = c.minus(a); 
        Vector3 ad = d.minus(a); 
        Vector3 abc = ab.cross(ac); 
        Vector3 acd = ac.cross(ad); 
        Vector3 adb = ad.cross(ab); 

        //the side (positive or negative) of B, C and D relative to the planes of ACD, ADB and ABC respectively 
        int BsideOnACD = acd.dotProduct(ab) > 0.0f ? 1 : 0; 
        int CsideOnADB = adb.dotProduct(ac) > 0.0f ? 1 : 0; 
        int DsideOnABC = abc.dotProduct(ad) > 0.0f ? 1 : 0; 

        //whether the origin is on the same side of ACD/ADB/ABC as B, C and D respectively 
        boolean ABsameAsOrigin = (acd.dotProduct(ao) > 0.0f ? 1 : 0) == BsideOnACD; 
        boolean ACsameAsOrigin = (adb.dotProduct(ao) > 0.0f ? 1 : 0) == CsideOnADB; 
        //if the origin is not on the side of B relative to ACD 
        if (!ABsameAsOrigin) { 
            //B is farthest from the origin among all of the tetrahedron's points, so remove it from the list and go on with the triangle case 
            simplex.remove(b); 
            //the new direction is on the other side of ACD, relative to B 
            direction = acd.times(-BsideOnACD);                    
        } 
        //if the origin is not on the side of C relative to ADB 
        else if (!ACsameAsOrigin) { 
            //C is farthest from the origin among all of the tetrahedron's points, so remove it from the list and go on with the triangle case 
            simplex.remove(c); 
            //the new direction is on the other side of ADB, relative to C 
            direction = adb.times(-CsideOnADB); 
        } 
        //if the origin is not on the side of D relative to ABC 
        else //if (!ADsameAsOrigin) { 
            //D is farthest from the origin among all of the tetrahedron's points, so remove it from the list and go on with the triangle case 
            simplex.remove(d); 
        //the new direction is on the other side of ABC, relative to D 
        direction = abc.times(-DsideOnABC); 


        //go on with the triangle case 
        //TODO: maybe we should restrict the depth of the recursion, just like we restricted the number of iterations in BodiesIntersect? 
        return findTriangleSimplex(simplex,direction);
    }

    static public Vector3 getSupport(Supportable lhs, Supportable rhs, Vector3 direction) {
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
        while((a = getSupport(lhs,rhs,direction)).dotProduct(direction) < 0){
            simplex.add(a);
            // If the simplex has enclosed the origin then the two objects are colliding
            direction = findSimplex(simplex,direction);

            if(containsOrigin(simplex))
                return true;
        }
        return false;
    }
}
