package ship.weapon;

import actor.Actor;

public class MissileLauncher extends ship.weapon.Weapon {
    private static final int MULTIPLIER = 0;
    
    //longer cool downs then machinegun
    @Override
    public long getShotCoolDown() {
        return 1000;
    }

    @Override
    protected actor.Projectile newProjectile(actor.Actor ship) {
        return new actor.Missile(ship,0.5f,MULTIPLIER);
    }
    @Override
    public void shoot(Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
            game.Game.getActors().add(newProjectile(ship));
            setLastShotTime(System.currentTimeMillis());
        }
    }

    @Override
    public String getWeaponName() {
        return "Missile Launcher";
    }
    public static int getMulitplier() {
        return MULTIPLIER;
    }
}
