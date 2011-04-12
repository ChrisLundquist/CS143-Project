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
    protected Quaternion pitch, yaw, roll;
    protected float pitchDegrees, yawDegrees, rollDegrees;
    protected float velocityPitchDegrees, velocityYawDegrees, velocityRollDegrees;
    
    protected int age; // Actor age in frames
    protected int parentId;


    public Actor(){
        id = generateId();
        pitch = new Quaternion();
        yaw = new Quaternion();
        roll = new Quaternion();
        position = new Vector3();
        velocity = new Vector3();
        modelId = Model.getModelIdFor(this);
        velocityPitchDegrees = 1.0f;
        velocityYawDegrees = 1.0f;
    }

    public void changeYaw(float degrees) {
        if(Math.abs(degrees) < Math.abs(YAW_RATE))
        {
            // Our Heading is less than the max heading rate that we 
            // defined so lets increment it but first we must check
            // to see if we are inverted so that our heading will not
            // become inverted.
            if(pitchDegrees > 90 && pitchDegrees < 270 || (pitchDegrees < -90 && pitchDegrees > -270))
            {
                yawDegrees -= degrees;
            }
            else
            {
                yawDegrees += degrees;
            }
        }
        else
        {
            // Our heading is greater than the max heading rate that
            // we defined so we can only increment our heading by the 
            // maximum allowed value.
            if(degrees < 0)
            {
                // Check to see if we are upside down.
                if((pitchDegrees > 90 && pitchDegrees < 270) || (pitchDegrees < -90 && pitchDegrees > -270))
                {
                    // Ok we would normally decrement here but since we are upside
                    // down then we need to increment our heading
                    yawDegrees += YAW_RATE;
                }
                else
                {
                    // We are not upside down so decrement as usual
                    yawDegrees -= YAW_RATE;
                }
            }
            else
            {
                // Check to see if we are upside down.
                if(pitchDegrees > 90 && pitchDegrees < 270 || (pitchDegrees < -90 && pitchDegrees > -270))
                {
                    // Ok we would normally increment here but since we are upside
                    // down then we need to decrement our heading.
                    yawDegrees -= YAW_RATE;
                }
                else
                {
                    // We are not upside down so increment as usual.
                    yawDegrees += YAW_RATE;
                }
            }
        }

        // We don't want our heading to run away from us either. Although it
        // really doesn't matter I prefer to have my heading degrees
        // within the range of -360.0f to 360.0f
        if(yawDegrees > 360.0f)
        {
            yawDegrees -= 360.0f;
        }
        else if(yawDegrees < -360.0f)
        {
            yawDegrees += 360.0f;
        }
        yaw = new Quaternion(0.0f,1.0f,0.0f,yawDegrees);
    }

    public void changePitch(float degrees)
    {
        if(Math.abs(degrees) < Math.abs(PITCH_RATE))
        {
            // Our pitch is less than the max pitch rate that we 
            // defined so lets increment it.
            pitchDegrees += degrees;
        }
        else
        {
            // Our pitch is greater than the max pitch rate that
            // we defined so we can only increment our pitch by the 
            // maximum allowed value.
            if(degrees < 0)
            {
                // We are pitching down so decrement
                pitchDegrees -= PITCH_RATE;
            }
            else
            {
                // We are pitching up so increment
                pitchDegrees += PITCH_RATE;
            }
        }

        // We don't want our pitch to run away from us. Although it
        // really doesn't matter I prefer to have my pitch degrees
        // within the range of -360.0f to 360.0f
        if(pitchDegrees > 360.0f)
        {
            pitchDegrees -= 360.0f;
        }
        else if(pitchDegrees < -360.0f)
        {
            pitchDegrees += 360.0f;
        }
        pitch = new Quaternion(1.0f,0.0f,0.0f,pitchDegrees);
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
        float[] matrix = new float[16];
        Quaternion q = new Quaternion();
        Vector3 direction = new Vector3();
        // Create a matrix from the pitch Quaternion and get the j vector 
        // for our direction.
        matrix = getPitch().toGlMatrix();
        direction.y = matrix[9];

        // Combine the heading and pitch rotations and make a matrix to get
        // the i and j vectors for our direction.
        q = getYaw().times(getPitch());
        matrix = q.toGlMatrix();
        direction.x = matrix[8];
        direction.z = matrix[10];
        return direction;
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

    public Quaternion getYaw() {
        yaw = new Quaternion(0.0f, 1.0f, 0.0f, yawDegrees);
        return yaw;
    }

    public Quaternion getPitch() {
        pitch = new Quaternion(1.0f, 0.0f, 0.0f, pitchDegrees);
        return pitch;
    }

    public Quaternion getRoll() {
        roll = new Quaternion(0.0f, 0.0f, 1.0f, rollDegrees);
        return roll;
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
        return yaw.times(pitch).times(roll);
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



