package graphics;

import static org.junit.Assert.*;
import graphics.WavefrontObjLoader;

import org.junit.Test;

public class WavrfrontObjLoaderTest {
    public static final String MODEL_DIR = "assets/models/";
    public static final String[] FILES = {
        "cube_cube.obj",
        "cessna.obj",
        "shuttle.obj",
        "bullet.obj",
        "missile.obj",
        "ship_test.obj"
    };

    @Test
    /* No output should be printed about unhandled tokens */
    public void testLoadString() {
        for(String file : FILES){
            WavefrontObjLoader.load(MODEL_DIR + file);
        }
        assertEquals(0, WavefrontMtlLoader.getErrors().size());
        assertEquals(0, WavefrontObjLoader.getErrors().size());
    }
}
