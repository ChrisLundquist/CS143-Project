package ship.shield;

public class PlayerShield extends ship.shield.Shield {
    private static final long serialVersionUID = -7318768199047822441L;
    final private int POWER = 1000;
    
    public PlayerShield() {
        setStrength(POWER);
    }
    
}
