package graphics.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import math.Vector3f;

public class Model implements math.Supportable{
    public static class Models{
        public static final String ROUND_CAPITAL_SHIP = "round_capital";//"ship3";
        public static final String SHIP_TEST = "ship_test";
        public static final String BULLET = "bullet";
        public static final String MISSILE = "missile";
        public static final String FIGHTER = "fighter";
        public static final String ASTEROID = "asteroid";
        public static final String BANDIT = "fighter";
        public static final String BANDIT_BASE = "ship5";

        public static final String[] TOTAL = {ROUND_CAPITAL_SHIP,SHIP_TEST,BULLET,MISSILE,FIGHTER,ASTEROID, BANDIT, BANDIT_BASE};
    }

    static final String MODEL_PATH = "assets/models/";
    static final String MODEL_EXTENSION = ".obj";
    private static final int NO_LIST = -1;
    protected static Map<String, Model>models = new java.util.HashMap<String, Model>();

    public static Model findOrCreateByName(String name) {
        Model model = models.get(name);
        if(model == null) {
            model = createByName(name);
        }
        return model;
    }

    public static Model findByName(String name){
        return models.get(name);
    }

    public static Model createByName(String name){
        Model model = WavefrontObjLoader.load(name, MODEL_PATH + name + MODEL_EXTENSION);
        models.put(name, model);
        return model;
    }

    public static void loadModels(Collection<String> models) {
        for (String model: models)
            Model.findOrCreateByName(model);

        for (WavefrontLoaderError err: WavefrontObjLoader.getErrors())
            System.err.println(err);      
        for (WavefrontLoaderError err: WavefrontMtlLoader.getErrors()) 
            System.err.println(err);  
    }

    public static void loadModels() {
        /* This was a little over ambitious for now, but if new models might be showing up it might be a good idea
        File dir = new File(MODEL_PATH);

        // Loop through all models in models directory
        for(String file: dir.list())
            if (file.toLowerCase().endsWith(MODEL_EXTENSION))
                Model.findOrCreateByName(file.replaceAll(".obj$", ""));
         */
        loadModels(Models.TOTAL);
    }

    public static void loadModels(String[] models) {      
        for (String model: models)
            Model.findOrCreateByName(model);

        for (WavefrontLoaderError err: WavefrontObjLoader.getErrors())
            System.err.println(err);      
        for (WavefrontLoaderError err: WavefrontMtlLoader.getErrors()) 
            System.err.println(err);      
    }

    public static Collection<Model> loaded_models() {
        return models.values();
    }


    int displayList;
    public final List<Polygon> polygons;
    public final Map<String, Model> groups;
    public final Map<String, List<Vector3f>> hotSpots;
    public final String name;
    public final float radius;
    public final boolean isTextured;

    public Model(String name, List<Polygon> polygons){
        this.name = name;
        this.polygons = polygons;
        groups = buildGroups();
        displayList = NO_LIST;
        radius = findRadius();
        boolean textures = false;
        for(Polygon p : polygons){
            if(p.getMaterial().getTexture() != null){
                textures = true;
                break;
            }
        }
        hotSpots = new HashMap<String, List<Vector3f>>();
        buildHotSpots();
        isTextured = textures;
    }

    private void buildHotSpots() {
        File hotSpotFile = new File(MODEL_PATH + name + ".hspot");
        BufferedReader in;
        String line;


        try {
            if (hotSpotFile.exists()){
                in = new BufferedReader(new FileReader(hotSpotFile));
                File transformFile = new File(MODEL_PATH + name + ".trfm");
                math.Matrix4f transform;
                
                if(transformFile.exists())
                    transform =  new math.Matrix4f(WavefrontObjLoader.loadTranform(transformFile));
                else
                    transform = new math.Matrix4f();

                // While we have lines in our file
                while((line = in.readLine()) != null){
                    String[] tokens = line.split(" ", 5);
                    // Get our type we are going to add
                    List<Vector3f> spotList = hotSpots.get(tokens[0]);
                    // Make it if it is a new type
                    if(spotList == null){
                        hotSpots.put(tokens[0], new ArrayList<Vector3f>());
                    }
                    try{
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        float z = Float.parseFloat(tokens[3]);
                        hotSpots.get(tokens[0]).add(transform.times(new Vector3f(x,y,z)));

                    } catch(NumberFormatException e){
                        System.err.println("Unable to parse hotspots for " + name + " on tokens " + tokens[0] + " " + tokens[1] + " " +tokens[2] + " " +tokens[3]);
                    }
                }
            }
        }
        catch (IOException e) {
            // Oh well
        }
    }



    // CL - XXX Hack used for collision detection.
    //      Might be worth making a ModelGroup class that extends Model
    public static final String PRIVATE_GROUP_NAME = "Private Group";
    private Model(List<Polygon> polygons){
        name = PRIVATE_GROUP_NAME;
        groups = null;
        this.polygons = polygons;
        displayList = NO_LIST;
        radius = findRadius();
        boolean textures = false;
        for(Polygon p : polygons){
            if(p.getMaterial().getTexture() != null){
                textures = true;
                break;
            }
        }
        hotSpots = new HashMap<String, List<Vector3f>>();
        isTextured = textures;
    }

    private Map<String, Model> buildGroups() {
        // Sort our polygons into groups
        Map<String, List<Polygon>> polyGroup = new java.util.HashMap<String, List<Polygon>>();
        for( Polygon p : polygons){
            for(String group : p.groups){
                if(group.isEmpty()) {
                    continue;
                }
                if(!polyGroup.containsKey(group)){ // If it doesn't have the key, add it
                    polyGroup.put(group, new java.util.ArrayList<Polygon>());
                }
                polyGroup.get(group).add(p); // Add this poly to the group
            }
        }
        // Make each group a model so we can use all our collision code for models on each group

        Map<String, Model> groups = new java.util.HashMap<String, Model>();

        for(String groupName : polyGroup.keySet()){
            groups.put(groupName, new Model( polyGroup.get(groupName)));
        }


        return groups;
    }

    @Override
    public Vector3f getFarthestPointInDirection(Vector3f direction) {
        Vector3f max = polygons.get(0).verticies.get(0).coord;
        float maxDotProduct = max.dotProduct(direction);

        // Loop though all of our polygons
        for(Polygon p : polygons){
            // And all of the vertices in each polygon
            for(Vertex v : p.verticies){
                float thisDotProduct = v.coord.dotProduct(direction);
                if ( maxDotProduct < thisDotProduct) {
                    max = v.coord;
                    maxDotProduct = thisDotProduct;
                }
            }
        }
        // It is important we return a new vector and not a reference to one in our geometry
        return new Vector3f(max);
    }

    public Polygon getIntersectingPolygon(Vector3f origin, Vector3f direction) {
        for(Polygon p : polygons) {
            if (p.isIntersecting(origin, direction)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Finds the bounding sphere radius
     * @return the bounding sphere radius
     */
    protected float findRadius() {
        float radius2 = 0.0f;

        for (Polygon p: polygons) {
            for (Vertex v: p.verticies) {
                radius2 = Math.max(radius2, v.coord.magnitude2());
            }
        }

        return (float)Math.sqrt(radius2);
    }
    
    public List<Vector3f> getHotSpotFor(String key){
        return hotSpots.get(key);
    }
}
