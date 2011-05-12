package graphics.particles;

public abstract class Particle {
    public float colorR;
    public float colorG;
    public float colorB;
    public float colorA;
    
    abstract protected void updateColor();
}