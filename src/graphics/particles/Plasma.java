package graphics.particles;


public class Plasma extends Particle {
    public Plasma(){
        super();
    }

    protected void update() {
        super.update();

        color.x -= 0.02f;
        color.y -= 0.05f;
        color.z -= 0.015f;
        color.t -= 0.01f;
    }
}

