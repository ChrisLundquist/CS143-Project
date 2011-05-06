package graphics;

import java.io.File;

public class WavefrontLoaderError {
    private String filepath;
    private String message;

    public WavefrontLoaderError(File file, String message) {
        this.filepath = file.getPath();
        this.message = message;
    }
    
    public String toString() {
        return filepath + ": " + message;
    }

}
