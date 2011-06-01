package ship.weapon;

import actor.Actor;

public class MissileLauncher extends ship.weapon.Weapon {
    private static int multiplier=0;
    
    //longer cool downs then machinegun
    @Override
    public long getShotCoolDown() {
        return 1000;
    }

    @Override
    protected actor.Projectile newProjectile(actor.Actor ship) {
        return new actor.Missile(ship,1.0f,multiplier);
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
        return multiplier;
    }
    public static void setMultiplier(int multiplier) {
        MissileLauncher.multiplier = multiplier;
    }
}
