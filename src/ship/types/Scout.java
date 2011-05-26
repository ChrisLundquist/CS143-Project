package ship.types;

import graphics.Model;
import ship.PlayerShip;

public class Scout extends PlayerShip  {
    private static final long serialVersionUID = -6885874668025162786L;
    
    private final float PITCH_RATE = 0.050f;
    private final float ROLL_RATE = 0.1f;
    private final float YAW_RATE = 0.017f;
    
    private final float DEFAULT_SPEED = 0.0f;
    private final float ADDITIVE_SPEED = 0.02f;
    private final float NEGATIVE_SPEED = 0.008f;
    
    private final float ANGULAR_DAMPENING = 0.035f;
    private final float VELOCITY_DAMPENING = 0.90f;
    
    public Scout() {
        weapons.add(new ship.weapon.Machinegun());
        weapons.add(new ship.weapon.Sniper());
        shields.add(new ship.shield.PlayerShield());
        
    }

    @Override
    protected float getAdditiveSpeed() {
        return ADDITIVE_SPEED;
    }

    @Override
    protected float getAngularDampening() {
        return ANGULAR_DAMPENING;
    }

    @Override
    protected float getDefaultSpeed() {
        return DEFAULT_SPEED;
    }

    @Override
    protected String getLocalModelName() {
        return Model.Models.SHIP_TEST;
    }

    @Override
    protected float getNegativeSpeed() {
        return NEGATIVE_SPEED;
    }

    @Override
    protected float getPitchRate() {
        return PITCH_RATE;
    }

    @Override
    protected float getRollRate() {
        return ROLL_RATE;
    }

    @Override
    protected float getVelocityDampening() {
        // TODO Auto-generated method stub
        return VELOCITY_DAMPENING;
    }

    @Override
    protected float getYawRate() {
        return YAW_RATE;
    }

}
