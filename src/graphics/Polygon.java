/**
 * 
 */
package graphics;

import java.util.List;
import javax.media.opengl.GL2;

public class Polygon {
    private transient Material material;
    List<Vertex> verticies;
    List<String> groups;
    private String materialName;
    public String object;

    public Polygon(String materialName, java.util.Collection<Vertex> verticies) {
        this.materialName = materialName;
        this.verticies = new java.util.ArrayList<Vertex>(verticies);
        this.groups = new java.util.ArrayList<String>();
    }
    
    public Polygon(Material material, java.util.Collection<Vertex> verticies) {
        this.material = material;
        this.materialName = material.getName();
        this.verticies = new java.util.ArrayList<Vertex>(verticies);
        this.groups = new java.util.ArrayList<String>();
    }

    public void render(GL2 gl) {
        if (verticies.size() < 2)
            return;
        
        gl.glColor4f(1.0f, 1.0f, 1.0f,1.0f);
        getMaterial().prepare(gl);
        gl.glBegin(GL2.GL_TRIANGLES);
        if (verticies.size() == 3) {
            for (Vertex v: verticies){
                gl.glTexCoord2f(v.u, v.v); 
                gl.glVertex3f(v.getX(), v.getY(), v.getZ());
            }
        } else {
            Vertex a = verticies.get(0);
            
            for (int i = 2; i < verticies.size(); i++) {
                Vertex b = verticies.get(i - 1);
                Vertex c = verticies.get(i);
                
                gl.glTexCoord2f(a.u, a.v); 
                gl.glVertex3f(a.getX(), a.getY(), a.getZ());
                gl.glTexCoord2f(b.u, b.v); 
                gl.glVertex3f(b.getX(), b.getY(), b.getZ());
                gl.glTexCoord2f(c.u, c.v); 
                gl.glVertex3f(c.getX(), c.getY(), c.getZ());
            }
            
        }
        gl.glEnd();
    }
    
    private Material getMaterial() {
        if(material == null)
            material = Material.findByName(materialName);
        return material;
    }
}
