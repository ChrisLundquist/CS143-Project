package graphics.particles;

public abstract class Particle extends actor.Actor {
    private static final long serialVersionUID = -1288874741029406494L;
    public float colorR;
    public float colorG;
    public float colorB;
    public float colorA;

    public void update(){
        // Update our position
        super.update();

        // Change our color
        updateColor();
    }

    abstract protected void updateColor();

    public void delete(){
        ParticleSystem.particles.remove(this);
    }

    @Override
    public void handleCollision(actor.Actor other) {
        // We have no collision value
        return;
    }
}
