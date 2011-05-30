package graphics;

import java.awt.Toolkit;

import javax.media.opengl.GL2;
/**
 * All the methods used by classes that draw Textures to the screen
 * @author Tim Mikeladze
 *
 */
public class HUDTools {
    protected final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    protected final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    /**
     * Easier way to draw 2d shapes
     * @param x x coord top left
     * @param y y coord top left
     * @param width width of image
     * @param height height of image
     * @param gl
     */
    protected void draw(float x, float y, float width, float height, GL2 gl) {
        gl.glTexCoord2d(0.0, 1.0); gl.glVertex2d(x,y);
        gl.glTexCoord2d(0.0, 0.0); gl.glVertex2d(x,y-height);
        gl.glTexCoord2d(1.0, 0.0); gl.glVertex2d(x+width,y-height); 
        gl.glTexCoord2d(1.0, 1.0); gl.glVertex2d(x+width, y);
    }

    /**
     * Changes to 2D
     * @param gl
     */
    protected void start2D(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_BLEND);
        gl.glDisable(GL2.GL_LIGHTING);

        gl.glMatrixMode(GL2.GL_PROJECTION );
        gl.glPushMatrix();   // projection matrix 
        gl.glLoadIdentity();

        //So I don't forget -Tim
        //glOrtho(left,right,bottom,top,nearVal,farVal)
        // gl.glOrtho(-100.0f, 100.0f, -100.0f, 100.0f, -100.0f, 100.0f );
        gl.glOrtho(-WIDTH, WIDTH, -HEIGHT, HEIGHT,-100f,100f );

        gl.glMatrixMode(GL2.GL_MODELVIEW );
        gl.glPushMatrix(); // save our model matrix 
        gl.glLoadIdentity();
        gl.glColor4f(1,1,1,1);
    }
    /**
     * Stops 2D and goes back to 3D
     * @param gl
     */
    protected void stop2D(GL2 gl) {
        gl.glPopMatrix(); // recover model matrix
        gl.glMatrixMode(GL2.GL_PROJECTION );

        gl.glPopMatrix(); // recover projection matrix 
        gl.glMatrixMode(GL2.GL_MODELVIEW ); 

        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_LIGHTING );
        gl.glDisable(GL2.GL_BLEND);
    }
}
