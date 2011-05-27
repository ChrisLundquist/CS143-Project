package sound;

/**
 * 
 * @author Durandal
 *
 */
public class Event {
    public float[] position, velocity;
    public final Buffer sound;
    public float gain, pitch;
    
    public Event(Buffer sound){
        this.sound = sound;
        gain = 1.0f;
        pitch = 1.0f;
    }
    
    public Event(float[] position, float[] velocity, Buffer sound){
        this(sound);
        this.position = position;
        this.velocity = velocity;

    }
    
    public Event(math.Vector3f pos, math.Vector3f vel, Buffer sound){
        this(sound);
        position = pos.toFloatArray();
        velocity = vel.toFloatArray();
    }
    
    public Buffer getBuffer() {
        return sound;
    }
    public float[] getVelocity() {
        return velocity;
    }
    public float[] getPosition() {
        return position;
    }

}
