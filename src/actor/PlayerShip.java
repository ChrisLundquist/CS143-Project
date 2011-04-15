package actor;

public class PlayerShip extends Actor {
    private static final float TURN_SPEED = 0.01f;
    private static final long serialVersionUID = 260627862699350716L;

    public PlayerShip(){
        super();
    }
    @Override
    public void handleCollision(Actor other) {
        // TODO Auto-generated method stub

    }

    public void shoot() {
        // TODO Auto-generated method stub

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
    
    public void update(){
        super.update();
    }
}
