package weapon;

import math.Vector3f;

public abstract class TwinLinkedWeapon extends Weapon {
    public final float DEFAULT_OFFSET = 0.5f;

    private final float BULLET_SPEED = 1.0f;
    
    public float getOffsetDistance(){
        return DEFAULT_OFFSET;
    }
    
    public void shoot(actor.Actor ship) {
        //calculates time passed in milliseconds
          if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
              // Left Shot
              game.Game.getActors().add(new actor.Bullet(ship,BULLET_SPEED,Vector3f.UNIT_X.times(ship.getRotation()).times(-getOffsetDistance())));

              // Right Shot
              game.Game.getActors().add(new actor.Bullet(ship,BULLET_SPEED,Vector3f.UNIT_X.times(ship.getRotation()).times(getOffsetDistance())));
              setLastShotTime(System.currentTimeMillis());
          }
      }

}
