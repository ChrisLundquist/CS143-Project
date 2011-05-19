package physics;

import java.util.List;
import math.Vector3f;
import math.Supportable;

// http://mollyrocket.com/forums/viewtopic.php?t=245
public class GJKSimplex{
    private static final int MAX_ITTERATIONS = 20;

    static boolean containsOrigin(List<Vector3f> simplex) {
        // If we don't have 4 points, then we can't enclose the origin in R3
        if(simplex.size() < 4)
            return false;
        
        Vector3f a = simplex.get(3); 
        Vector3f b = simplex.get(2); 
        Vector3f c = simplex.get(1); 
        Vector3f d = simplex.get(0); 

        // Compute all the edges we will use first, to avoid computing the same edge twice.
        Vector3f ac = c.minus(a);
        Vector3f ab = b.minus(a);
        Vector3f bc = c.minus(b);
        Vector3f bd = d.minus(b);
        Vector3f ad = d.minus(a);
        Vector3f ba = ab.negate();
        Vector3f ao = a.negate();
        Vector3f bo = b.negate();

        
        /* We need to find the normals of all the faces
         * of a tetrahedron
         * 
         * Tetrahedron net (unfolded)
         * A-----------------B-----------------A
         *  \               / \               /
         *   \             /   \             /
         *    \  AC x AB  /     \  AB x AD  /
         *     \         /       \         /
         *      \       /         \       /
         *       \     /  BC x BD  \     /
         *        \   /             \   /
         *         \ /               \ /
         *          C-----------------D
         *           \               /
         *            \             /
         *             \  AD x AC  /
         *              \         /
         *               \       /
         *                \     /
         *                 \   /
         *                  \ /
         *                   A
         */
        Vector3f abc = ac.cross(ab);
        Vector3f bcd = bc.cross(bd);
        Vector3f adb = ab.cross(ad);
        Vector3f acd = ad.cross(ac);

        /*
         * We don't know which way our sides are described, so we could have an inside out
         * tetrahedron.
         * 
         * So we multiple two dot products, the first tells us which way the normal is facing
         * and the second tells us which way the origin is from that face, if they are the same
         * sign then the origin and the vertex opposite that face are in the same direction.
         * 
         * Since we just want to know if they are the same sign we multiple the two dot products
         * together and see if the product is positive.
         * 
         * For the origin to be within the tetrahedron, it must be on the inside of all four faces.
         */
        return
            (abc.dotProduct(ad) * abc.dotProduct(ao) >= 0.0f) &&
            (bcd.dotProduct(ba) * bcd.dotProduct(bo) >= 0.0f) &&
            (adb.dotProduct(ac) * adb.dotProduct(ao) >= 0.0f) &&
            (acd.dotProduct(ab) * acd.dotProduct(ao) >= 0.0f);
    }

    /**
     *  update the simplex and the new direction
     */
    static public Vector3f findSimplex(List<Vector3f> simplex){
        switch(simplex.size()){
            case 2:
                return findLineSimplex(simplex);
            case 3:
                return findTriangleSimplex(simplex);
            default:
                return findTetrahedronSimplex(simplex);
        }
    }

    static public Vector3f findLineSimplex(List<Vector3f> simplex){
        Vector3f newDirection;
        //A is the point added last to the simplex 
        Vector3f a = simplex.get(1); 
        Vector3f b = simplex.get(0); 
        Vector3f ab = b.minus(a); 
        Vector3f ao = Vector3f.ORIGIN.minus(a); 

        if (ab.sameDirection(ao)) { 
            // The new direction is perpendicular to AB pointing to the origin
            newDirection = ab.cross(ao).cross(ab); 
        } else { 
            newDirection = ao; 
        } 
        return newDirection;
    }

    static public Vector3f findTriangleSimplex(List<Vector3f> simplex){
        Vector3f newDirection;
        //A is the point added last to the simplex 
        Vector3f a = simplex.get(2); 
        Vector3f b = simplex.get(1); 
        Vector3f c = simplex.get(0); 
        Vector3f ao = Vector3f.ORIGIN.minus(a); 

        // The AB edge
        Vector3f ab = b.minus(a); 
        // the AC edge
        Vector3f ac = c.minus(a); 
        // The normal to the triangle
        Vector3f abc = ab.cross(ac); 

        if (abc.cross(ac).sameDirection(ao)) {
            // The origin is above
            if (ac.sameDirection(ao)) { 
                simplex.clear(); 
                simplex.add(a); 
                simplex.add(c); 
                newDirection = ac.cross(ao).cross(ac); 
            } 
            else 
                if (ab.sameDirection(ao)) { 
                    simplex.clear(); 
                    simplex.add(a); 
                    simplex.add(b); 
                    newDirection = ab.cross(ao).cross(ab); 
                } 
                else { 
                    simplex.clear(); 
                    simplex.add(a); 
                    newDirection = ao; 
                } 
        } 
        else {
            // The origin is below
            if (ab.cross(abc).sameDirection(ao)) { 
                if (ab.sameDirection(ao)) { 
                    simplex.clear(); 
                    simplex.add(a); 
                    simplex.add(b); 
                    newDirection = ab.cross(ao).cross(ab); 
                } 
                else { 
                    simplex.clear(); 
                    simplex.add(a); 
                    newDirection = ao; 
                } 
            } 
            else { 
                if (abc.sameDirection(ao)) { 
                    //the simplex stays A, B, C 
                    newDirection = abc; 
                } 
                else { 
                    simplex.clear(); 
                    simplex.add(a);
                    simplex.add(c); 
                    simplex.add(b); 
                    
                    newDirection = abc.negate(); 
                } 
            } 
        } 
        return newDirection;
    }

    static public Vector3f findTetrahedronSimplex(List<Vector3f> simplex){
        //A is the point added last to the simplex 
        Vector3f a = simplex.get(3); 
        Vector3f b = simplex.get(2); 
        Vector3f c = simplex.get(1); 
        Vector3f d = simplex.get(0); 

        Vector3f ao = a.negate(); 
        Vector3f ab = b.minus(a); 
        Vector3f ac = c.minus(a); 
        Vector3f ad = d.minus(a); 
        Vector3f acd = ac.cross(ad); 
        Vector3f adb = ad.cross(ab); 

        //the side (positive or negative) of B, C and D relative to the planes of ACD, ADB and ABC respectively 
        int BsideOnACD = acd.dotProduct(ab) > 0.0f ? 1 : 0; 
        int CsideOnADB = adb.dotProduct(ac) > 0.0f ? 1 : 0; 

        //whether the origin is on the same side of ACD/ADB/ABC as B, C and D respectively 
        boolean ABsameAsOrigin = (acd.dotProduct(ao) > 0.0f ? 1 : 0) == BsideOnACD; 
        boolean ACsameAsOrigin = (adb.dotProduct(ao) > 0.0f ? 1 : 0) == CsideOnADB; 
        //if the origin is not on the side of B relative to ACD 
        if (!ABsameAsOrigin) { 
            //B is farthest from the origin among all of the tetrahedron's points, so remove it from the list and go on with the triangle case 
            simplex.remove(b); 
            //the new direction is on the other side of ACD, relative to B 
        } 
        //if the origin is not on the side of C relative to ADB 
        else if (!ACsameAsOrigin) { 
            //C is farthest from the origin among all of the tetrahedron's points, so remove it from the list and go on with the triangle case 
            simplex.remove(c); 
            //the new direction is on the other side of ADB, relative to C 
        } 
        //if the origin is not on the side of D relative to ABC 
        else //if (!ADsameAsOrigin) { 
            //D is farthest from the origin among all of the tetrahedron's points, so remove it from the list and go on with the triangle case 
            simplex.remove(d); 
        //the new direction is on the other side of ABC, relative to D 

        //go on with the triangle case 
        //TODO: maybe we should restrict the depth of the recursion, just like we restricted the number of iterations in BodiesIntersect? 
        return findTriangleSimplex(simplex);
    }

    static Vector3f getSupport(Supportable lhs, Supportable rhs, Vector3f direction) {
        return lhs.getFarthestPointInDirection(direction).minus(rhs.getFarthestPointInDirection(direction.negate()));
    }

    static public boolean isColliding(math.Supportable lhs, math.Supportable rhs){
        List<Vector3f> simplex = new java.util.ArrayList<Vector3f>();
        Vector3f support = getSupport(lhs,rhs,Vector3f.UNIT_X);
        simplex.add(support);
        Vector3f direction = support.negate();
        int loopCounter = 0;
        // If A is in the same direction as we were heading, then we haven't crossed the origin,
        // so that means we can't get to the origin
        while((support = getSupport(lhs,rhs,direction)).sameDirection(direction)){
            simplex.add(support);
            
            if(loopCounter > MAX_ITTERATIONS)
                break;
            
            // If the simplex has enclosed the origin then the two objects are colliding
            if(containsOrigin(simplex))
                return true;
            
            direction = findSimplex(simplex);
            
            // If our dot product gave us the zero vector, our vectors must be colinear and we contain the origin and further test will break
            if (direction.equals(Vector3f.ZERO))
                return true;
        }
        return false;
    }
}
