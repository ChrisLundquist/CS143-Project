public class Vector {
    public float x,y,z;

    Vector(){
        x = 0;
        y = 0;
        z = 0;
    }

    Vector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector times(float scalar){
        return new Vector( x * scalar, y * scalar, z * scalar);
    }
}
