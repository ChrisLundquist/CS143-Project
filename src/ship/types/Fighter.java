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
        return 0.06f;
    }

    @Override
    protected float getRollDegree() {
        return 0.05f;
    }

    @Override
    protected float getYawDegree() {
        return 0.02f;
    }

}
