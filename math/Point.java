package math;

// Differentiates between a position vector and a direction vector
// Points should be used for positions, not directions.
public class Point {
    public float x,y,z;

    public Point(){
        x = 0;
        y = 0;
        z = 0;
    }

    Point( float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vector velocity) {
        x += velocity.x;
        y += velocity.y;
        z += velocity.z;
    }

    public void incrementBy(Vector velocity) {
        this.x += velocity.x;
        this.y += velocity.y;
        this.z += velocity.z;
        // TODO Auto-generated method stub
        
    }
}
