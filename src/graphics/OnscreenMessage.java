package graphics;
import math.Vector3f;

public class OnscreenMessage {
    private static final int MAX_AGE = 90;
    private static final int FADE_AGE = 30;
    static private java.util.Vector<OnscreenMessage> messages;

    static public void init() {
        messages = new java.util.Vector<OnscreenMessage>();
    }

    static public void updateMessages() {
        for(int i = 0; i < messages.size(); i ++) {
            OnscreenMessage msg = messages.get(i);

            if (msg.age > MAX_AGE)
                messages.remove(msg);
            msg.update();
        }
    }

    static public java.util.Vector<OnscreenMessage> getMessages() {
        return messages;
    }

    static public void add(OnscreenMessage msg) {
        if (msg == null)
            return;
        messages.add(msg);
    }


    private String text;
    private Vector3f position;
    private Vector3f velocity;
    private int age;

    public OnscreenMessage(String msg) {
        this(msg, new Vector3f());
    }

    public OnscreenMessage(String msg, Vector3f position) {
        this(msg, position, new Vector3f());
    }

    public OnscreenMessage(String msg, actor.Actor actor) {
        this(msg, actor.getPosition(), actor.getVelocity());
    }

    public OnscreenMessage(String msg, Vector3f position, Vector3f velocity) {
        text = msg;
        this.position = position;
        this.velocity = velocity;
        age = 0;
    }

    public String getText() {
        return text;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public float getAlpha() {
        if (age <= FADE_AGE)
            return 1.0f;
        else
            return 1 / (age - FADE_AGE);	
    }

    private void update() {
        position.plusEquals(velocity);
        age ++;
    }
}
