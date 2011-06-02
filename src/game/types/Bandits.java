package game.types;

import math.Vector3f;
import actor.Actor;
import actor.BanditBase;

public class Bandits implements game.GameType{
    private static final long serialVersionUID = 7105712880129225225L;
    private static final int DEFAULT_LIMIT = 1;
    public static final float FIELD_SIZE = graphics.Skybox.SKYBOX_SIZE;

    private int baseCount, baseMax;
    private boolean respawnBases, spawned;
    
    public Bandits(){
        baseMax = DEFAULT_LIMIT;
        baseCount = 0;
        respawnBases = false;
        spawned = false;
    }
    
    @Override
    public void update() {
        // See if we have any work to do
        if(spawned == true && respawnBases == false)
            return;
        
        baseCount = 0;
        // count our asteroids alive
        for(Actor a : game.Game.getActors().getCopyList()){
            if( a instanceof actor.BanditBase)
                baseCount++;
        }
        // Add more to taste
        while(baseCount < baseMax){
            Vector3f pos = Vector3f.newRandom(FIELD_SIZE);
            BanditBase base = new BanditBase(pos);
            // Test collision of the new Asteroid before actually spawning it
            for(Actor actor : game.Game.getActors().getCopyList()){
                // make sure we have a safe place to put this
                if(! base.isColliding(actor)){
                    baseCount++; // if we make it though all the collision tests then we can spawn it
                    game.Game.getActors().add(base);
                }
            }
        }
        spawned = true;
    }
}