package graphics;

import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Hud {
    Texture healthbackdrop, healthbar, healthcross;

    private static final String HEALTHBACKDROP="assets/images/hud/health_backdrop.png";
    private static final String HEALTHBAR="assets/images/hud/health_bar.png";
    private static final String HEALTHCROSS = "assets/images/hud/health_cross.png";

    /**
     * Constructor loads all the textures
     */
    public Hud() {   
        healthbackdrop = Texture.findOrCreateByName(HEALTHBACKDROP);
        healthbar = Texture.findOrCreateByName(HEALTHBAR);
        healthcross =  Texture.findOrCreateByName(HEALTHCROSS);
    }
    /**
     * Draws the static elements of the HUD
     * @param gl
     */
    public void drawStaticHud(GL2 gl) {
        start2D(gl);

        if(healthbackdrop != null) {
            healthbackdrop.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );

        gl.glTexCoord2d(0.0, 0.0); gl.glVertex2d( -90.0f, 90.0f );
        gl.glTexCoord2d(0.0, 1.0); gl.glVertex2d( -90.0f, 40.0f );
        gl.glTexCoord2d(1.0, 1.0); gl.glVertex2d( -40.0f, 40.0f );
        gl.glTexCoord2d(1.0, 0.0); gl.glVertex2d( -40.0f, 90.0f );

        gl.glEnd();
        gl.glFlush();
        stop2D(gl);
    }
    /**
     * Changes to 2D
     * @param gl
     */
    public void start2D(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_BLEND);
        gl.glDisable(GL2.GL_LIGHTING);

        gl.glMatrixMode(GL2.GL_PROJECTION );
        gl.glPushMatrix();   // projection matrix 
        gl.glLoadIdentity();
        gl.glOrtho(-100.0f, 100.0f, -100.0f, 100.0f, -100.0f, 100.0f );

        gl.glMatrixMode(GL2.GL_MODELVIEW );
        gl.glPushMatrix(); // save our model matrix 
        gl.glLoadIdentity();
    }
    /**
     * Stops 2D and goes back to 3D
     * @param gl
     */
    public void stop2D(GL2 gl) {
        gl.glPopMatrix(); // recover model matrix
        gl.glMatrixMode(GL2.GL_PROJECTION );

        gl.glPopMatrix(); // recover projection matrix 
        gl.glMatrixMode(GL2.GL_MODELVIEW ); 

        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_LIGHTING );
        gl.glDisable(GL2.GL_BLEND);
    }
}

