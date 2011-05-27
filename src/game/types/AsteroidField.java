package game.types;


import game.GameType;

import math.Vector3f;
import actor.Actor;
import actor.Asteroid;

public class AsteroidField implements GameType{
    private static final long serialVersionUID = 7105712880129225225L;
    public static final int DEFAULT_ASTEROID_LIMIT = 50;
    private int asteroidCount, asteroidMax;

    public static final float FIELD_SIZE = graphics.Skybox.SKYBOX_SIZE;

    public AsteroidField(){
        asteroidCount = 0;
        asteroidMax = DEFAULT_ASTEROID_LIMIT;
    }

    @Override
    public void update() {
        asteroidCount = 0;
        // count our asteroids alive
        for(Actor a : game.Game.getActors().getCopyList()){
            if( a instanceof Asteroid)
                asteroidCount++;
        }
        // Add more to taste
        while(asteroidCount < asteroidMax){
            Vector3f pos = Vector3f.newRandom(FIELD_SIZE);
            Asteroid asteroid = new Asteroid(pos,Vector3f.newRandom(FIELD_SIZE).times(0.001f));
            // Test collision of the new Asteroid before actually spawning it
            for(Actor actor : game.Game.getActors().getCopyList()){
                // make sure we have a safe place to put this
                if(! asteroid.isColliding(actor)){
                    asteroidCount++; // if we make it though all the collision tests then we can spawn it
                    game.Game.getActors().add(asteroid);
                }
            }
        }
    }
}
