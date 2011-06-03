package ship.weapon;

public class Machinegun extends ship.weapon.Weapon {
    @Override
    public long getShotCoolDown() {
        return 100;
    }

    @Override
    protected actor.Projectile newProjectile(actor.Actor ship) {
        return new actor.Bullet(ship,1.0f,getMultiplier());
    }

    @Override
    public String getWeaponName() {
        return "Machine Gun";
    }
}
