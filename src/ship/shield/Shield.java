package ship.shield;

import java.io.Serializable;

public abstract class Shield implements Serializable{
    private static final long serialVersionUID = -464289746987986899L;
    int strength;

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void takeDamage(int damage) {
        strength -= damage;
    }

    public boolean getStatus() {
        return strength > 0;
    }
}
