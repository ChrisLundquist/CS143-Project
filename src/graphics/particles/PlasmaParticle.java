package graphics.particles;

import math.Vector3f;

public class PlasmaParticle extends Particle {
    public PlasmaParticle(){
        color = new math.Vector4f(1,1,1,0);
    }
    public PlasmaParticle(actor.Actor actor,Vector3f direction) {
        super(actor,direction);
        color = new math.Vector4f(1,1,1,0);
    }

    protected void update() {
        super.update();

        color.x -= 0.02f;
        color.y -= 0.05f;
        color.z -= 0.015f;
        color.t -= 0.001f;
    }
}

