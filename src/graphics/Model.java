package graphics;

import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Model {
    int displayList;
    Vector<Polygon> polygons;

    private static Vector<Model> models = new Vector<Model>();

    public Model(Vector<Polygon> polygons){
        this.polygons = polygons;
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
        displayList = gl.glGenLists(1);
        gl.glNewList(displayList, GL2.GL_COMPILE);
        renderPolygons(gl);
        gl.glEndList();  
    }
    
    private void renderPolygons(GL2 gl){
        gl.glBegin(GL.GL_TRIANGLES);
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
        gl.glCallList(displayList);
    }

    // Test method to test rendering geometry
    public static void renderCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);                          // Start Drawing Quads
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);  // Top Right Of The Texture and Quad
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);  // Top Left Of The Texture and Quad
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);  // Bottom Left Of The Texture and Quad
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);  // Bottom Right Of The Texture and Quad
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);  // Bottom Left Of The Texture and Quad
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);  // Bottom Right Of The Texture and Quad
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);  // Top Right Of The Texture and Quad
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);  // Top Left Of The Texture and Quad
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);  // Bottom Right Of The Texture and Quad
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);  // Top Right Of The Texture and Quad
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);  // Top Left Of The Texture and Quad
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);  // Bottom Left Of The Texture and Quad
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);  // Bottom Right Of The Texture and Quad
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);  // Top Right Of The Texture and Quad
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);  // Top Left Of The Texture and Quad
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);  // Bottom Left Of The Texture and Quad
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);  // Bottom Left Of The Texture and Quad
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);  // Bottom Right Of The Texture and Quad
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);  // Top Right Of The Texture and Quad
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);  // Top Left Of The Texture and Quad
        gl.glEnd();                                // Done Drawing Quads

    }
}
