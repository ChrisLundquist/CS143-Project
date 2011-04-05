package sound;


public class Theme {
    public static Piece[] pieces;
    
    public Theme(Piece[] pieces){
        this.pieces = pieces;
    }
    
    public void play(){
        for(Piece p: pieces){
            p.play();
        }
    }
}
