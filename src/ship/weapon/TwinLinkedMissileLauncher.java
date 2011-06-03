package ship.weapon;

import actor.Actor;
import actor.Projectile;

public class TwinLinkedMissileLauncher extends TwinLinkedWeapon {

    public long getShotCoolDown() {
        return 1000;
    }
    
    @Override
    protected Projectile newProjectile(Actor ship) {
        return new actor.Missile(ship,0.5f,getMultiplier());
    }

    @Override
    public String getWeaponName() {
        return "Twin Linked Missle Launcher";
    }

}
