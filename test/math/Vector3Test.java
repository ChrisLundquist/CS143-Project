package math;

import static org.junit.Assert.*;
import java.util.Random;
import math.Vector3f;
import org.junit.Test;


public class Vector3Test {
    private static final double EPSILON = 1.00E-6;
    protected static Random gen = new Random(42); /* constant seed to make our tests determined */

    public static Vector3f getRandom(int range){
        return new Vector3f(
                gen.nextFloat() * 2.0f * range - range,
                gen.nextFloat() * 2.0f * range - range,
                gen.nextFloat() * 2.0f * range - range
        );

    }

    @Test
    public void testVector3() {
        Vector3f v = new Vector3f();

        assertEquals(0.0f, v.x, EPSILON);
        assertEquals(0.0f, v.y, EPSILON);
        assertEquals(0.0f, v.z, EPSILON);
    }

    @Test
    public void testVector3FloatFloatFloat() {
        float x = 1.23f;
        float y = -2.34f;
        float z = 0.23f;

        Vector3f v = new Vector3f(x, y, z);

        assertEquals(x, v.x, EPSILON);
        assertEquals(y, v.y, EPSILON);
        assertEquals(z, v.z, EPSILON);
    }

    @Test
    public void testSameDirection(){
        assertTrue(Vector3f.UNIT_X.sameDirection(Vector3f.UNIT_X));
        assertFalse(Vector3f.UNIT_X.sameDirection(Vector3f.UNIT_X.negate()));
        assertFalse(Vector3f.UNIT_X.sameDirection(Vector3f.UNIT_Y));
        assertFalse(Vector3f.UNIT_X.sameDirection(Vector3f.UNIT_Z));
    }

    @Test
    public void testVector3Vector3() {
        Vector3f original = new Vector3f(1.23f, -2.34f, 0.23f);

        Vector3f copy = new Vector3f(original);

        assertVector3Equals(original, copy);
    }

    @Test
    public void testToString() {
        Vector3f v = new Vector3f(1.23f, -2.34f, 0.23f);
        assertEquals("<1.23000, -2.34000, 0.230000>", v.toString());
    }

    @Test
    public void testTimesFloat() {
        Vector3f v1 = new Vector3f(0.0f, -2.0f, 6.3f);
        assertVector3Equals(new Vector3f(0.0f, -4.0f, 12.6f),v1.times(2.0f));
        assertVector3Equals(v1,v1.times(1.0f));
    }

    @Test
    public void testPlus() {
        Vector3f v1 = new Vector3f(1.0f,1.0f,1.0f);
        Vector3f v1_copy = new Vector3f(v1);
        Vector3f v2 = new Vector3f( -3.0f, 0.0f, 7.2f);
        Vector3f v2_copy = new Vector3f(v2);
        assertVector3Equals(new Vector3f(4.0f, 1.0f, -6.2f), v1.minus(v2));
        assertVector3Equals(v1_copy, v1);
        assertVector3Equals(v2_copy, v2);

    }

    @Test
    public void testPlusEquals() {
        for (int i = 0; i < 4096; i++) {
            Vector3f v = getRandom(100);
            Vector3f original = new Vector3f(v);
            Vector3f result = v.plusEquals(v);
            assertVector3Equals(original.times(2.0f), v);           
            assertVector3Equals(original.times(2.0f), result);
            assertTrue(v == result);
        }
    }

    @Test
    public void testNormalize() {
        assertEquals(Vector3f.UNIT_X, (new Vector3f(5,0,0)).normalize());
        assertEquals(Vector3f.UNIT_Y, (new Vector3f(0,5,0)).normalize());
        assertEquals(Vector3f.UNIT_Z, (new Vector3f(0,0,5)).normalize());
        assertEquals(Vector3f.ORIGIN, Vector3f.ORIGIN.normalize());

        for (int i = 0; i < 4096; i++) {
            Vector3f v = getRandom(100);
            assertEquals(1.0f, v.normalize().magnitude(), EPSILON);
        }
    }

    @Test
    public void testDotProduct() {
        assertEquals(Vector3f.UNIT_X.dotProduct(Vector3f.UNIT_X),1.0f,EPSILON);
        assertEquals(Vector3f.UNIT_X.dotProduct(Vector3f.UNIT_X.negate()),-1.0f,EPSILON);
        assertEquals(Vector3f.UNIT_X.dotProduct(Vector3f.UNIT_Y),0.0f,EPSILON);
        assertEquals(Vector3f.UNIT_X.dotProduct(Vector3f.UNIT_Y.negate()),0.0f,EPSILON);

        assertEquals(Vector3f.UNIT_X.dotProduct(Vector3f.UNIT_Z),0.0f,EPSILON);
        assertEquals(Vector3f.UNIT_X.dotProduct(Vector3f.UNIT_Z.negate()),0.0f,EPSILON);
    }

    @Test
    public void testCross() {
        assertVector3Equals(Vector3f.UNIT_Z, Vector3f.UNIT_X.cross(Vector3f.UNIT_Y));
    }

    @Test
    public void testTimesQuaternion() {
        Quaternion rotate90ccwAboutZ = new Quaternion(Vector3f.UNIT_Z, -90); // Quaternion angles are backwards to facilitate pitch, yaw and roll
        
        assertVector3Equals(Vector3f.UNIT_Y, new Vector3f(Vector3f.UNIT_X).times(rotate90ccwAboutZ));
        assertVector3Equals(Vector3f.UNIT_Z, new Vector3f(Vector3f.UNIT_Z).times(rotate90ccwAboutZ));
        assertVector3Equals(Vector3f.UNIT_X.negate(), new Vector3f(Vector3f.UNIT_Y).times(rotate90ccwAboutZ));
    }

    @Test
    public void testTimesFloatArray() {
        float[] rotate90ccwAboutZ = {
                0.0f,  1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f, 0.0f,
                0.0f,  0.0f, 1.0f, 0.0f,
                0.0f,  0.0f, 0.0f, 1.0f
        };
        
        assertVector3Equals(Vector3f.UNIT_Y, new Vector3f(Vector3f.UNIT_X).times(rotate90ccwAboutZ));
        assertVector3Equals(Vector3f.UNIT_Z, new Vector3f(Vector3f.UNIT_Z).times(rotate90ccwAboutZ));
        assertVector3Equals(Vector3f.UNIT_X.negate(), new Vector3f(Vector3f.UNIT_Y).times(rotate90ccwAboutZ));
    }

    @Test
    public void testMagnitude() {
        assertEquals(1.0f, Vector3f.UNIT_X.magnitude(), EPSILON);
        assertEquals(0.0f, Vector3f.ZERO.magnitude(), EPSILON);
        assertEquals(5.0f, new Vector3f(3.0f, 4.0f, 0.0f).magnitude(), EPSILON);
    }

    @Test
    public void testMinus() {
        for (int i = 0; i < 4096; i++) {
            Vector3f v = getRandom(100);
            Vector3f result = v.minus(v);
            assertVector3Equals(Vector3f.ZERO, result);
            assertFalse(v == result); // Minus() should return a new instances of a Vector3       
        }
    }

    @Test
    public void testMinusEquals() {
        for (int i = 0; i < 4096; i++) {
            Vector3f v = getRandom(100);
            Vector3f result = v.minusEquals(v);
            assertVector3Equals(Vector3f.ZERO, v);           
            assertVector3Equals(Vector3f.ZERO, result);
            assertTrue(v == result);
        }
    }

    @Test
    public void testEqualsVector3() {
        assertTrue(Vector3f.UNIT_X.equals(Vector3f.UNIT_X));
        assertTrue(Vector3f.UNIT_X.equals(new Vector3f(Vector3f.UNIT_X)));
    }

    @Test
    public void testMagnitude2() {
        assertEquals(1.0f, Vector3f.UNIT_X.magnitude2(), EPSILON);
        assertEquals(0.0f, Vector3f.ZERO.magnitude2(), EPSILON);
        assertEquals(25.0f, new Vector3f(3.0f, 4.0f, 0.0f).magnitude2(), EPSILON);
    }

    @Test
    public void testNegate() {
        for (int i = 0; i < 4096; i++) {
            Vector3f v = getRandom(100);
            assertVector3Equals(Vector3f.ZERO, v.plus(v.negate()));
        }
    }

    public static void assertVector3Equals(Vector3f a, Vector3f b) {
        assertVector3Equals(a, b, EPSILON);
    }


    public static void assertVector3Equals(Vector3f a, Vector3f b, double delta) {
        assertEquals(a.x, b.x, delta);
        assertEquals(a.y, b.y, delta);
        assertEquals(a.z, b.z, delta);
    }
}
