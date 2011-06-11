package actor;

import graphics.core.Model;
import actor.ship.Bandit;
import actor.ship.Ship;
import actor.ship.shield.CapitalShipShield;
import math.Vector3f;

public class BanditBase extends Ship {
    private static final long serialVersionUID = 4896997019168002153L;
    private static final int DEFAULT_HITPOINTS = 500;
    public static final int DEFAULT_LIMIT = 5;
    private int banditMax;

    public static final float FIELD_SIZE = graphics.Skybox.SKYBOX_SIZE;

    public BanditBase(){
        super();
        banditMax = DEFAULT_LIMIT;
        hitPoints = DEFAULT_HITPOINTS;
        shields.add(new CapitalShipShield());
        modelName = Model.Models.BANDIT_BASE;
        angularVelocity = new math.Quaternion(Vector3f.UNIT_Z,2);
    }
    public BanditBase(Vector3f pos) {
        this();
        setPosition(pos);
    }
    
    @Override
    public void update() {
        super.update();
        spawnBandits();
    }
    
    private void spawnBandits(){
        // Add more to taste
        while(game.Game.getActors().getBanditCount() < banditMax){
            Vector3f pos = Vector3f.newRandom(FIELD_SIZE);
            Bandit bandit = new Bandit(pos);
            game.Game.getActors().add(bandit);
        }
    }

}
