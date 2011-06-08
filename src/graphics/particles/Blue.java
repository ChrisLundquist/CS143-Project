
package graphics.particles;

/**
 * Particle System for each particle
 * @author Chris Lundquist
 *
 */
public class Blue extends Particle  {
    public Blue(){
        super();
    }

    protected void update() {
        super.update();

        color.x -= 0.2f;
        color.y -= 0.05f;
        color.z -= 0.015f;
        color.t -= 0.01f;
    }
}