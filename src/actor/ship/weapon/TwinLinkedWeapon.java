package actor.ship.weapon;

import actor.ship.projectile.Projectile;
import math.Vector3f;

public class TwinLinkedWeapon<T extends Projectile> extends Weapon<T> {
    private static final long serialVersionUID = 1390245390427366837L;

    public TwinLinkedWeapon(Class<? extends T> projectileType, long coolDown,int maxAmmo) {
        super(projectileType, coolDown,maxAmmo);
    }

    public final float DEFAULT_OFFSET = 0.5f;

    public float getOffsetDistance(){
        return DEFAULT_OFFSET;
    }

    public void shoot(actor.Actor ship) {
        // Don't shoot if we have no ammo
        if(hasNoAmmo())
            return;
        // Don't shoot if we are on cool down
        if((System.currentTimeMillis() - getLastShotTime()) < coolDown)
            return;

        // Update our last shot time
        setLastShotTime(System.currentTimeMillis());

        // Left Shot
        actor.ship.projectile.Projectile p = newProjectile(ship);
        p.setPosition(p.getPosition().plus(Vector3f.UNIT_X.times(ship.getRotation()).times(-getOffsetDistance())));
        ship.add(p);
        currentAmmo--;

        // The left shot may have ran us out of ammo
        if(hasNoAmmo())
            return;

        // Right Shot
        p = newProjectile(ship);
        p.setPosition(p.getPosition().plus(Vector3f.UNIT_X.times(ship.getRotation()).times(getOffsetDistance())));
        ship.add(p);
        currentAmmo--;
    }
}
