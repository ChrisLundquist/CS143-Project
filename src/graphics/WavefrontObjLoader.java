package graphics;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

/*
 * Singleton class to load Wavefront .obj files
 */
public class WavefrontObjLoader {
    public static void main(String[] args) {
        File file = new File("assets/cube.obj");
        loadModel(file);
    }
    
    public static Model loadModel(File file) {
        Vector<ObjVertex> geo_verticies = new Vector<ObjVertex>();
        Vector<ObjVertex> texture_verticies = new Vector<ObjVertex>();
        Vector<ObjVertex> vertex_normals = new Vector<ObjVertex>();
        Vector<Polygon> polygons = new Vector<Polygon>();
        BufferedReader in;
        String line;

        try {
            in = new BufferedReader(new FileReader(file));

            while((line = in.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);

                switch(tokenType(tokenizer.nextToken())) {
                    case GEOMETRIC_VERTEX:
                        geo_verticies.add(readVertex(tokenizer));
                        break;
                    case VERTEX_NORMAL:
                        vertex_normals.add(readVertex(tokenizer));
                        break;  
                    case TEXTURE_COORDINATE:
                        texture_verticies.add(readVertex(tokenizer));
                        break;
                    case FACE:
                        polygons.add(readPolygon(tokenizer));
                        
                    default:
                        System.out.println("Unhandled Line: " + line);
                }
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }      
    }

    private static ObjVertex readVertex(StringTokenizer tokenizer) {
        ObjVertex vertex = new ObjVertex();
        float value;
        
        while (tokenizer.hasMoreTokens()) {
            value = Float.parseFloat(tokenizer.nextToken());
            vertex.add(value);
        }
        return vertex;
    }
    
    private static Polygon readPolygon(StringTokenizer tokenizer) {
        
        while (tokenizer.hasMoreTokens()) {
            String vertex_token = tokenizer.nextToken();
            
            
            
        }
        
        return null;
    }

    private static TokenType tokenType(String token) {
        if (token.equals("#"))
            return TokenType.COMMENT;
        if (token.equals("v"))
            return TokenType.GEOMETRIC_VERTEX;
        if (token.equals("vt"))
            return TokenType.TEXTURE_COORDINATE;
        if (token.equals("vn"))
            return TokenType.VERTEX_NORMAL;
        if (token.equals("f"))
            return TokenType.FACE;

        return TokenType.UNKNOWN;
    }

    private static enum TokenType {
        UNKNOWN,
        COMMENT,
        GEOMETRIC_VERTEX,
        VERTEX_NORMAL,
        TEXTURE_COORDINATE,
        FACE,
    }

    private static class ObjVertex {
        int dim;
        float x, y, z, w;
        
        public ObjVertex() {
            dim = 0;
            this.x = 0.0f;
            this.y = 0.0f;
            this.z = 0.0f;
            this.w = 0.0f;
        }
        
        void add(float v) {
            switch(dim) {
                case 1:
                    y = v;
                    dim ++;
                    return;
                case 2:
                    z = v;
                    dim ++;
                    return;
                case 3:
                    w = v;
                    dim ++;
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
