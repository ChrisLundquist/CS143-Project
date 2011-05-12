package sound;

/**
 * 
 * @author Durandal
 * NOTE: You must use Mono (single channel) buffers if you want them to be spatialized by OpenAL
 */
public class Loader {

    public static Buffer load(String path) {
        return new Buffer(path);
    }

}
