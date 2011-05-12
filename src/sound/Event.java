package sound;

/**
 * 
 * @author Durandal
 *
 */
public class Event {
    float[] position, velocity;
    Buffer sound;
    public Event(float[] position, float[] velocity, Buffer sound){
        this.position = position;
        this.velocity = velocity;
        this.sound = sound;
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
