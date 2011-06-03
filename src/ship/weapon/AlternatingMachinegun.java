package ship.weapon;

import actor.Actor;
import actor.Projectile;

public class AlternatingMachinegun extends ship.weapon.AlternatingWeapon {
    private final float BULLET_SPEED = 1.0f;
    @Override
    public long getShotCoolDown() {
        return 50;
    }
    @Override
    protected Projectile newProjectile(Actor ship) {
        return new actor.Bullet(ship,BULLET_SPEED,getMultiplier());
    }
    @Override
    public String getWeaponName() {
        return "Alternating Machine Gun";
    }
}
