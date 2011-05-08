package graphics;

import java.util.Map;
import java.util.List;
import javax.media.opengl.GL2;
import math.Vector3;

public class Model implements math.Supportable{
    static final String MODEL_PATH = "assets/models/";
    static final String MODEL_EXTENSION = ".obj";
    private static String[] COMMON_MODELS = {"round_capital", "bullet", "missile", "cube_cube"};
    private static final int NO_LIST = -1;
    
    protected static Map<String, Model>models = new java.util.HashMap<String, Model>();

    public static Model findOrCreateByName(String name) {
        Model model = models.get(name);
        if(model == null)
            model = createByName(name);
        return model;
    }

    public static Model findByName(String name){
        return models.get(name);
    }

    public static Model createByName(String name){
        Model model = WavefrontObjLoader.load(name, MODEL_PATH + name + MODEL_EXTENSION);
        models.put(name, model);
        return model;
    }

    public static void loadModels() {
        /* This was a little over ambitious for now, but if new models might be showing up it might be a good idea
        File dir = new File(MODEL_PATH);
        
        // Loop through all models in models directory
        for(String file: dir.list())
            if (file.toLowerCase().endsWith(MODEL_EXTENSION))
                Model.findOrCreateByName(file.replaceAll(".obj$", ""));
        */
        for (String model: COMMON_MODELS)
            Model.findOrCreateByName(model);
       

        for (WavefrontLoaderError err: WavefrontObjLoader.getErrors())
            System.err.println(err);
        for (WavefrontLoaderError err: WavefrontMtlLoader.getErrors())
            System.err.println(err);
    }
    
    /**
     * Initializes the models and needed textures
     * @param gl the current OpenGL Context
     */
    public static void initialize(GL2 gl) {
        Texture.initialize(gl);
        loadModels();
        for(Model model : models.values()){
            model.init(gl);
        }
    }

    
    
    int displayList;
    List<Polygon> polygons;
    public final String name;
    public final float radius;
    
    public Model(String name, List<Polygon> polygons){
        this.name = name;
        this.polygons = polygons;
        displayList = NO_LIST;
        radius = findRadius();
    }

    /* 
     * Build a display list for this model
     */
    public void init(GL2 gl) {
        displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        renderPolygons(gl);
        gl.glEndList();
        // CL -We don't free our geometry so we can keep it for collision detection
    }

    private void renderPolygons(GL2 gl){
        for (Polygon p: polygons)
            p.render(gl);
    }

    public void render_slow(GL2 gl){
        renderPolygons(gl);
    }

    public void render(GL2 gl) {
        // CL - The scaling, rotating, translating is handled per actor
        //      The display list should have already been "adjusted" if it
        //      wasn't at the center of mass or correct world orientation
        //      when it was loaded.
        if(gl.glIsList(displayList) == false)
            init(gl);
        else
            gl.glCallList(displayList);
    }

    @Override
    public Vector3 getFarthestPointInDirection(Vector3 direction) {
        Vector3 max = polygons.get(0).verticies.get(0).coord;

        // Loop though all of our polygons
        for(Polygon p : polygons){
            // And all of the vertices in each polygon
            for(Vertex v : p.verticies){
                if (max.dotProduct(direction) < v.coord.dotProduct(direction)) { 
                    max = v.coord; 
                } 
            }
        }
        // It is important we return a new vector and not a reference to one in our geometry
        return new Vector3(max);
    }
    
    /**
     * Finds the bounding sphere radius
     * @return the bounding sphere radius
     */
    private float findRadius() {
        float radius2 = 0.0f;
        
        for (Polygon p: polygons)
            for (Vertex v: p.verticies)
                radius2 = Math.max(radius2, v.coord.magnitude2());
        
        return (float)Math.sqrt(radius2);
    }
}
