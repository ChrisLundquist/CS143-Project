/**
 * 
 */
package graphics;
import math.Vector3;
public class Vertex {
    Vector3 coord; // Model space coordinates
    float u; // Texture coordinates
    float v;

    public Vertex(float x, float y, float z, float u, float v) {
        this(new Vector3(x,y,z));
        this.u = u;
        this.v = v;
    }

    public Vertex(float x, float y, float z) {
        this(new Vector3(x,y,z));
        this.u = 0.0f;
        this.v = 0.0f; 
    }

    public Vertex(Vector3 coord){
        this.coord = coord;
        u = 0.0f;
        v = 0.0f;
    }

    public Vertex(Vertex other) {
        this.coord = other.coord;
        this.u = other.u;
        this.v = other.v;
    }

    public String toString() {
        return "<" + coord.x + ", " + coord.y + ", " + coord.z + ">";
    }
    
    public float getX(){
        return coord.x;
    }
    
    public float getY(){
        return coord.y;
    }
    
    public float getZ(){
        return coord.z;
    }
}