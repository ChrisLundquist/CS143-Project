package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Debug {
    public static void debugOff(){
        System.setOut(new java.io.PrintStream(
                new java.io.OutputStream() {
                   public void write(int b){}
                }
             ));
        System.setErr(new java.io.PrintStream(
                new java.io.OutputStream() {
                   public void write(int b){}
                }
             ));
    }
    
    public static void debugConsole(){
        System.setOut(System.out);
        System.setErr(System.err);
    }
    
    public static void debugFile(File f) throws FileNotFoundException{
        PrintStream sim = new PrintStream(f);
        
        System.setOut(sim);
        System.setErr(sim);
    }
}
