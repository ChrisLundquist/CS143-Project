package actor;
import math.*;
import graphics.Model;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;
import javax.media.opengl.GL2;

public abstract class Actor implements Serializable {
    private static final float YAW_RATE = 1.0f;
    private static final float PITCH_RATE = 1.0f;
    private static final long serialVersionUID = 744085604446096658L;
    /**
     *  All the actors currently in play
     *  We use the fully qualified named space for the Vector container so 
     *  it doesn't clash with our name space. Vectors work like ArrayLists,
     *  but are synchronized.
     */
    public static Vector<Actor> actors = new Vector<Actor>();
    /**
     * Common random number generator object
     */
    protected static Random gen = new Random();
    private static int lastId = 0;
    
    public static void removeActorId(int idToRemove) {
        for (Actor a: actors)
            if (a.id == idToRemove)
                actors.remove(a);
    }
    
    public static void updateActors() {
        // Update each actor
        for(int i = 0; i < actors.size(); i++) {
            // We get the actor only once in case we the actor is removed
            // during the update phase. E.G. Bullets FramesToLive reaches 0
            Actor a = actors.get(i);

            // Track down actors without ids.
            if (a.id == 0)
                System.err.println("DEBUG: " + a + " actor without ID set");

            a.update();
        }
    }
    
    
    protected int id; // unique ID for each Actor 
    protected int modelId;
    protected transient Model model; // CL - Used to store the model reference after we look it up once
    protected Vector3 position, velocity;
    protected float scale;
    
    // Rotation
    protected Quaternion rotation;
    protected float pitchDegrees, yawDegrees, rollDegrees;
    protected float velocityPitchDegrees, velocityYawDegrees, velocityRollDegrees;
    
    protected int age; // Actor age in frames
    protected int parentId;


    public Actor(){
        id = generateId();
        rotation = new Quaternion();
        position = new Vector3();
        velocity = new Vector3();
        modelId = Model.getModelIdFor(this);
        velocityPitchDegrees = 1.0f;
        velocityYawDegrees = 1.0f;
    }

    public void changeYaw(float degrees) {
        rotation = rotation.times(new Quaternion(rotation.yawAxis(), degrees));
    }

    public void changePitch(float degrees)   {
        rotation = rotation.times(new Quaternion(rotation.pitchAxis(), degrees));
    }

    /**
     * CL - We need to synchronize removing actors so we don't have threads
     *      stepping on eachother's toes.
     *      
     *      NOTE: thread concurrency is an advanced topic. This is a base
     *      implementation to handle the problem.
     */
    protected void delete() {
        // NOTE: This needs to be thread safe.
        Actor.actors.remove(this);
    }

    protected int generateId() {
        return (lastId += gen.nextInt(1000) + 1); // Pseudo random increments
    }

    public Vector3 getDirection(){
        return rotation.rollAxis();
    }

    public float getHeadingDegrees() {
        return yawDegrees;
    }

    public float getMass() {
        // This does not account for different actors having different densities
        // but the mass should scale with the cube of the linear scale (the volume)
        // But the area is more fun!
        return scale * scale;
    }

    public Model getModel() {
        // CL - If our reference is null, go look it up
        if(model == null)
            model = Model.findById(modelId);

        return model;
    }


    public float getPitchDegrees() {
        return pitchDegrees;
    }

    /**
     * 
     * @return the actors current position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * 
     * @return the actors size (for texture scaling and collision detection)
     */
    public float getSize(){
        return scale;
    }

    /**
     * 
     * @return the actors current velocity
     */
    public Vector3 getVelocity(){
        return velocity;
    }

    /**
     * Call back upon collision detection for object to handle collision
     * It could...
     *   Bounce off
     *   Explode into many smaller objects
     *   Just explode
     * @param other the object this actor collided with
     */
    abstract public void handleCollision(Actor other);

    public void render(GL2 gl) {
        // Translate the actor to it's position
        gl.glTranslatef(position.x, position.y, position.z);
        // Rotate the actor
        gl.glMultMatrixf(getRotation().toGlMatrix(), 0);
        // Scale the Actor

        // CL - Render our model.
        getModel().render(gl);
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public void setHeadingDegrees(float headingDegrees) {
        this.yawDegrees = headingDegrees;
    }


    public void setPitchDegrees(float pictchDegrees) {
        this.pitchDegrees = pictchDegrees;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    // Lets you reference chain
    public Actor setSize(float newSize){
        scale = newSize;
        return this;
    }
    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    // CL - updates the state of the actor for the next frame
    public void update(){
        position.plusEquals(velocity);
        pitchDegrees += velocityPitchDegrees;
        yawDegrees += velocityYawDegrees;
        rollDegrees += velocityRollDegrees;
    }
}



