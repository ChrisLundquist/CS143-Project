package ship;

import math.Vector3;

public abstract class TwinLinkedWeapon extends Weapon {
    public final float DEFAULT_OFFSET = 0.5f;
    
    public float getOffsetDistance(){
        return DEFAULT_OFFSET;
    }
    
    public void shoot(actor.Actor ship) {
        //calculates time passed in milliseconds
          if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
              // Left Shot
              actor.Actor.addActor(new actor.Bullet(ship,Vector3.UNIT_X.times(ship.getRotation()).times(-getOffsetDistance())));

              // Right Shot
              actor.Actor.addActor(new actor.Bullet(ship,Vector3.UNIT_X.times(ship.getRotation()).times(getOffsetDistance())));
              setLastShotTime(System.currentTimeMillis());
          }
      }

}
