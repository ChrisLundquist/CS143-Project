package math;

public interface Supportable {
    
    /**
     * 
     * @param direction the direction to find a support in
     * @return the farthest point in the given direction given the space
     */
    Vector3f getFarthestPointInDirection(Vector3f direction);
}
