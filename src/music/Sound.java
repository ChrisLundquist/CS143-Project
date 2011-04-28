package music;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound{ 

	private String filename;
	private File soundFile;

	private SourceDataLine auline = null;
	private AudioInputStream audioInputStream = null;

	private boolean isPlaying = false;
	private boolean isStarted = false;


	private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 

	public Sound(String wavfile) throws UnsupportedAudioFileException, IOException { 
		filename = wavfile;

		setup();
	} 

	/**
	 * Prepares the audio file for execution.  
	 * This should be run at the beginning of the game otherwise you can see lag.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	private void setup() throws UnsupportedAudioFileException, IOException{
	    //Try to construct the file object to read
		this.soundFile = new File(filename);
		if (!this.soundFile.exists()) { 
			System.err.println("Wave file not found: " + filename);
			return;
		} 
		
		//Gets the input stream

		audioInputStream = AudioSystem.getAudioInputStream(this.soundFile);


		AudioFormat format = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try { 
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (LineUnavailableException e) { 
			e.printStackTrace();
			return;
		} catch (Exception e) { 
			e.printStackTrace();
			return;
		} 

		//start parsing, but don't actually push the data to output
		auline.start();
	}

	/**
	 * Actually plays the song.  
	 */
	private void begin(){
		this.isPlaying = true;
		this.isStarted = true;
		
		try {
			audioInputStream = AudioSystem.getAudioInputStream(this.soundFile);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int nBytesRead = 0;
		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

		try { 
			while (nBytesRead != -1) {
				if(this.isPlaying){
					nBytesRead = audioInputStream.read(abData, 0, abData.length);
					if (nBytesRead >= 0){
						auline.write(abData, 0, nBytesRead);
					}
				}
				
			} 
		} catch (IOException e) { 
			e.printStackTrace();
			return;
		} finally { 
			this.isStarted = false; 
		}
	}
	
	public SourceDataLine getAulin(){
		return this.auline;
	}

	/**
	 * Gets rid of the data from the input line.  Don't call flush if you will ever play the songs again.
	 */
	public void flush() {
		this.auline.drain();
		this.auline.close();
		
	}

	public void play() {
		this.begin();
	}
} 
