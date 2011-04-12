package sound;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;
import java.util.Map;

import utils.Cycle;

/**
 * The main class that deals with the mixing of music.  
 * @author ty
 * TODO: Make this actually run in a thread.
 */
public class Mixer implements Runnable {
    //boolean switch to debug the application
    private boolean debug = false;
    //The map of ThemeEnums to actual Themes that it plays from.
    private Map<Integer,Theme> map;
    //The stack that is played from
    private Cycle<Piece> songList = new Cycle<Piece>();
    //Theoretically stops the music.  TODO: test this
    private boolean stop = false;
    
    public Mixer(Map<Integer,Theme> map){
        this.map = map;
    }
    
    public Mixer(Map<Integer,Theme> map,boolean debug){
        this.map = map;
        this.debug = debug;
    }
    /**
     * Starts playing on the mix.
     */
    private void play(){
        if(this.debug){System.out.println("Starting to play music");}
        while(!this.stop && !songList.empty()){
            Piece current = songList.recycle();
            if(this.debug){System.out.println("Playing: " + current.getName());}
            current.play();
            //TODO: make the song recycle back into the loop
        }
    }
    
    /**
     * Changes the theme based on what ThemeKey is passed
     * @param themeKey The theme that you want to switch to 
     */
    public void change(Integer themeKey ){
        System.out.println("");
        Theme theme = this.map.get(themeKey);
        songList.clear();
        //songList.add(theme.getTransition());
        
        for(Piece p:theme.getPieces()){
            songList.add(p);
        }
    }
    
    /**
     * Overridden for the thread
     */
    public void run() {
        play();
    }
    
    /**
     * Stops the music.
     */
    public void terminate(){
        this.stop = true;
        //TODO: Test to see if this actually works
        //TODO: Make it so that it stops the actual song rather than just refusing to play the next one.
    }
}
