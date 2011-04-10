package graphics;

import javax.media.opengl.GL2;

import math.*;
public class Camera {
    
    public Camera(){
    }

    /* Based on the Nehe Tutorial by  Vic Hollis */
    public void setPerspective(GL2 gl) {
        float[] matrix;
        actor.Player player = game.Game.getPlayer();
        Quaternion q = new Quaternion();

        // Make the Quaternions that will represent our rotations
        Quaternion pitch = new Quaternion(1.0f, 0.0f, 0.0f, player.getPitchDegrees());
        Quaternion heading = new Quaternion(0.0f, 1.0f, 0.0f, player.getHeadingDegrees());

        // Combine the pitch and heading rotations and store the results in q
        q = pitch.times(heading);
        matrix = q.toGlMatrix();

        // Let OpenGL set our new perspective on the world!
        gl.glMultMatrixf(matrix,0);

        // Translate to our new position.
        gl.glTranslatef(-player.getPosition().x, -player.getPosition().y, player.getPosition().z);
    }

}