
package graphics.particles;

import math.Vector3f;
/**
 * Particle System for each particle
 * @author Tim Mikeladze, Chris Lundquist
 *
 */
public class FireParticle extends Particle  {
    /**
     * Creates a new ParticleSystem with the given parameters. lifetime, decay, and size can not be 0
     * @param position
     * @param lifetime
     * @param decay
     * @param size
     */
    public FireParticle(actor.Actor actor,Vector3f direction) {
        super(actor,direction);
    }

    protected void update() {
        super.update();

        color.x -= 0.015f;
        color.y -= 0.05f;
        color.z -= 0.2f;
    }
}