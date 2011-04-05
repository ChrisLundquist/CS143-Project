
public class EntryPoint {
	public static void main( String[] args ){

		SongPlayer song = new SongPlayer("roar.mp3");
		song.play();
		song.play();
		System.out.println("reached");
	}
}
