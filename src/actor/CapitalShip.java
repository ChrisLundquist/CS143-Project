package actor;

public class CapitalShip extends Actor {

    public CapitalShip() {
        super();
    }
    public void shoot() {
        actor.Actor.addActor(new actor.Bullet(this));
    }
    @Override
    public void handleCollision(Actor other) {
        // TODO Auto-generated method stub
    }
    public void update() {
        super.update();
    }
}
