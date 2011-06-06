package actor.ship;

import graphics.core.Model;
import actor.ship.projectile.FlakShell;
import actor.ship.weapon.AlternatingWeapon;

public class CapitalShip extends actor.ship.Ship {
    private static final long serialVersionUID = 3527730335695237893L;
    private static final String MODEL_NAME = Model.Models.ROUND_CAPITAL_SHIP;
    
    private static final int ALTERNATING_FLAK = Integer.MAX_VALUE;
    
    public CapitalShip() {
        super();
        shields.add(new actor.ship.shield.CapitalShipShield());
        weapons.add(new AlternatingWeapon<FlakShell>(FlakShell.class,FlakShell.getShotCoolDown(),ALTERNATING_FLAK));
        modelName = MODEL_NAME;
    }

    public void update() {
        super.update();
        shoot();
    }
}
