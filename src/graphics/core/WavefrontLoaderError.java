package graphics.core;

import java.io.*;

public class WavefrontLoaderError {
    private String filepath;
    private String message;


    public WavefrontLoaderError(File file, String message) {
        this.filepath = file.getPath();
        this.message = message;
    }

    public WavefrontLoaderError(File file, Throwable e) {
        this.filepath = file.getPath();
        Writer result = new StringWriter();

        e.printStackTrace(new java.io.PrintWriter(result));

        message = result.toString();
    }

    public String toString() {
        return filepath + ": " + message;
    }
}
