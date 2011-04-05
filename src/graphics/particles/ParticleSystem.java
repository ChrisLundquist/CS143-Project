package graphics.particles;


public class ParticleSystem {
    public static java.util.Vector<Particle> particles;
    public static boolean enabled;
    public static final int DENSITY = 5;

    public static void init(boolean enable){
        System.err.println("Initializing Particles");
        enabled = enable;
        particles = new java.util.Vector<Particle>();
    }

    public static void updateParticles(){
        for(int i = 0; i < particles.size(); i++){
            Particle p = particles.get(i);
            p.update();
        }
    }
    
    public static boolean isEnabled() {
        return enabled;
    }
    
    public static boolean isEnabled(boolean toggle) {
        enabled = toggle;
        
        if (enabled == false)
            particles.clear(); // Don't keep our particles around if the user disables them mid game
            
        return enabled;
    }


}

