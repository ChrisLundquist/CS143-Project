package game;

import java.io.Serializable;
import math.Quaternion;
import math.Vector3f;

public class SpawningPosition implements Serializable {
    private static final long serialVersionUID = -5274750818163383655L;
    
    private Quaternion orientation;
    private Vector3f position;
    
    public SpawningPosition(Vector3f position, Quaternion orientation) {
        this.position = position;
        this.orientation = orientation;
    }
    
    public Quaternion getOrientation() {
        return new Quaternion(orientation);
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
    }
}
