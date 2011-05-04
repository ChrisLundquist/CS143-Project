package math;

import static org.junit.Assert.*;
import org.junit.Test;

public class QuaternionTest {

    private static final double EPSILON = 1.00E-8;

    @Test
    public void testEqualsQuaternion() {
        Quaternion a = new Quaternion(0.232f, 12.232f, 234.23f, 76.23f);
        Quaternion b = new Quaternion(0.232f, 12.232f, 234.23f, 76.23f);
        assertQuaternionEquals(a, b);
    }
    
    @Test
    public void testBasicQuaternionProperties() {
        Quaternion n = new Quaternion(-1, 0, 0, 0);
        Quaternion i = new Quaternion(0, 1, 0, 0);
        Quaternion j = new Quaternion(0, 0, 1, 0);
        Quaternion k = new Quaternion(0, 0, 0, 1);

        assertQuaternionEquals(Quaternion.IDENTITY, n.times(n)); // (-1)^2 == 1

        // i^2 = j^2 = k^2 = ijk = -1
        assertQuaternionEquals(n, i.times(i)); // i^2 = -1
        assertQuaternionEquals(n, j.times(j)); // j^2 = -1
        assertQuaternionEquals(n, k.times(k)); // k^2 = -1
        assertQuaternionEquals(n, i.times(j).times(k)); // ijk = -1
    }

    @Test
    public void testToMatrixString() {
        // This transformation matrix should rotate 90 degrees about the x axis
        assertEquals(
                "| 01.000 00.000 00.000 00.000 |\n" +
                "| 00.000 00.000 01.000 00.000 |\n" +
                "| 00.000 -1.000 00.000 00.000 |\n" +
                "| 00.000 00.000 00.000 01.000 |",
                new Quaternion(Vector3.UNIT_X, 90).toMatrixString());
    }
    
    @Test
    public void testRollPitchAndYawAxies() {
        // Start with a fairly random rotation
        Quaternion r = new Quaternion(Vector3.UNIT_X, 15); 
        r.timesEquals(new Quaternion(Vector3.UNIT_Y, 7));
        r.timesEquals(new Quaternion(Vector3.UNIT_Z, -328));

        assertVector3Equals(Vector3.UNIT_X.times(r), r.pitchAxis());
        assertVector3Equals(Vector3.UNIT_Y.times(r), r.yawAxis());
        assertVector3Equals(Vector3.UNIT_Z.times(r), r.rollAxis());
    }
   

    @Test
    public void testGimbleLock() {
        Quaternion rotation = new Quaternion();
        rotation.timesEquals(new Quaternion(Vector3.UNIT_X, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Z, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Y, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Z, -90));
        
        // There should be a better way to compare within epsilon
        assertQuaternionEquals(Quaternion.IDENTITY, rotation);
        
        // Test that rotating through 360 degrees on all axies brings us back to normal
        rotation.timesEquals(new Quaternion(Vector3.UNIT_X, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_X, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_X, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_X, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Y, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Y, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Y, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Y, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Z, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Z, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Z, 90));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Z, 90));
        
        // Here we get a negative real component by the transformation matrix is the same
        assertQuaternionEquals(Quaternion.IDENTITY, rotation);        
    }
            
    @Test
    public void testMagnitude() {
        Quaternion rotation = new Quaternion();
        assertEquals(1.0, rotation.magnitude(), EPSILON);
    }

    @Test
    public void testMagnitude2() {
        Quaternion rotation = new Quaternion();
        assertEquals(1.0, rotation.magnitude2(), EPSILON);
    }

    @Test
    public void testNormalize() {
        Quaternion rotation = new Quaternion();
        assertEquals(1.0, rotation.magnitude(), EPSILON);
        assertQuaternionEquals(Quaternion.IDENTITY, rotation.normalize());

        for( int i = 0; i < (4 * 4096); i++){
            // Rotate a lot to denormalize our vector
            rotation.timesEquals(new Quaternion(Vector3.UNIT_X, i));
            rotation.timesEquals(new Quaternion(Vector3.UNIT_Y, i));
            rotation.timesEquals(new Quaternion(Vector3.UNIT_Z, i));
            rotation.timesEquals(new Quaternion(Vector3.UNIT_X, i));
        }
        rotation.normalize();
    }

    @Test
    public void testInverse() {
        // Test the inverse works as expected
        // Sometimes the inverse isn't quite accurate, but every near due to floating point
        Quaternion rotation = new Quaternion();
        rotation.timesEquals(new Quaternion(Vector3.UNIT_X, 40));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Y, 40));
        rotation.timesEquals(new Quaternion(Vector3.UNIT_Z, 40));
        assertQuaternionEquals(Quaternion.IDENTITY, rotation.times(rotation.inverse()));

    }

    private static void assertQuaternionEquals(Quaternion a, Quaternion b) {
        assertQuaternionEquals(a, b, EPSILON);
    }

    private void assertVector3Equals(Vector3 a, Vector3 b) {
        assertVector3Equals(a, b, EPSILON);
    }

    
    private void assertVector3Equals(Vector3 a, Vector3 b, double delta) {
        assertEquals(a.x, b.x, delta);
        assertEquals(a.y, b.y, delta);
        assertEquals(a.z, b.z, delta);
    }

    private static void assertQuaternionEquals(Quaternion a, Quaternion b, double delta) {
        assertEquals(a.w_, b.w_, delta);
        assertEquals(a.x_, b.x_, delta);
        assertEquals(a.y_, b.y_, delta);
        assertEquals(a.z_, b.z_, delta);       
    }
}
