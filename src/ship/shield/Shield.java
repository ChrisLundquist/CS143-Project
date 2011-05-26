package ship.shield;

import java.io.Serializable;

public abstract class Shield implements Serializable{
    private static final long serialVersionUID = -464289746987986899L;
    int strength;
    long rechargeRate;
    int maxStrength;

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

    public long getRechargeRate() {
        return rechargeRate;
    }

    public void setRechargeRate(long rechargeRate) {
        this.rechargeRate = rechargeRate;
    }

    public int getMaxStrength() {
        return maxStrength;
    }

    public void setMaxStrength(int maxStrength) {
        this.maxStrength = maxStrength;
    }
}
