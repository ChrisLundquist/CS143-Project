package ship;

public abstract class PlayerShip extends Ship {
    private static final long serialVersionUID = 1L;

    protected abstract float getRollDegree();
    protected abstract float getPitchDegree();
    protected abstract float getYawDegree();
    protected abstract String getLocalModelName();

    public PlayerShip(){
        super();
        modelName = this.getLocalModelName();
    }

    public void forwardThrust() {
        velocity.plusEquals(getDirection().times(0.01f));
    }
    public void reverseThrust() {
        velocity.minusEquals(getDirection().times(0.01f));
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
        selectedWeapon = (selectedWeapon + 1) % weapons.size();

        System.out.println("Switching to "+weapons.get(selectedWeapon).getWeaponName());
    }
    public void previousWeapon() {
        System.err.println("Changing Weapon");
        // Get the next weapon in the list
        selectedWeapon = (selectedWeapon - 1) % weapons.size();
    }
    
    public void setWeapon(int weaponNumber){
        selectedWeapon = weaponNumber % weapons.size();
    }
    
    @Override
    public void update(){
        super.update();
        dampenAngularVelocity();
        velocity = velocity.times(0.95f);
    }
}
