package graphics;

import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Model {
    int displayList;
    Vector<Polygon> polygons;
    
    private static Vector<Model> models = new Vector<Model>();

    public Model(GL2 gl) {
        displayList = gl.glGenLists(1);
    }
    
    public static Model findById(int modelId) {
        return models.get(modelId);
    }

    public static void loadModels() {
        // TODO Auto-generated method stub
    }
    
    
    /* 
     * Build a display list for this model
     */
    public void init(GL2 gl) {
        gl.glNewList(displayList, GL2.GL_COMPILE);
        
        gl.glBegin(GL.GL_TRIANGLES);
        for (Polygon p: polygons) {
            p.render(gl);
        }
        gl.glEnd();
        
        gl.glEndList();  
    }
    
    public void render(GL2 gl) {
        // TODO push rotation, scaling, etc
        gl.glCallList(displayList);
    }
}
