package ship;

public class PlayerShip extends ship.Ship {
    private static final float TURN_SPEED = 0.01f;
    private static final float ROLL_DEGREE = 0.05f;
    private static final long serialVersionUID = 260627862699350716L;
    private static final String MODEL_NAME = "ship_test";

    public PlayerShip(){
        super();
        weapons.add(new weapon.TwinLinkedMachinegun());
        weapons.add(new weapon.Machinegun());
        weapons.add(new weapon.Sniper());
        shields.add(new shield.PlayerShield());
        modelName = MODEL_NAME;
    }

    public void forwardThrust() {
        velocity.plusEquals(getDirection().times(0.01f));
    }
    public void reverseThrust() {
        velocity.minusEquals(getDirection().times(0.01f));
    }
    public void turnUp(){
        changePitch(TURN_SPEED);
    }
    public void turnDown(){
        changePitch(-TURN_SPEED);
    }
    public void turnLeft() {
        changeYaw(TURN_SPEED);
    }
    public void turnRight() {
        changeYaw(-TURN_SPEED);
    }
    public void rollLeft() {
        changeRoll(ROLL_DEGREE);
    }
    public void rollRight() {
        changeRoll(-ROLL_DEGREE);
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
