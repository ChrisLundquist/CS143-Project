package graphics;

import static org.junit.Assert.*;

import java.io.File;

import graphics.WavefrontObjLoader;

import org.junit.Test;

public class WavrfrontObjLoaderTest {
    public static final String MODEL_DIR = "assets/models/";


    @Test
    /* No output should be printed about unhandled tokens */
    public void testLoadString() {
        File dir = new File(MODEL_DIR);
        
        // Loop through all models in models directory
        for(String file: dir.list()) {
            if (file.toLowerCase().endsWith(".obj"))
                WavefrontObjLoader.load(MODEL_DIR + file);

            if (WavefrontObjLoader.getErrors().size() > 0) {
                for (WavefrontLoaderError err: WavefrontObjLoader.getErrors())
                    System.err.println(err);
                fail();
            }

            if (WavefrontMtlLoader.getErrors().size() > 0) {
                for (WavefrontLoaderError err: WavefrontMtlLoader.getErrors())
                    System.err.println(err);
                fail();
            }
        }
    }
}
