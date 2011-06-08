package actor.ship.projectile;

import actor.Actor;

public class Shrapnel extends Bullet {
    private static final long serialVersionUID = 3635519515225153771L;
    private static final long MAX_AGE = 45;

    public Shrapnel(Actor actor) {
        super(actor);
        damage = 20;
        scale.timesEquals(0.7f);
        // Cheat so we don't collide with our ship that really shot the flakShell which generated this
        parentId = actor.getParentId();
    }
    
    @Override
    public void handleCollision(Actor other) {
        // Don't shoot our parents or other shrapnel
        if (other instanceof Shrapnel)
            return;
        super.handleCollision(other);
    }
    
    public void update() {
        super.update();

        if (age > MAX_AGE){
            die();   
        }
    }
}
