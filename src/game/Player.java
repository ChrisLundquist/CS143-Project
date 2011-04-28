package game;

import java.io.Serializable;
import graphics.Camera;
import input.InputRouter;
import input.PlayerInput;
import actor.Actor;
import actor.PlayerShip;

public class Player implements Serializable {
    private static final long serialVersionUID = 8330574859953611636L;

    private String name;
    private PlayerStatus status;
    private transient Camera camera;
    private transient PlayerShip ship;
    private int shipId;
    // TODO ship preference - once we have ships
    
    public Player() {
        setName("Pilot");
        status = PlayerStatus.ALIVE;
        camera = new Camera();
        ship = new PlayerShip();
        setShipId(ship.getId());
        Actor.addActor(ship);
    }
    
    public String toString() {
        String msg = name + " " + status;
        Actor ship = getShip();
        if (ship != null)
            msg += " @ " + ship.getPosition();
        return msg;
    }
    
    public Camera getCamera() {
        return camera;
    }

    public PlayerShip getShip() {
        if (ship == null) {
            Actor a = Actor.findById(shipId);
            if (a instanceof PlayerShip)
                ship = (PlayerShip) a;
        }
        return ship;
    }
    
    public void input(InputRouter.Interaction action) {
        if (getShip() == null)
            return;
        
        switch(action) {
            case SHOOT:
                ship.shoot();
                break;
            case YAW_LEFT:
                ship.turnLeft();
                break;
            case YAW_RIGHT:
                ship.turnRight();
                break;
            case PITCH_UP:
                ship.turnUp();
                break;
            case PITCH_DOWN:
                ship.turnDown();
                break;
            case FORWARD:
                ship.forwardThrust();
                break;
            case BACK:
                ship.reverseThrust();
                break;
            default:
                System.err.println("Player: unhandled input: " + action);
        }
    }

    public boolean isAlive() {
        return (status == PlayerStatus.ALIVE);
    }

    public void respawn() {
        Actor a =  Actor.findById(getShipId());
        if (a instanceof PlayerShip)
            ship = (PlayerShip)a;
        else {
            // TODO create ship of players preference
            ship = new PlayerShip();
            setShipId(ship.getId());
            Actor.addActor(ship);     
        }
        status = PlayerStatus.ALIVE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public int getShipId() {
        return shipId;
    }

    // Statuses for our Player state machine
    public static enum PlayerStatus {
        OBSERVING,
        ALIVE,
        DEAD,
    }

    /**
     * Update the camera position based on the players status, ship position and camera model
     * currently it's just set the camera to the players ship position and rotation.
     * @return the players camera object so it can be chained with setPerspective()
     */
    public Camera updateCamera() {
        if (ship != null)
            camera.updateFromActor(ship);
        
        return camera;
    }
}
