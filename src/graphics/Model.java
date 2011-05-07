package graphics;

import java.util.Map;
import java.util.List;
import javax.media.opengl.GL2;
import math.Vector3;

public class Model implements math.Supportable{
    static final String MODEL_PATH = "assets/models/";
    private static final String ASTEROID = "cube_cube.obj";
    private static final String PLAYER = "cube_cube.obj";
    private static final String SKYBOX = "skybox.obj";
    private static final String BULLET = "bullet.obj";
    private static final String MISSILE = "missile.obj";
    private static final String CAPITAL_SHIP = "round_capital.obj";
    private static final int NO_LIST = -1;
    
    protected static Map<String, Model> models = new java.util.HashMap<String, Model>();
    
    int displayList;
    List<Polygon> polygons;
    protected String name;
    public final float radius;
    
    public Model(List<Polygon> polygons){
        this.polygons = polygons;
        displayList = NO_LIST;
        radius = findRadius();
    }

    private float findRadius() {
        float radius2 = 0.0f;
        for (Polygon p: polygons)
            for (Vertex v: p.verticies)
                radius2 = Math.max(radius2, v.coord.magnitude2());
        
        return (float)Math.sqrt(radius2);
    }

    public static Model findOrCreateByName(String filePath) {
        Model model = models.get(filePath);
        if(model == null)
            model = createByName(filePath);
        return model;
    }

    public static Model findByName(String filePath){
        return models.get(filePath);
    }

    public static Model createByName(String filePath){
        Model model = WavefrontObjLoader.load(MODEL_PATH + filePath);
        model.name = filePath;
        models.put(filePath, model);
        return model;
    }

    public static void loadModels() {
        Model.findOrCreateByName(ASTEROID);
        Model.findOrCreateByName(SKYBOX);
        Model.findOrCreateByName(PLAYER);
        for (WavefrontLoaderError err: WavefrontObjLoader.getErrors())
            System.err.println(err);
        for (WavefrontLoaderError err: WavefrontMtlLoader.getErrors())
            System.err.println(err);
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
        for (Polygon p: polygons) {
            p.render(gl);
        }
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


    public static String getModelIdFor(Object actor) {
        if(actor instanceof actor.Asteroid){
            return ASTEROID;
        } else if (actor instanceof ship.PlayerShip) {
            return PLAYER;
        } else if (actor instanceof Skybox){
            return SKYBOX;
        }else if (actor instanceof ship.CapitalShip){
            return CAPITAL_SHIP;
        } else if(actor instanceof actor.Bullet){
            return BULLET;
        } else if ( actor instanceof actor.Missile){
            return MISSILE;
        }
        return ASTEROID;
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

    public String getName() {
        return name;
    }
}
