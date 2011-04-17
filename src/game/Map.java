package game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import math.Vector3;
import actor.Actor;

public class Map implements Serializable {
    private static final String MAP_DIR = "assets/maps/";
    private static final String MAP_EXTENSION = ".map";
    private static final long serialVersionUID = 4499508076059412730L;

    public static Map load(File file) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));

            Object obj = input.readObject();
            if (obj instanceof Map)
                return (Map)obj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
    
    public static Map load(String filename) {
        return load(new File(MAP_DIR + filename + MAP_EXTENSION));
    }
    
    public static void main(String[] args) {
        Map map = new Map("Example 1");
        assert map.filepath().equals("assets/maps/example_1.map") : "File name sanitization";
        assert map.actors != null : "Actors was not initialized";
        assert map.spawningPositions != null : "Spawning positions was not initialized";

        map.spawningPositions.add(new Vector3(20.0f, 0.0f, 0.0f));
        map.spawningPositions.add(new Vector3(-20.0f, 0.0f, 0.0f));
        map.spawningPositions.add(new Vector3(0.0f, 20.0f, 0.0f));
        map.spawningPositions.add(new Vector3(0.0f, -20.0f, 0.0f));
        map.spawningPositions.add(new Vector3(0.0f, 0.0f, 20.0f));
        map.spawningPositions.add(new Vector3(0.0f, 0.0f, -20.0f));

        map.skybox = new graphics.Skybox("assets/models/sky_cube.obj");
        map.write();
        
        Map loaded = Map.load("example_1");
        
        assert loaded.spawningPositions.size() == 6;
    }
    
    
    public String name;
    public graphics.Skybox skybox;
    public List<Actor> actors;
    public List<Vector3> spawningPositions;
    // TODO private Vector<Object> triggers;

    public Map() {
        this.spawningPositions = new Vector<Vector3>();
        this.actors = new Vector<Actor>();
    }
    
    public Map(String name) {
        this();
        this.name = name;
    }
        
    public graphics.Skybox getSkybox() {
        return skybox;
    }

    public void setSkybox(graphics.Skybox skybox) {
        this.skybox = skybox;
    }
    
    /**
     * Returns a safe file path for this map based on its name
     * @return
     */
    private String filepath() {
        return MAP_DIR + name.replaceAll("[^a-zA-Z0-9]", "_").toLowerCase() + ".map";
    }
    
    private boolean valid() {
        if (actors == null)
            return false;
        if (spawningPositions == null || spawningPositions.size() < 1)
            return false;
        if (name == null)
            return false;
        if (skybox == null)
            return false;
        
        return true;
    }

    public void write() {
        if (!valid()) {
            System.err.println("Not saving invalid map");
            return;
        }
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filepath()));
            output.writeObject(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
