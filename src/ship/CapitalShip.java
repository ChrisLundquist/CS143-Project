package ship;

import actor.Actor;

public class CapitalShip extends ship.Ship {
    private static final long serialVersionUID = 3527730335695237893L;
    public CapitalShip() {
        super();
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);
    }
    public void update() {
        super.update();
    }
}
