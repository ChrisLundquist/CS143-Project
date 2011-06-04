package actor.ship;

import graphics.core.Model;

public class CapitalShip extends actor.ship.Ship {
    private static final long serialVersionUID = 3527730335695237893L;
    private static final String MODEL_NAME = Model.Models.ROUND_CAPITAL_SHIP;
    
    public CapitalShip() {
        super();
        shields.add(new actor.ship.shield.CapitalShipShield());
        modelName = MODEL_NAME;
    }

    public void update() {
        super.update();
    }
}
