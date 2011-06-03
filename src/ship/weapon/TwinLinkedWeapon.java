package ship.weapon;

import math.Vector3f;

public abstract class TwinLinkedWeapon extends Weapon {
    public final float DEFAULT_OFFSET = 0.5f;

    public float getOffsetDistance(){
        return DEFAULT_OFFSET;
    }

    public void shoot(actor.Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
            actor.Projectile p = newProjectile(ship);
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
