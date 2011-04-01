package math;

// Differentiates between a position vector and a direction vector
// Points should be used for positions, not directions.
public class Point {
    public float x,y,z;

    Point(){
        x = 0;
        y = 0;
        z = 0;
    }

    Point( float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
