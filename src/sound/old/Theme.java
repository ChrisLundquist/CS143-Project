package sound;

import java.util.ArrayList;


public class Theme implements Runnable {
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    
    public Theme(ArrayList<Piece> pieces){
        this.pieces = pieces;
    }
    
    public Theme(){
        
    }
    
    public void addPiece(Piece piece){
            pieces.add(piece);
    }
    
    public void play(){
        for(Piece p: pieces){
            p.play();
        }
    }

    @Override
    public void run() {
        this.play();
    }
}
