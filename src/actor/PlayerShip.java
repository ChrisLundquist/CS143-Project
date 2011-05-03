package actor;

public class PlayerShip extends Actor {
    private static final float TURN_SPEED = 0.01f;
    private static final float ROLL_DEGREE = 0.05f;
    private static final int SHOT_COOLDOWN = 1000;
    private static final long serialVersionUID = 260627862699350716L;
    long lastShotTime, lastRollLeftTime;
    public PlayerShip(){
        super();
    }
    @Override
    public void handleCollision(Actor other) {
        // TODO Auto-generated method stub
    }
    /**
     * @author Tim Mikeladze
     * 
     * Shoot your cube_cube!
     * 
     * Will be moved into more suitable location once we have more projectiles and/or xbox controller input is implemented
     * 
     */
    public void shoot() {  
        //calculates time passed in milliseconds
        if((System.currentTimeMillis() - lastShotTime) > SHOT_COOLDOWN) {
            actor.Actor.addActor(new actor.Bullet(this));
            lastShotTime = System.currentTimeMillis();
        }
    }
    public void forwardThrust() {
        position.plusEquals(getDirection().times(0.1f));
    }
    public void reverseThrust() {
        position.minusEquals(getDirection().times(0.1f));
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
        
    }
    public void update(){
        super.update();
        dampenAngularVelocity();
    }

}
