package game;

import graphics.Camera;
import graphics.InGameMenu;
import graphics.RespawnMenu;
import input.InputRouter;
import java.io.Serializable;
import actor.Actor;
import actor.ActorId;
import actor.ActorSet;
import actor.ship.PlayerShip;

public class Player implements Serializable {
    // Statuses for our Player state machine
    public static enum PlayerStatus {
        ALIVE,
        DEAD,
        OBSERVING,
    }
    //different ships to respawn as
    public static enum ShipType {
        FIGHTER,
        SCOUT,
        BOMBER,
        SHOTGUNNER,
    }


    private static final long serialVersionUID = 8330574859953611636L;
    public static final String DEFAULT_NAME = "Pilot";
    private transient final Camera camera;
    private String name;
    private int playerId;
    private transient PlayerShip ship;
    private ActorId shipId;
    private PlayerStatus status;
    private ShipType shipPreference;

    public Player() {
        this(DEFAULT_NAME, ShipType.FIGHTER);
        
    }

    public Player(String name, ShipType shipPreference) {
        this.name = name;
        status = PlayerStatus.OBSERVING;
        camera = new Camera();
        playerId = 0;
        this.shipPreference = shipPreference;     
    }

    public Camera getCamera() {
        return camera;
    }

    public String getName() {
        return name;
    }

    // TODO create ship of players preference
    public PlayerShip getNewShip() {
        switch(shipPreference) {
            case BOMBER:
                return new actor.ship.types.Bomber();
            case FIGHTER:
                return new actor.ship.types.Fighter();
            case SCOUT:
                return new actor.ship.types.Scout();
            case SHOTGUNNER:
                return new actor.ship.types.Shotgunner();
            default:
                return new actor.ship.types.Scout();
        }
    }

    public int getPlayerId() {
        return playerId;
    }


    public PlayerShip getShip() {
        return getShip(Game.getActors());
    }

    public PlayerShip getShip(ActorSet actors) {
        if (ship == null) {
            if (shipId != null) {
                Actor a = actors.findById(shipId);
                if (a instanceof PlayerShip)
                    return ship = (PlayerShip) a;
                shipId = null;
            }
            ship = getNewShip();
            shipId = ship.getId();
        }
        return ship;
    }

    public ActorId getShipId() {
        return shipId;
    }

    public void input(InputRouter.Interaction action) {
        if (isAlive())
            inputAlive(action);
        else {
            RespawnMenu.setRespawnOpen(true);
            switch(action) {
                case MENU_UP:
                    RespawnMenu.selectionUp();
                    break;
                case MENU_DOWN:
                    RespawnMenu.selectionDown();
                    break;
                case MENU_SELECT:
                    shipPreference = RespawnMenu.getSelection();
                    respawn();    
                    RespawnMenu.setRespawnOpen(false);
                    break;
                default:
                    System.err.println("Player: unhandled input: " + action);
            }
        }
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
                break;
            case MENU_UP:
                if(InGameMenu.isMenuOpen()) {
                    InGameMenu.selectionUp();
                    //   System.out.println("Up " + InGameMenu.getSelection());
                }
                break;
            case MENU_DOWN:
                if(InGameMenu.isMenuOpen()) {
                    InGameMenu.selectionDown();
                    // System.out.println("Down " +InGameMenu.getSelection());
                }
                break;
            case MENU_SELECT:
                if(InGameMenu.isMenuOpen()) {
                    //System.out.println("Select");
                    if(InGameMenu.getSelection() == 0) {
                        InGameMenu.setMenuOpen(false);
                    }
                    if(InGameMenu.getSelection() == 1) {
                        System.err.println("EXITED");
                        Game.exit();
                    }
                }
                break;
            default:
                System.err.println("Player: unhandled input: " + action);
        }
    }



    public boolean isAlive() {
        if (getShip().isDead() || getShip().getId() == null) {
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
        if (ship == null)
            ship = getNewShip();



        if (ship == null)
            throw new RuntimeException("WTF");

        ship.setPosition(spawningPosition.getPosition());
        ship.setRotation(spawningPosition.getOrientation());

        if (ship == null)
            throw new RuntimeException("WTF");

        if (actors.add(ship)) {

            if (ship == null)
                throw new RuntimeException("WTF");

            shipId = ship.getId();
            status = PlayerStatus.ALIVE;
        } else {
            throw new RuntimeException("Unable to add new player ship to ActorSet");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player setPlayerId(int id) {
        playerId = id;
        return this;
    }

    public void setShipId(ActorId shipId) {
        this.shipId = shipId;
    }

    @Override
    public String toString() {
        String msg = name + " #" + playerId + " " + status;
        if (ship != null) {
            msg += " @ " + ship.getPosition();
        }
        return msg;
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
