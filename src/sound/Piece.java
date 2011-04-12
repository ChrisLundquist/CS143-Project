package sound;

import java.io.File;

<<<<<<< HEAD
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * The Piece is just a part of a theme.  It contains one .wav file, and the name of that wav file.
 * The reason for wrapping this around the Sound class is that it will eventually have more differences, and I want
 * It to be abstracted from the other Sounds like sound affects.
 * @author ty
 *
 */
public class Piece extends Sound {
    private final String name;

    /**
     * Constructs a Piece with a filename
     * @param file The location of the file
     * @param nameName The name of the Piece
     */
    public Piece(String fileName,String name){
        super(fileName);
        this.name = name;
    }
   
    /**
     * @return The name of the Piece
     */
    public String getName(){
        return this.name;
    }
}
