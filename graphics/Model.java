package graphics;

import java.io.File;
import java.util.Vector;

public class Model {
    private static Vector<Model> models = new Vector<Model>();

    public static Model findById(int modelId) {
        // XXX FIXME
        // This line was:
        // return textures.get(id);
        // which was nonsense, I don't think we want to return a straight index
        return models.get(modelId);
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

        public Polygon(int textureId, java.util.Collection<Vertex> verticies) {
            this.textureId = textureId;
            this.verticies = new Vector<Vertex>(verticies);
        }

        public Polygon[] toTriangleFan() {
            Polygon[] result;
            Vertex first;

            if (verticies.size() < 3)
                return null;

            //FIXME
            //if (verticies.size() == 3)
            //  return this;

            result = new Polygon[verticies.size() - 2];
            for (Polygon iter: result) {
                 // TODO
            }
            return result;

        }
    }

    public static void load3ds(File input) {
        Vector<math.Vector> points = new Vector<math.Vector>();
        Vector<Polygon> polygons = new Vector<Polygon>();
    }

}
