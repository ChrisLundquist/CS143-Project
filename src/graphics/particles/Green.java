
package graphics.particles;

/**
 * Particle System for each particle
 * @author Chris Lundquist
 *
 */
public class Green extends Particle  {
    public Green(){
        super();
    }

    protected void update() {
        super.update();

        color.x -= 0.2f;
        color.y -= 0.015f;
        color.z -= 0.05f;
        color.t -= 0.01f;
    }
}