package sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import javax.sound.sampled.*;

public class EntryPoint2 {
	
	public final static String A_ASSETS = "assets/audio/";
	
	public static void main(String[] args) {
	    Map<Integer,Theme> map = new HashMap<Integer,Theme>();
    	    int victory = 1;
    	    Piece p1 = new Piece(A_ASSETS+"200-0.5.wav","200");
            Piece p2 = new Piece(A_ASSETS+"100-0.5.wav","100");
    
            ArrayList<Piece> pieces = new ArrayList<Piece>();
            pieces.add(p1);
            pieces.add(p2);
	    Theme victoryTheme = new Theme(pieces);
	    map.put(victory, victoryTheme);
	    
	    Mixer mix = new Mixer(map,true);
	    mix.change(victory);
	    mix.run();
	    print("End");
	    
	    
	}
	
	public static void print(Object o){
		System.out.println(o.toString());
	}
}
