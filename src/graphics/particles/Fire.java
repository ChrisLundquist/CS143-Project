
package graphics.particles;

/**
 * Particle System for each particle
 * @author Tim Mikeladze, Chris Lundquist
 *
 */
public class Fire extends Particle  {
    public Fire(){
        super();
    }

    protected void update() {
        super.update();

        color.x -= 0.015f;
        color.y -= 0.05f;
        color.z -= 0.2f;
        color.t -= 0.01f;
    }
}