package game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import math.Vector3;
import actor.Actor;

public class Map implements Serializable {
    private static final long serialVersionUID = 4499508076059412730L;
    private static final String MAP_DIR = "assets/maps/";

    public String name;  
    public Vector<Vector3> spawningPositions;
    public Vector<Actor> actors;
    private Object skybox;
    private Vector<Object> triggers;

    public Map() {
        this.spawningPositions = new Vector<Vector3>();
        this.actors = new Vector<Actor>();
    }
    
    public Map(String name) {
        this.name = name;
        this.spawningPositions = new Vector<Vector3>();
        this.actors = new Vector<Actor>();
    }

    public static Map load(String filepath) {
        return load(new File(filepath));
    }

    public static Map load(File file) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));

            Object obj = input.readObject();
            if (obj instanceof Map)
                return (Map)obj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void write() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename()));
            output.writeObject(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String filename() {
        return MAP_DIR + name.replaceAll("[^a-zA-Z0-9]", "_").toLowerCase() + ".map";
    }
    
    public static void main(String[] args) {
        Map map = new Map("Example 1");
        assert map.filename().equals("assets/maps/example_1.map") : "File name sanitization";

        map.spawningPositions.add(new Vector3(20.0f, 0.0f, 0.0f));
        map.spawningPositions.add(new Vector3(-20.0f, 0.0f, 0.0f));
        map.spawningPositions.add(new Vector3(0.0f, 20.0f, 0.0f));
        map.spawningPositions.add(new Vector3(0.0f, -20.0f, 0.0f));
        map.spawningPositions.add(new Vector3(0.0f, 0.0f, 20.0f));
        map.spawningPositions.add(new Vector3(0.0f, 0.0f, -20.0f));

        
        map.write();
        
        Map loaded = Map.load("assets/maps/example_1.map");
        
        assert loaded.spawningPositions.size() == 6;
    }


}
