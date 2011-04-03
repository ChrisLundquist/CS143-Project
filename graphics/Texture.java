package graphics;

import java.util.Vector;

public class Texture {
    private static Vector<Texture> textures = new Vector<Texture>();

    private int glTexture;

    public static Texture findById(int id) {
        return textures.get(id);
    }
}
