package sound;
import java.io.File;
import java.util.ArrayList;

public class EntryPoint {
    
    public static final String A_ASSETS = "assets/audio/";

    public static void main(String[] args) {
        Piece p1 = new Piece(new File(A_ASSETS+"roar.mp3"));
        Piece p2 = new Piece(new File(A_ASSETS+"bond.mp3"));

        ArrayList<Piece> pieces = new ArrayList<Piece>();
        pieces.add(p1);
        pieces.add(p2);
        
        Theme test = new Theme(pieces);
        test.play();
    }

}
