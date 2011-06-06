package sound;

import math.Vector3f;

/**
 * 
 * @author Durandal
 *
 */
public class Event {
    public Vector3f position, velocity;
    public final Buffer sound;
    public float gain, pitch;
    
    public Event(Buffer sound){
        this.sound = sound;
        gain = 1.0f;
        pitch = 1.0f;
    }
    
    public Event(math.Vector3f pos, math.Vector3f vel, Buffer sound){
        this(sound);
        position = pos;
        velocity = vel;
    }
    
    public Buffer getBuffer() {
        return sound;
    }
    public Vector3f getVelocity() {
        return velocity;
    }
    public Vector3f getPosition() {
        return position;
    }
}
