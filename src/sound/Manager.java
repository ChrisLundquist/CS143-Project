package sound;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.jogamp.openal.AL;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

/**
 * 
 * @author Durandal NOTE: You must use Mono (single channel) buffers if you want
 *         them to be spatialized by OpenAL
 */
public class Manager {
    private static AL al;
    private static Queue<Event> events;
    private static final int MAX_SOURCES = 64;
    private static List<Source> sources;
    private static actor.interfaces.Movable listener;
    public static boolean enabled = false;

    static public void addEvent(Event event) {
        if (enabled) {
            synchronized (events) {
                events.add(event);
            }
        }
    }

    static public void processEvents() {
        updateListener();
          
        synchronized (events) {
            Event event;
            while ((event = events.poll()) != null) {
                Source source = getNextFreeSource();
                if (source == null){ // We can't play any more sounds right now
                    events.clear(); // Clear the queue so we avoid laggy sound we played the closest 64 anyhow
                    
                    return;
                }
                source.load(event);
                source.play();
                checkForALErrorsWithMessage("Error when proccessing Event: "
                        + event);
            }
        }
    }

    static private Source getNextFreeSource() {
        for (Source source : sources) {
            if (!source.isPlaying()) // means playback has ended
                return source;
        }
        return null;
    }

    static public void checkForALErrorsWithMessage(String message) {
        int code = -1;
        if ((code = al.alGetError()) != AL.AL_NO_ERROR)
            throw new RuntimeException(message + " Error "
                    + ALErrorCodeToString(code));
    }

    static public String ALErrorCodeToString(int code) {
        switch (code) {
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

    static public AL getAL() {
        return al;
    }

    static private void setListenerPosition(math.Vector3f listenerPos) {
        getAL().alListenerfv(AL.AL_POSITION, listenerPos.toFloatArray(), 0);
    }

    static private void setListenerVelocity(math.Vector3f listenerVel) {
        getAL().alListenerfv(AL.AL_VELOCITY, listenerVel.toFloatArray(), 0);
    }

    static private void setListenerOrientation(math.Vector3f listenerOri,
            math.Vector3f listenerUp) {
        float[] orientation = new float[6];
        orientation[0] = listenerOri.x;
        orientation[1] = listenerOri.y;
        orientation[2] = listenerOri.z;
        orientation[3] = listenerUp.x;
        orientation[4] = listenerUp.y;
        orientation[5] = listenerUp.z;

        getAL().alListenerfv(AL.AL_ORIENTATION, orientation, 0);
    }

    static public void updateListener() {
        sound.Manager.setListenerOrientation(listener.getRotation().rollAxis(),
                listener.getRotation().yawAxis());
        sound.Manager.setListenerPosition(listener.getPosition());
        sound.Manager.setListenerVelocity(listener.getVelocity());
    }

    static public void initialize(actor.interfaces.Movable newListener) {
        if (enabled) {
            listener = newListener;
            al = ALFactory.getAL();
            ALut.alutInit();
            checkForALErrorsWithMessage("Failed to initialize OpenAl");
            sources = new LinkedList<Source>();
            /*
             * Queue our sound events based on their distance from the camera
             */
            events = new PriorityQueue<Event>(16, new Comparator<Event>() {
                @Override
                public int compare(Event event1, Event event2) {
                    float d1 = event1.getPosition().minus(
                            listener.getPosition()).magnitude2();
                    float d2 = event2.getPosition().minus(
                            listener.getPosition()).magnitude2();

                    if (d1 > d2)
                        return -1;
                    else if (d2 > d1)
                        return 1;
                    else
                        return 0;
                }
            });
            Library.initialize();

            for (int i = 0; i < MAX_SOURCES; i++)
                try {
                    sources.add(new Source());
                } catch (RuntimeException e) {
                    System.err.println(e.toString());
                    System.err
                            .println("Error when generating OpenAL Sources. Successfully generated "
                                    + i + " sources of " + MAX_SOURCES);
                    break;
                }
        }
    }
}
