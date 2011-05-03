package settings;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Settings {
    public static class Profile{
        public static class Keys{
            public static int forward = -1;
            public static int backward = -1;

            public static int pitchUp = -1;
            public static int pitchDown = -1;

            public static int rollLeft = -1;
            public static int rollRight = -1;

            public static int yawLeft = -1;
            public static int yawRight = -1;

            public static int shoot = -1;
        }
    }

    public static void debug(){
        System.out.println("forward: "+Profile.Keys.forward);
        System.out.println("backward: "+Profile.Keys.backward);
        System.out.println("pitchUp: "+Profile.Keys.pitchUp);
        System.out.println("pitchDown: "+Profile.Keys.pitchDown);
        System.out.println("rollLeft: "+Profile.Keys.rollLeft);
        System.out.println("rollRight: "+Profile.Keys.rollRight);
        System.out.println("yawLeft: "+Profile.Keys.yawLeft);
        System.out.println("yawRight: "+Profile.Keys.yawRight);
        System.out.println("shoot: "+Profile.Keys.shoot);

    }

    public static void init() throws IOException{

        File settingsFile = new File("config/settings.ini");
        File configFile = null;

        String[] settingsLines = stringFromFile(settingsFile).split("\n");
        for(String line:settingsLines){
            String[] rmComment = line.split(";");
            String[] part = rmComment[0].split("=");

            if(part[0].equalsIgnoreCase("conf")){
                configFile = new File("config/"+part[1]);
            }
        }

        String[] configLines = stringFromFile(configFile).split("\n");
        for(String line: configLines){
            String[] rmComment = line.split(";");
            String[] part = rmComment[0].split("=");

            if(part[0].equalsIgnoreCase("forward")){
                Profile.Keys.forward = stringToKey(part[1]);
            }
            if(part[0].equalsIgnoreCase("backward")){
                Profile.Keys.backward = stringToKey(part[1]);
            }
            if(part[0].equalsIgnoreCase("pitch_up")){
                Profile.Keys.pitchUp = stringToKey(part[1]);
            }
            if(part[0].equalsIgnoreCase("pitch_down")){
                Profile.Keys.pitchDown = stringToKey(part[1]);
            }
            if(part[0].equalsIgnoreCase("roll_left")){
                Profile.Keys.rollLeft = stringToKey(part[1]);
            }
            if(part[0].equalsIgnoreCase("roll_right")){
                Profile.Keys.rollRight = stringToKey(part[1]);
            }
            if(part[0].equalsIgnoreCase("yaw_left")){
                Profile.Keys.yawLeft = stringToKey(part[1]);
            }
            if(part[0].equalsIgnoreCase("yaw_right")){
                Profile.Keys.yawRight = stringToKey(part[1]);
            }
            if(part[0].equalsIgnoreCase("shoot")){
                Profile.Keys.shoot = stringToKey(part[1]);
            }   
        }
    }

    public static int stringToKey(String input){
        try{
            return Integer.parseInt(input);
        }
        catch(Exception e){
            return -1;
        }
    }

    public static String stringFromFile(File f) throws IOException{
        RandomAccessFile ram = new RandomAccessFile(f, "r");
        byte[] byteBuffer = new byte[(int)f.length()];
        ram.readFully(byteBuffer);
        ram.close();
        return new String(byteBuffer);
    }

    public static void main(String[] args) throws IOException{
        init();
        Settings.debug();
    }
}
