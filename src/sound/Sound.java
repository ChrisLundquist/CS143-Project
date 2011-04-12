package sound;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.sound.sampled.*;

public class Sound{ 

	private String filename;
	private File soundFile;

	private SourceDataLine auline = null;
	private AudioInputStream audioInputStream = null;

	private boolean isPlaying = false;
	private boolean isStarted = false;


	private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 

	public Sound(String wavfile) { 
		filename = wavfile;

		setup();
	} 

	/**
	 * Prepares the audio file for execution.  
	 * This should be run at the beginning of the game otherwise you can see lag.
	 */
	private void setup(){
	    //Try to construct the file object to read
		this.soundFile = new File(filename);
		if (!this.soundFile.exists()) { 
			System.err.println("Wave file not found: " + filename);
			return;
		} 
		
		//Gets the input stream
		
		try { 
			audioInputStream = AudioSystem.getAudioInputStream(this.soundFile);
		} catch (UnsupportedAudioFileException e1) { 
			e1.printStackTrace();
			return;
		} catch (IOException e1) { 
			e1.printStackTrace();
			return;
		}

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
            return;
        } catch (IOException e1) { 
            e1.printStackTrace();
            return;
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
