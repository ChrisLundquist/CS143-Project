package sound;
import java.util.ArrayList;

public class EntryPoint {
    
    public static final String A_ASSETS = "assets/audio/";

    public static void main(String[] args) {
        Piece p1 = new Piece(A_ASSETS+"200-0.5.wav","first");
        Piece p2 = new Piece(A_ASSETS+"200-0.5.wav","second");

        ArrayList<Piece> pieces = new ArrayList<Piece>();
        pieces.add(p1);
        pieces.add(p2);
        
        Theme test = new Theme(pieces);
        test.play();
    }

}
