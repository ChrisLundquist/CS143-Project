package ship.types;

import graphics.Model;
import ship.PlayerShip;
import ship.weapon.TwinLinkedMissileLauncher;

public class Bomber extends PlayerShip {
    private static final long serialVersionUID = -8832365458776599623L;
    private final float PITCH_RATE = 0.035f;
    private final float ROLL_RATE = 0.05f;
    private final float YAW_RATE = 0.01f;
    
    private final float DEFAULT_SPEED = 0.075f;
    private final float ADDITIVE_SPEED = 0.075f;
    private final float NEGATIVE_SPEED = 0.003f;
    
    private final float ANGULAR_DAMPENING = 0.025f;
    private final float VELOCITY_DAMPENING = 0.75f;
    
    public Bomber() {
        weapons.add(new TwinLinkedMissileLauncher());
        weapons.add(new ship.weapon.Machinegun());
        shields.add(new ship.shield.PlayerShield());    
    }
    
    @Override
    protected String getLocalModelName() {
        return Model.Models.FIGHTER;
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
    protected float getYawRate() {
        return YAW_RATE;
    }
    
    @Override
    protected float getDefaultSpeed() {
        return DEFAULT_SPEED;
    }
    
    @Override
    protected float getAdditiveSpeed() {
        return ADDITIVE_SPEED;
    }
    
    @Override
    protected float getNegativeSpeed() {
        return NEGATIVE_SPEED;
    }
    
    @Override
    protected float getAngularDampening() {
        return ANGULAR_DAMPENING;
    }

    @Override
    protected float getVelocityDampening() {
        return VELOCITY_DAMPENING;
    }
    
    @Override
    public void update(){
        super.update();
        velocity.plusEquals(getDirection().times(this.getDefaultSpeed()));
    }
}

