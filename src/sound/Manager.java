package sound;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

/**
 * 
 * @author Durandal
 * NOTE: You must use Mono (single channel) buffers if you want them to be spatialized by OpenAL
 */
public class Manager {
    private static AL al;
    private static List<Event> events;
    private static final int MAX_SOURCES = 32;
    private static List<Source> sources;
    private static actor.Movable listener;

    static public void addEvent(Event event){
        events.add(event);
    }

    static public void processEvents(){
        updateListener();
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
    
    static private void setListenerPosition(math.Vector3f listenerPos ){
            getAL().alListenerfv(AL.AL_POSITION, listenerPos.toFloatArray(), 0);
    }
    
    static private void setListenerVelocity(math.Vector3f listenerVel){
        getAL().alListenerfv(AL.AL_VELOCITY, listenerVel.toFloatArray(), 0);
    }
    
    static private void setListenerOrientation(math.Vector3f listenerOri, math.Vector3f listenerUp){
        float[] orientation = new float[6];
        orientation[0] = listenerOri.x;
        orientation[1] = listenerOri.y;
        orientation[2] = listenerOri.z;
        orientation[3] = listenerUp.x;
        orientation[4] = listenerUp.y;
        orientation[5] = listenerUp.z;

        getAL().alListenerfv(AL.AL_ORIENTATION, orientation, 0);
    }
    
    static public void updateListener(){
        sound.Manager.setListenerOrientation(listener.getRotation().rollAxis(), listener.getRotation().yawAxis());
        sound.Manager.setListenerPosition(listener.getPosition());
        sound.Manager.setListenerVelocity(listener.getVelocity());
    }

    static public void initialize(actor.Movable newListener){
        listener = newListener;
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
