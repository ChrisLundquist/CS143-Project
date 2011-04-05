package sound;
import java.io.File;

public class EntryPoint {

    public static void main(String[] args) {
        Piece p1 = new Piece(new File("roar.mp3"));
        Piece p2 = new Piece(new File("bond.mp3"));

        Piece[] pieces = {p1,p2};
        
        Theme test = new Theme(pieces);
        test.play();
    }

}
