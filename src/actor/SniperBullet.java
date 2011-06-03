package actor;

import graphics.particles.ParticleSystem;
import graphics.particles.PlasmaParticle;
import math.Vector3f;

public class SniperBullet extends Bullet {
    private static final long serialVersionUID = 7453924541312180985L;
    protected static final long DEFAULT_DELAY = 1000;
    
    public SniperBullet(Actor actor) {
        super(actor);
        damage = 40;
        velocity.timesEquals(5);
    }
    
    public void handleCollision(Actor other){
        // Don't shoot our parents
        if (parentId.equals(other.getId()))
            return;
        
        if(ParticleSystem.isEnabled()){
            for(int i = 0; i < 128; i++){
                ParticleSystem.addParticle( new PlasmaParticle(this,Vector3f.newRandom(0.4f)));
            }
        }
        die();
    }
    
    public static long getShotCoolDown() {
        return DEFAULT_DELAY;
    }
}
