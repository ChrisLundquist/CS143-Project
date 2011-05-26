package game;

import actor.Actor;
import actor.Asteroid;
import java.util.Random;

public class AsteroidField implements Updateable {
    private int maxAsteroids;
    Asteroid asteroid; 
    Random random = new Random();
    public AsteroidField(int max) {
        maxAsteroids = max;
    }
    public void populateField() {
        for(int i=0; i>maxAsteroids; i++) {
            asteroid = new Asteroid(new math.Vector3f(), new math.Vector3f(0.0f,0.0f,-10.0f)); 
        }
    }
    //<tim>
    //position.normalize.negate
    private float randomFloat() {
       int a = (int) (Math.random() * (256));
       float b = a + random.nextFloat();
       return b; 
    }
    //</tim>
    @Override
    public void update() {
    }

}
