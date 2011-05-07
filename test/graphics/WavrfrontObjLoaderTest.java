package graphics;

import static org.junit.Assert.*;
import java.io.File;
import graphics.WavefrontObjLoader;
import org.junit.Test;

public class WavrfrontObjLoaderTest {



    @Test
    /* No output should be printed about unhandled tokens */
    public void testLoadString() {
        File dir = new File(graphics.Model.MODEL_PATH);
        
        // Loop through all models in models directory
        for(String file: dir.list()) {
            if (file.toLowerCase().endsWith(graphics.Model.MODEL_EXTENSION))
                WavefrontObjLoader.load("foo", graphics.Model.MODEL_PATH + file);

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
