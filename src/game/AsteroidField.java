package game;

import java.io.Serializable;

import math.Vector3f;
import actor.Actor;
import actor.Asteroid;

public class AsteroidField implements Updateable, Serializable{
    private static final long serialVersionUID = 7105712880129225225L;
    public static final int DEFAULT_ASTEROID_LIMIT = 50;
    protected int asteroidCount, asteroidMax;
    
    public AsteroidField(){
        asteroidCount = 0;
        asteroidMax = DEFAULT_ASTEROID_LIMIT;
    }

    @Override
    public void update() {
        while(asteroidCount < asteroidMax){
            Vector3f pos = Vector3f.randomPosition(graphics.Skybox.SKYBOX_SIZE);
            Asteroid asteroid = new Asteroid(pos,pos.negate().times(0.001f));
            asteroid.setAsteroidField(this);
            // Test collision of the new Asteroid before actually spawning it
            for(Actor actor : game.Game.getActors().getCopyList()){
                if(asteroid.isColliding(actor))
                    break; // We are colliding with something, so we have to try again
            }
            asteroidCount++; // if we make it though all the collision tests then we can spawn it
            game.Game.getActors().add(asteroid);
        }
        
    }
    
    public void asteroidDied(){
        asteroidCount--;
    }
}
