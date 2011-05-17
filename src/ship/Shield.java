package ship;

public abstract class Shield {
    int power;
    public Shield() {

    }

    protected int getPower() {
        return power;
    }

    protected void setPower(int power) {
        this.power = power;
    }

    protected void takeDamage(int damage) {
        if(getStatus()) {
            power = power -  damage;  
        }
        else {
            //TODO remove the ship
        }
    }

    protected boolean getStatus() {
        return power > 0;
    }
}
