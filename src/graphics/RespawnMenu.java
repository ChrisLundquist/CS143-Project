package graphics;

import javax.media.opengl.GL2;
import graphics.core.Texture;
/**
 * Allows user to choose what ship to respawn as
 * @author Tim Mikeladze
 *
 */
public class RespawnMenu extends HUDTools {
    private static boolean respawnOpen = false;
    static Texture respawnMenu;
    private final static String FIGHTER = "assets/images/respawnmenu/fighter_selected.png";
    private final static String SCOUT = "assets/images/respawnmenu/scout_selected.png";
    private final static String BOMBER = "assets/images/respawnmenu/bomber_selected.png";

    public void drawRespawnMenu(GL2 gl) {
        // TODO Auto-generated method stub
    }
    
    public static boolean isRespawnOpen() {
        return respawnOpen;
    }

    public static void setRespawnOpen(boolean respawnOpen) {
        RespawnMenu.respawnOpen = respawnOpen;
    }

}
