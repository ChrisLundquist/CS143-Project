package actor;
import math.*;
import graphics.Model;
import java.io.Serializable;
import java.util.Random;

import javax.media.opengl.GL2;

public abstract class Actor implements Serializable {
    private static final long serialVersionUID = 744085604446096658L;
    static protected final float MASS_SCALING = 2.0f;
    private static final float HEADING_RATE = 1.0f;
    private static final float PITCH_RATE = 1.0f;

    /**
     *  All the actors currently in play
     *  We use the fully qualified named space for the Vector container so 
     *  it doesn't clash with our name space. Vectors work like ArrayLists,
     *  but are synchronized.
     */
    static public java.util.Vector<Actor> actors = new java.util.Vector<Actor>();

    // Used by generateId();
    public static int lastId;


    /**
     * Common random number generator object
     */
    static protected Random gen = new Random();
    protected float size; // the radius of the object
    protected int id; // unique ID for each Actor 
    protected int modelId;
    protected transient Model model; // CL - Used to store the model reference after we look it up once
    protected int parentId;
    protected int age; // Actor age in frames

    protected float headingDegrees;
    protected Quaternion pitch;
    protected float pitchDegrees;
    protected Vector3 velocity;
    protected Vector3 position;
    protected Quaternion heading;
    
    public Actor(){
        id = generateId();
        pitch = new Quaternion();
        heading = new Quaternion();
        position = new Vector3();
        velocity = new Vector3();
        modelId = Model.getModelIdFor(this);
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

    /**
     * Call back upon collision detection for object to handle collision
     * It could...
     *   Bounce off
     *   Explode into many smaller objects
     *   Just explode
     * @param other the object this actor collided with
     */
    abstract public void handleCollision(Actor other);

    protected int generateId() {
        return (lastId =+ gen.nextInt(1000) + 1); // Pseudo random increments
    }

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

    public static void collisionDetection() {
        /*
         * Collision detection
         * For each actor, check for collisions with the remaining actors
         * For collision purposes we are modeling each actor as a circle
         * This algorithm is 1/2 n^2 compares, but it should be sufficient for our purposes
         */
        for(int i = 0; i < actors.size(); i++) {
            Actor a = actors.get(i);

            for (int j = i + 1; j < actors.size(); j++) {
                Actor b = actors.get(j);

                if (a.checkCollision(b)) {
                    //System.err.println("DEBUG: detected collision between " + a + " and " + b);
                    a.handleCollision(b);
                    b.handleCollision(a);
                }
            }
        }
    }

    /**
     * Check for a collision between this actor and another in the next frame
     * @param other - another actor
     * @return truth if a collision will occur in the next frame
     */
    private boolean checkCollision(Actor other) {
        /*
         * To check for a collision in the next frame we use
         * parametric equations for the position of each object
         * and find where there paths will intersect, and
         * check if it's in the next frame.
         * 
         * We model each objects motion with the vector equation
         *      P + t * V
         * 
         * So for two objects we have
         *      P(1) + t * V(1)
         * and
         *      P(2) + t * V(2)
         * 
         * To find the intersection we set the two equations
         * equal to each other
         *      P(1) + t * V(1) = P(2) + t * V(2)
         * 
         * Then solve for t
         *      t * V(1) - t * V(2) = P(2) - P(1)
         * 
         *      t * (V(1) - V(2)) = P(2) - P(1)
         * 
         *          P(2) - P(1)
         *      t = -----------
         *          V(1) - V(2)
         *  
         * Since we simply increment the position by the velocity
         * each frame, we just just need to check if there is
         * an intersection in t = 0..1.
         */

        float deltaVX = other.velocity.x - velocity.x;
        float deltaVY = other.velocity.y - velocity.y;
        float deltaPX = position.x - other.position.x;
        float deltaPY = position.y - other.position.y;

        /* Our sizes are the diameter of each object and we want the distance between their centers */                          
        //float minDistance = getRadius() + other.getRadius();

        /*
         * Since we are looking for an intersection in two dimensions
         * we check for a collision in each dimension and return
         * true only if both are true.
         */
        //boolean collideX = isCollision1D(deltaPX, deltaVX, minDistance);
        //boolean collideY = isCollision1D(deltaPY, deltaVY, minDistance);

        //return collideX && collideY;
        //TODO
        return false;
    }

    /**
     * Check for a collision on one dimension
     * @param deltaP - delta position
     * @param deltaV - delta velocity
     * @param minDist - minimum distance between particles for a collision to occur, usually the sum of their radii
     * @return truth if a collision will occur in the next frame
     */
    private static boolean isCollision1D(float deltaP, float deltaV, float minDist) {
        /* Since we want to detect collision of objects, rather than just
         * point like particles, we check for collisions our minimum
         * collision distance each side of the point of collision if our
         * our objects where both just points.
         * 
         * The code for point collisions is:
         *      float t = deltaP / deltaV;
         *      return t >= 0 && t < 1;
         * 
         * Note: this doesn't protect against dividing by zero
         */

        if (deltaV != 0) { // Don't divide by zero
            // Calculate the extremes of our collision range
            float a = (deltaP - minDist) / deltaV;
            float b = (deltaP + minDist) / deltaV;

            /*
             * There are six cases, excluding the cases
             * where a and b are swapped by a negative
             * deltaV:
             * 
             * a--b    a--b    a--b
             *     a--b    a--b
             *      a--------b
             * <-----0------1-----> 
             * We only check for the two non collision
             * cases, else assume the collision case 
             * takes place. 
             */     
            if(a > 1 && b > 1)
                return false;    
            if(a <= 0 && b <= 0)
                return false;
        } else {
            /*
             * The zero velocity case is actually much simpler
             */
            if (deltaP >= minDist)
                return false;
            if (-deltaP >= minDist)
                return false;
        }
        return true;
    }
    /* End Collision Detection */


    // CL - updates the state of the actor for the next frame
    public void update(){
        position.plusEquals(velocity);
    }

    public void render(GL2 gl) {
        // CL - Render our model.
        getModel().render(gl);
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
     * @return the actors current velocity
     */
    public Vector3 getVelocity(){
        return velocity;
    }

    /**
     * 
     * @return the actors size (for texture scaling and collision detection)
     */
    public float getSize(){
        return size;
    }

    // Lets you reference chain
    public Actor setSize(float newSize){
        size = newSize;
        return this;
    }

    public float getMass() {
        // This does not account for different actors having different densities
        // but the mass should scale with the cube of the linear scale (the volume)
        // But the area is more fun!
        return size * size;
    }

    public float getHeadingDegrees() {
        return headingDegrees;
    }

    public void setHeadingDegrees(float headingDegrees) {
        this.headingDegrees = headingDegrees;
    }

    public float getPitchDegrees() {
        return pitchDegrees;
    }

    public void setPitchDegrees(float pictchDegrees) {
        this.pitchDegrees = pictchDegrees;
    }

    public Quaternion getHeading() {
        return heading;
    }

    public void setHeading(Quaternion heading) {
        this.heading = heading;
    }

    public Quaternion getPitch() {
        return pitch;
    }

    public void setPitch(Quaternion pitch) {
        this.pitch = pitch;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
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
        q = getHeading().times(getPitch());
        matrix = q.toGlMatrix();
        direction.x = matrix[8];
        direction.z = matrix[10];
        return direction;
    }

    public Model getModel() {
        // CL - If our reference is null, go look it up
        if(model == null)
            model = Model.findById(modelId);
    
        return model;
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
    
    public void changeHeading(float degrees)
    {
        if(Math.abs(degrees) < Math.abs(HEADING_RATE))
        {
            // Our Heading is less than the max heading rate that we 
            // defined so lets increment it but first we must check
            // to see if we are inverted so that our heading will not
            // become inverted.
            if(pitchDegrees > 90 && pitchDegrees < 270 || (pitchDegrees < -90 && pitchDegrees > -270))
            {
                headingDegrees -= degrees;
            }
            else
            {
                headingDegrees += degrees;
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
                    headingDegrees += HEADING_RATE;
                }
                else
                {
                    // We are not upside down so decrement as usual
                    headingDegrees -= HEADING_RATE;
                }
            }
            else
            {
                // Check to see if we are upside down.
                if(pitchDegrees > 90 && pitchDegrees < 270 || (pitchDegrees < -90 && pitchDegrees > -270))
                {
                    // Ok we would normally increment here but since we are upside
                    // down then we need to decrement our heading.
                    headingDegrees -= HEADING_RATE;
                }
                else
                {
                    // We are not upside down so increment as usual.
                    headingDegrees += HEADING_RATE;
                }
            }
        }
        
        // We don't want our heading to run away from us either. Although it
        // really doesn't matter I prefer to have my heading degrees
        // within the range of -360.0f to 360.0f
        if(headingDegrees > 360.0f)
        {
            headingDegrees -= 360.0f;
        }
        else if(headingDegrees < -360.0f)
        {
            headingDegrees += 360.0f;
        }
        heading = new Quaternion(0.0f,1.0f,0.0f,headingDegrees);
    }
}

    

