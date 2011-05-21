package ship.types;

import ship.PlayerShip;

public class Fighter extends PlayerShip {
    private static final long serialVersionUID = -2035608530335353067L;
    
    public Fighter() {
        weapons.add(new weapon.TwinLinkedMachinegun());
        weapons.add(new weapon.Machinegun());
        
        shields.add(new shield.PlayerShield());
    }

    @Override
    protected String getLocalModelName() {
        return "ship_test";
    }

    @Override
    protected float getPitchDegree() {
        return 0.055f;
    }

    @Override
    protected float getRollDegree() {
        return 0.1f;
    }

    @Override
    protected float getYawDegree() {
        return 0.02f;
    }
    
    @Override
    protected float getDefaultSpeed() {
        return 0.01f;
    }
    
    protected float getAdditiveSpeed() {
        return 0.01f;
    }
    
    protected float getNegativeSpeed() {
        return 0.005f;
    }
    
    @Override
    protected float getAngularDampening() {
        return 0.035f;
    }

    @Override
    protected float getVelocityDampening() {
        return 0.95f;
    }
    
    @Override
    public void update(){
        super.update();
        velocity.plusEquals(getDirection().times(this.getDefaultSpeed()));
    }
}
