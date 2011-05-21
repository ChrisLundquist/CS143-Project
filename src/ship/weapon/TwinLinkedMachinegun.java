package ship.weapon;

import actor.Actor;
import actor.Projectile;

public class TwinLinkedMachinegun extends ship.weapon.TwinLinkedWeapon {    
    private final float BULLET_SPEED = 1.0f;
    
    @Override
    public long getShotCoolDown() {
        return 100;
    }
    
    @Override
    protected Projectile newProjectile(Actor ship) {
        return new actor.Bullet(ship,BULLET_SPEED);
    }

    @Override
    public String getWeaponName() {
        return "Twin Linked Machine Gun";
    }
}