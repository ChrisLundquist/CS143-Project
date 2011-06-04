package graphics.particles;


public class Plasma extends Particle {
    public Plasma(){
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

