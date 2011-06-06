package actor.ship.weapon;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


import actor.Actor;
import actor.ship.projectile.Projectile;


public abstract class Weapon<T extends Projectile> implements Serializable{
    private static final long serialVersionUID = -5639387880039566435L;
    long lastShot,coolDown;
    transient protected Constructor<? extends T> ctor;
    protected Class<? extends T> projectileType;

    protected final int maxAmmo;
    protected int currentAmmo;

    public Weapon(Class<? extends T> projectileType, long coolDown, int maxAmmo) {
        this.projectileType = projectileType;
        lastShot = 0;
        this.coolDown = coolDown;
        this.maxAmmo = maxAmmo;
        this.currentAmmo = maxAmmo;
        try {
            ctor = getCtor();
        } catch (SecurityException e) {
            e.printStackTrace();
            return;
        }
    }

    protected Constructor<? extends T> getCtor() {
        if(ctor == null)
            try {
                ctor = projectileType.getDeclaredConstructor(actor.Actor.class);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return ctor;
    }

    protected long getLastShotTime() {
        return lastShot;
    }

    public T newProjectile(actor.Actor ship){
        T projectile = null;
        try {
            projectile = getCtor().newInstance(ship);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return projectile;
    }

    protected void setLastShotTime(long time) {
        lastShot = time;
    }

    public abstract void shoot(Actor ship);

    public float getAmmoPercent(){
        return (float)currentAmmo/(float)maxAmmo;
    }

    protected boolean hasAmmo(){
        return currentAmmo > 0;
    }

    protected boolean hasNoAmmo(){
        return currentAmmo <= 0;
    }

    protected int getCurrentAmmo(){
        return currentAmmo;
    }

    public String toString(){
        return "" + getClass().getSimpleName() + " " + getCtor().getName();
    }
}
