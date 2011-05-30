package graphics;

import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GL2;

/**
 * The HUD
 * @author Tim Mikeladze
 *
 */
public class Hud extends HUDTools {


    private Texture healthbackdrop, healthbar, crosshair, gunbar, gunbackdrop, gunammosingle, gunammodouble, gunammomissile;
    
    private static Texture healthcross;

    private static final String HEALTHBACKDROP="assets/images/hud/health_backdrop.png";
    private static final String HEALTHBAR="assets/images/hud/health_bar.png";
    private static final String HEALTHCROSS = "assets/images/hud/health_cross.png";
    private static final String HEALTHCROSSFLASH = "assets/images/hud/health_cross_red.png";
    private static final String CROSSHAIR = "assets/images/hud/crosshair.png";
    private static final String GUNBAR = "assets/images/hud/gun_bar.png";
    private static final String GUNBACKDROP = "assets/images/hud/gun_backdrop.png";
    private static final String GUNAMMOSINGLE = "assets/images/hud/gun_ammo_single.png";
    private static final String GUNAMMODOUBLE = "assets/images/hud/gun_ammo_double.png";
    private static final String GUNAMMOMISSILE = "assets/images/hud/gun_ammo_missile.png";
  
    /**
     * Constructor loads all the textures   
     */
    public Hud() {   
        healthbackdrop = Texture.findOrCreateByName(HEALTHBACKDROP);
        healthbar = Texture.findOrCreateByName(HEALTHBAR);
        healthcross =  Texture.findOrCreateByName(HEALTHCROSS);
        gunbar = Texture.findOrCreateByName(GUNBAR);
        gunbackdrop = Texture.findOrCreateByName(GUNBACKDROP);
        gunammosingle = Texture.findOrCreateByName(GUNAMMOSINGLE);
        gunammodouble = Texture.findOrCreateByName(GUNAMMODOUBLE);
        gunammomissile = Texture.findOrCreateByName(GUNAMMOMISSILE);
        crosshair = Texture.findOrCreateByName(CROSSHAIR);
    }
    
    /**
     * Flashes the health cross red and then resets back to green after 500 ms
     */
    public static void flashHealthCross() {
        healthcross = Texture.findOrCreateByName(HEALTHCROSSFLASH);
        final Timer timer = new Timer();
        long inteveral = 500;
        timer.schedule(new TimerTask() {
            public void run() {
                healthcross = Texture.findOrCreateByName(HEALTHCROSS);
                timer.cancel();
            }
        }, inteveral);
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
        draw(0,0,WIDTH,HEIGHT,gl);
        gl.glEnd();


        if(healthbar != null) {
            healthbar.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(0,0,WIDTH,HEIGHT,gl);
        gl.glEnd();

        if(healthcross != null) {
            healthcross.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(0,0,WIDTH,HEIGHT,gl);
        gl.glEnd();

        //
        if(gunbackdrop != null) {
            gunbackdrop.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(-WIDTH,0,WIDTH,HEIGHT,gl);
        gl.glEnd();

        if(gunbar != null) {
            gunbar.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(-WIDTH,0,WIDTH,HEIGHT,gl);
        gl.glEnd();

        if(gunammosingle != null) {
            gunammosingle.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(-WIDTH,0,WIDTH,HEIGHT,gl);
        gl.glEnd();
        if(crosshair != null) {
            crosshair.bind(gl);
        }
        gl.glBegin(GL2.GL_QUADS );
        //draw(-18,20,36,36,gl);
        gl.glEnd();

        gl.glFlush();
        stop2D(gl);
    }


}
