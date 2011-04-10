package graphics;

import java.util.Vector;

import javax.media.opengl.GL2;

public class Model {
    private static final int NO_LIST = -1;
    protected int id;
    int displayList;
    Vector<Polygon> polygons;

    private static Vector<Model> models = new Vector<Model>();

    public Model(Vector<Polygon> polygons){
        this.polygons = polygons;
        displayList = NO_LIST;
    }

    public static Model findById(int modelId) {
        return models.get(modelId);
    }

    public static void loadModels() {
        models.add( WavefrontObjLoader.load("assets/models/cube.obj"));
    }

    /* 
     * Build a display list for this model
     */
    public void init(GL2 gl) {
        displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        renderPolygons(gl);
        gl.glEndList();  
    }

    private void renderPolygons(GL2 gl){
        gl.glBegin(GL2.GL_TRIANGLES);
        for (Polygon p: polygons) {
            p.render(gl);
        }
        gl.glEnd();
    }

    public void render_slow(GL2 gl){
        renderPolygons(gl);
    }

    public void render(GL2 gl) {
        // CL - The scaling, rotating, translating is handled per actor
        //      The display list should have already been "adjusted" if it
        //      wasn't at the center of mass or correct world orientation
        //      when it was loaded.
        if(displayList == NO_LIST)
            init(gl);
        gl.glCallList(displayList);
    }

    public static int getModelIdFor(actor.Actor actor) {
        // FIXME replace the magic numbers!
        if(actor instanceof actor.Asteroid){
            return 0;
        } else if(actor instanceof actor.Player) {
            return 1;
        }
        return 0;
    }
}
