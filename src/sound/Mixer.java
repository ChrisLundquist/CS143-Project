package sound;

import java.util.Map;
import java.util.Stack;

/**
 * The main class that deals with the mixing of music.  
 * @author ty
 * TODO: Make this actually run in a thread.
 */
public class Mixer implements Runnable {
    //TODO: get rid of this shit
    public enum ThemeEnum{
        FIGHT,
        VICTORY,
        LOSE,
        RACE
    }
    //The map of ThemeEnums to actual Themes that it plays from.
    private Map<ThemeEnum,Theme> map;
    //The stack that is played from
    private Stack<Piece> songList = new Stack<Piece>();
    //Theoretically stops the music.  TODO: test this
    private boolean stop = false;
    
    public Mixer(Map<ThemeEnum,Theme> map){
        this.map = map;
    }
    /**
     * Starts playing on the mix.
     */
    private void play(){
        System.out.println("Starting to play music");
        while(!this.stop){
            System.out.println("Playing a new song");
            if(!songList.empty()){
                Piece cur = songList.pop();
                cur.play();
            }
            else{
                break;
            }
            //TODO: make the song recycle back into the loop
        }
    }
    
    /**
     * Changes the theme based on what ThemeKey is passed
     * @param themeKey The theme that you want to switch to 
     */
    public void change(ThemeEnum themeKey ){
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
