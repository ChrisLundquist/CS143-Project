package actor;

import graphics.Model;
import graphics.Model.Model_Enum;

import java.io.Serializable;
import java.util.Random;

import math.Matrix4f;
import math.Quaternion;
import math.Supportable;
import math.Vector3f;
import physics.GJKSimplex;

public abstract class Actor implements Serializable, Supportable, Movable, Collidable {
    protected static Random gen = new Random(); // Common random number generator object
    private static final long serialVersionUID = 744085604446096658L;


    protected long age; // actor age in frames;
    protected ActorId id, parentId; // unique ID for each Actor
    private Model.Model_Enum model; // CL - Used to store the model reference, after we look it up once
    
    /*
     * DL - a back reference to the actor set containing this actor
     * This is so we can avoid Game.getActors() or similar and the same code can work
     * in both the dedicated server and client contexts
     * 
     * I'm not sure if a back reference on each object is a greater evil than a global variable.
     * Chris doesn't like it, if this ends up being an issue, we can put the ActorSet in a static
     * variable somewhere. At this point it seem like this is a better way.
     */
    protected transient ActorSet actors;
    protected Vector3f position, velocity, scale;
    protected Quaternion rotation, angularVelocity;


    public Actor(Model_Enum model) {
        rotation = new Quaternion();
        angularVelocity = new Quaternion();
        position = new Vector3f();
        velocity = new Vector3f();
        scale = new Vector3f(1.0f,1.0f,1.0f);
        age = 0;
        this.model=model;
        //sets the time of the actor's birth
        //setTimeStamp();
    }
    
    public void add(Actor actor) {
        actors.add(actor);
    }

    public void bounce(Actor other) {
        // Transform our position and velocity into other's model space
        Vector3f delta_p = position.minus(other.position);
        Vector3f delta_v = velocity.minus(other.velocity);
        delta_p.x /= other.scale.x; delta_p.z /= other.scale.z; delta_p.z /= other.scale.z;
        delta_v.x /= other.scale.x; delta_v.z /= other.scale.z; delta_v.z /= other.scale.z;
        delta_p.timesEquals(other.rotation.inverse());
        delta_v.timesEquals(other.rotation.inverse());

        graphics.Polygon intersection = other.model.m.getIntersectingPolygon(delta_p, delta_v);
        // CL - Not sure if not handling the collision if we don't know how is best
        // but its better than throwing excepions
        if(intersection == null) {
            return; // We couldn't find the intersecting polygon so return so we don't throw an exception
        }
        Vector3f newVelocity = intersection.reflectDirection(delta_v);

        newVelocity.timesEquals(other.rotation);
        newVelocity.x *= other.scale.x; newVelocity.z *= other.scale.z; newVelocity.z *= other.scale.z;
        newVelocity.plusEquals(other.velocity);

        velocity = newVelocity;
    }

    public void changePitch(float degrees) {
        angularVelocity = angularVelocity.times(new Quaternion(rotation
                .pitchAxis(), degrees));
    }

    public void changeRoll(float degrees) {
        angularVelocity = angularVelocity.times(new Quaternion(rotation
                .rollAxis(), degrees));
    }

    public void changeYaw(float degrees) {
        angularVelocity = angularVelocity.times(new Quaternion(rotation
                .yawAxis(), degrees));
    }

    protected void dampenAngularVelocity() {
        angularVelocity = angularVelocity.dampen(0.01f);
    }
    
    protected void dampenAngularVelocity(float amount) {
        angularVelocity = angularVelocity.dampen(amount);
    }

    public void delete() {
        actors.remove(this);
    }

    protected long getAge() {
        return age;
    }

    public Quaternion getAngularVelocity() {
        return angularVelocity;
    }

    public Vector3f getDirection() {
        return rotation.rollAxis();
    }

    public Vector3f getFarthestPointInDirection(Vector3f direction){
        // CL - put it into world space by translating it and rotating it
        // CL - NOTE we have to push the inverse of our transform of the direction
        //      so we can figure it out in model space

        // CL - We need to do the sweeping further point so we need to see if we want where we
        // are or where we will be is better



        Vector3f max = getModel().m.getFarthestPointInDirection(direction.times(getRotation().inverse()));
        // Scale the point by our actor's scale in world space
        max.x *= scale.x;
        max.y *= scale.y;
        max.z *= scale.z;

        // Rotate and translate our point to world space
        max = max.times(getRotation()).plus(getPosition());

        // If our velocity is in the same direction as the direction, then
        // we need to sweep the furthest point by our velocity
        if(velocity.sameDirection(direction)) {
            max.plusEquals(velocity);
        // Do the same thing for angular velocity
        //if(max.times(rotation.times(getAngularVelocity())).dotProduct(direction) > max.dotProduct(direction))
        //   max = max.times(getAngularVelocity());
        }

        return max;
    }

    public ActorId getId() {
        return id;
    }

    public Model_Enum getModel() {
        return model;
    }

    /**
     * @return the actors current position
     */
    public Vector3f getPosition() {
        return position;
    }

    public float getRadius() {
        // TODO optimize: either cache, or change scale to a float
        return getModel().m.radius * Math.max(scale.x, Math.max(scale.y, scale.z));
    }

    public Quaternion getRotation() {
        return rotation;
    }

    /**
     * @return the actors size (for texture scaling and collision detection)
     */
    public Vector3f getSize() {
        return scale;
    }

    public Matrix4f getTransform(){
        return Matrix4f.newFromScale(scale).times(getRotation().toMatrix4f()).setTranslation(getPosition());
    }
    
    public Matrix4f getTransformInverse(){
        return Matrix4f.newFromScale(new Vector3f(1.0f / scale.x, 1.0f / scale.y, 1.0f / scale.z)).times(getRotation().inverse().toMatrix4f()).setTranslation(position.negate());
    }
    /**
     * 
     * @return the actors current velocity
     */
    public Vector3f getVelocity() {
        return velocity;
    }

    /**
     * Call back upon collision detection for object to handle collision It
     * could... Bounce off Explode into many smaller objects Just explode
     * 
     * @param other
     *            the object this actor collided with
     */
    abstract public void handleCollision(Actor other);

    /**
     * Helper method to get rid of stupid syntax
     * @param other the other actor to test collision with
     * @return true if colliding, else false
     */
    public boolean isColliding(Actor other){
        if (isPossiblyColliding(other)) {
            return GJKSimplex.isColliding(this, other);
        }
        return false;

    }

    /**
     * Simple bounding sphere test for trivial collision rejection
     * @param other other actor to test collision with
     * @return if a collision is possible
     */
    private boolean isPossiblyColliding(Actor other) {
        Vector3f delta_p = other.position.minus(position);
        float collisionRadius = other.velocity.minus(velocity).magnitude();
        collisionRadius += getRadius();
        collisionRadius += other.getRadius();

        return (delta_p.magnitude2() <= collisionRadius * collisionRadius);
    }
    
    public void setModel(Model_Enum model) {
        this.model = model;
    }
    
    @Override
    public Actor setPosition(Vector3f position) {
        this.position = position;
        return this;
    }

    public Actor setRotation(Quaternion rot){
        rotation = rot;
        return this;
    }

    // Lets you reference chain
    public Actor setSize(float size) {
        scale.x = size;
        scale.y = size;
        scale.z = size;
        return this;
    }

    public Actor setSize(Vector3f size){
        scale = size;
        return this;
    }

    public Actor setVelocity(Vector3f velocity) {
        this.velocity = velocity;
        return this;
    }

    // CL - updates the state of the actor for the next frame
    public void update() {
        position.plusEquals(velocity);
        rotation.normalize();

        // This should also take into effect our maximum angular velocity --
        // this may be an overridden in subclasses to provide different handling
        rotation.timesEquals(angularVelocity);
        age++;
    }

    public ActorId getParentId() {
        return parentId;
    }
}
