package graphics;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.StringTokenizer;

public class WavefrontMtlLoader {
    public static void main(String[] args) {
        for(Material m: load("assets/example.mtl").values()) {
            System.out.println(m);
        }

        System.out.println("Complete");
    }

    public static HashMap<String, Material> load(String filepath){
        return load(new File(filepath));
    }
    
    public static HashMap<String, Material> load(File file) {
        WavefrontMtlLoader mtl = new WavefrontMtlLoader();
        BufferedReader in;
        String line;

        try {
            in = new BufferedReader(new FileReader(file));

            // While we have lines in our file
            while((line = in.readLine()) != null)
                mtl.readLine(line);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return mtl.getMaterials();
    }

    
    
    private HashMap<String, Material> getMaterials() {
        return materials;
    }



    private Material current_material;
    private HashMap<String, Material> materials;


    private WavefrontMtlLoader() {
        materials = new HashMap<String, Material>();
    }

    private void readLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);

        // And while we have tokens in the line
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            // The first token in the line determines the data type that this line specifies
            switch(tokenType(token)) {
                case COMMENT:
                    /* NOOP - skip remainder of this line */
                    return;
                case NEW_MATERIAL:
                    current_material = new Material(tokenizer.nextToken());
                    materials.put(current_material.getName(), current_material);
                    break;
                case AMBIENT_COLOR:
                    current_material.setAmbientColor(readColor(tokenizer));
                    break;
                case DIFFUSE_COLOR:
                    current_material.setDiffuseColor(readColor(tokenizer));
                    break;
                case SPECULAR_COLOR:
                    current_material.setSpecularColor(readColor(tokenizer));
                    break;
                case ALPHA_TRANSPARENCY:
                    current_material.setTransparency(Float.parseFloat(tokenizer.nextToken()));
                    break;
                case ILLUMINATION_MODEL:
                    // ignore for now
                    return;
                case SHININESS:
                    current_material.setShininess(Float.parseFloat(tokenizer.nextToken()));
                    break;
                default:
                    System.out.println("Unhandled Token: " + token + "\n" +"Line: " + line);
                    return;
            }
        }
    }


    private float[] readColor(StringTokenizer tokenizer) {
        float[] components = new float[5];
        float[] result;
        int i = 0;
    
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            try {
                components[i] = Float.parseFloat(token);
                i++;
            } catch (NumberFormatException e) {
                System.err.println("Unexpected token " + token);
            }
        }
        switch (i) {
            case 1:
                // Java arrays are dumb - if we could create a Vector or ArrayList of floats we could just .toArray, but no we have to member by member copy to an array of the correct length
                result = new float[1];
                result[0] = components[0];
                return result;
            case 3:
                result = new float[3];
                result[0] = components[0];
                result[1] = components[1];
                result[2] = components[2];
                return result;
        }
        return null;
    }

    private TokenType tokenType(String token) {
        if (token.equals("#"))
            return TokenType.COMMENT;
        if (token.equals("newmtl"))
            return TokenType.NEW_MATERIAL;
        if (token.equals("Ka"))
            return TokenType.AMBIENT_COLOR;
        if (token.equals("Kd"))
            return TokenType.DIFFUSE_COLOR;
        if (token.equals("Ks"))
            return TokenType.SPECULAR_COLOR;
        if (token.equals("d"))
            return TokenType.ALPHA_TRANSPARENCY;
        if (token.equals("Tr"))
            return TokenType.ALPHA_TRANSPARENCY;
        if (token.equals("Ns"))
            return TokenType.SHININESS;
        if (token.equals("illum"))
            return TokenType.ILLUMINATION_MODEL;
        if (token.equals("map_Ka"))
            return TokenType.TEXTURE_MAP_FILENAME;
        return TokenType.UNKNOWN;
    }

    private enum TokenType {
        COMMENT, AMBIENT_COLOR, DIFFUSE_COLOR, SPECULAR_COLOR, ALPHA_TRANSPARENCY, UNKNOWN, SHININESS, ILLUMINATION_MODEL, TEXTURE_MAP_FILENAME, NEW_MATERIAL,
    }
}
