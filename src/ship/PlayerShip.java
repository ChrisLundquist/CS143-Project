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

    public PlayerShip(){
        super();
        modelName = this.getLocalModelName();
    }

    public void forwardThrust() {
        velocity.plusEquals(getDirection().times(this.getAdditiveSpeed()));
    }
    public void reverseThrust() {
        velocity.minusEquals(getDirection().times(this.getNegativeSpeed()));
    }
    public void pitchUp(){
        changePitch(this.getPitchRate());
    }
    public void pitchDown(){
        changePitch(-this.getPitchRate());
    }
    public void yawLeft() {
        changeYaw(this.getYawRate());
    }
    public void yawRight() {
        changeYaw(-this.getYawRate());
    }
    public void rollLeft() {
        changeRoll(this.getRollRate());
    }
    public void rollRight() {
        changeRoll(-this.getRollRate());
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
