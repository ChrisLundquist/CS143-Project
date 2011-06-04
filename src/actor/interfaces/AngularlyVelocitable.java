package actor.interfaces;

import math.Quaternion;

public interface AngularlyVelocitable {
    public Quaternion getAngularVelocity();
    public Object setAngularVelocity(Quaternion omega);
}
