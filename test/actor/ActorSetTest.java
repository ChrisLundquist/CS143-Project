package actor;

import static org.junit.Assert.*;

import java.util.Set;

import math.Vector3f;

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
        assertTrue(as instanceof ActorSet);
        assertEquals(0, as.size());
    }

    @Test
    public void testAdd() {
        ActorSet as = new ActorSet(1);
        as.add(new TestActor(Vector3f.randomPosition(100)));
        assertEquals(1, as.size());
    }

    @Test
    public void testIterator() {
        Set<ActorId> ids = new java.util.HashSet<ActorId>();
        ActorSet as = new ActorSet();
        for (int i = 0; i < 1024; i ++)
            as.add(new TestActor(Vector3f.randomPosition(100)));


        for (Actor a: as) {
            assertTrue(a instanceof TestActor);
            assertTrue(ids.add(a.id));
        }
        assertEquals(1024, ids.size());
        assertEquals(1024, as.size());

        for (int i = 0; i < 1024; i ++)
            as.add(new TestActor(Vector3f.randomPosition(100)));
        assertEquals(2048, as.size());

        int count = 0;
        for (Actor a: as) {
            assertTrue(a instanceof TestActor);
            ids.add(a.id);
            count ++;
        }
        assertEquals(2048, ids.size());
        assertEquals(2048, count);
    }

    @Test
    public void testRemove() {
        // Test that we can remove an actor while iterating over the set
        ActorSet as = new ActorSet();
        for (int i = 0; i < 100; i ++)
            as.add(new TestActor(Vector3f.randomPosition(100)));

        int count = 0;
        for (Actor a: as) {
            if (count % 7 == 0)
                as.remove(a);

            count ++;
        }
        assertEquals(100, count);
    }

    @Test
    public void testGetBounds() {
        ActorSet as = new ActorSet();
        for (int i = 0; i < 1024; i ++)
            as.add(new TestActor(Vector3f.randomPosition(100)));
        //fail();
    }

    @Test
    public void testMultiThreadIteration() {
        ActorSet as = new ActorSet();
        Thread[] threads = new Thread[16];

        for (int i = 0; i < threads.length; i++)
            threads[i] = new TestWorker(as, i);

        for (Thread t: threads)
            t.start();


        try {
            for (Thread t: threads)
                t.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private class TestWorker extends Thread {
        private ActorSet actorSetRef;
        private int id;

        public TestWorker(ActorSet actors, int id) {
            this.actorSetRef = actors;
            this.id = id;
        }

        public void run() {
            for (int i = 0; i < 4096; i ++) {
                Actor a = new TestActor(Vector3f.randomPosition(100));
                a.id = new ActorId(id);
                actorSetRef.add(a);
                for (Actor it: actorSetRef)
                    if (it.id.getPlayerId() != id && (i & 15) == 12)
                        actorSetRef.remove(it);
            }
        }
    }
}
