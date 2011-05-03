package actor;

import math.*;

public class Bullet extends Actor {
    private static final long serialVersionUID = -3860927022451699968L;
    private static final int MAX_AGE = 60 * 5; /* 60 fps * 5 seconds = 300 frames */
    private static final float BULLET_SPEED = 1.0f;
    
    public Bullet(PlayerShip player){
        super();
        angularVelocity = new Quaternion(Vector3.UNIT_Y, 1);
        this.velocity = player.velocity.plus(player.getDirection().times(BULLET_SPEED));
        position = new math.Vector3(player.getPosition());
        setSize(.1f);
    }
    
    public Bullet(CapitalShip capitalShip){
        super();
        angularVelocity = new Quaternion(Vector3.UNIT_Y, 1);
        this.velocity = capitalShip.velocity.plus(capitalShip.getDirection().times(BULLET_SPEED));
        position = new math.Vector3(capitalShip.getPosition());
        setSize(.1f);
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("DEBUG: Collision Detected");
    }
    
    public void update(){
        super.update();
        if(age > MAX_AGE){
            // FIXME this throws a concurrent access exception
            //delete();
        }
    }
}
