package test.physics;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import physics.GJKSimplex;
import test.math.SphereTest;
import math.*;

public class GJKSimplexTest {
    protected static Random gen = new Random(42); /* constant seed to make our tests determined */
    private Vector3 getRandomPosition(int range){
        return new Vector3(
                gen.nextFloat() * range - range / 2.0f,
                gen.nextFloat() * range - range / 2.0f,
                gen.nextFloat() * range - range / 2.0f
        );

    }

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
        actor.Asteroid cube1 = new actor.Asteroid();

        actor.Asteroid cube2 = new actor.Asteroid();
        cube2.setPosition(new Vector3(4.0f,4.0f,4.0f));
        cube2.setSize(0.5f);

        actor.Asteroid cube3 = new actor.Asteroid();
        cube3.setPosition(new Vector3(4.0f,4.0f,3.0f));

        assertTrue(GJKSimplex.isColliding(cube1, cube1));
        assertFalse(GJKSimplex.isColliding(cube1, cube2));
        // Both cubes should be colliding
        assertTrue(GJKSimplex.isColliding(cube3, cube2));
        assertTrue(GJKSimplex.isColliding(cube2, cube3));

        for(int i = 0; i < 4096; i++){
            actor.Asteroid cubeA = new actor.Asteroid();
            cubeA.setPosition(new Vector3(gen.nextFloat(),gen.nextFloat(),gen.nextFloat()));
            cubeA.setSize(gen.nextInt());
            actor.Asteroid cubeB= new actor.Asteroid();
            cubeA.setPosition(new Vector3(gen.nextFloat(),gen.nextFloat(),gen.nextFloat()));
            cubeA.setSize(gen.nextInt());
            assertEquals(cubeA.isColliding(cubeB),GJKSimplex.isColliding(cubeA, cubeB));
            assertEquals(cubeB.isColliding(cubeA),GJKSimplex.isColliding(cubeB, cubeA));
        }
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
        for(int range = 0; range < 4096; range++){
            for(int i = 0; i < 4096; i++){
                Sphere sphere1 = new Sphere(getRandomPosition(range),gen.nextFloat() * range / 64.0f);
                Sphere sphere2 = new Sphere(getRandomPosition(range),gen.nextFloat() * range / 64.0f);
                try {
                    assertEquals(sphere1.isColliding(sphere2),GJKSimplex.isColliding(sphere1, sphere2));
                    assertEquals(sphere2.isColliding(sphere1),GJKSimplex.isColliding(sphere2, sphere1));
                } catch (AssertionError e){
                    System.err.println(e);
                    System.err.println(sphere1.distance(sphere2));
                    System.err.println(sphere1);
                    System.err.println(sphere2);
                    throw e;
                }
            }
        }
    }
}
