package actor.ship;

import graphics.core.Model;
import graphics.particles.Particle;
import graphics.particles.ParticleSystem;
import graphics.particles.Plasma;
import graphics.particles.generators.ParticleGenerator;

import java.util.ArrayList;
import java.util.List;

import math.Quaternion;
import math.Vector3f;
import actor.Actor;
import actor.ship.projectile.Projectile;
import actor.ship.shield.Shield;
import actor.ship.weapon.Weapon;

public abstract class Ship extends Actor {
    private static final long serialVersionUID = -7048308038567858490L;
    private static final int MAX_HIT_POINTS = 200;

    protected List<Weapon<? extends actor.ship.projectile.Projectile>> weapons;
    protected List<Shield> shields; /* If we want to have different shield generators so front and rear shields are different */
    protected int selectedWeapon, hitPoints;
    protected long lastHitFrame; // age when last hit
    protected transient List<ParticleGenerator<? extends Particle>> particleGenerators;

    public Ship(){
        super();
        modelName = Model.Models.FIGHTER;
        selectedWeapon = 0;
        weapons = new java.util.ArrayList<Weapon<? extends actor.ship.projectile.Projectile>>();
        shields = new java.util.ArrayList<Shield>();
        particleGenerators = new ArrayList<ParticleGenerator<? extends Particle>>();
        hitPoints = MAX_HIT_POINTS;
    }

    public Ship(math.Vector3f pos){
        this();
        setPosition(pos);
    }

    public void shoot(){
        weapons.get(selectedWeapon).shoot(this);
    }

    @Override
    public void handleCollision(Actor other) {
        if(other instanceof actor.ship.projectile.Projectile) {
            // Don't collide with our own bullets
            if(other.getParentId().equals(id))
                return;
            //shield testing code
            actor.ship.projectile.Projectile projectile = (actor.ship.projectile.Projectile) other;
            takeDamage(projectile.getDamage());

        } else if( other instanceof actor.Asteroid ){
            // TODO bounce or spin
            takeDamage(other.getMass());
        } else if (other instanceof Ship){
            // TODO bounce or spin
            takeDamage(other.getMass());
        }
    }

    /**
     * returns the number of frames since the last hit   
     * @return
     */
    public long getLastHitAge() {
        return (age - lastHitFrame);
    }

    public void takeDamage(float amount){
        //TODO When we have multiple shields find which shield to take damage on
        amount = shields.get(0).takeDamage((int)amount);
        lastHitFrame = age;

        if(shields.get(0).getStatus() == false){
            hitPoints -= amount;
        }
    }

    @Override
    public void update(){
        if(hitPoints <= 0)
            die();
        super.update();
        for(Shield shield : shields)
            shield.update();
    }

    @Override
    public void onFirstUpdate(){
        if(ParticleSystem.isEnabled()){
            
            // XXX DANGER WILL ROBINSON XXX
            List<Vector3f> hotSpots = getHotSpotsFor("Engine");
            if( hotSpots == null) {
                // No Engine hotspots defined
                return;
            }
            
            for(Vector3f v : hotSpots){
                final Vector3f hotspot = v;
                final Actor a = this;
                ParticleGenerator<? extends Particle> particleGenerator = new graphics.particles.generators.Exhaust<Plasma>(Plasma.class,
                        new actor.interfaces.Movable() {
                    @Override
                    public Vector3f getVelocity() {
                        return a.getVelocity();
                    }

                    @Override
                    public Object setVelocity(Vector3f vel) {
                        // NOT IMPLEMENTED
                        return null;
                    }

                    @Override
                    public Vector3f getPosition() {
                        return a.getPosition().plus(hotspot);
                    }

                    @Override
                    public Object setPosition(Vector3f newPosition) {
                        // NOT IMPLEMENTED
                        return null;
                    }

                    @Override
                    public Quaternion getRotation() {
                        return a.getRotation();
                    }

                    @Override
                    public Object setRotation(Quaternion rot) {
                        // NOT IMPLEMENTED
                        return null;
                    }
                });
                if (particleGenerators != null)
                    particleGenerators.add(particleGenerator);
                ParticleSystem.addGenerator(particleGenerator);
            }
        }
    }

    @Override
    public void die(){
        if(ParticleSystem.isEnabled() && particleGenerators != null)
            for(ParticleGenerator<? extends Particle> particleGenerator : particleGenerators)
                ParticleSystem.removeGenerator(particleGenerator);
        delete();
    }

    public void nextWeapon() {
        setWeapon((selectedWeapon + 1) % weapons.size());
    }

    public void previousWeapon() {
        setWeapon((selectedWeapon - 1) % weapons.size());
    }

    public void setWeapon(int weaponNumber){
        selectedWeapon = weaponNumber % weapons.size();
    }

    public Weapon<? extends Projectile> getWeapon() {
        return weapons.get(selectedWeapon);
    }
    
    /**
     * Returns the ships health from 1.0 .. 0.0
     * @return
     */
    public float health() {
        return (float)hitPoints / MAX_HIT_POINTS;
    }

    public float shield(){
        return (float)shields.get(0).getStrength()/shields.get(0).getMaxStrength();
    }

    public boolean isAlive(){
        return hitPoints > 0;
    }

    public boolean isDead(){
        return hitPoints <= 0;
    }
}
