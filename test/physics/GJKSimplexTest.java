package physics;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import physics.GJKSimplex;
import math.*;

public class GJKSimplexTest {
    private static final float EPSILON = 1.00E-6f;
    protected static Random gen = new Random(42); /* constant seed to make our tests determined */


    @Test
        public void testFindSimplex() {
            //fail("Not yet implemented"); // TODO
        }

    /**
     * Tests to make sure we can't miss collisions based on velocity
     */
    @Test 
        public void testLinearSweepingCollision() {
            Sphere sphereA = new Sphere(new Vector3(-2,0,0),1);
            Sphere sphereB = new Sphere(new Vector3(2,0,0),1);

            sphereA.setVelocity(new Vector3(5,0,0));
            sphereB.setVelocity(new Vector3(-5,0,0));

            // The two spheres should pass through each other
            assertTrue(GJKSimplex.isColliding(sphereA, sphereB));

        }

    public void testAngularSweepingCollision(){
        // TODO
    }

    /**
     * Tests to make sure that actor size is taken into account when finding Minkowski sum
     */
    @Test
        public void testSizeVariation() {
            actor.Asteroid cubeA = new actor.Asteroid();

            actor.Asteroid cubeB = new actor.Asteroid();
            cubeB.setPosition(new Vector3(4,0,0));

            // They should be Colliding right now
            assertTrue(GJKSimplex.isColliding(cubeA, cubeB));

            // Make both of them smaller
            cubeA.setSize(0.5f);
            cubeB.setSize(0.25f);
            // So now they shouldn't be colliding
            assertFalse(GJKSimplex.isColliding(cubeA, cubeB));

        }

    @Test
        public void testGetSupport() {
            assertEquals(Vector3.UNIT_X.times(2),
                    GJKSimplex.getSupport(SphereTest.UNIT_SPHERE, SphereTest.UNIT_SPHERE, Vector3.UNIT_X));
            Sphere a = new Sphere(new Vector3(4, 0, 0), 1);
            Sphere b = new Sphere(new Vector3(0, 0, 0), 1);

            // Test two no intersecting spheres considering the simple case where they are all on the x axis
            assertEquals(new Vector3(6, 0, 0),
                    GJKSimplex.getSupport(a, b, Vector3.UNIT_X));
            assertEquals(new Vector3(2, 0, 0),
                    GJKSimplex.getSupport(a, b, Vector3.UNIT_X.negate()));

            // Test two spheres of radius one on top of each other, support should be 2 in all directions
            for (int i = 0; i < 4096; i++) {
                Vector3 direction = Vector3Test.getRandom(100);
                assertEquals(2, GJKSimplex.getSupport(SphereTest.UNIT_SPHERE, SphereTest.UNIT_SPHERE, direction).magnitude(), EPSILON); 
            }

        }


    @Test
        public void testContainsOrigin() {
            java.util.List<Vector3> simplex = new java.util.ArrayList<Vector3>();

            // An empty simplex should not contain the origin
            assertFalse(GJKSimplex.containsOrigin(simplex));

            simplex.add(new Vector3(0f,    0f,    1f));
            simplex.add(new Vector3(1f,    0f,    -0.5f));
            simplex.add(new Vector3(-0.5f, 0.5f,  -0.5f));
            simplex.add(new Vector3(-0.5f, -0.5f, -0.5f));
            assertTrue(GJKSimplex.containsOrigin(simplex));

            // A tetrahedron above the origin should not contain the origin.
            simplex.clear();
            simplex.add(new Vector3(0f,    0f,    2f));
            simplex.add(new Vector3(1f,    0f,    0.5f));
            simplex.add(new Vector3(-0.5f, 0.5f,  0.5f));
            simplex.add(new Vector3(-0.5f, -0.5f, 0.5f));
            assertFalse(GJKSimplex.containsOrigin(simplex));

            // Try each permutation of this tetrahedren to test logic on all faces
            simplex.clear();
            simplex.add(new Vector3(1f,    0f,    0.5f));
            simplex.add(new Vector3(-0.5f, 0.5f,  0.5f));
            simplex.add(new Vector3(-0.5f, -0.5f, 0.5f));
            simplex.add(new Vector3(0f,    0f,    2f));
            assertFalse(GJKSimplex.containsOrigin(simplex));
            simplex.clear();
            simplex.add(new Vector3(-0.5f, 0.5f,  0.5f));
            simplex.add(new Vector3(-0.5f, -0.5f, 0.5f));
            simplex.add(new Vector3(0f,    0f,    2f));
            simplex.add(new Vector3(1f,    0f,    0.5f));
            assertFalse(GJKSimplex.containsOrigin(simplex));
            simplex.clear();
            simplex.add(new Vector3(-0.5f, -0.5f, 0.5f));
            simplex.add(new Vector3(0f,    0f,    2f));
            simplex.add(new Vector3(1f,    0f,    0.5f));
            simplex.add(new Vector3(-0.5f, 0.5f,  0.5f));
            assertFalse(GJKSimplex.containsOrigin(simplex));

            // Now try the same tetrahedren inverted: vertex pointing at the origin
            simplex.clear();
            simplex.add(new Vector3(0f,    0f,    0.5f));
            simplex.add(new Vector3(1f,    0f,    2f));
            simplex.add(new Vector3(-0.5f, 0.5f,  2f));
            simplex.add(new Vector3(-0.5f, -0.5f, 2f));
            assertFalse(GJKSimplex.containsOrigin(simplex));
            simplex.clear();
            simplex.add(new Vector3(1f,    0f,    2f));
            simplex.add(new Vector3(-0.5f, 0.5f,  2f));
            simplex.add(new Vector3(-0.5f, -0.5f, 2f));
            simplex.add(new Vector3(0f,    0f,    0.5f));
            assertFalse(GJKSimplex.containsOrigin(simplex));
            simplex.clear();
            simplex.add(new Vector3(-0.5f, 0.5f,  2f));
            simplex.add(new Vector3(-0.5f, -0.5f, 2f));
            simplex.add(new Vector3(0f,    0f,    0.5f));
            simplex.add(new Vector3(1f,    0f,    2f));
            assertFalse(GJKSimplex.containsOrigin(simplex));
            simplex.clear();
            simplex.add(new Vector3(-0.5f, -0.5f, 2f));
            simplex.add(new Vector3(0f,    0f,    0.5f));
            simplex.add(new Vector3(1f,    0f,    2f));
            simplex.add(new Vector3(-0.5f, 0.5f,  2f));
            assertFalse(GJKSimplex.containsOrigin(simplex));


            // Test corner case
            simplex.clear();
            simplex.add(new Vector3(0f,    0f,    0f));
            simplex.add(new Vector3(0f,    0f,    0.5f));
            simplex.add(new Vector3(1f,    0f,    2f));
            simplex.add(new Vector3(-0.5f, 0.5f,  2f));
            assertTrue(GJKSimplex.containsOrigin(simplex));

            // and edge case
            // Test corner case
            simplex.clear();
            simplex.add(new Vector3(-1f,   0f,    0f));
            simplex.add(new Vector3(1f,    0f,    0f));
            simplex.add(new Vector3(1f,    0f,    2f));
            simplex.add(new Vector3(-0.5f, 0.5f,  2f));
            assertTrue(GJKSimplex.containsOrigin(simplex));
        }

    @Test
        public void testCubeColliding(){
            actor.Asteroid cube1 = new actor.Asteroid();

            actor.Asteroid cube2 = new actor.Asteroid();
            cube2.setPosition(new Vector3(16.0f,16.0f,16.0f));

            actor.Asteroid cube3 = new actor.Asteroid();
            cube3.setPosition(new Vector3(16.0f,16.0f,15.0f));

            assertTrue(GJKSimplex.isColliding(cube1, cube1));
            assertFalse(GJKSimplex.isColliding(cube1, cube2));
            // Both cubes should be colliding
            assertTrue(GJKSimplex.isColliding(cube3, cube2));
            assertTrue(GJKSimplex.isColliding(cube2, cube3));

            /*
               for(int range = 0; range < 4096; range++){
               for(int i = 0; i < 4096; i++){
               actor.Asteroid cubeA = new actor.Asteroid();
               cubeA.setPosition(Vector3Test.getRandom(range));
               cubeA.setSize(gen.nextFloat() * range);
               actor.Asteroid cubeB= new actor.Asteroid();
               cubeA.setPosition(Vector3Test.getRandom(range));
               cubeA.setSize(gen.nextFloat() * range);
            // TODO make a safe cube cube collider as a refference case
            assertEquals(cubeA.isColliding(cubeB),GJKSimplex.isColliding(cubeA, cubeB));
            assertEquals(cubeB.isColliding(cubeA),GJKSimplex.isColliding(cubeB, cubeA));
               }
               }
               */
        }

    @Test
        public void testSphereColliding(){
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
            assertTrue(SphereTest.UNIT_SPHERE.isColliding(SphereTest.UNIT_SPHERE));
            assertEquals(SphereTest.UNIT_SPHERE.isColliding(SphereTest.UNIT_SPHERE), GJKSimplex.isColliding(SphereTest.UNIT_SPHERE, SphereTest.UNIT_SPHERE));
            assertEquals(s4441.isColliding(s4441),GJKSimplex.isColliding(s4441, s4441));

            // partially colliding Spheres
            assertTrue(GJKSimplex.isColliding(SphereTest.UNIT_SPHERE, s0505051));
            assertTrue(GJKSimplex.isColliding(s0505051,SphereTest.UNIT_SPHERE));

            // Partially Colliding
            assertTrue(GJKSimplex.isColliding(s050501, s0505051));

            // non unit radius
            assertTrue(GJKSimplex.isColliding(SphereTest.UNIT_SPHERE, s0004));
            assertFalse(GJKSimplex.isColliding(s4441, s0004));
            assertFalse(GJKSimplex.isColliding(s0004,s8881));
            // NOTE the case of 0 radius and 0 distance of each other is undefined, the algorithms differ with their interpretation
            for(int range = 1; range < 64; range++){
                for(int i = 0; i < 64; i++){
                    Sphere sphere1 = new Sphere(Vector3Test.getRandom(range),gen.nextFloat() * range / 64.0f);
                    Sphere sphere2 = new Sphere(Vector3Test.getRandom(range),gen.nextFloat() * range / 64.0f);
                    try {
                        assertEquals(sphere1.isColliding(sphere2),GJKSimplex.isColliding(sphere1, sphere2));
                        assertEquals(sphere2.isColliding(sphere1),GJKSimplex.isColliding(sphere2, sphere1));
                    } catch (AssertionError e){
                        System.err.println(e);
                        System.err.println("Distance " + sphere1.distance(sphere2));
                        System.err.println(sphere1);
                        System.err.println(sphere2);
                        throw e;
                    }
                }
                //System.out.print(".");
            }
        }
}
