package test.math;

import static org.junit.Assert.*;

import math.Vector3;

import org.junit.Test;

public class Vector3Test {

    private static final float EPSILON = 0.00001f;

    @Test
    public void testSameDirection(){
        assertTrue(Vector3.UNIT_X.sameDirection(Vector3.UNIT_X));
        assertFalse(Vector3.UNIT_X.sameDirection(Vector3.UNIT_X.negate()));
        assertFalse(Vector3.UNIT_X.sameDirection(Vector3.UNIT_Y));
        assertFalse(Vector3.UNIT_X.sameDirection(Vector3.UNIT_Z));
    }

    @Test
    public void testVector3Vector3() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testTimesFloat() {
        Vector3 v1 = new Vector3(0.0f, -2.0f, 6.3f);
        assertEquals(new Vector3(0.0f, -4.0f, 12.6f),v1.times(2.0f));
        assertEquals(v1,v1.times(1.0f));
    }

    @Test
    public void testPlus() {
        Vector3 v1 = new Vector3(1.0f,1.0f,1.0f);
        Vector3 v1_copy = new Vector3(v1);
        Vector3 v2 = new Vector3( -3.0f, 0.0f, 7.2f);
        Vector3 v2_copy = new Vector3(v2);
        assertEquals(new Vector3(-2.0f, 1.0f, -6.2f), v1.minus(v2));
        assertEquals(v1_copy, v1);
        assertEquals(v2_copy, v2);
        
    }

    @Test
    public void testPlusEquals() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testNormalize() {
        assertEquals(Vector3.UNIT_X, (new Vector3(5,0,0)).normalize());
        assertEquals(Vector3.UNIT_Y, (new Vector3(0,5,0)).normalize());
        assertEquals(Vector3.UNIT_Z, (new Vector3(0,0,5)).normalize());
        assertEquals(Vector3.ORIGIN, Vector3.ORIGIN.normalize());
    }

    @Test
    public void testDotProduct() {
        assertEquals(Vector3.UNIT_X.dotProduct(Vector3.UNIT_X),1.0f,EPSILON);
        assertEquals(Vector3.UNIT_X.dotProduct(Vector3.UNIT_X.negate()),-1.0f,EPSILON);
        assertEquals(Vector3.UNIT_X.dotProduct(Vector3.UNIT_Y),0.0f,EPSILON);
        assertEquals(Vector3.UNIT_X.dotProduct(Vector3.UNIT_Y.negate()),0.0f,EPSILON);

        assertEquals(Vector3.UNIT_X.dotProduct(Vector3.UNIT_Z),0.0f,EPSILON);
        assertEquals(Vector3.UNIT_X.dotProduct(Vector3.UNIT_Z.negate()),0.0f,EPSILON);
    }

    @Test
    public void testCross() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testProjectionFrom() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testProjectionTo() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testTimesQuaternion() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testTimesFloatArray() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testMagnitude() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testMinus() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testMinusEquals() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testEqualsVector3() {
        assertTrue(Vector3.UNIT_X.equals(Vector3.UNIT_X));
        assertTrue(Vector3.UNIT_X.equals(new Vector3(Vector3.UNIT_X)));
    }

    @Test
    public void testMagnitude2() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testNegate() {
        fail("Not yet implemented"); // TODO
    }

}
