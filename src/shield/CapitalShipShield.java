package shield;

public class CapitalShipShield extends ship.Shield {
    private static final long serialVersionUID = 7903890271618282339L;
    private int POWER = 5000;

    public CapitalShipShield() {
        setStrength(POWER);
    }
}
