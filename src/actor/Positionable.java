package actor;

import math.Vector3;
import math.Quaternion;

public interface Positionable {
    public Vector3 getPosition();
    public Quaternion getRotation();
}
