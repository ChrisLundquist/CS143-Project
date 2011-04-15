package graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.media.opengl.GL2;

public class Shader {
    private final static String SHADER_DIR = "assets/shaders/";
    private final String vertexFilePath;
    private final String fragmentFilePath;
    
    int vertexID;
    int fragmentID;
    int shaderID;
        
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
        
        this.vertexID = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        this.fragmentID = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
        
        gl.glShaderSource(this.vertexID, 1, readFile(this.vertexFilePath), (int[])null,0);  
        gl.glCompileShader(vertexID);
        gl.glShaderSource(this.fragmentID, 1, readFile(this.fragmentFilePath), (int[])null,0);  
        gl.glCompileShader(this.fragmentID);
        
        this.shaderID = gl.glCreateProgram();
        gl.glAttachShader(this.shaderID, this.vertexID);
        gl.glAttachShader(this.shaderID, this.vertexID);
        
        gl.glLinkProgram(this.shaderID);
        gl.glValidateProgram(this.shaderID);
    }
    
    public void enable(GL2 gl){
        gl.glUseProgram(this.shaderID);
    }
    
    private String[] readFile(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(this.vertexFilePath));
        
        String[] toReturn = new String[1];
        String line;
        while((line = reader.readLine())!=null){
            toReturn[0] += line + "\n";
        }
        return toReturn;
    }
    
    public static void main(String[] args){
        @SuppressWarnings("unused")
        Shader test = new Shader("minimal.vert", "minimal.frag");
    }
}
