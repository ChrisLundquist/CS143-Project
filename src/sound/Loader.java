package sound;

import com.jogamp.openal.util.ALut;

/**
 * 
 * @author Durandal
 * NOTE: You must use Mono (single channel) buffers if you want them to be spatialized by OpenAL
 */
public class Loader {

    private static final String OGG_EXTENSION = ".ogg";
    private static final String WAV_EXTENSION = ".wav";


    public static Buffer load(String path) {
        Buffer buffer = new Buffer(path);
        if(path.endsWith(WAV_EXTENSION))
            buffer = loadWAV(buffer);
        else if(path.endsWith(OGG_EXTENSION))
            buffer = loadOGG(buffer);
        else
            throw new RuntimeException("Unrecognized File format on file " + path);
        return buffer;
    }

    private static Buffer loadOGG(Buffer buffer) {
        return null;
    }

    private static Buffer loadWAV(Buffer buffer){
        ALut.alutLoadWAVFile(
                buffer.name,
                buffer.format,
                buffer.data,
                buffer.size,
                buffer.freq,
                buffer.loop);
        Manager.getAL().alBufferData(buffer.getId(), buffer.format[0], buffer.data[0], buffer.size[0], buffer.freq[0]);
        Manager.checkForALErrorsWithMessage("Unable to generate buffer for " + buffer.name);
        return buffer;
    }

}
