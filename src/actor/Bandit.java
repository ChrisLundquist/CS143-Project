package actor;

import math.Vector3f;
import ship.Ship;
import ship.shield.PlayerShield;
import ship.weapon.TwinLinkedMachinegun;

public class Bandit extends Ship {
    static final long serialVersionUID = 8866311066404784908L;
    private static final int DEFAULT_HITPOINTS = 100;
    
    public Bandit(){
        super();
        hitPoints = DEFAULT_HITPOINTS;
        weapons.add(new TwinLinkedMachinegun());
        shields.add(new PlayerShield());
    }

    public Bandit(Vector3f pos) {
       this();
       setPosition(pos);
    }
    
    public void update(){
        super.update();
        shoot();
    }
}
