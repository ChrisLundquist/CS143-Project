package actor.ship.weapon;

import actor.ship.projectile.Projectile;
import math.Vector3f;

public class AlternatingWeapon<T extends Projectile> extends Weapon<T> {
    private static final long serialVersionUID = 4362231465338858745L;

    public AlternatingWeapon(Class<? extends T> projectileType, long coolDown) {
        super(projectileType,coolDown);
        coolDown /= 2;
    }

    public final float DEFAULT_OFFSET = 0.5f;
    protected short counter = 0;

    public float getOffsetDistance(){
        return DEFAULT_OFFSET;
    }

    public void shoot(actor.Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > coolDown) {
            actor.ship.projectile.Projectile p = newProjectile(ship);

            if(counter%2==0){
                // Left Shot
                p.setPosition(p.getPosition().plus(Vector3f.UNIT_X.times(ship.getRotation()).times(-getOffsetDistance())));
            } else {
                // Right Shot
                p.setPosition(p.getPosition().plus(Vector3f.UNIT_X.times(ship.getRotation()).times(getOffsetDistance())));
            }
            game.Game.getActors().add(p);
            setLastShotTime(System.currentTimeMillis());
            counter++;
        }
    }
}
