package physics;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import physics.SpatialTree;
import math.Sphere;

import java.util.Iterator;
import java.util.List;

public class SpatialTreeTest {
    private static final float LOWER_BOUND = -100.0f;
    private static final float UPPER_BOUND = 100.0f;
    private static final float INCREMENT = 5.0f;
    private static final float RADIUS = 0.5f;

    private List<Sphere> objects;

    @Before
    public void setUp() throws Exception {
        objects = new java.util.ArrayList<Sphere>();
        for (float x = LOWER_BOUND; x <= UPPER_BOUND; x += INCREMENT)
            for (float y = LOWER_BOUND; y <= UPPER_BOUND; y += INCREMENT)
                for (float z = LOWER_BOUND; z <= UPPER_BOUND; z += INCREMENT)
                    objects.add(new math.Sphere(new math.Vector3(x, y, z),
                            RADIUS));
        //System.out.println(objects.size());
    }

    @After
    public void tearDown() throws Exception {
        objects = null;
    }

    @Test
    public final void testSpatialTreeCollectionOfE() {
        new SpatialTree<Sphere>(objects);
    }

    @Test
    public final void testSpatialTreeCollectionOfEInt() {
        new SpatialTree<Sphere>(objects, 40);

    }

    @Test
    public final void testAdd() {
        // fail("Not yet implemented");
    }

    @Test
    public final void testIterator() {
        Iterator<SpatialTree<Sphere>> it;
        SpatialTree<Sphere> tree = new SpatialTree<Sphere>(objects);
        int count = 0;

        it = tree.leafInterator();

        while (it.hasNext()) {
            for (Sphere s : it.next()) {
                assertTrue(s instanceof Sphere);
                count++;
            }
        }

        assertEquals(objects.size(), count);
    }

    @Test
    public final void testLeafInterator() {
        int count = 0;
        Iterator<SpatialTree<Sphere>> it;

        SpatialTree<Sphere> tree = new SpatialTree<Sphere>(objects);

        it = tree.leafInterator();

        while (it.hasNext()) {
            assertTrue(it.next() instanceof SpatialTree<?>);
            count++;
        }
        //System.out.println(count);

        // fail("Not yet implemented");
    }

}
