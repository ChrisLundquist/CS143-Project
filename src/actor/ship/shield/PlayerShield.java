package actor.ship.shield;

public class PlayerShield extends Shield {
    private static final long serialVersionUID = -7318768199047822441L;
    final static private int STRENGTH = 1000;
    
    public PlayerShield() {
        setStrength(STRENGTH);
        setMaxStrength(STRENGTH);
    }
    
}
