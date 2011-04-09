package graphics;

import javax.media.opengl.GL2;

import math.*;
public class Camera {
    protected float headingDegrees;
    protected float pitchDegrees;
    protected Vector3 velocity;
    protected Vector3 position;
    protected Quaternion heading;
    protected Quaternion pitch;
    
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
    }
    
    
    private void pushTranslationTransformation(GL2 gl){
        gl.glTranslatef(position.x,position.y,position.z);
    }
    
    private void pushRotationTransformation(GL2 gl){
        /* The second parameter is the matrix offset
         * glMultMatrixf(float[] matrix, int matrix_offset) */
        gl.glMultMatrixf(heading.toGlMatrix(), 0);
    }
       
    public void pushTransformation(GL2 gl) {
        pushTranslationTransformation(gl);
        pushRotationTransformation(gl);
    }
}
