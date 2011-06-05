package actor.ship.weapon;

import java.lang.reflect.Constructor;


import actor.Actor;
import actor.ship.projectile.Projectile;


public class SingleShotWeapon<T extends Projectile> extends Weapon<T>{
    private static final long serialVersionUID = -5639387880039566435L;
    transient protected Constructor<? extends T> ctor;
    protected Class<? extends T> projectileType;

    public SingleShotWeapon(Class<? extends T> projectileType, long coolDown,int maxAmmo) {
        super(projectileType,coolDown,maxAmmo);
    }

    public void shoot(Actor ship) {
        if(canShoot()){
            if((System.currentTimeMillis() - getLastShotTime()) > coolDown) {
                game.Game.getActors().add(newProjectile(ship));
                setLastShotTime(System.currentTimeMillis());
                minusAmmo(1);
            }
        }
    }
}
