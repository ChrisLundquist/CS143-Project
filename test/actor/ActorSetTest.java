package actor;

import static org.junit.Assert.*;

import java.util.Set;

import math.Vector3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActorSetTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testActorSet() {
        ActorSet as = new ActorSet();
    }
    
    @Test
    public void testAdd() {
        ActorSet as = new ActorSet(1);
        as.add(new TestActor(Vector3.randomPosition(100)));
    }

    @Test
    public void testIterator() {
        Set<ActorId> ids = new java.util.HashSet<ActorId>();
        ActorSet as = new ActorSet();
        for (int i = 0; i < 1024; i ++)
            as.add(new TestActor(Vector3.randomPosition(100)));
       
        
        for (Actor a: as) {
            assertTrue(a instanceof TestActor);
            assertTrue(ids.add(a.id));
        }
        assertEquals(1024, ids.size());
    }

    @Test
    public void testRemove() {
        // Test that we can remove an actor while iterating over the set
        ActorSet as = new ActorSet();
        for (int i = 0; i < 100; i ++)
            as.add(new TestActor(Vector3.randomPosition(100)));

        int count = 0;
        for (Actor a: as) {
            if (count % 7 == 0)
                as.remove(a);

            count ++;
        }
    }
    
    @Test
    public void testGetBounds() {
        ActorSet as = new ActorSet();
        for (int i = 0; i < 1024; i ++)
            as.add(new TestActor(Vector3.randomPosition(100)));
        fail();
    }
}
