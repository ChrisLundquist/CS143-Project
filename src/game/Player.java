package game;

import graphics.Camera;
import input.InputRouter;

import java.io.Serializable;

import math.Vector3f;
import ship.PlayerShip;
import actor.Actor;
import actor.ActorId;
import actor.ActorSet;

public class Player implements Serializable {
    private static final long serialVersionUID = 8330574859953611636L;

    private String name;
    private PlayerStatus status;
    private transient final Camera camera;
    private transient PlayerShip ship;
    private ActorId shipId;
    // TODO ship preference - once we have ships

    public Player() {
        setName("Pilot");
        status = PlayerStatus.OBSERVING;
        camera = new Camera();
    }

    @Override
    public String toString() {
        String msg = name + " " + status;
        Actor ship = getShip();
        if (ship != null) {
            msg += " @ " + ship.getPosition();
        }
        return msg;
    }

    public Camera getCamera() {
        return camera;
    }

    public PlayerShip getShip() {
        if (ship == null) {
            if (shipId != null) {
                Actor a = Game.getActors().findById(shipId);
                if (a instanceof PlayerShip) {
                    return ship = (PlayerShip) a;
                }
            }
            ship = getNewShip();
        }
        return ship;
    }

    public void input(InputRouter.Interaction action) {
        if (getShip() == null) {
            return;
        }

        switch(action) {
            case SHOOT_PRIMARY:
                ship.shoot();
                break;
            case YAW_LEFT:
                ship.yawLeft();
                break;
            case YAW_RIGHT:
                ship.yawRight();
                break;
            case PITCH_UP:
                ship.pitchUp();
                break;
            case PITCH_DOWN:
                ship.pitchDown();
                break;
            case FORWARD:
                ship.forwardThrust();
                break;
            case BACK:
                ship.reverseThrust();
                break;
            case ROLL_LEFT:
                ship.rollLeft();
                break;
            case ROLL_RIGHT:
                ship.rollRight();
                break;
            case NEXT_WEAPON:
                ship.nextWeapon();
                break;
            default:
                System.err.println("Player: unhandled input: " + action);
        }
    }

    public boolean isAlive() {
        return (status == PlayerStatus.ALIVE);
    }

    public void respawn(ActorSet actors, Vector3f position) {
        if (shipId == null) {
            ship = getShip();
        } else {
            Actor a = actors.findById(shipId);
            if (a instanceof PlayerShip) {
                status = PlayerStatus.ALIVE;
            } else {
                ship = getShip();
            }
        }

        ship.setPosition(position);
        setShipId(ship.getId());
        status = PlayerStatus.ALIVE;
        actors.add(ship);
    }

    // TODO create ship of players preference
    public PlayerShip getNewShip() {
        return new ship.types.Fighter();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShipId(ActorId shipId) {
        this.shipId = shipId;
    }

    public ActorId getShipId() {
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
        if (ship != null) {
            camera.updateFromActor(ship);


        }

        return camera;
    }
}
