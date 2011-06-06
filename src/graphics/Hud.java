package graphics;

import game.Player;
import graphics.core.Texture;
import javax.media.opengl.GL2;
import actor.ship.PlayerShip;
import actor.ship.projectile.Projectile;
import actor.ship.weapon.Weapon;

/**
 * The HUD
 * @author Tim Mikeladze
 *
 */
public class Hud extends HUDTools {
    private static final String HEALTHBACKDROP="assets/images/hud/health_backdrop.png";
    private static final String HEALTHBAR="assets/images/hud/health_bar.png";
    private static final String SHIELDBAR="assets/images/hud/shield_bar.png";
    private static final String HEALTHCROSS = "assets/images/hud/health_cross.png";
    private static final String HEALTHCROSSFLASH = "assets/images/hud/health_cross_red.png";
    private static final String CROSSHAIRDUAL = "assets/images/hud/dual_crosshair.png";
    private static final String CROSSHAIRSINGLE = "assets/images/hud/machinegun_crosshair.png";
    //private static final String CROSSHAIRSNIPER = "assets/images/hud/sniper_crosshair.png";
    private static final String GUNBAR = "assets/images/hud/gun_bar.png";
    private static final String GUNBACKDROP = "assets/images/hud/gun_backdrop.png";
    private static final String GUNAMMOSINGLE = "assets/images/hud/gun_ammo_single.png";
    private static final String GUNAMMODOUBLE = "assets/images/hud/gun_ammo_double.png";
    private static final String GUNAMMOMISSILE = "assets/images/hud/gun_ammo_missile.png";
    
    private Player player;
    private PlayerShip ship;
    private Texture healthbackdrop, healthbar, shieldbar, gunbar, gunbackdrop;
    private Texture healthcross, crosshair, gunammo;
    
    public Hud(Player player) {
        this.player = player;
        
        healthbackdrop = Texture.findOrCreateByName(HEALTHBACKDROP);
        healthbar = Texture.findOrCreateByName(HEALTHBAR);
        shieldbar = Texture.findOrCreateByName(SHIELDBAR);
        healthcross =  Texture.findOrCreateByName(HEALTHCROSS);
        gunbar = Texture.findOrCreateByName(GUNBAR);
        gunbackdrop = Texture.findOrCreateByName(GUNBACKDROP);  
        gunammo = Texture.findOrCreateByName(GUNAMMODOUBLE);
        crosshair = Texture.findOrCreateByName(CROSSHAIRDUAL);
    }
    
    /**
     * Draws the static elements of the HUD     
     * @param gl
     */
    public void drawStaticHud(GL2 gl) {
        this.gl = gl;
       
        if (! player.isAlive())
            return;
        
        update();
        
        start2D();
        //drawEnergy(gl);
        
        // Set the crosshair color to green
        gl.glColor4f(0.0f, 1.0f, 0.0f, 0.0f);
        
        if(crosshair != null) {
            crosshair.bind(gl);
        }
        gl.glBegin(GL2.GL_QUADS);
        int s = HEIGHT / 2; // Cross hair 10% screen height
        
        draw(-s / 2, -s / 2, s, s);
        gl.glEnd();
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        
        if(healthbackdrop != null) { 
            healthbackdrop.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(-WIDTH, -HEIGHT, WIDTH *2, HEIGHT * 2);
        gl.glEnd();

        
        if(healthbar != null) {
            healthbar.bind(gl);
        }
        
        //draws the health bar
        gl.glBegin(GL2.GL_QUADS );
        drawBarGraph(-WIDTH, -HEIGHT, WIDTH *2, HEIGHT * 2, ship.health()*0.72f);
        gl.glEnd();
        
        if(shieldbar!=null){
            shieldbar.bind(gl);
        }
        gl.glBegin(GL2.GL_QUADS );
        drawBarGraph(-WIDTH, -HEIGHT, WIDTH *2, HEIGHT * 2, ship.shield()*0.70f);
        gl.glEnd();

        //health cross
        if(healthcross != null) {
            healthcross.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(-WIDTH, -HEIGHT, WIDTH *2, HEIGHT * 2);
        gl.glEnd();
        
        
        if(gunbackdrop != null) {
            gunbackdrop.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(-WIDTH, -HEIGHT, WIDTH *2, HEIGHT * 2);
        gl.glEnd();

        if(gunbar != null) {
            gunbar.bind(gl);
        }

        //draw the percent bar for the gun
        gl.glBegin(GL2.GL_QUADS );
        drawBarGraph(-WIDTH, -HEIGHT, WIDTH *2, HEIGHT * 2,ship.getWeapon().getAmmoPercent()*0.72f);
        gl.glEnd();

        if(gunammo != null) {
            gunammo.bind(gl);
        }

        gl.glBegin(GL2.GL_QUADS );
        draw(-WIDTH, -HEIGHT, WIDTH *2, HEIGHT * 2);
        gl.glEnd();

        gl.glFlush();
        stop2D();
    }
    @SuppressWarnings("unused")
    private void drawEnergy(GL2 gl) {
        unBind();
        gl.glBegin(GL2.GL_QUADS );
        gl.glColor4f( 1.0f, 0.0f, 0.0f, 0.0f );
        int s = HEIGHT / 2;
        draw(-s / 2, -s / 2, s, s);
        gl.glEnd();
    }
    private void update() {
        ship = player.getShip();
        setupCrossHair(ship.getWeapon());   
        /**
         * Flashes the health cross red and then resets back to green after 500 ms
         */
        if (ship.getLastHitAge() < 30) {
            healthcross = Texture.findOrCreateByName(HEALTHCROSSFLASH);
        } else {
            healthcross = Texture.findOrCreateByName(HEALTHCROSS);
        }
    }

    private void setupCrossHair(Weapon<? extends Projectile> weapon) {
        
        if (weapon instanceof actor.ship.weapon.AlternatingWeapon<?>) {
            crosshair = Texture.findOrCreateByName(CROSSHAIRDUAL);
            gunammo = Texture.findOrCreateByName(GUNAMMODOUBLE);
        } else if (weapon instanceof actor.ship.weapon.TwinLinkedWeapon<?>) {
            crosshair = Texture.findOrCreateByName(CROSSHAIRDUAL);
            gunammo = Texture.findOrCreateByName(GUNAMMODOUBLE);
        } else {
            crosshair = Texture.findOrCreateByName(CROSSHAIRSINGLE);
            gunammo = Texture.findOrCreateByName(GUNAMMOSINGLE);
        }
        
        Projectile p = weapon.newProjectile(ship);
        if (p instanceof actor.ship.projectile.Missile) {
            gunammo = Texture.findOrCreateByName(GUNAMMOMISSILE);
        }   
    }
}
