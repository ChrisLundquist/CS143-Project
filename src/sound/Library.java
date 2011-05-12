package sound;
import java.util.Map;


public class Library {
    private static final String SOUND_PATH = "assets/audio/";
    protected static Map<String, Buffer> sounds = new java.util.HashMap<String, Buffer>();
    protected static String[] files = {"Gun1.wav"};

    public static Buffer findOrCreateByName(String name) {
        Buffer sound = sounds.get(name);
        if(sound == null)
            sound = Library.createByName(name);
        return sound;
    }

    public static Buffer findByName(String name){
        return sounds.get(name);
    }

    public static Buffer createByName(String name){
        Buffer sound = null;
        try{
            sound = Loader.load(SOUND_PATH + name);
        }catch(com.jogamp.openal.ALException e){
            // We didn't find the file, try it without the path prefix
            sound = Loader.load(name);
        }
        sounds.put(name, sound);
        return sound;
    }

    public static void initialize(){
        for(String file : files)
            createByName(file);
    }
}
