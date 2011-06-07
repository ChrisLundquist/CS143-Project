package actor.ship.types;

import actor.ship.PlayerShip;
import actor.ship.projectile.Bullet;
import actor.ship.projectile.FlakShell;
import actor.ship.weapon.AlternatingWeapon;
import actor.ship.weapon.SingleShotWeapon;
import graphics.core.Model;

public class Shotgunner extends PlayerShip {
    private static final long serialVersionUID = -2035608530335353067L;
    
    private final float PITCH_RATE = 0.055f;
    private final float ROLL_RATE = 0.1f;
    private final float YAW_RATE = 0.02f;
    
    private final float ADDITIVE_SPEED = 0.003f;
    private final float NEGATIVE_SPEED = 0.001f;
    
    private final float ANGULAR_DAMPENING = 0.035f;
    
    private static final int FLAK_SHELL_AMMO = 300;
    private static final int BULLET_AMMO = 5000;

    
    public Shotgunner() {
        weapons.add(new AlternatingWeapon<FlakShell>(FlakShell.class,FlakShell.getShotCoolDown(),FLAK_SHELL_AMMO));
        weapons.add(new SingleShotWeapon<Bullet>(Bullet.class,Bullet.getShotCoolDown(),BULLET_AMMO));
        shields.add(new actor.ship.shield.PlayerShield());
        
    }
    
    @Override
    protected String getLocalModelName() {
        return Model.Models.FIGHTER;
    }

    @Override
    protected float getPitchRate() {
        return PITCH_RATE;
    }

    @Override
    protected float getRollRate() {
        return ROLL_RATE;
    }

    @Override
    protected float getYawRate() {
        return YAW_RATE;
    }
    
    @Override
    protected float getAdditiveSpeed() {
        return ADDITIVE_SPEED;
    }
    
    @Override
    protected float getNegativeSpeed() {
        return NEGATIVE_SPEED;
    }
    
    @Override
    protected float getAngularDampening() {
        return ANGULAR_DAMPENING;
    }
}
