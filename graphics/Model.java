package graphics;

import java.util.Vector;

public class Model {
    private static Vector<Model> models = new Vector<Model>();	



    public static Model findById(int modelId) {
        return textures.get(id);
    }

    public static void loadModels() {
        // TODO Auto-generated method stub

    }
    
    public class Vertex {
        float x;
        float y;
        float z;
        float u;
        float v;
        
        public Vertex(x, y, z, u, v) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.u = u;
            this.v = v;
        }
        
        public Vertex(x, y, z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.u = 0.0f;
            this.v = 0.0f; 
        }
        
        public String toString() {
            return "<" + x + ", " + y + ", " + z + ">";
        }
    }
    
    public class Polygon {
        int textureId;
        Vector<Vertex> verticies;
        
        
        public Polygon(int textureId, Vertex[] verticies) {
            this.textureId = textureId;
            this.verticies = new Vector<Vertex>(verticies.length);
            this.verticies.copyInto(verticies);
        }
        
        public Polygon(int textureId, Collection<Vertex> verticies) {
            this.textureId = textureId;
            this.verticies = new Vector<Vertex>(verticies);
        }
        
        public Polygon[] toTriangleFan() {
            Polygon[] result;
            Vertex first;
            
            if (verticies.length < 3)
                return null;
            
            if (verticies.length == 3)
                return this;
            
            result = new Polygon[verticies.length - 2];
            for (Polygon iter: result) {
                
            }
            
 
        }
    }
    
    public static void load3ds(File input) {
        Vector<Point> points = new Vector<Point>();
        Vector<Polygon> polygons = new Vector<Polygon>();
    }

}
