package ship;

public abstract class PlayerShip extends Ship {
    private static final long serialVersionUID = 1L;

    private final float ROLL_DEGREE;
    
    protected abstract float getRollDegree();
    protected abstract float getPitchDegree();
    protected abstract float getYawDegree();
    
    protected abstract float getDefaultSpeed();
    protected abstract float getAdditiveSpeed();
    protected abstract float getNegativeSpeed();
    
    protected abstract float getAngularDampening();
    protected abstract float getVelocityDampening();
    protected abstract String getLocalModelName();
    protected int[] ship_points = new int[4];
    // power,shields,speed, availbale points, total points
    // 0,1,2,3,4
    public PlayerShip(){
        super();
        modelName = this.getLocalModelName();
        //availibale points
        ship_points[3]=12;
        //total points
        ship_points[4]=12;
    }
    public int getPower_points() {
        return ship_points[0];
    }
    public void setPower_points(int powerPoints) {
        ship_points[0] = powerPoints;
    }
    public void changePower_points(int change) {

    }
    public int getShield_points() {
        return ship_points[1];
    }
    public void setShield_points(int shieldPoints) {
        ship_points[1] = shieldPoints;
    }
    /**
     * Changes shield points and sets the shield strength while there are availble points availble 
     * 
     * @param change the amount you want the shield points by
     */
    public void changeShield_points(int change) {
        if(getAvailible_points() > 0) {
            ship_points[1] += change;
            shields.get(0).setStrength(ship_points[1]*100);
        }
    }
    public int getSpeed_points() {
        return ship_points[2];
    }
    public void setSpeed_points(int speedPoints) {
        ship_points[2] = speedPoints;
    }

    public int getTotal_points() {
        return ship_points[4];
    }

    public void setTotal_points(int totalPoints) {
        ship_points[4] = totalPoints;
    }

    public int getAvailible_points() {
        ship_points[3] = ship_points[4] - ( ship_points[0]+ ship_points[1] +  ship_points[2] );
        return ship_points[3];
    }

    public void setAvailible_points(int availiblePoints) {
        ship_points[3] = availiblePoints;
    }
    public String getAll_points() {
        String s =  ship_points[0] + ","+  ship_points[1] + "," + ship_points[2] + "," +  ship_points[3] + "," +  ship_points[4];
        return s;
    }
    public void forwardThrust() {
        velocity.plusEquals(getDirection().times(this.getAdditiveSpeed()));
    }
    public void reverseThrust() {
        velocity.minusEquals(getDirection().times(this.getNegativeSpeed()));
    }
    public void pitchUp(){
        changePitch(this.getPitchDegree());
    }
    public void pitchDown(){
        changePitch(-this.getPitchDegree());
    }
    public void yawLeft() {
        changeYaw(this.getYawDegree());
    }
    public void yawRight() {
        changeYaw(-this.getYawDegree());
    }
    public void rollLeft() {
        changeRoll(this.getRollDegree());
    }
    public void rollRight() {
        changeRoll(-this.getRollDegree());
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
        dampenAngularVelocity(this.getAngularDampening());
        velocity = velocity.times(this.getVelocityDampening());
    }
}
