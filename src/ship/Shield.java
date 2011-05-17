package ship;

import java.io.Serializable;

public abstract class Shield implements Serializable{
    private static final long serialVersionUID = -464289746987986899L;
    int power;

    protected int getPower() {
        return power;
    }

    protected void setPower(int power) {
        this.power = power;
    }

    protected void takeDamage(int damage) {
        power -= damage;
    }

    protected boolean getStatus() {
        return power > 0;
    }
}
