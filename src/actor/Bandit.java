package actor;

import graphics.Model;
import math.Vector3f;
import ship.Ship;
import ship.shield.PlayerShield;
import ship.weapon.AlternatingWeapon;

public class Bandit extends Ship {
    static final long serialVersionUID = 8866311066404784908L;
    private static final int DEFAULT_HITPOINTS = 100;
    
    public Bandit(){
        super();
        hitPoints = DEFAULT_HITPOINTS;
        weapons.add(new AlternatingWeapon<Bullet>(Bullet.class,Bullet.getShotCoolDown()));
        shields.add(new PlayerShield());
        modelName = Model.Models.BANDIT;
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
