package graphics;

import java.util.Vector;

public class Model {
    Vector<Polygon> polygons;
    
    private static Vector<Model> models = new Vector<Model>();

    public static Model findById(int modelId) {
        return models.get(modelId);
    }

    public static void loadModels() {
        // TODO Auto-generated method stub
    }
}
