package math;

// Differentiates between position and direction.
// Vectors should be used for directions, not positions.
public class Vector3 {
    public float x,y,z;

    public Vector3(){
        x = 0;
        y = 0;
        z = 0;
    }

    Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
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
}
