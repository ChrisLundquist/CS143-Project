/**
 * 
 */
package graphics;

import java.util.Vector;
import javax.media.opengl.GL2;

public class Polygon {
    private transient Material material;
    Vector<Vertex> verticies;
    Vector<String> groups;
    private String materialName;

    public Polygon(String materialName, Vertex[] verticies) {
        this.materialName = materialName;
        this.verticies = new Vector<Vertex>(verticies.length);
        this.groups = new Vector<String>();
        this.verticies.copyInto(verticies);
    }

    public Polygon(String materialName, java.util.Collection<Vertex> verticies) {
        this.materialName = materialName;
        this.verticies = new Vector<Vertex>(verticies);
        this.groups = new Vector<String>();
    }
    
    public Polygon(Material material, Vertex[] verticies) {
        this.materialName = material.getName();
        this.material = material;
        this.verticies = new Vector<Vertex>(verticies.length);
        this.groups = new Vector<String>();
        this.verticies.copyInto(verticies);
    }

    public Polygon(Material material, java.util.Collection<Vertex> verticies) {
        this.material = material;
        materialName = material.getName();
        this.verticies = new Vector<Vertex>(verticies);
        groups = new Vector<String>();
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
                gl.glVertex3f(v.x, v.y, v.z);
            }
        } else {
            Vertex a = verticies.get(0);
            
            for (int i = 2; i < verticies.size(); i++) {
                Vertex b = verticies.get(i - 1);
                Vertex c = verticies.get(i);
                
                gl.glTexCoord2f(a.u, a.v); 
                gl.glVertex3f(a.x, a.y, a.z);
                gl.glTexCoord2f(b.u, b.v); 
                gl.glVertex3f(b.x, b.y, b.z);
                gl.glTexCoord2f(c.u, c.v); 
                gl.glVertex3f(c.x, c.y, c.z);
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
