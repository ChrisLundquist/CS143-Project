package graphics.core;

import static org.junit.Assert.*;
import graphics.core.Polygon;
import graphics.core.Vertex;

import java.util.List;
import math.Vector3f;
import math.Vector3Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PolygonTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public final void testPolygonCollectionOfVertex() {
        List<Vertex> verticies = new java.util.ArrayList<Vertex>();
        
        verticies.add(new Vertex(1, 1, 10));
        verticies.add(new Vertex(-1, 1, 10));
        verticies.add(new Vertex(-1, -1, 10));
        verticies.add(new Vertex(1, -1, 10));
        
        Polygon p = new Polygon(verticies);
        
        assertEquals(4, p.verticies.size());
        Vector3Test.assertVector3Equals(Vector3f.UNIT_Z, p.normal);
    }

    @Test
    public final void testReflectDirection() {
        List<Vertex> verticies = new java.util.ArrayList<Vertex>();
        
        verticies.add(new Vertex(1, 1, 10));
        verticies.add(new Vertex(-1, 1, 10));
        verticies.add(new Vertex(-1, -1, 10));
        verticies.add(new Vertex(1, -1, 10));
        
        Polygon p = new Polygon(verticies);
        
        Vector3Test.assertVector3Equals(Vector3f.UNIT_Z.negate(), p.reflectDirection(Vector3f.UNIT_Z));
    }

    @Test
    public final void testParallelDirection() {
        List<Vertex> verticies = new java.util.ArrayList<Vertex>();
        
        verticies.add(new Vertex(1, 1, 10));
        verticies.add(new Vertex(-1, 1, 10));
        verticies.add(new Vertex(-1, -1, 10));
        verticies.add(new Vertex(1, -1, 10));
        
        Polygon p = new Polygon(verticies);
        
        Vector3Test.assertVector3Equals(Vector3f.ZERO, p.parallelDirection(Vector3f.UNIT_Z));
    }

    @Test
    public final void testIsIntersecting() {
        List<Vertex> verticies = new java.util.ArrayList<Vertex>();
        
        verticies.add(new Vertex(1, 1, 10));
        verticies.add(new Vertex(-1, 1, 10));
        verticies.add(new Vertex(-1, -1, 10));
        verticies.add(new Vertex(1, -1, 10));
        
        Polygon p = new Polygon(verticies);
        
        assertTrue(p.isIntersecting(Vector3f.ORIGIN, Vector3f.UNIT_Z)); 
        assertFalse(p.isIntersecting(Vector3f.ORIGIN, Vector3f.UNIT_Y));
        assertFalse(p.isIntersecting(Vector3f.ORIGIN, Vector3f.UNIT_X));
        assertTrue(p.isIntersecting(Vector3f.ORIGIN, new Vector3f(0.099f, 0.099f, 1)));
        assertFalse(p.isIntersecting(Vector3f.ORIGIN, new Vector3f(0.11f, 0.11f, 1)));
        assertFalse(p.isIntersecting(new Vector3f(2, 0, 0), Vector3f.UNIT_Z));
        assertFalse(p.isIntersecting(new Vector3f(0, 2, 0), Vector3f.UNIT_Z));
        assertFalse(p.isIntersecting(new Vector3f(-2, 0, 0), Vector3f.UNIT_Z));
        assertFalse(p.isIntersecting(new Vector3f(0, -2, 0), Vector3f.UNIT_Z));
        assertTrue(p.isIntersecting(new Vector3f(1, 0, 0), Vector3f.UNIT_Z));
        assertTrue(p.isIntersecting(new Vector3f(0, 1, 0), Vector3f.UNIT_Z));
        assertTrue(p.isIntersecting(new Vector3f(-1, 0, 0), Vector3f.UNIT_Z));
        assertTrue(p.isIntersecting(new Vector3f(0, -1, 0), Vector3f.UNIT_Z));
        assertFalse(p.isIntersecting(new Vector3f(0, 0, 20), Vector3f.UNIT_Z));
    }

}
