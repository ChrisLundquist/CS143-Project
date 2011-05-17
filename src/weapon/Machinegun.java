package weapon;

import actor.Actor;
import actor.Projectile;


public class Machinegun extends ship.Weapon {
    final double damage = 5.0;

    protected
    long getShotCoolDown() {
        return 100;
    }

    protected double getDamage() {
        return damage;
    }

    @Override
    protected Projectile newProjectile(Actor ship) {
        return new actor.Bullet(ship);
    }
}
