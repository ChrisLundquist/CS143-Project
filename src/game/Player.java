package game;

import graphics.Camera;
import graphics.InGameMenu;
import input.InputRouter;
import java.io.Serializable;
import actor.Actor;
import actor.ActorId;
import actor.ActorSet;
import actor.ship.PlayerShip;

public class Player implements Serializable {
    private static final long serialVersionUID = 8330574859953611636L;

    private String name;
    private PlayerStatus status;
    private transient final Camera camera;
    private transient PlayerShip ship;
    private ActorId shipId;
    // TODO ship preference - once we have ships

    private int playerId;

    public Player() {
        setName("Pilot");
        status = PlayerStatus.OBSERVING;
        camera = new Camera();
        playerId = 0;
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
            shipId = ship.getId();
        }
        return ship;
    }

    public void input(InputRouter.Interaction action) {
        if (isAlive())
            inputAlive(action);
        else
            respawn();

    }

    private void inputAlive(InputRouter.Interaction action) {
        switch(action) {
            case SHOOT:
                ship.shoot();
                break;
            case SHOOT_PRIMARY:
                ship.setWeapon(0);
                ship.shoot();
                break;
            case SHOOT_SECONDARY:
                ship.setWeapon(1);
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
            case ENERGY_GUN_UP:
                ship.energy.increaseGunEnergy();
                break;
            case ENERGY_GUN_DOWN:
                ship.energy.decreaseGunEnergy();
                break;
            case ENERGY_SHIELD_UP:
                ship.energy.increaseShieldEnergy();
                break;
            case ENERGY_SHIELD_DOWN:
                ship.energy.decreaseShieldEnergy();
                break;
            case ENERGY_SPEED_UP:
                ship.energy.increaseSpeedEnergy();
                break;
            case ENERGY_SPEED_DOWN:
                ship.energy.decreaseSpeedEnergy();
                break;
            case OPEN_MENU:
                InGameMenu.setMenuOpen(true);
                switch(action) {
                    case FORWARD:
                        InGameMenu.selectionUp();
                        break;
                    case BACK:
                        InGameMenu.selectionDown();
                        break;
                    case SHOOT:
                        if(InGameMenu.getSelection() == 1) {
                            System.exit(0);
                        }
                        else{
                            InGameMenu.setMenuOpen(false);
                        }
                        break;
                }
                break;
            default:
                System.err.println("Player: unhandled input: " + action);
        }
    }

    public boolean isAlive() {
        if (getShip().isAlive() == false) {
            status = PlayerStatus.DEAD;
            getShip().die();
            ship = null;
            shipId = null;
        }

        return status == PlayerStatus.ALIVE;
    }

    /**
     * Respawn the player, this will only be called on the client
     * the server can not transition us from the observing state
     */
    public void respawn() {
        SpawningPosition spawningPosition = Game.getMap().getSpawnPosition();
        ActorSet actors = Game.getActors();

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

        ship.setPosition(spawningPosition.getPosition());
        ship.setRotation(spawningPosition.getOrientation());
        setShipId(ship.getId());
        status = PlayerStatus.ALIVE;
        actors.add(ship);
    }

    // TODO create ship of players preference
    public PlayerShip getNewShip() {
        return new actor.ship.types.Bomber();
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
        if (ship != null)
            camera.updateFromActor(ship);

        return camera;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Player setPlayerId(int id) {
        playerId = id;
        return this;
    }
}
