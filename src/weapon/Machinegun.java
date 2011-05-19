package weapon;

import actor.Actor;

public class Machinegun extends weapon.Weapon {
    @Override
    public long getShotCoolDown() {
        return 100;
    }

    @Override
    protected actor.Projectile newProjectile(actor.Actor ship) {
        return new actor.Bullet(ship);
    }

    @Override
    public void shoot(Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
            game.Game.getActors().add(newProjectile(ship));
            setLastShotTime(System.currentTimeMillis());
        }
    }
}
