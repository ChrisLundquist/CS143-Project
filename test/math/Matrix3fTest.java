package math;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Matrix3fTest {
     private static final float EPSILON = 0.0000001f;
    static Matrix3f scale2, scale3;

    @Before
    public void setUp() throws Exception {
         scale2 = new Matrix3f(2,0,0,
                0,2,0,
                0,0,2);
         scale3 = new Matrix3f(3,0,0,
                0,3,0,
                0,0,3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMatrix3f() {
        // Should be the identity matrix
        Matrix3f identity = new Matrix3f();
        assertEquals(identity.entries[0],1.0f,0.0);
        assertEquals(identity.entries[1],0.0f,0.0);
        assertEquals(identity.entries[2],0.0f,0.0);
        
        assertEquals(identity.entries[3],0.0f,0.0);
        assertEquals(identity.entries[4],1.0f,0.0);
        assertEquals(identity.entries[5],0.0f,0.0);
        
        assertEquals(identity.entries[6],0.0f,0.0);
        assertEquals(identity.entries[7],0.0f,0.0);
        assertEquals(identity.entries[8],1.0f,0.0);
    }

    @Test
    public void testMatrix3fFloatFloatFloatFloatFloatFloatFloatFloatFloat() {
        Matrix3f m = new Matrix3f(1,2,3,4,5,6,7,8,9);
        
        for(int i = 0; i < 9; i++)
            assertEquals(m.entries[i],i + 1,EPSILON);
    }

    @Test
    public void testMatrix3fFloatArray() {
        float[] entries = new float[9];
        for(int i = 0; i < 9; ++i)
            entries[i] = i;
        
        Matrix3f m = new Matrix3f(entries);
        for(int i = 0; i < 9; i++)
            assertEquals(m.entries[i],entries[i],EPSILON);
    }
    
    @Test
    public void testNewFromQuaternion(){
        assertEquals(Matrix3f.IDENTITY,Quaternion.IDENTITY.toMatrix3f());
        assertEquals(Matrix3f.IDENTITY,Matrix3f.newFromQuaternion(Quaternion.IDENTITY));
    }

    @Test
    public void testNewFromScale(){
        assertEquals(Matrix3f.IDENTITY, Matrix3f.newFromScale(new Vector3f(1,1,1)));
        assertEquals(scale2, Matrix3f.newFromScale(new Vector3f(2,2,2)));
        assertEquals(scale3, Matrix3f.newFromScale(new Vector3f(3,3,3)));

    }
    @Test
    public void testTimesVector3() {
        Vector3f v = new Vector3f(1,2,3);
        assertEquals(scale2.times(v),new Vector3f(2,4,6));
        assertEquals(scale3.times(v),new Vector3f(3,6,9));
    }

    
    @Test
    public void testTimesMatrix3f(){
        Matrix3f scale4 = new Matrix3f(4,0,0,0,4,0,0,0,4);
        
        assertEquals(scale4, scale2.times(scale2));
    }
    
    @Test
    public void testTimesFloat() {
        assertEquals(scale2,Matrix3f.IDENTITY.times(2));
        assertEquals(scale3,Matrix3f.IDENTITY.times(3));
    }

    @Test
    public void testTranspose() {
        assertEquals(Matrix3f.IDENTITY,Matrix3f.IDENTITY.transpose());
        assertEquals(scale2,scale2.transpose());
        
        Matrix3f m = new Matrix3f(1,2,3,4,5,6,7,8,9);
        
        Matrix3f mTranspose = new Matrix3f(1,4,7,2,5,8,3,6,9);
        
        assertEquals(mTranspose, m.transpose());
        assertEquals(mTranspose.transpose(),m);
        assertEquals(m,m.transpose().transpose());
    }
    
    @Test
    public void testEquals(){
        assertEquals(Matrix3f.IDENTITY,Matrix3f.IDENTITY);
        assertEquals(Matrix3f.IDENTITY, new Matrix3f());

        assertFalse(scale2.equals(Matrix3f.IDENTITY));
        assertFalse(scale2.equals(scale3));
        assertFalse(scale3.equals(scale2));
    }
}
