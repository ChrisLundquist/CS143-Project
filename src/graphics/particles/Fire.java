
package graphics.particles;

/**
 * Particle System for each particle
 * @author Tim Mikeladze, Chris Lundquist
 *
 */
public class Fire extends Particle  {
    public Fire(){
        color = new math.Vector4f(1,1,1,0);
    }

    protected void update() {
        super.update();

        color.x -= 0.015f;
        color.y -= 0.05f;
        color.z -= 0.2f;
        color.t -= 0.001f;
    }
}