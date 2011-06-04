package actor.ship;

import actor.ship.projectile.Bullet;
import actor.ship.shield.PlayerShield;
import actor.ship.weapon.AlternatingWeapon;
import graphics.core.Model;
import math.Vector3f;

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
