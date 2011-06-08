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
        detonationTime = gen.nextInt(10) + 45;
        shrapnelCount = 8;
        scale.timesEquals(3);
        damage = 30;
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
        // Don't collide with our siblings
        if(other instanceof FlakShell && ((FlakShell) other).parentId.equals(getParentId()))
            return;
        // Don't shoot our parents
        if (parentId.equals(other.getId()))
            return;
        detonate();
        die();
    }
    
    protected void detonate(){
        for(int i = 0; i < shrapnelCount; ++i){
            Shrapnel s = new Shrapnel(this);
            Vector3f v = Vector3f.newRandom(1000).normalize().times(0.15f);
            s.setVelocity(v);
            add(s);
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
