package actor.ship.types;

import actor.ship.PlayerShip;
import actor.ship.projectile.Bullet;
import actor.ship.projectile.Missile;
import actor.ship.weapon.AlternatingWeapon;
import actor.ship.weapon.SingleShotWeapon;
import graphics.core.Model;

public class Fighter extends PlayerShip {
    private static final long serialVersionUID = -2035608530335353067L;
    
    private final float PITCH_RATE = 0.055f;
    private final float ROLL_RATE = 0.1f;
    private final float YAW_RATE = 0.02f;
    
    private final float ADDITIVE_SPEED = 0.005f;
    private final float NEGATIVE_SPEED = 0.002f;
    
    private final float ANGULAR_DAMPENING = 0.035f;
    
    private static final int SINGLE_GUN_AMMO = Integer.MAX_VALUE;
    private static final int DOUBLE_GUN_AMMO = Integer.MAX_VALUE/2;
    
    public Fighter() {
        weapons.add(new SingleShotWeapon<Missile>(Missile.class,Missile.getShotCoolDown(),SINGLE_GUN_AMMO));
        weapons.add(new AlternatingWeapon<Bullet>(Bullet.class,Bullet.getShotCoolDown(),DOUBLE_GUN_AMMO));
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
