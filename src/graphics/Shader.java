package graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.media.opengl.GL2;
public class Shader {
    private final static String SHADER_DIR = "assets/shaders/";
    private final String fragmentFilePath;
    private final String vertexFilePath;
    protected transient int fragmentID;
    protected transient int programID;
    protected transient int vertexID;

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
            e.printStackTrace();
        }
    }

    public void setUniform1f(GL2 gl, String name, float value){
        int location = gl.glGetUniformLocation(programID, name);
        gl.glUniform1f(location, value);
    }

    public void setUniform1i(GL2 gl, String name, int value){
        int location = gl.glGetUniformLocation(programID, name);
        gl.glUniform1i(location, value);
    }
    
    public void setUniform1b(GL2 gl, String name, boolean value){
        int location = gl.glGetUniformLocation(programID, name);
        gl.glUniform1i(location, value ? 1 : 0);
    }

    private void compileShader(GL2 gl, int programObject, String filePath) throws IOException{
        int[] sourceArrayLength = new int[1];
        int[] compiledSuccessfully = new int[1];

        String[] source = readFile(filePath);
        sourceArrayLength[0] = source[0].length();
        gl.glShaderSource(programObject, 1, source, sourceArrayLength,0);  
        gl.glCompileShader(programObject);
        gl.glGetShaderiv(programObject, GL2.GL_COMPILE_STATUS, compiledSuccessfully,0);

        if(compiledSuccessfully[0] == 0){
            System.err.println("Problems with Shaders:" + filePath);
            printErrorLog(gl,programObject);
        }
    }

    private void buildProgram(GL2 gl){
        int[] success = new int[1];
        programID = gl.glCreateProgram();
        gl.glAttachShader(programID, vertexID);
        gl.glAttachShader(programID, fragmentID);

        gl.glLinkProgram(programID);
        gl.glGetProgramiv(programID, GL2.GL_LINK_STATUS, success,0);
        if(success[0] == 0)
        {
            IntBuffer intValue = IntBuffer.allocate(1);
            gl.glGetProgramiv(programID, GL2.GL_OBJECT_INFO_LOG_LENGTH_ARB, intValue);

            int lengthWithNull = intValue.get();

            if (lengthWithNull <= 1) {
                return;
            }

            java.nio.ByteBuffer infoLog = java.nio.ByteBuffer.allocate(lengthWithNull);

            intValue.flip();
            gl.glGetProgramInfoLog(programID, lengthWithNull, intValue, infoLog);

            int actualLength = intValue.get();

            byte[] infoBytes = new byte[actualLength];
            infoLog.get(infoBytes);
            System.out.println("GLSL Validation >> " + new String(infoBytes));
        }

        gl.glValidateProgram(programID);
    }

    public void enable(GL2 gl){
        gl.glUseProgram(programID);
    }

    public void init(GL2 gl) throws IOException{
        vertexID = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        fragmentID = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
        compileShader(gl,vertexID,vertexFilePath);
        compileShader(gl,fragmentID,fragmentFilePath);
        buildProgram(gl);
    }

    private void printErrorLog(GL2 gl, int programObject) {
        IntBuffer intValue = IntBuffer.allocate(1);
        gl.glGetObjectParameterivARB(programObject, GL2.GL_OBJECT_INFO_LOG_LENGTH_ARB, intValue);

        int lengthWithNull = intValue.get();

        if (lengthWithNull <= 1) {
            return;
        }

        java.nio.ByteBuffer infoLog = java.nio.ByteBuffer.allocate(lengthWithNull);

        intValue.flip();
        gl.glGetShaderInfoLog(programObject, lengthWithNull, intValue, infoLog);

        int actualLength = intValue.get();

        byte[] infoBytes = new byte[actualLength];
        infoLog.get(infoBytes);
        System.out.println("GLSL Validation >> " + new String(infoBytes));
    }
    private String[] readFile(String path) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String[] source = new String[1];
        source[0] = "";
        String line;
        while((line = reader.readLine())!=null){
            source[0] += line + "\n";
        }
        return source;
    }
}
