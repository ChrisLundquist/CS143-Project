package actor.ship.weapon;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


import actor.Actor;
import actor.ship.projectile.Projectile;


public class Weapon<T extends Projectile> {
    long lastShot,coolDown;
    protected Constructor<? extends T> ctor;

    public Weapon(Class<? extends T> projectileType, long coolDown) {
        try {
            ctor = projectileType.getDeclaredConstructor(actor.Actor.class);
        } catch (SecurityException e) {
            e.printStackTrace();
            return;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        lastShot = 0;
        this.coolDown = coolDown;
    }

    protected long getLastShotTime() {
        return lastShot;
    }

    protected T newProjectile(actor.Actor ship){
        T projectile = null;
        try {
            projectile = ctor.newInstance(ship);
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

    public void shoot(Actor ship) {
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - getLastShotTime()) > coolDown) {
            game.Game.getActors().add(newProjectile(ship));
            setLastShotTime(System.currentTimeMillis());
        }
    }

    public String getWeaponName(){
        return "" + getClass().getName() + " " + ctor.getName();
    }
}
