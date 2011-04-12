package sound;


public class Theme {
    public static Piece[] pieces;
    
    public Theme(Piece[] pieces){
        Theme.pieces = pieces;
    }
    
    public void play(){
        for(Piece p: pieces){
            p.play();
        }
    }
}
