package graphics.core;

import java.util.Map;
import javax.media.opengl.GL2;

public class Material {
    public static class Color {
        public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);
        public static final Color GRAY = new Color(0.7f,0.7f, 0.7f);

        public float r, g, b;

        public Color(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public Color(float[] color) {
            switch(color.length) {
                case 3:
                    r = color[0];
                    g = color[1];
                    b = color[2];
                    break;
                case 1:
                    r = g = b = color[0];
                    break;
                default:
                    String msg = "Invalid color : ";
                    for (float i: color)
                        msg += i + " ";
                    throw new IllegalArgumentException(msg);
            }
        }

        public Color(int r, int g, int b) {
            this.r = r / 255.0f;
            this.g = g / 255.0f;
            this.b = b / 255.0f;
        }

        public float[] toFloat(){
            float[] array = new float[4];
            array[0] = r;
            array[1] = g;
            array[2] = b;
            array[3] = 1.0f; /* alpha */
            return array;
        }

        public String toString() {
            return String.format("#%02x%02x%02x", (int)(r * 255), (int)(g * 255), (int)(b * 255));
        }
    }
    protected static final Material DEFAULT_MATERIAL = new Material("NONE");
    protected static Map<String, Material> materials = new java.util.HashMap<String, Material>();
    
    public static Material findByName(String name) {
        // TODO Optimize: See if we can load this and get rid of the if statement
        Material mat = materials.get(name);
        if(mat == null)
            return DEFAULT_MATERIAL;
        else
            return mat;
    }
    public static Material findOrCreateByName(String name) {
        Material mat = materials.get(name);
        if(mat == null)
            mat = new Material(name);
        return mat;
    }
    private float alpha;
    private Color ambient;
    private Color diffuse;
    private String name;
    private float[] shininess;
    private Color specular;
    private Texture texture;
    
    private String textureName;

    private Material(String name) {
        this.name = name;
        ambient = Color.GRAY;
        specular = Color.GRAY;
        diffuse = Color.GRAY;
        alpha = 1.0f;
        shininess = new float[1];
    }

    public String getName() {
        return name;
    }

    public Texture getTexture() {
        if(texture == null)
            texture = Texture.findByName(textureName);
        return texture;
    }

    public void prepare(GL2 gl) {
        //TODO find a way to clean this up, its pretty ugly
        float[] color = specular.toFloat();
        color[3] = alpha;
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, color,0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, shininess,0);
        color = diffuse.toFloat();
        color[3] = alpha;
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, color,0);
        color = ambient.toFloat();
        color[3] = alpha;
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, color,0);
        gl.glColor4f(color[0], color[1], color[2], color[3]);
        

        Texture texture = getTexture();
        if(texture != null)
            texture.bind(gl);
        else // We don't have a texture so don't use one
            gl.glBindTexture(GL2.GL_TEXTURE_2D, 0); // Based of NeHe lesson 36 this is how you unbind a texture
    }

    public void setAmbientColor(float[] color) {
        ambient = new Color(color);
    }

    public void setDiffuseColor(float[] color) {
        diffuse = new Color(color);
    }

    public void setShininess(float shininess) {
        this.shininess[0] = shininess;
    }

    public void setSpecularColor(float[] color) {
        specular = new Color(color);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.textureName = texture.getName();
    }

    public void setTransparency(float alpha) {
        this.alpha = alpha;
    }

    public String toString() {
        return "<Material: " + name + " " + ambient + " " + diffuse + " " + specular + " " + alpha + " " + shininess + ">";
    }
}
