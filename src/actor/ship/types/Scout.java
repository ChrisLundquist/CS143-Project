package actor.ship.types;

import actor.ship.PlayerShip;
import actor.ship.projectile.Bullet;
import actor.ship.projectile.SniperBullet;
import actor.ship.weapon.Weapon;
import graphics.core.Model;

public class Scout extends PlayerShip  {
    private static final long serialVersionUID = -6885874668025162786L;
    
    private final float PITCH_RATE = 0.050f;
    private final float ROLL_RATE = 0.1f;
    private final float YAW_RATE = 0.017f;
    
    private final float ADDITIVE_SPEED = 0.005f;
    private final float NEGATIVE_SPEED = 0.002f;
    
    private final float ANGULAR_DAMPENING = 0.035f;
    
    public Scout() {
        weapons.add(new Weapon<Bullet>(Bullet.class,Bullet.getShotCoolDown()));
        weapons.add(new Weapon<SniperBullet>(SniperBullet.class,SniperBullet.getShotCoolDown()));

        shields.add(new actor.ship.shield.PlayerShield());
    }

    @Override
    protected float getAdditiveSpeed() {
        return ADDITIVE_SPEED;
    }

    @Override
    protected float getAngularDampening() {
        return ANGULAR_DAMPENING;
    }

    @Override
    protected String getLocalModelName() {
        return Model.Models.SHIP_TEST;
    }

    @Override
    protected float getNegativeSpeed() {
        return NEGATIVE_SPEED;
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
}
