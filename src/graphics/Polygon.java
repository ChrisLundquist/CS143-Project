/**
 * 
 */
package graphics;

import java.util.List;
import math.Vector3f;

public class Polygon {
    private transient Material material;
    List<Vertex> verticies;
    List<String> groups;
    private String materialName;
    public String object;
    public final Vector3f normal;

    public Polygon(String materialName, java.util.Collection<Vertex> verticies) {
        this.materialName = materialName;
        this.verticies = new java.util.ArrayList<Vertex>(verticies);
        this.groups = new java.util.ArrayList<String>();
        
        Vector3f a, b, c;
        a = this.verticies.get(0).coord;
        b = this.verticies.get(1).coord;
        c = this.verticies.get(2).coord;
        this.normal = c.minus(b).cross(a.minus(b)).normalize();
    }
    
    public Polygon(java.util.Collection<Vertex> verticies) {
        this(Material.DEFAULT_MATERIAL, verticies);
    }
    
    public Polygon(Material material, java.util.Collection<Vertex> verticies) {
        this(material.getName(), verticies);
        this.material = material;
    }
    
    Material getMaterial() {
        if(material == null)
            material = Material.findByName(materialName);
        return material;
    }
    
    public Vector3f reflectDirection(Vector3f a) {
        return a.minus(normal.times(2 * a.dotProduct(normal)));
    }
    
    public Vector3f parallelDirection(Vector3f a) {
        return a.minus(normal.times(a.dotProduct(normal)));
    }
    
    public boolean isIntersecting(Vector3f origin, Vector3f direction) {
        Vector3f delta = verticies.get(0).coord.minus(origin);
               
        // Find the time when they intersect
        float t = normal.dotProduct(direction);
        if (t <= 0) // don't divide by zero or intersect with the back of objects
            return false;
        t = normal.dotProduct(delta) / t;
        
        if (t < 0) // We intersected the polygon in the past
            return false;
        
        Vector3f intersection = origin.plus(direction.times(t));
        
        // Walk around the polygon checking the intersection is on the inside of all the edges
        Vector3f last = verticies.get(verticies.size() - 1).coord;
        for (Vertex v: verticies) {
            if (intersection.minus(v.coord).cross(last.minus(v.coord)).dotProduct(normal) < 0)
                return false;
           
            last = v.coord;
        }
        
        return true;
    }
}
