package graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.media.opengl.GL2;

public class Shader {
    private final static String SHADER_DIR = "assets/shaders/";
    private final String vertexFilePath;
    private final String fragmentFilePath;

    protected transient int vertexID;
    protected transient int fragmentID;
    protected transient int shaderID;

    public Shader(String vertexFilePath, String fragmentFilePath){
        this.vertexFilePath = SHADER_DIR + vertexFilePath;
        this.fragmentFilePath = SHADER_DIR + fragmentFilePath;
    }

    public Shader(String vertexFilePath, String fragmentFilePath,GL2 gl){
        this.vertexFilePath = vertexFilePath;
        this.fragmentFilePath = fragmentFilePath;
        try {
            init(gl);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void init(GL2 gl) throws IOException{
        vertexID = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        fragmentID = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

        // Ugly parameters that we have to pass to make it work
        gl.glShaderSource(vertexID, 1, readFile(vertexFilePath), (int[])null,0);  
        gl.glCompileShader(vertexID);
        gl.glShaderSource(fragmentID, 1, readFile(fragmentFilePath), (int[])null,0);  
        gl.glCompileShader(fragmentID);

        this.shaderID = gl.glCreateProgram();
        gl.glAttachShader(shaderID, vertexID);
        gl.glAttachShader(shaderID, vertexID);

        gl.glLinkProgram(shaderID);
        gl.glValidateProgram(shaderID);
    }

    public void enable(GL2 gl){
        gl.glUseProgram(this.shaderID);
    }

    private String[] readFile(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(this.vertexFilePath));

        String[] source = new String[1];
        String line;
        while((line = reader.readLine())!=null){
            source[0] += line + "\n";
        }
        return source;
    }

    public static void main(String[] args){
        @SuppressWarnings("unused")
        Shader test = new Shader("minimal.vert", "minimal.frag");
    }
}
