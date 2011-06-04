package game.types;


import game.GameType;

import math.Vector3f;
import actor.Asteroid;

public class AsteroidField implements GameType{
    private static final long serialVersionUID = 7105712880129225225L;
    public static final int DEFAULT_ASTEROID_LIMIT = 50;
    private int asteroidMax;

    public static final float FIELD_SIZE = graphics.Skybox.SKYBOX_SIZE * 2;

    public AsteroidField(){
        asteroidMax = DEFAULT_ASTEROID_LIMIT;
    }

    @Override
    public void update() {
        // Add more to taste
        while(game.Game.getActors().getAsteroidCount() < asteroidMax){
            Vector3f pos = Vector3f.newRandom(FIELD_SIZE);
            Asteroid asteroid = new Asteroid(pos,Vector3f.newRandom(FIELD_SIZE).times(0.001f));
            game.Game.getActors().add(asteroid);
        }
    }
}
