package weapon;

import actor.Actor;
import actor.Projectile;

public class TwinLinkedMachinegun extends ship.TwinLinkedWeapon {
    final double damage = 10.0;
    
    @Override
    public long getShotCoolDown() {
        return 100;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    protected Projectile newProjectile(Actor ship) {
        return new actor.Bullet(ship);
    }
}
