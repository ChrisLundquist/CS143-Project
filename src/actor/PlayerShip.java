package actor;

public class PlayerShip extends Actor {
    private static final float TURN_SPEED = 0.01f;
    private static final long serialVersionUID = 260627862699350716L;
    long time;
    int i=0;
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
     * Currently a switch statement that prevent you from shooting constantly shooting bullets
     * Will be moved into more suitable location once we have more projectiles and/or xbox controller input is implemented
     * 
     */
    public void shoot() {  
        System.out.println(this.getDirection());
        switch(i) {
            case 0:
                time=System.currentTimeMillis();
                actor.Actor.addActor(new actor.Bullet(this));
                i=1;
                break;
            case 1:
                //calculates time passed in milliseconds
                if((System.currentTimeMillis() - time) > 2000) {
                    actor.Actor.addActor(new actor.Bullet(this));
                    time=System.currentTimeMillis();
                    i=0;
                }
                break;
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
    public void update(){
        super.update();
        dampenAngularVelocity();
    }
}
