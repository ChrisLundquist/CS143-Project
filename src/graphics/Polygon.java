/**
 * 
 */
package graphics;

import java.util.Vector;

public class Polygon {
    int textureId;
    Vector<Vertex> verticies;

    public Polygon(int textureId, Vertex[] verticies) {
        this.textureId = textureId;
        this.verticies = new Vector<Vertex>(verticies.length);
        this.verticies.copyInto(verticies);
    }

    public Polygon(int textureId, java.util.Collection<Vertex> verticies) {
        this.textureId = textureId;
        this.verticies = new Vector<Vertex>(verticies);
    }

    public Polygon[] toTriangleFan() {
        return null;
    }
}