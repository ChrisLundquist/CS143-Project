package sound;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.jogamp.openal.AL;


/**
 * 
 * @author Durandal
 * Buffer represents an OpenAL sound buffer and handles buffer resources
 * NOTE: You must use Mono (single channel) buffers if you want them to be spatialized by OpenAL
 */
public class Buffer {
    private static final int NO_SOUND = -1;
    private transient int[] alSound;
    protected String name; /* fileName */
    protected int[] format, size, freq, loop;
    protected ByteBuffer[] data;

    // New Buffers should only be created by Loader
    Buffer(String filePath){
        name = filePath;
        format = new int[1];
        size = new int[1];
        freq = new int[1];
        loop = new int[1];
        alSound = new int[1]; // The Id generated from OpenAL for this buffer
        data = new ByteBuffer[1];
        alSound[0] = NO_SOUND;
        
        AL al = Manager.getAL();
        // Load wav data into a buffer.
        al.alGenBuffers(1, IntBuffer.wrap(alSound));
        Manager.checkForALErrorsWithMessage("Unable to generate buffer for " + filePath);
        // Loader takes care of opening Wav or Ogg and populates these fields
    }

    public String getName(){
        return name;
    }

    public int getId() {
        return alSound[0];
    }
}
