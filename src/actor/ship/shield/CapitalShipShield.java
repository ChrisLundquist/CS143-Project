package actor.ship.shield;

public class CapitalShipShield extends Shield {
    private static final long serialVersionUID = 7903890271618282339L;
    private static int STRENGTH = 5000;

    public CapitalShipShield() {
        setStrength(STRENGTH);
    }
}
