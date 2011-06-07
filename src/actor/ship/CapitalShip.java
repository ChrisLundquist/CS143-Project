package actor.ship;

import graphics.core.Model;
import math.Vector3f;
import actor.ship.projectile.FlakShell;
import actor.ship.weapon.AlternatingWeapon;
import actor.ship.weapon.TwinLinkedWeapon;
import actor.ship.weapon.Weapon;

public class CapitalShip extends actor.ship.Ship {
    private static final long serialVersionUID = 3527730335695237893L;
    private static final String MODEL_NAME = Model.Models.ROUND_CAPITAL_SHIP;

    private static final int ALTERNATING_FLAK = Integer.MAX_VALUE;

    public CapitalShip() {
        super();
        shields.add(new actor.ship.shield.CapitalShipShield());
        weapons.add(new AlternatingWeapon<FlakShell>(FlakShell.class,FlakShell.getShotCoolDown(),ALTERNATING_FLAK));
        weapons.add(new TwinLinkedWeapon<FlakShell>(FlakShell.class,FlakShell.getShotCoolDown(),ALTERNATING_FLAK));
        weapons.add(new AlternatingWeapon<FlakShell>(FlakShell.class,FlakShell.getShotCoolDown(),ALTERNATING_FLAK));
        weapons.add(new TwinLinkedWeapon<FlakShell>(FlakShell.class,FlakShell.getShotCoolDown(),ALTERNATING_FLAK));
        //weapons.add(new AlternatingWeapon<Bullet>(Bullet.class,Bullet.getShotCoolDown(),ALTERNATING_FLAK));
        //weapons.add(new TwinLinkedWeapon<Bullet>(Bullet.class,Bullet.getShotCoolDown(),ALTERNATING_FLAK));


        hitPoints = 5000;
        modelName = MODEL_NAME;
    }

    public void update() {
        super.update();
        //shoot();
    }

    @Override
    public void shoot(){
        for(Weapon<? extends actor.ship.projectile.Projectile> weapon : weapons){
            weapon.shoot(this, Vector3f.newRandom(1));
        }
    }
}
