package graphics;

import java.io.Serializable;

import javax.media.opengl.GL2;

public class Skybox implements Serializable{
    private static final long serialVersionUID = 1229301019236369376L;
    public static final float SKYBOX_SIZE = -128.0f;
    private transient Model model;
    private final String modelName;
    
    public Skybox(String waveFrontObjectFilepath){
        modelName = waveFrontObjectFilepath;
    }
    
    private void init(GL2 gl){
        model = WavefrontObjLoader.load(modelName);
    }
    
    public void render(GL2 gl) {
        if(model == null) {
            init(gl);
        }
        gl.glPushMatrix();
        math.Vector3 pos = game.Game.getPlayer().getCamera().position;
        gl.glTranslatef(-pos.x, -pos.y, -pos.z);
        gl.glScalef(SKYBOX_SIZE,SKYBOX_SIZE , SKYBOX_SIZE);
        model.render(gl);
        gl.glPopMatrix();
    }
}
