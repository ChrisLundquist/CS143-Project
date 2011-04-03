package actor;
import math.*;
import graphics.Model;
public abstract class Actor {
    protected float headingDegrees;
    protected float pictureDegrees;
    protected Vector velocity;
    protected Point position;
    protected Quaternion heading;
    protected Quaternion pitch;
    
    protected int modelId;
    protected transient Model model; // CL - Used to store the model reference after we look it up once
    
    public Model getModel() {
        // CL - If our reference is null, go look it up
        if(model == null)
            model = Model.findById(modelId);
        
        return model;
    }
    
    // CL - updates the state of the actor for the next frame
    public void update(){
        position.add(velocity);
    }
}
