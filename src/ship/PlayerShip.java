package ship;

public abstract class PlayerShip extends Ship {
    private static final long serialVersionUID = 1L;

    
    protected abstract float getRollRate();
    protected abstract float getPitchRate();
    protected abstract float getYawRate();
    
    protected abstract float getDefaultSpeed();
    protected abstract float getAdditiveSpeed();
    protected abstract float getNegativeSpeed();
    
    protected abstract float getAngularDampening();
    protected abstract float getVelocityDampening();
    protected abstract String getLocalModelName();
    protected int[] energyPoints = new int[4];
    public static final byte POWER = 0;
    public static final byte SHIELD = 1;
    public static final byte SPEED = 2;
    public static final byte TOTAL = 3;
    
    public PlayerShip(){
        super();
        modelName = this.getLocalModelName();
        energyPoints[TOTAL] = 12;
    }
    public int getPowerPoints() {
        return energyPoints[POWER];
    }
    public void setPowerPoints(int powerPoints) {
        energyPoints[POWER] = powerPoints;
    }
    public void changePower_points(int change) {

    }
    public int getShieldPoints() {
        return energyPoints[SHIELD];
    }
    public void setShieldPoints(int shieldPoints) {
        energyPoints[SHIELD] = shieldPoints;
    }
    /**
     * Changes shield points and sets the shield strength while there are available points available 
     * 
     * @param change the amount you want the shield points by
     */
    public void changeShieldPoints(int change) {
        if(getAvailablePoints() > 0) {
            energyPoints[SHIELD] += change;
            shields.get(0).setStrength(energyPoints[SHIELD]*100);
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

    public String getAllPoints() {
        String s =  energyPoints[POWER] + ","+  energyPoints[SHIELD] + "," + energyPoints[SPEED] + "," +  energyPoints[TOTAL] + "," +  getAvailablePoints();
        return s;
    }
    
    public void forwardThrust() {
        velocity.plusEquals(getDirection().times(getAdditiveSpeed()));
    }
    
    public void reverseThrust() {
        velocity.minusEquals(getDirection().times(getNegativeSpeed()));
    }
    
    public void pitchUp(){
        changePitch(getPitchRate());
    }
    
    public void pitchDown(){
        changePitch(-getPitchRate());
    }
    
    public void yawLeft() {
        changeYaw(getYawRate());
    }
    
    public void yawRight() {
        changeYaw(-getYawRate());
    }
    
    public void rollLeft() {
        changeRoll(getRollRate());
    }
    
    public void rollRight() {
        changeRoll(-getRollRate());
    }
    
    public void nextWeapon() {
        // Get the next weapon in the list
        setWeapon((selectedWeapon + 1) % weapons.size());
    }
    
    public void previousWeapon() {
        setWeapon((selectedWeapon - 1) % weapons.size());
    }

    public void setWeapon(int weaponNumber){
        selectedWeapon = weaponNumber % weapons.size();
        System.out.println("Switching to "+weapons.get(weaponNumber).getWeaponName());
    }

    @Override
    public void update(){
        super.update();
        dampenAngularVelocity(getAngularDampening());
        velocity = velocity.times(getVelocityDampening());
    }
}
