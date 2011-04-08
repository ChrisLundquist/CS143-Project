package actor;

public class Player extends Actor {
    private static final long serialVersionUID = 260627862699350716L;
    protected boolean alive;
    protected graphics.Camera camera;

    
    public Player(){
        alive = true;
    }
    @Override
    public void handleCollision(Actor other) {
        // TODO Auto-generated method stub
        
    }

    public boolean isAlive() {
        return alive;
    }

    public void respawn() {
        // Don't respawn the player if they are alive
        if(alive == true)
            return;
        
        // TODO Auto-generated method stub
        
    }

    public void shoot() {
        // TODO Auto-generated method stub
        
    }

    public void forwardThrust() {
        // TODO Auto-generated method stub
        
    }

    public void reverseThrust() {
        // TODO Auto-generated method stub
        
    }

    public void turnLeft() {
        // TODO Auto-generated method stub
        
    }

    public void turnRight() {
        // TODO Auto-generated method stub
        
    }

}
