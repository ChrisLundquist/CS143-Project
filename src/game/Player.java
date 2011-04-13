package game;

import java.io.Serializable;
import graphics.Camera;
import input.PlayerInput;
import actor.Actor;
import actor.PlayerShip;

public class Player implements Serializable {
    private static final long serialVersionUID = 8330574859953611636L;

    private String name;
    private PlayerStatus status;
    private Camera camera;
    private PlayerShip ship;
    private int shipId;
    
    public Player() {
        setName("Pilot");
        status = PlayerStatus.ALIVE;
        camera = new Camera();
        ship = new PlayerShip();
        shipId = ship.getId();
        Actor.actors.add(ship);
    }
    
    public Camera getCamera() {
        return camera;
    }

    public PlayerShip getShip() {
        return null;
    }
    
    public void input(PlayerInput action) {
        if (ship == null)
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
        
        // TODO figure out a more elegant way to do this
        camera.updateFromActor(ship);
    }

    public boolean isAlive() {
        return (status == PlayerStatus.ALIVE);
    }

    public void respawn() {
        Actor a =  Actor.findById(shipId);
        if (a instanceof PlayerShip)
            ship = (PlayerShip)a;
        else {
            // TODO create ship of players preference
            ship = new PlayerShip();
            shipId = ship.getId();
            Actor.actors.add(ship);     
        }
        status = PlayerStatus.ALIVE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Statuses for our Player state machine
    public static enum PlayerStatus {
        OBSERVING,
        ALIVE,
        DEAD,
    }
}
