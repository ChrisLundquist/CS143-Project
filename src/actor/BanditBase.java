package actor;

import math.Vector3f;
import ship.Ship;
import ship.shield.CapitalShipShield;

public class BanditBase extends Ship {
    private static final long serialVersionUID = 4896997019168002153L;
    private static final int DEFAULT_HITPOINTS = 500;
    public static final int DEFAULT_LIMIT = 5;
    private int banditCount, banditMax;

    public static final float FIELD_SIZE = graphics.Skybox.SKYBOX_SIZE;

    public BanditBase(){
        super();
        banditCount = 0;
        banditMax = DEFAULT_LIMIT;
        hitPoints = DEFAULT_HITPOINTS;
        shields.add(new CapitalShipShield());
    }
    public BanditBase(Vector3f pos) {
        this();
        setPosition(pos);
    }
    
    @Override
    public void update() {
        spawnBandits();
    }
    
    private void spawnBandits(){
        banditCount = 0;
        // count our asteroids alive
        for(Actor a : game.Game.getActors().getCopyList()){
            if( a instanceof Bandit)
                banditCount++;
        }
        // Add more to taste
        while(banditCount < banditMax){
            Vector3f pos = Vector3f.newRandom(FIELD_SIZE);
            Bandit bandit = new Bandit(pos);
            // Test collision of the new Asteroid before actually spawning it
            for(Actor actor : game.Game.getActors().getCopyList()){
                // make sure we have a safe place to put this
                if(! bandit.isColliding(actor)){
                    banditCount++; // if we make it though all the collision tests then we can spawn it
                    game.Game.getActors().add(bandit);
                }
            }
        }
    }

}
