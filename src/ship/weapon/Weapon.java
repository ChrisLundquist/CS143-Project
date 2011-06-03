package ship.weapon;

import actor.Actor;
import actor.Projectile;


public abstract class Weapon {
    public final long DEFAULT_DELAY = 1000;
    long lastShot;
    protected int multiplier=0;

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

    public void shoot(Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > getShotCoolDown()) {
            game.Game.getActors().add(newProjectile(ship));
            setLastShotTime(System.currentTimeMillis());
        }
    }
    
    public abstract String getWeaponName();
    
    public int getMultiplier() {
        return multiplier;
    }
    
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

}
