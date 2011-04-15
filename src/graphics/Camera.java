package graphics;

import javax.media.opengl.GL2;

import actor.Actor;
import actor.Player;
import math.Quaternion;
import math.Vector3;

/*
 * A camera is kind of like an actor in that it has a position and rotation
 * but it's position and rotation are opposite of the actors.
 */
public class Camera {
    Vector3 position;
    Quaternion rotation;
    
    public Camera(){
        position = new Vector3();
        rotation = new Quaternion();
    }


    public void updateFromActor(Actor actor) {
        position = actor.getPosition().times(-1.0f);
        rotation = actor.getRotation().inverse();
    }
    
    public void setPerspective(GL2 gl) {
        float[] matrix;       
        gl.glLoadIdentity();
        // Combine the pitch and heading rotations and store the results in q
        matrix = rotation.toGlMatrix();
        // Let OpenGL set our new perspective on the world!
        gl.glMultMatrixf(matrix,0);
        // Translate to our new position.
        gl.glTranslatef(position.x, position.y, position.z);
    }
}