package settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import sun.misc.BASE64Decoder;

public class Util {
    static final String defaultSettings = "Y29uZj1jb25maWd1cmF0aW9uMS5jb25mCmRlYnVnPW9uCnBhcnR"+
    "pY2xlcz1vbgpzaGFkZXI9dGV4dHVyZQpzb3VuZD1vbgpjb250cm9sbGVyPW9mZgo=";
    
    static final String defaultConfig= "Zm9yd2FyZD13CmJhY2t3YXJkPXMKcGl0Y2h" +
    "fdXA9aQpwaXRjaF9kb3duPWsKcm9sbF9sZWZ0PWoKcm9sbF9yaWdodD1s" +
    "Cnlhd19sZWZ0PWEKeWF3X3JpZ2h0PWQKc2hvb3Q9c3Bh" +
    "Y2UKbmV4dF93ZWFwb249Y3RybAplbmVyZ3lfZ3VuPTEKZW5lcmd5X3Noa" +
    "WVsZD0yCmVuZXJneV9zcGVlZD0zCmVuZXJneV9tb2RpZmllcj1zaGlmdA==";
    
    /**
     * Writes a string to a file
     * @param file
     * @param input
     * @throws IOException
     */
    public static void writeToFile(File file, String input) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(input);
        output.close();
        System.out.println("String has been written to " + file.getCanonicalFile());  
    }
    
    public static String stringFromFile(File f) throws IOException{
        RandomAccessFile ram = new RandomAccessFile(f, "r");
        byte[] byteBuffer = new byte[(int)f.length()];
        ram.readFully(byteBuffer);
        ram.close();
        return new String(byteBuffer);
    }
    
    /**
     * Decodes a Base64 String
     * @param base64String
     * @return
     * @throws IOException
     */
    public static String Base64ToString(String base64String) throws IOException {        
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(base64String);
        String s = new String(decodedBytes);
        return s;
    }
    
    public static void checkSettingFile(File settingsFile) throws IOException{
        if(!settingsFile.exists()) {
            settingsFile.createNewFile();
            writeToFile(settingsFile, Util.Base64ToString(defaultSettings));
        }
    }
    public static void checkConfFile(File configFile) throws IOException{
        if(!configFile.exists()){
            configFile.createNewFile();
            Util.writeToFile(configFile, Util.Base64ToString(defaultConfig));
        }
    }
}
