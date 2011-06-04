package actor.ship.projectile;

import math.Vector3f;
import graphics.particles.Fire;
import graphics.particles.generators.Explosion;
import actor.Actor;
import actor.ship.projectile.Projectile;

public class FlakShell extends Projectile {
    private static final long serialVersionUID = -4925538158994975440L;
    protected int detonationTime,shrapnelCount;
    
    public FlakShell(Actor actor) {
        super(actor);
        detonationTime = gen.nextInt(35) + 20;
        shrapnelCount = 8;
        scale.timesEquals(3);
    }
    
    @Override
    public void update(){
        super.update();
        
        if(age == detonationTime){
            detonate();
            die();
        }
    }
    
    @Override
    public void handleCollision(Actor other) {
        // Don't shoot our parents
        if (parentId.equals(other.getId()))
            return;
        detonate();
        die();
    }
    
    protected void detonate(){
        for(int i = 0; i < shrapnelCount; ++i){
            Shrapnel s = new Shrapnel(this);
            Vector3f v = Vector3f.newRandom(1000).normalize().times(0.1f);
            s.setVelocity(v);
            game.Game.getActors().add(s);
        }
    }
    
    @Override
    public void die(){
        graphics.particles.ParticleSystem.addEvent((new Explosion<Fire>(Fire.class,this)).setIntensity(32));
        delete();
    }
    
    public static long getShotCoolDown(){
        return 200;
    }
}
