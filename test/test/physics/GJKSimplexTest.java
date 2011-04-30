package test.physics;

import static org.junit.Assert.*;
import graphics.Model;

import org.junit.Test;

import physics.GJKSimplex;
import test.math.SphereTest;
import math.*;

public class GJKSimplexTest {

    @Test
    public void testFindSimplex() {
        //fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsColliding() {
        sphereColliding();
        cubeColliding();
        
    }
    
    private void cubeColliding(){
        Model cube = Model.findOrCreateByName("cube.obj");
    }
    
    private void sphereColliding(){
        // spheres at an arbitrary location and is obviously not colliding
        Sphere s4441 = new Sphere(new Vector3(4.0f,4.0f,4.0f),1.0f);
        Sphere s8881 = new Sphere(new Vector3(8.0f,8.0f,8.0f),1.0f);
        Sphere s0004 = new Sphere(new Vector3(),4.0f);
        Sphere s0505051 = new Sphere(new Vector3(0.5f,0.5f,0.5f),1.0f);
        Sphere s050501 = new Sphere(new Vector3(0.5f,0.5f,0.0f),1.0f);

        assertFalse(GJKSimplex.isColliding(s4441, SphereTest.UNIT_SPHERE));
        // Order shouldn't matter for detection
        assertFalse(GJKSimplex.isColliding(SphereTest.UNIT_SPHERE, s4441));
        assertFalse(GJKSimplex.isColliding(s4441, s050501));
        assertFalse(GJKSimplex.isColliding(s4441, s0505051));

        // According to the simplex algorithm two of the same object should be colliding
        // We handle this before we test them against each other to keep the detection simpler
        assertTrue(GJKSimplex.isColliding(SphereTest.UNIT_SPHERE, SphereTest.UNIT_SPHERE));
        assertTrue(GJKSimplex.isColliding(s4441, s4441));

        // partially colliding Spheres
        assertTrue(GJKSimplex.isColliding(SphereTest.UNIT_SPHERE, s0505051));
        assertTrue(GJKSimplex.isColliding(s0505051,SphereTest.UNIT_SPHERE));
        
        // Partially Colliding
        assertTrue(GJKSimplex.isColliding(s050501, s0505051));
        
        // non unit radius
        assertTrue(GJKSimplex.isColliding(SphereTest.UNIT_SPHERE, s0004));
        assertFalse(GJKSimplex.isColliding(s4441, s0004));
        assertFalse(GJKSimplex.isColliding(s0004,s8881));
    }

}
