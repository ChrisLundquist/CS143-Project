package graphics;

import graphics.core.Model;

import java.io.Serializable;

public class Skybox implements Serializable{
    private static final long serialVersionUID = 1229301019236369376L;
    public static final float SKYBOX_SIZE = 512.0f;
    private transient Model model;
    private final String modelName;

    public Skybox(String waveFrontObjectFilepath){
        modelName = waveFrontObjectFilepath;
    }

    public Model getModel() {
        if (model == null)
            model = Model.findOrCreateByName(modelName);
        return model;
    }
}
