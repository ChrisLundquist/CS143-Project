package actor.ship.types;

import actor.ship.PlayerShip;
import actor.ship.projectile.*;
import actor.ship.weapon.*;
import graphics.core.Model;

public class Bomber extends PlayerShip {
    private static final long serialVersionUID = -8832365458776599623L;
    private final float PITCH_RATE = 0.035f;
    private final float ROLL_RATE = 0.05f;
    private final float YAW_RATE = 0.01f;

    private final float ADDITIVE_SPEED = 0.001f;
    private final float NEGATIVE_SPEED = 0.0005f;

    private final float ANGULAR_DAMPENING = 0.025f;
    
    private static final int MISSILE_AMMO = 30;
    private static final int NUKE_AMMO = 5;
    private static final int FLAK_SHELL_AMMO = 300;
    private static final int BULLET_AMMO = 5000;

    public Bomber() {
        weapons.add(new TwinLinkedWeapon<Missile>(Missile.class,Missile.getShotCoolDown(),MISSILE_AMMO));
        weapons.add(new SingleShotWeapon<Bullet>(Bullet.class,Bullet.getShotCoolDown(),BULLET_AMMO));
        weapons.add(new SingleShotWeapon<Nuke>(Nuke.class,Nuke.getShotCoolDown(),NUKE_AMMO));
        weapons.add(new AlternatingWeapon<FlakShell>(FlakShell.class,FlakShell.getShotCoolDown(),FLAK_SHELL_AMMO));

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

