package ship;

import java.io.Serializable;

public abstract class Shield implements Serializable{
    private static final long serialVersionUID = -464289746987986899L;
    int strength;

    protected int getStrength() {
        return strength;
    }

    protected void setStrength(int strength) {
        this.strength = strength;
    }

    protected void takeDamage(int damage) {
        strength -= damage;
    }

    protected boolean getStatus() {
        return strength > 0;
    }
}
