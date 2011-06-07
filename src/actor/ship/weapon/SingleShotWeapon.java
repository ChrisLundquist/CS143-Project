package actor.ship.weapon;

import java.lang.reflect.Constructor;

import math.Vector3f;
import actor.Actor;
import actor.ship.projectile.Projectile;


public class SingleShotWeapon<T extends Projectile> extends Weapon<T>{
    private static final long serialVersionUID = -5639387880039566435L;
    transient protected Constructor<? extends T> ctor;
    protected Class<? extends T> projectileType;

    public SingleShotWeapon(Class<? extends T> projectileType, long coolDown,int maxAmmo) {
        super(projectileType,coolDown,maxAmmo);
    }

    public void shoot(actor.Actor ship) {
        //calculates time passed in milliseconds
        if(hasNoAmmo())
            return;
        if((System.currentTimeMillis() - getLastShotTime()) < coolDown)
            return;

        ship.add(configureProjectile(newProjectile(ship), ship));

        setLastShotTime(System.currentTimeMillis());
        currentAmmo--;
    }

    @Override
    public void shoot(actor.Actor ship, Vector3f direction) {
        // Don't shoot if we have no ammo
        if(hasNoAmmo())
            return;
        // Don't shoot if we are on cool down
        if((System.currentTimeMillis() - getLastShotTime()) < coolDown)
            return;

        // Update our last shot time
        setLastShotTime(System.currentTimeMillis());
        ship.add(configureProjectile(newProjectile(ship), ship, direction));
        currentAmmo--;
    }

    @Override
    protected T configureProjectile(T projectile, Actor ship) {
        projectile.setPosition(projectile.getPosition().minus(Vector3f.UNIT_Y.times(ship.getRotation())));
        return projectile;
    }

    @Override
    protected T configureProjectile(T projectile, Actor ship, Vector3f direction) {
        Vector3f bestGunPosition = findBestGunPosition(ship, direction);

        // If we don't have any positions default to normal
        if(bestGunPosition == null)
            return configureProjectile(projectile,ship);
        // Figure out if we are shooting the left or right shot so we can displace it correctly
        projectile.setPosition(ship.getPosition().plus(bestGunPosition).plus(Vector3f.UNIT_X.times(ship.getRotation()).times(ship.getRadius())));
        projectile.setVelocity(direction.times(projectile.getSpeed()));
        projectile.setRotation(new math.Quaternion(direction,0));

        return projectile;
    }
}
