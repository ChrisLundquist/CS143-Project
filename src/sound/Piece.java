package sound;

import java.io.File;

import javazoom.jl.player.advanced.AdvancedPlayer;


public class Piece extends Sound {
    
    private AdvancedPlayer player;
    
    public Piece(File file) {
        super(file);
        System.out.println("opening");
        player = this.open();
        System.out.println("opened");
    }
    
    public void play(){
        System.out.println("playing");
        try {
            player.play();
            System.out.println("closing");
            player.stop();
            System.out.println("closed");
        } catch (Exception e) {
            System.out.println(e); 
        }
    }
}
