/*************************************************************************  
 *  Plays an MP3 file using the JLayer MP3 library.
 *
 *  Reference:  http://www.javazoom.net/javalayer/sources.html
 *  			http://www.cs.princeton.edu/introcs/faq/mp3/MP3.java.html
 *************************************************************************/
package sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/* Defined in the JLayer Jar */
import javazoom.jl.player.advanced.*;

public class Sound {
    static public final String SOUND_DIR = "data";

    private File soundFile;

    // Constructor takes the filePath
    public Sound(String filename) {
        this(new File(SOUND_DIR, filename));
    }

    public Sound(File file) {
        soundFile = file;
        open();
    }

    public AdvancedPlayer open() {
        try {
            FileInputStream fis     = new FileInputStream(soundFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            return new AdvancedPlayer(bis);
        } catch (Exception e) {
            System.err.println("Problem opening file " + soundFile);
            System.err.println(e);
        }
        return null;
    }

    /* FIXME: There must be a cleaner way to do the playing of sounds in threads. */
    public void play() {
        // Run in new thread to play in background
        // by creating an anonymous class like a Functor in C++
        // or a block in Ruby
        new Thread() {
            public void run() {
                try {
                    AdvancedPlayer player = open();
                    player.play();
                    player.close();
                } catch (Exception e) {
                    System.out.println(e); 
                }
            }
        }.start();
    }

    /**
     * Main method for testing this module.
     * @param args
     */
    public static void main(String[] args) {
        Sound test = new Sound("player_death.mp3");
        test.play();
        System.err.println("Success");
    }
}

