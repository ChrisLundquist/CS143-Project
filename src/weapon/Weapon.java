package weapon;

import actor.Projectile;


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

    protected abstract
    Projectile newProjectile(actor.Actor ship);

    protected
    void setLastShotTime(long time) {
        lastShot = time;
    }

    public abstract void shoot(actor.Actor ship);
    
    public abstract String getWeaponName();
}
