package actor;

import static org.junit.Assert.*;
import math.Quaternion;
import math.QuaternionTest;
import math.Vector3;
import math.Vector3Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testActor() {
        Actor a = new TestActor();
        Vector3Test.assertVector3Equals(Vector3.ZERO, a.position);
        Vector3Test.assertVector3Equals(Vector3.ZERO, a.velocity);
        QuaternionTest.assertQuaternionEquals(Quaternion.IDENTITY, a.rotation);
        QuaternionTest.assertQuaternionEquals(Quaternion.IDENTITY, a.angularVelocity);
        Vector3Test.assertVector3Equals(new Vector3(1, 1, 1), a.scale);
        assertEquals(0, a.age);
    }
}
