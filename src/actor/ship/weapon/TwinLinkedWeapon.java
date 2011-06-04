package actor.ship.weapon;

import actor.ship.projectile.Projectile;
import math.Vector3f;

public class TwinLinkedWeapon<T extends Projectile> extends Weapon<T> {
    private static final long serialVersionUID = 1390245390427366837L;

    public TwinLinkedWeapon(Class<? extends T> projectileType, long coolDown) {
        super(projectileType, coolDown);
    }

    public final float DEFAULT_OFFSET = 0.5f;

    public float getOffsetDistance(){
        return DEFAULT_OFFSET;
    }

    public void shoot(actor.Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > coolDown) {
            actor.ship.projectile.Projectile p = newProjectile(ship);
            p.setPosition(p.getPosition().plus(Vector3f.UNIT_X.times(ship.getRotation()).times(-getOffsetDistance())));
            // Left Shot
            game.Game.getActors().add(p);
            // Right Shot
            p = newProjectile(ship);
            p.setPosition(p.getPosition().plus(Vector3f.UNIT_X.times(ship.getRotation()).times(getOffsetDistance())));
            game.Game.getActors().add(p);
            setLastShotTime(System.currentTimeMillis());
        }
    }
}
