package sound;

import java.util.ArrayList;

/**
 * The Theme contains individual Pieces that make up the song, as well as a transition Piece.
 * It contains an ArrayList of pieces for the song pieces, and a specific Piece just for the transition.
 * @author ty
 *
 */
public class Theme  {
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    private Piece transition;
    
    /**
     * Pass an ArrayList of Pieces to construct the 
     * @param pieces
     */
    public Theme(ArrayList<Piece> pieces){
        this.pieces = pieces;
        //TODO:add a part of the constructor that takes in the transition piece.
    }
    
    public void addPiece(Piece piece){
            pieces.add(piece);
    }
    
    public ArrayList<Piece> getPieces(){
        return pieces;
    }
    public Piece getTransition(){
        return this.transition;
    }
    
    /**
     * Use only for debugging purposes
     * @deprecated 
     */
    public void play(){
        System.out.println("playing theme");
        for(Piece p: pieces){
            System.out.println("playing piece");
            p.play();
        }
    }
}
