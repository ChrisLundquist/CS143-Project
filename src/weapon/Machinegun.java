package weapon;

public class Machinegun extends weapon.Weapon {
    @Override
    public long getShotCoolDown() {
        return 100;
    }

    @Override
    protected actor.Projectile newProjectile(actor.Actor ship) {
        return new actor.Bullet(ship);
    }
}
