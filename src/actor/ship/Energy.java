package actor.ship;

/**
 * Manages energy distributition of ship
 * @author Tim Mikeladze
 *
 */ 
public class Energy {
    private int leftOverEnergy = 15;
    private int shieldEnergy = 0;
    private int gunEnergy = 0;
    private int speedEnergy = 0;
    

    private final PlayerShip ship;

    public Energy(PlayerShip ship) {
        this.ship=ship;
    }
    
    public void increaseGunEnergy() {
        if(leftOverEnergy>0) {
            gunEnergy++;
            leftOverEnergy--;
        }
    }
    public void decreaseGunEnergy() {
        if(gunEnergy>0){
            gunEnergy--;
            leftOverEnergy++;
        }
    }
    public int getGunEnergy(){
        return gunEnergy;
    }
    
    /**
     * Increases strength of each shield by 100
     */
    public void increaseShieldEnergy() {
        if(leftOverEnergy>0) {
            shieldEnergy++;
            leftOverEnergy--;
            for(int i=0; i < ship.shields.size(); i++) {
                ship.shields.get(i).setStrength(shieldEnergy*100);
            }
        }
    }
    /**
     * Decreases each shield strength by 100
     * @param ship
     */
    public void decreaseShieldEnergy() {
        if(shieldEnergy > 0) {
            shieldEnergy--;
            leftOverEnergy++;
            for(int i=0; i < ship.shields.size(); i++) {
                ship.shields.get(i).setStrength(shieldEnergy*100);
            }
        }
    }
    public int getShieldEnergy() {
        return shieldEnergy;
    }
    
    public void increaseSpeedEnergy(){
        if(leftOverEnergy>0){
            speedEnergy++;
            leftOverEnergy--;
        }
    }
    public void decreaseSpeedEnergy(){
        if(speedEnergy>0){
            speedEnergy--;
            leftOverEnergy++;
        }
    }

    public String toString() {
        String toReturn = String.format("gunEnergy: %d, shieldEnergy: %d, speedEnergy: %d, leftOverEnergy: %d",gunEnergy,shieldEnergy,speedEnergy,leftOverEnergy);  
        return toReturn;
    }
    
    public void debug(){
        System.out.println(this.toString());
    }
}
