package graphics;

import graphics.core.Texture;

import javax.media.opengl.GL2;

/**
 * In game menu, allows people to rage quit
 * @author Tim Mikeladze
 *
 */
public class InGameMenu extends HUDTools {
    private static boolean menuOpen = false;
    private static int selection=0;
    static Texture inGameMenu;
    private static String RESUME = "assets/images/ingamemenu/resume_selected.png";
    private static String QUIT = "assets/images/ingamemenu/quit_selected.png";
    private int s = HEIGHT / 2;

    public void drawInGameMenu(GL2 gl) {
        this.gl = gl;
        if(isMenuOpen()) {
            start2D();
            if(selection == 0) {
                inGameMenu = Texture.findOrCreateByName(RESUME);
                if(inGameMenu != null) {
                    inGameMenu.bind(gl);
                    gl.glBegin(GL2.GL_QUADS);
                    draw(-s / 2, -s / 2, s, s);
                    gl.glEnd();
                }
            }
            if(selection == 1) {
                inGameMenu = Texture.findOrCreateByName(QUIT);
                inGameMenu.bind(gl);
                gl.glBegin(GL2.GL_QUADS);
                draw(-s / 2, -s / 2, s, s);
                gl.glEnd();
            }
            if(isMenuOpen() == false) {
                unBind();
            }
            stop2D();
        }
    }
    public static void selectionDown() {
        selection = 1;
    }
    public static void selectionUp() {
        selection = 0;
    }
    public static int getSelection() {
        return selection;
    }

    public static boolean isMenuOpen() {
        return menuOpen;
    }

    public static void setMenuOpen(boolean menuOpen) {
        InGameMenu.menuOpen = menuOpen;
    }
}
