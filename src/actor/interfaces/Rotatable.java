package actor.interfaces;

import math.Quaternion;

public interface Rotatable {
    public Quaternion getRotation();
    public Object setRotation(Quaternion rot);
}
