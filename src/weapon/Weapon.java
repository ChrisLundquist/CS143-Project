package weapon;

import actor.Projectile;

public abstract class Weapon {
    public final long DEFAULT_DELAY = 1000;
    long lastShot;
    double damage;


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

    protected abstract
    Projectile newProjectile(actor.Actor ship);

    protected
    void setLastShotTime(long time) {
        lastShot = time;
    }
  
    public double getDamage() {
        return damage;
    }


    public void shoot(actor.Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
            game.Game.getActors().add(newProjectile(ship));
            setLastShotTime(System.currentTimeMillis());
        }
    }
}
