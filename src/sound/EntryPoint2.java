package sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sound.Mixer.ThemeEnum;

public class EntryPoint2 {
	
	public final static String A_ASSETS = "assets/audio/";
	
	public static void main(String[] args) {
	    Map<ThemeEnum,Theme> map = new HashMap<ThemeEnum,Theme>();
    	    ThemeEnum victoryEnum = ThemeEnum.VICTORY;
    	    Piece p1 = new Piece(A_ASSETS+"200-0.5.wav","200");
            Piece p2 = new Piece(A_ASSETS+"200-0.5.wav","200");
    
            ArrayList<Piece> pieces = new ArrayList<Piece>();
            pieces.add(p1);
            pieces.add(p2);
	    Theme victoryTheme = new Theme(pieces);
	    map.put(victoryEnum, victoryTheme);
	    
	    Mixer mix = new Mixer(map);
	    mix.change(ThemeEnum.VICTORY);
	    mix.run();
	    print("test");
	    
	    
	}
	
	public static void print(Object o){
		System.out.println(o.toString());
	}
}
