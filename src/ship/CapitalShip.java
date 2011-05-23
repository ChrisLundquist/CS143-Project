package ship;

import graphics.Model;


public class CapitalShip extends ship.Ship {
    private static final long serialVersionUID = 3527730335695237893L;
    private static final String MODEL_NAME = "round_capital";
    
    public CapitalShip() {
        super(Model.Model_Enum.ROUND_CAPITAL_SHIP);
        shields.add(new ship.shield.CapitalShipShield());
    }

    @Override
    public void update() {
        super.update();
    }
}
