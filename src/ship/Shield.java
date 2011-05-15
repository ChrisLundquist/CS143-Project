package ship;

public abstract class Shield {
    double power;
    public Shield() {

    }

    protected double getPower() {
        return power;
    }

    protected void setPower(double power) {
        this.power = power;
    }

    protected void takeDamage(double damage) {
        if(getStatus()) {
            power = power -  damage;  
        }
        else {
            //TODO remove the ship
        }
    }

    protected boolean getStatus() {
        if(power > 0 ) {
            return true;
        }

        else {
            return false;
        }

    }




}
