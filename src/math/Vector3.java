package math;

import java.io.Serializable;

// Differentiates between position and direction.
// Vectors should be used for directions, not positions.
public class Vector3 implements Serializable {
    private static final long serialVersionUID = -1520340574791669154L;
    public static final Vector3 UNIT_X = new Vector3(1, 0, 0);
    public static final Vector3 UNIT_Y = new Vector3(0, 1, 0);
    public static final Vector3 UNIT_Z = new Vector3(0, 0, 1);
    
    public float x,y,z;

    public Vector3(){
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public String toString() {
        return String.format("<%g, %g, %g>", x, y, z);
    }

    public Vector3 times(float scalar){
        return new Vector3( x * scalar, y * scalar, z * scalar);
    }

    public Vector3 plus(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }
    
    public Vector3 plusEquals(Vector3 other){
        x += other.x;
        y += other.y;
        z += other.z;
        
        return this;
    }
    
    public Vector3 times(Quaternion rotation) {
        float[] m = rotation.toGlMatrix();
        
        return times(m);
    }
    
    public Vector3 times(float[] m) {
        Vector3 result = new Vector3();
        
        result.x = x * m[0] + y * m[4] + z * m[8];
        result.y = x * m[1] + y * m[5] + z * m[9];
        result.z = x * m[2] + y * m[6] + z * m[10];
        return result;
    }

    public float magnitude() {
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3 minusEquals(Vector3 other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        
        return this;
    }
}