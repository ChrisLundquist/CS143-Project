package graphics;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

/*
 *  lass to load Wavefront .obj files
 */
public class WavefrontObjLoader {
    private static List<WavefrontLoaderError> errors = new java.util.ArrayList<WavefrontLoaderError>();


    public static Model load(String name, String filepath){
        return load(name, new File(filepath));
    }

    public static Model load(String name, File file) {
        File transform_file = new File(file.getPath().replaceAll(".obj$", ".trfm"));
        WavefrontObjLoader wol = new WavefrontObjLoader(file);
        BufferedReader in;
        String line;


        try {
            if (transform_file.exists())
                wol.loadTranform(transform_file);

            in = new BufferedReader(new FileReader(file));

            // While we have lines in our file
            while((line = in.readLine()) != null)
                wol.readLine(line);
        } catch (IOException e) {
            errors.add(new WavefrontLoaderError(file, e));
            return null;
        }

        return wol.generateModel(name);
    }

    private List<ObjVertex> geoVerticies;
    private List<ObjVertex> textureverticies;
    private List<ObjVertex> vertexNormals;
    private List<Polygon> polygons;
    // our default transform
    private float[] transform = {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    };
    private Material currentMaterial;
    private String currentObject;
    private List<String> currentGroups;
    private File file;


    private WavefrontObjLoader(File file) {
        geoVerticies = new java.util.ArrayList<ObjVertex>();
        textureverticies = new java.util.ArrayList<ObjVertex>();
        vertexNormals = new java.util.ArrayList<ObjVertex>();
        polygons = new java.util.ArrayList<Polygon>();
        currentMaterial = Material.DEFAULT_MATERIAL;
        currentObject = "";
        currentGroups = new java.util.ArrayList<String>();
        this.file = file;
    }

    private void readLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line);

        // And while we have tokens in the line
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            switch(tokenType(token)) {
                case SMOOTHING_GROUP: /* NOT IMPLEMENTED */
                case COMMENT:
                    /* NOOP - skip remainder of this line */
                    return;
                case GROUP:
                    setGroup(tokenizer);
                    return;
                case OBJECT_NAME:
                    setObject(tokenizer.nextToken());
                    return;
                case GEOMETRIC_VERTEX:
                    geoVerticies.add(readVertex(tokenizer).transform(transform));
                    break;
                case VERTEX_NORMAL:
                    vertexNormals.add(readVertex(tokenizer).transform(transform));
                    break;
                case TEXTURE_COORDINATE:
                    textureverticies.add(readVertex(tokenizer));
                    break;
                case USEMAP:
                    break;
                case MAPLIB:
                    break;
                case MATLIB:
                    // Do we need to handle relative paths here?
                    while (tokenizer.hasMoreTokens())
                        WavefrontMtlLoader.load(tokenizer.nextToken());
                    break;
                case USEMTL:
                    setMaterial(tokenizer.nextToken());
                    return;
                case FACE:
                    polygons.add(readPolygon(tokenizer));
                    break;
                default:
                    errors.add(new WavefrontLoaderError(file, "WavefrontObjLoader: Unhandled Token: " + token + "\n" +"Line: " + line));
                    return;
            }
        }
    }

    private void loadTranform(File file) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(new FileReader(file));
        float[] newTransform = new float[16];
        int pos = 0;

        try {
            while (pos < newTransform.length && (line = in.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);

                while (pos < newTransform.length && tokenizer.hasMoreTokens())
                    newTransform[pos++] = Float.parseFloat(tokenizer.nextToken());
            }
        } catch (NumberFormatException e) {
            errors.add(new WavefrontLoaderError(file, "Malformed tranform file"));
            return;
        }

        // Check that we loaded a complete transform
        if (pos != 16) {
            errors.add(new WavefrontLoaderError(file, "Malformed tranform file"));
            return;
        }
        transform = newTransform;
    }

    private void setObject(String nextToken) {
        currentObject = nextToken;
    }

    private void setGroup(StringTokenizer tokenizer) {
        currentGroups.clear();
        while(tokenizer.hasMoreTokens())
            currentGroups.add(tokenizer.nextToken());
    }

    private void setMaterial(String nextToken) {
        currentMaterial = Material.findOrCreateByName(nextToken);
    }

    private TokenType tokenType(String token) {
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
        if (token.equals("usemap"))
            return TokenType.USEMAP;
        if (token.equals("usemtl"))
            return TokenType.USEMTL;
        if (token.equals("maplib"))
            return TokenType.MAPLIB;
        if (token.equals("mtllib"))
            return TokenType.MATLIB;
        if (token.equals("g"))
            return TokenType.GROUP;
        if (token.regionMatches(0, "#", 0, 1))
            return TokenType.COMMENT;
        if (token.equals("o"))
            return TokenType.OBJECT_NAME;
        if (token.equals("s"))
            return TokenType.SMOOTHING_GROUP;

        return TokenType.UNKNOWN;
    }

    private ObjVertex readVertex(StringTokenizer tokenizer) {
        ObjVertex vertex = new ObjVertex();
        float value;

        while (tokenizer.hasMoreTokens()) {
            value = Float.parseFloat(tokenizer.nextToken());
            vertex.add(value);
        }
        return vertex;
    }

    private Polygon readPolygon(StringTokenizer tokenizer) {
        List<Vertex> verticies = new java.util.ArrayList<Vertex>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String vertex_tokens[] = token.split("/");
            @SuppressWarnings("unused")
            ObjVertex geo, tex, norm;

            switch(vertex_tokens.length) {
                case 1:
                    /* Single number - lookup geo vertex only */
                    geo = geoVerticies.get(Integer.parseInt(vertex_tokens[0]) - 1); /* Obj files starts w/ vertex 1 */
                    verticies.add(new Vertex(geo.x, geo.y, geo.z));
                    break;
                case 2:
                    /* Two numbers - geo vertex, and texture coordinate */
                    geo = geoVerticies.get(Integer.parseInt(vertex_tokens[0]) - 1);
                    tex = textureverticies.get(Integer.parseInt(vertex_tokens[1]) - 1);
                    verticies.add(new Vertex(geo.x, geo.y, geo.z, tex.x, tex.y));
                    break;
                case 3:
                    /* geo vertex, texture coordinate and normal */
                    geo = geoVerticies.get(Integer.parseInt(vertex_tokens[0]) - 1);
                    tex = textureverticies.get(Integer.parseInt(vertex_tokens[1]) - 1);
                    norm = vertexNormals.get(Integer.parseInt(vertex_tokens[2]) - 1);
                    verticies.add(new Vertex(geo.x, geo.y, geo.z, tex.x, tex.y));
                    break;
                default:
                    throw new IllegalArgumentException("Malformed vertex token: " + token);
            }
        }
        Polygon p = new Polygon(currentMaterial, verticies);
        p.groups = currentGroups;
        p.object = currentObject;
        return p;
    }

    private Model generateModel(String name) {
        return new Model(name, polygons);
    }

    private static enum TokenType {
        UNKNOWN,
        COMMENT,
        GEOMETRIC_VERTEX,
        VERTEX_NORMAL,
        TEXTURE_COORDINATE,
        FACE,
        USEMAP,
        USEMTL,
        MAPLIB,
        MATLIB,
        GROUP, 
        OBJECT_NAME,
        SMOOTHING_GROUP
    }

    private static class ObjVertex {
        int dim;
        float x, y, z, w;

        public ObjVertex() {
            dim = 0;
            this.x = 0.0f;
            this.y = 0.0f;
            this.z = 0.0f;
            this.w = 1.0f;
        }

        public ObjVertex(float x, float y, float z, float w) {
            dim = 0;
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        public ObjVertex transform(float[] transform) {
            return new ObjVertex(
                    x * transform[0]  + y * transform[1]  + z * transform[2]  + w * transform[3], 
                    x * transform[4]  + y * transform[5]  + z * transform[6]  + w * transform[7], 
                    x * transform[8]  + y * transform[9]  + z * transform[10] + w * transform[11], 
                    x * transform[12] + y * transform[13] + z * transform[14] + w * transform[15]
            );
        }

        void add(float v) {
            switch(dim) {
                case 0:
                    x = v;
                    dim ++;
                    return;
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

    public static List<WavefrontLoaderError> getErrors() {
        return errors;
    }
}
