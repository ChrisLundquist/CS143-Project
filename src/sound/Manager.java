package sound;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

public class Manager {
    private static AL al;
    private static List<Event> events;
    private static final int MAX_SOURCES = 32;
    private static List<Source> sources;

    static public void addEvent(Event event){
        events.add(event);
    }

    static public void processEvents(){
        ListIterator<Event> it = events.listIterator();
        while(it.hasNext()){
            Event event = it.next();
            Source source = getNextFreeSource();
            if(source == null) // We can't play any more sounds right now
                return; 
            source.load(event);
            source.play();
            checkForALErrorsWithMessage("Error when proccessing Event: " + event);
            it.remove();
        }
    }

    static private Source getNextFreeSource(){
        for(Source source : sources){
            if(! source.isPlaying()) // means playback has ended
                return source;
        }
        return null;
    }

    static public void checkForALErrorsWithMessage(String message){
        int code = -1;
        if ((code = al.alGetError()) != AL.AL_NO_ERROR)
            throw new RuntimeException(message + " Error " + ALErrorCodeToString(code));
    }

    static public String ALErrorCodeToString(int code){
        switch(code){
        case AL.AL_NO_ERROR:
            return "No Error";
        case AL.AL_INVALID_NAME:
            return "Invalid Name";
        case AL.AL_INVALID_ENUM:
            return "Invalid Enum";
        case AL.AL_INVALID_VALUE:
            return "Invalid Value";
        case AL.AL_INVALID_OPERATION:
            return "Invalid operation";
        case AL.AL_OUT_OF_MEMORY:
            return "Out Of Memory";
        default:
            return "Unknown";
        }

    }
    static public AL getAL(){
        return al;
    }
    
    static public void setListenerPosition(float[] listenerPos ){
            getAL().alListenerfv(AL.AL_POSITION, listenerPos, 0);
    }
    
    static public void setListenerVelocity(float[] listenerVel){
        getAL().alListenerfv(AL.AL_VELOCITY, listenerVel, 0);
    }
    
    static public void setListenerOrientation(float[] listenerOri){
        getAL().alListenerfv(AL.AL_ORIENTATION, listenerOri, 0);
    }
    
    static public void initialize(){
        al = ALFactory.getAL();
        ALut.alutInit();
        checkForALErrorsWithMessage("Failed to initialize OpenAl");
        sources = new LinkedList<Source>();
        events = new LinkedList<Event>();
        Library.initialize();

        for(int i = 0; i < MAX_SOURCES; i++)
            try{
                sources.add(new Source());
            } catch( RuntimeException e) {
                System.err.println(e.toString());
                System.err.println("Error when generating OpenAL Sources. Successfully generated " + i + " sources of " + MAX_SOURCES);
                break;
            }
    }
}
