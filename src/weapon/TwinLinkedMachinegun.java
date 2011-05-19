package weapon;

import actor.Actor;
import actor.Projectile;

public class TwinLinkedMachinegun extends ship.TwinLinkedWeapon {    
    @Override
    public long getShotCoolDown() {
        return 100;
    }
    
    @Override
    protected Projectile newProjectile(Actor ship) {
        return new actor.Bullet(ship);
    }
}
