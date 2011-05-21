package ship;

/**
 * Manages energy distributition of ship
 * @author Tim Mikeladze
 *
 */ 
public class Energy {
    final int[] energyPoints = new int[4];
    public static final byte POWER = 0;
    public static final byte SHIELD = 1;
    public static final byte SPEED = 2;
    public static final byte TOTAL = 3;

    public Energy() {
        energyPoints[TOTAL] = 12;
    }   
    public int getPowerPoints() {
        return energyPoints[POWER];
    }
    public void setPowerPoints(int powerPoints) {
        energyPoints[POWER] = powerPoints;
    }
    public void increasePowerEnergy(PlayerShip ship) {
        if(arePointsAvailable()) {
            energyPoints[POWER] += 1;
            for(int i=0; i < ship.weapons.size(); i++) {
               // ship.weapons.get(i);
            }
        }
    }
    public void decreasePowerEnergy() {

    }
    public int getShieldPoints() {
        return energyPoints[SHIELD];
    }
    public void setShieldPoints(int shieldPoints) {
        energyPoints[SHIELD] = shieldPoints;
    }
    /**
     * Increases strength of each shield by 100
     * @param ship
     */
    public void increaseShieldEnergy(PlayerShip ship) {
        if(arePointsAvailable()) {
            energyPoints[SHIELD] += 1; 
            for(int i=0; i < ship.shields.size(); i++) {
                ship.shields.get(i).setStrength(energyPoints[SHIELD]*100);
            }
        }
    }
    /**
     * Decreases each shield strength by 100
     * @param ship
     */
    public void decreaseShieldEnergy(PlayerShip ship) {
        if(energyPoints[SHIELD] > 0) {
            energyPoints[SHIELD] -= 1;
            for(int i=0; i < ship.shields.size(); i++) {
                ship.shields.get(i).setStrength(energyPoints[SHIELD]*100);
            }
        }
    }
    public int getSpeedPoints() {
        return energyPoints[SPEED];
    }
    public void setSpeedPoints(int speedPoints) {
        energyPoints[SPEED] = speedPoints;
    }

    public int getTotalPoints() {
        return energyPoints[TOTAL];
    }

    public void setTotalPoints(int totalPoints) {
        energyPoints[TOTAL] = totalPoints;
    }

    public int getAvailablePoints() {
        return energyPoints[TOTAL] - energyPoints[POWER] - energyPoints[SHIELD] - energyPoints[SPEED];
    }

    public boolean arePointsAvailable() {
        return (energyPoints[TOTAL] - energyPoints[POWER] - energyPoints[SHIELD] - energyPoints[SPEED]) > 0;
    }

    public int[] getAllPoints() {
        return energyPoints;
    }

    public String toString() {
        String returnString =  energyPoints[POWER] + ","+  energyPoints[SHIELD] + "," + energyPoints[SPEED] +
        "," +  energyPoints[TOTAL] + "," +  getAvailablePoints();   
        return returnString;
    }
}
