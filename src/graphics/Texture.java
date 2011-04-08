package graphics;

import java.util.Vector;
import javax.media.opengl.*;
import javax.imageio.*;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Texture {
    private static Vector<Texture> textures = new Vector<Texture>();
    private static int lastID = 0;

    private transient int glTexture;
    private int id;

    public int getGlTexture() {
        return glTexture;
    }

    public static Texture findById(int id) {
        for(Texture texture : textures){
            if(texture.id == id)
                return texture;
        }
        return null;
    }
    
    
    public Texture(GL gl, File textureFile) {
        id = ++lastID;
        BufferedImage image;
        try {
            image = ImageIO.read(textureFile);
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new RuntimeException("unable to open " + textureFile.getAbsolutePath());
        }
        // Java really wanted to modify an array pointer
        int[] texture_ids = new int[1];
        gl.glGenTextures(1, texture_ids, 0); // not sure what the third argument is.
        glTexture = texture_ids[0];

        gl.glBindTexture(GL.GL_TEXTURE_2D, glTexture);
        makeRGBTexture(gl, image, GL.GL_TEXTURE_2D);
        // Setup filters
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
    }

    
    /* Switched texture loading method, to method from
     * http://today.java.net/pub/a/today/2003/09/11/jogl2d.html
     * 
     * This uses the Java graphics library to convert the color space
     * byte order and flip the image vertically so it is suitable for OpenGL.
     */
    private void makeRGBTexture(GL gl, BufferedImage img, int target) {
        /* Setup a BufferedImage suitable for OpenGL */
        WritableRaster raster = Raster.createInterleavedRaster (DataBuffer.TYPE_BYTE,
                img.getWidth(),
                img.getHeight(),
                4,
                null);
        ComponentColorModel colorModel = new ComponentColorModel (ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[] {8,8,8,8},
                true,
                false,
                ComponentColorModel.TRANSLUCENT,
                DataBuffer.TYPE_BYTE);          
        BufferedImage bufImg = new BufferedImage (colorModel,
                raster,
                false,
                null);


        /* Setup a Graphic2D context that will flip the image vertically along the way */
        Graphics2D g = bufImg.createGraphics();
        AffineTransform gt = new AffineTransform();
        gt.translate (0, img.getHeight());
        gt.scale (1, -1d);
        g.transform (gt);
        g.drawImage (img, null, null );

        /* Fetch the raw data out of the image and destroy the graphics context */
        byte[] imgRGBA = ((DataBufferByte)raster.getDataBuffer()).getData();
        g.dispose();

        /* Convert the raw data to a buffer for glTexImage2D */
        ByteBuffer dest = ByteBuffer.allocateDirect(imgRGBA.length);
        dest.order(ByteOrder.nativeOrder());
        dest.put(imgRGBA, 0, imgRGBA.length);

        // Rewind the buffer so we can read it starting and beginning
        dest.rewind();

        gl.glTexImage2D(target, 0, GL.GL_RGBA, img.getWidth(), img.getHeight(), 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, dest);
    }
}
