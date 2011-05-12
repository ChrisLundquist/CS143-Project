package sound;

import java.nio.IntBuffer;

import com.jogamp.openal.AL;

public class Source {
    transient int sourceId[];
    float pitch, gain;
    // Not sure if we will need this
    //Queue<Buffer> queuedSounds;

    public Source(){
        //queuedSounds = new LinkedList<Buffer>();
        sourceId = new int[1];
        pitch = 1.0f;
        gain = 1.0f;

        AL al = Manager.getAL();
        // Bind buffer with a source.
        al.alGenSources(1, IntBuffer.wrap(sourceId));

        Manager.checkForALErrorsWithMessage("failed to generate new source");

        al.alSourcef(sourceId[0], AL.AL_PITCH, pitch);
        al.alSourcef(sourceId[0], AL.AL_GAIN, gain);
    }

    public void play(){
        Manager.getAL().alSourcePlay(getId());
    }

    public void pause(){
        Manager.getAL().alSourcePause(getId());
    }

    public void stop(){
        Manager.getAL().alSourceStop(getId());
    }
    public int getState(){
        int[] state = new int[1];
        Manager.getAL().alGetSourcei(getId(), AL.AL_SOURCE_STATE, state,0);
        return state[0];
    }

    public boolean isPaused(){
        return getState() == AL.AL_PAUSED;
    }

    public boolean isPlaying(){
        return getState() == AL.AL_PLAYING;
    }

    public boolean isStopped(){
        return getState() == AL.AL_STOPPED;
    }
    
    public void load(Event event){
        AL al = Manager.getAL();
        // Bind the buffer to this source
        al.alSourcei(getId(), AL.AL_BUFFER, event.getBuffer().getId());
        al.alSourcefv(getId(), AL.AL_POSITION, event.getPosition(), 0);
        al.alSourcefv(getId(), AL.AL_VELOCITY, event.getVelocity(), 0);
        al.alSourcei(getId(), AL.AL_LOOPING, event.getBuffer().loop[0]);

        Manager.checkForALErrorsWithMessage("Error configuring source to play buffer");

    }

    public int getId(){
        return sourceId[0];
    }
}
