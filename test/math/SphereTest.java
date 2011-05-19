package math;
import static org.junit.Assert.*;

import org.junit.Test;

public class SphereTest {
    public static final Sphere UNIT_SPHERE = new Sphere(new Vector3f(),1.0f);
    @Test
    public void testGetFarthestPointInDirection() {
        
        // If we are at the origin the further point in any direction should be the unit vector
        assertEquals(Vector3f.UNIT_X, UNIT_SPHERE.getFarthestPointInDirection(Vector3f.UNIT_X));
        assertEquals(Vector3f.UNIT_Y, UNIT_SPHERE.getFarthestPointInDirection(Vector3f.UNIT_Y));
        assertEquals(Vector3f.UNIT_Z, UNIT_SPHERE.getFarthestPointInDirection(Vector3f.UNIT_Z));
        
        //Scale our direction vectors to make sure non normalized vectors don't break us
        assertEquals(Vector3f.UNIT_X, UNIT_SPHERE.getFarthestPointInDirection(Vector3f.UNIT_X.times(3.0f)));
        assertEquals(Vector3f.UNIT_Y, UNIT_SPHERE.getFarthestPointInDirection(Vector3f.UNIT_Y.times(7.2f)));
        assertEquals(Vector3f.UNIT_Z, UNIT_SPHERE.getFarthestPointInDirection(Vector3f.UNIT_Z.times(13.7f)));

    }

    @Test
    public void testGetPosition() {
        assertEquals(Vector3f.ORIGIN,UNIT_SPHERE.getPosition());
    }
    
    @Test
    public void testGetVelocity(){
        assertEquals(Vector3f.ZERO,UNIT_SPHERE.getPosition());
    }

}
