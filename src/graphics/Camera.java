package graphics;

import javax.media.opengl.GL2;

import math.*;
public class Camera {
    private static final float PITCH_RATE = 1.0f;
    private static final float HEADING_RATE = 1.0f;

    
    
    protected float headingDegrees;
    protected float pitchDegrees;
    protected Vector3 velocity;
    protected Vector3 position;
    protected Quaternion heading;
    protected Quaternion pitch;
    protected Vector3 direction;

    public Camera(){

    }

    public Camera(actor.Player player){
        headingDegrees = player.getHeadingDegrees();
        pitchDegrees = player.getPictchDegrees();
        pitch = new Quaternion(player.getPitch());
        heading = new Quaternion(player.getHeading());

        // Shallow Copies below this line
        position = player.getPosition();
        velocity = player.getVelocity();
        direction = new Vector3();
    }

/* Based on the Nehe Tutorial by  Vic Hollis */
    public void setPerspective(GL2 gl) {
        float[] matrix;
        Quaternion q = new Quaternion();

        // Make the Quaternions that will represent our rotations
        pitch = new Quaternion(1.0f, 0.0f, 0.0f, pitchDegrees);
        heading = new Quaternion(0.0f, 1.0f, 0.0f, headingDegrees);

        // Combine the pitch and heading rotations and store the results in q
        q = pitch.times(heading);
        matrix = q.toGlMatrix();

        // Let OpenGL set our new perspective on the world!
        gl.glMultMatrixf(matrix,0);

        // Create a matrix from the pitch Quaternion and get the j vector 
        // for our direction.
        matrix = pitch.toGlMatrix();
        direction.y = matrix[9];

        // Combine the heading and pitch rotations and make a matrix to get
        // the i and j vectors for our direction.
        q = heading.times(pitch);
        matrix = q.toGlMatrix();
        direction.x = matrix[8];
        direction.z = matrix[10];

        // Scale the direction by our speed.
        direction = direction.times(velocity.magnitude());

        // Increment our position by the vector
        position.x += direction.x;
        position.y += direction.y;
        position.z += direction.z;

        // Translate to our new position.
        gl.glTranslatef(-position.x, -position.y, position.z);
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
    }
}
