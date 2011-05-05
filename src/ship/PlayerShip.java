package ship;

import actor.Actor;

public class PlayerShip extends ship.Ship {
    private static final float TURN_SPEED = 0.01f;
    private static final float ROLL_DEGREE = 0.05f;
    private static final long serialVersionUID = 260627862699350716L;
    long lastShotTime, lastRollLeftTime;
    
    public PlayerShip(){
        super();
        weapons.add(new weapon.TwinLinkedMachinegun());
        weapons.add(new weapon.Machinegun());
    }
    @Override
    public void handleCollision(Actor other) {
        // TODO Auto-generated method stub
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
    public void changeWeapon() {
        System.err.println("Changing Weapon");
        // Get the next weapon in the list
        selectedWeapon = selectedWeapon + 1 % weapons.size();
    }
    public void update(){
        super.update();
        dampenAngularVelocity();
        velocity = velocity.times(0.95f);
    }

}
