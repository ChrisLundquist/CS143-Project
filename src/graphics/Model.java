package graphics;

import java.util.Vector;
import javax.media.opengl.GL2;

import math.Vector3;

public class Model implements math.Supportable{
    private static final String MODEL_PATH = "assets/models/";

    private static final int NO_LIST = -1;
    protected int id;
    int displayList;
    Vector<Polygon> polygons;

    private static Vector<Model> models = new Vector<Model>();

    public Model(Vector<Polygon> polygons){
        this.polygons = polygons;
        displayList = NO_LIST;
        //TODO load collision models from a manifest file
        //collisionModel = new math.Sphere(new math.Vector3(),2.0f);
        
    }

    public static Model findById(int modelId) {
        return models.get(modelId);
    }

    public static void loadModels() {
        // CL - Do we want to generate display lists for everything at load time
        //      or do that lazily?
        models.add(WavefrontObjLoader.load(MODEL_PATH + "cube_cube.obj"));
        models.add(WavefrontObjLoader.load(MODEL_PATH + "cube.obj"));
        models.add(WavefrontObjLoader.load(MODEL_PATH + "skybox.obj"));
        models.add(WavefrontObjLoader.load(MODEL_PATH + "shuttle.obj"));
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
    

    public static int getModelIdFor(actor.Actor actor) {
        // FIXME replace the magic numbers!
        if(actor instanceof actor.Asteroid){
            return 0;
        } else if (actor instanceof actor.PlayerShip) {
            return 3;
        }
        return 0;
    }

    /**
     * Initializes the models and needed textures
     * @param gl the current OpenGL Context
     */
    public static void initialize(GL2 gl) {
        Texture.initialize(gl);
        loadModels();
        for(Model model : models){
            model.init(gl);
        }
    }

    @Override
    public Vector3 getFarthestPointInDirection(Vector3 direction) {
        //TODO Loop through our geometry and find that point
        return new Vector3();
    }
}
