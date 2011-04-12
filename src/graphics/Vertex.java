/**
 * 
 */
package graphics;

public class Vertex {
    float x; // Model space coordinates
    float y;
    float z;
    float u; // Texture coordinates
    float v;

    public Vertex(float x, float y, float z, float u, float v) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.u = u;
        this.v = v;
    }

    public Vertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.u = 0.0f;
        this.v = 0.0f; 
    }
    
    public Vertex(Vertex other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.u = other.u;
        this.v = other.v;
    }
    
    public String toString() {
        return "<" + x + ", " + y + ", " + z + ">";
    }
}