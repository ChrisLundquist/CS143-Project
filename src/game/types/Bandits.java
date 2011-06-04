package game.types;

import math.Vector3f;
import actor.BanditBase;

public class Bandits implements game.GameType{
    private static final long serialVersionUID = 7105712880129225225L;
    private static final int DEFAULT_LIMIT = 1;
    public static final float FIELD_SIZE = graphics.Skybox.SKYBOX_SIZE;

    private int baseMax;
    private boolean respawnBases, spawned;
    
    public Bandits(){
        baseMax = DEFAULT_LIMIT;
        respawnBases = false;
        spawned = false;
    }

    @Override
    public void update() {
        // See if we have any work to do
        if(spawned == true && respawnBases == false)
            return;
        
        // Add more to taste
        while(game.Game.getActors().getBanditBaseCount() < baseMax){
            Vector3f pos = Vector3f.newRandom(FIELD_SIZE);
            BanditBase base = new BanditBase(pos);
            game.Game.getActors().add(base);
        }
        spawned = true;
    }
}