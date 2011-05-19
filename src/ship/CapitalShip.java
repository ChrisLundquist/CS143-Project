package ship;

public class CapitalShip extends ship.Ship {
    private static final long serialVersionUID = 3527730335695237893L;
    private static final String MODEL_NAME = "round_capital";
    
    public CapitalShip() {
        super();
        shields.add(new shield.CapitalShipShield());
        modelName = MODEL_NAME;
    }

    public void update() {
        super.update();
    }
}
