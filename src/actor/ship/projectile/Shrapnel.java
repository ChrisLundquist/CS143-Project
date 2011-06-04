package actor.ship.projectile;

import actor.Actor;

public class Shrapnel extends Bullet {
    private static final long serialVersionUID = 3635519515225153771L;
    private static final long MAX_AGE = 45;

    public Shrapnel(Actor actor) {
        super(actor);
        damage = 7;
        scale.timesEquals(0.7f);
    }
    
    @Override
    public void handleCollision(Actor other) {
        // Don't shoot our parents or other shrapnel
        if (other instanceof Shrapnel || parentId.equals(other.getId()))
            return;
        die();
    }
    
    public void update() {
        super.update();

        if (age > MAX_AGE){
            die();   
        }
    }
}
