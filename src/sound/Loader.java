package sound;

public class Loader {

    public static Buffer load(String path) {
        return new Buffer(path);
    }

}
