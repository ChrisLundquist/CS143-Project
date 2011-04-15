// Import the JLayer classes
import javazoom.jl.player.*;

// Import the Java classes
import java.io.*;

/**
 *
 * @author Ty Overby, shaines
 */
public class SongPlayer {

	private Player player;
	private InputStream is;
	public int position = 0;

	/** Creates a new instance of MP3Player */
	public SongPlayer( String filename ){
		try{
			// Create an InputStream to the file
			is = new FileInputStream( filename );
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

	public void play(){
		try{
			player = new Player( is );
			PlayerThread pt = new PlayerThread();
			pt.start();
			while( !player.isComplete() ){
				this.position = player.getPosition();
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}

	class PlayerThread extends Thread{
		public void run(){
			try{
				player.play();
			}
			catch( Exception e ){
				e.printStackTrace();
			}
		}
	}
}