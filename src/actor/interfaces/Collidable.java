package actor.interfaces;

import actor.Actor;

// TODO see if we can abstract the parameters from type Actor to Collidable
//      The current challenge is that GJKSimplex needs a supportable and to handle the
//      collision we will need to know actor details
public interface Collidable {
    boolean isColliding(Actor other);
    void handleCollision(Actor other);
}
