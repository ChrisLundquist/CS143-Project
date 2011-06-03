package ship.weapon;

import math.Vector3f;

public abstract class AlternatingWeapon extends Weapon {
    public final float DEFAULT_OFFSET = 0.5f;
    protected short counter = 0;

    public float getOffsetDistance(){
        return DEFAULT_OFFSET;
    }

    public void shoot(actor.Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
            actor.Projectile p = newProjectile(ship);

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
