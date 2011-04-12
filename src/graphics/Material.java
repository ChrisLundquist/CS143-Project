package graphics;

import java.util.HashMap;

public class Material {
    protected static HashMap<String, Material> materials = new HashMap<String, Material>();
    
    private String name;
    private Color ambient;
    private Color specular;
    private Color diffuse;
    private float alpha;
    private float shininess;
    // TODO do we store textures here?

    public Material(String name) {
        this.name = name;
        ambient = Color.WHITE;
        specular = Color.WHITE;
        diffuse = Color.WHITE;
        alpha = 1.0f;
    }
    
    public String toString() {
        return "<Material: " + name + " " + ambient + " " + diffuse + " " + specular + " " + alpha + " " + shininess + ">";
    }
    
    public String getName() {
        return name;
    }

    public static class Color {
        public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);
        
        public float r, g, b;

        public Color(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
        
        public Color(int r, int g, int b) {
            this.r = r / 255.0f;
            this.g = g / 255.0f;
            this.b = b / 255.0f;
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
                    if (color == null)
                        msg += "<NULL>";
                    else 
                        for (float i: color)
                            msg += i + " ";
                    throw new IllegalArgumentException(msg);
            }
        }
        
        public String toString() {
            return String.format("#%02x%02x%02x", (int)(r * 255), (int)(g * 255), (int)(b * 255));
        }
    }

    public void setSpecularColor(float[] color) {
        specular = new Color(color);
    }

    public void setDiffuseColor(float[] color) {
        diffuse = new Color(color);
    }

    public void setAmbientColor(float[] color) {
        ambient = new Color(color);
    }

    public void setTransparency(float alpha) {
        this.alpha = alpha;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
}
