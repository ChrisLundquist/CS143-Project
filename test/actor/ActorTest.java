package actor;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import actor.Actor;
import actor.Asteroid;

public class ActorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRemoveActorId() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddActor() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveActor() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateActors() {
        fail("Not yet implemented");
    }

    @Test
    public void testCheckCollisions() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsColliding() {
        fail("Not yet implemented");
    }
    
    
    /**
     * Simple test for updateFromNetwork()
     */
    @Test
    public void testUpdateFromNetwork() {
        List<Actor> update = new java.util.ArrayList<Actor>();
        update.add(new Asteroid());
        update.add(new Asteroid());
        update.add(new Asteroid());      

        for (int i = 0; i < 3; i++) {
            Actor.updateFromNetwork(update, null);
        }    
    }

    @Test
    public void testMain() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetActorCount() {
        fail("Not yet implemented");
    }

    @Test
    public void testActor() {
        fail("Not yet implemented");
    }

    @Test
    public void testChangeYaw() {
        fail("Not yet implemented");
    }

    @Test
    public void testChangePitch() {
        fail("Not yet implemented");
    }

    @Test
    public void testChangeRoll() {
        fail("Not yet implemented");
    }

    @Test
    public void testDelete() {
        fail("Not yet implemented");
    }

    @Test
    public void testGenerateId() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDirection() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetModel() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPosition() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetRotation() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetRotation() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetSize() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetVelocity() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetActors() {
        fail("Not yet implemented");
    }

    @Test
    public void testHandleCollision() {
        fail("Not yet implemented");
    }

    @Test
    public void testRender() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetPosition() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetSizeFloat() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetSizeVector3() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetVelocity() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetFarthestPointInDirection() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

    @Test
    public void testDampenAngularVelocity() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindById() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetId() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAngularVelocity() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetTimeStamp() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAge() {
        fail("Not yet implemented");
    }

    @Test
    public void testSetModel() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetModelName() {
        fail("Not yet implemented");
    }

}
