package game;
/**
 * Get operating system
 * @author Tim
 *  
 */
public class GetOS {
      /**
     * Checks if Windows
     * @return
     */
    public static boolean isWindows(){
            String os = System.getProperty("os.name").toLowerCase();
            return (os.indexOf( "win" ) >= 0);
    }
    /**
     * Checks if Mac
     * @return
     */
    public static boolean isMac(){
            String os = System.getProperty("os.name").toLowerCase();
            return (os.indexOf( "mac" ) >= 0);
    }
    /**
     * Checks if Unix/Linux
     * @return
     */
    public static boolean isUnix(){
            String os = System.getProperty("os.name").toLowerCase();
            return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);

    }
}
