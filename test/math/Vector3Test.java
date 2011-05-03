package math;

import static org.junit.Assert.*;

import math.Vector3;

import org.junit.Test;

public class Vector3Test {

    @Test
    public void testVector3() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testVector3FloatFloatFloat() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testVector3Vector3() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testToString() {
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
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testDotProduct() {
        fail("Not yet implemented"); // TODO
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
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testEqualsObject() {
        fail("Not yet implemented"); // TODO
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
