package actor.ship.weapon;

import math.Vector3f;
import actor.Actor;
import actor.ship.projectile.Projectile;

public class AlternatingWeapon<T extends Projectile> extends Weapon<T> {
    private static final long serialVersionUID = 4362231465338858745L;

    public AlternatingWeapon(Class<? extends T> projectileType, long coolDown,int maxAmmo) {
        super(projectileType,coolDown/2,maxAmmo);
    }

    public final float DEFAULT_OFFSET = 0.5f;

    public float getOffsetDistance(){
        return DEFAULT_OFFSET;
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
        // Figure out if we are shooting the left or right shot so we can displace it correctly
        float leftOrRight = (currentAmmo % 2 == 0) ? -1 : 1;
        
        projectile.setPosition(projectile.getPosition().plus(Vector3f.UNIT_X.times(ship.getRotation()).times(leftOrRight * getOffsetDistance())));
        return projectile;
    }

    @Override
    protected T configureProjectile(T projectile, Actor ship, Vector3f direction) {
        Vector3f bestGunPosition = findBestGunPosition(ship, direction);
        
        // If we don't have any positions default to normal
        if(bestGunPosition == null)
            return configureProjectile(projectile,ship);
        // Figure out if we are shooting the left or right shot so we can displace it correctly
        
        float leftOrRight = (currentAmmo % 2 == 0) ? -1 : 1;
        
        projectile.setPosition(ship.getPosition().plus(bestGunPosition.plus(Vector3f.UNIT_X.times(ship.getRotation()).times(leftOrRight * getOffsetDistance()))));
        projectile.setVelocity(direction.times(projectile.getSpeed()));
        projectile.setRotation(new math.Quaternion(direction,0));

        return projectile;
    }
}
