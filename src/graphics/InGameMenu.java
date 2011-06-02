package graphics;

/**
 * In game menu, allows people to rage quit and turn off their sound
 * @author Tim Mikeladze
 *
 */
public class InGameMenu extends HUDTools {
    private static boolean menuOpen = false;
    @SuppressWarnings("unused")
    private int selection;
  
    public InGameMenu() {
      
    }
    
    public static boolean isMenuOpen() {
        return menuOpen;
    }

    public static void setMenuOpen(boolean menuOpen) {
        InGameMenu.menuOpen = menuOpen;
    }
}
