package ship;

import actor.Bullet;

public abstract class Weapon {
    public final long DEFAULT_DELAY = 1000;
    long lastShot;

    public Weapon(){
        lastShot = 0;
    }

    protected
    long getLastShotTime() {
        return lastShot;
    }

    protected
    long getShotCoolDown() {
        return DEFAULT_DELAY;
    }

    protected
    Bullet newBullet(actor.Actor ship) {
        return new Bullet(ship);
    }

    protected
    void setLastShotTime(long time) {
        lastShot = time;
    }

    public void shoot(actor.Actor ship) {
      //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
            actor.Actor.addActor(newBullet(ship));
            setLastShotTime(System.currentTimeMillis());
        }
    }
}
