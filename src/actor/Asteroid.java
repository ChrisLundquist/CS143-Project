package actor;

import actor.ship.projectile.Projectile;
import graphics.core.Model;
import graphics.particles.Fire;
import graphics.particles.ParticleSystem;
import graphics.particles.generators.Explosion;
import math.Quaternion;
import math.Vector3f;

public class Asteroid extends Actor {
    private static final long serialVersionUID = 916554544709785597L;
    private static final String MODEL_NAME = Model.Models.ASTEROID;
    private static final int SHATTER_THRESHOLD = 8;
    private static final float DEFAULT_SCALE = 10f;
    private static final float HEALTH_MODIFIER= 1.0f / 2.0f;
    private static final String SOUND_EFFECT = "explode.wav";
    private static final float EFFECT_VOLUME = 10.5f;
    private static final float FRAGMENT_SPEED = 0.01f;
    private static final float MAX_ROTATION_SPEED = 1.5f;

    protected int hitPoints;
    
    // orbitField paramters
    protected float r_x, r_y, r_z;
    protected float e_x, e_y, e_z;

    public Asteroid(){
        super();
        this.angularVelocity = Quaternion.newRandom(MAX_ROTATION_SPEED);
        this.scale = new Vector3f(DEFAULT_SCALE,DEFAULT_SCALE,DEFAULT_SCALE).plus(Vector3f.newRandom(1));
        this.hitPoints = calculateHitpoints(); 
        this.modelName = MODEL_NAME;
        
        r_x = gen.nextFloat() * gen.nextFloat() * 100 - 50;
        r_y = gen.nextFloat() * gen.nextFloat() * 100 - 50;
        r_z = gen.nextFloat() * gen.nextFloat() * 100 - 50;
        e_x = (gen.nextFloat() + 0.5f) * (gen.nextFloat() + 0.5f) * (gen.nextFloat() + 0.5f);
        e_y = (gen.nextFloat() + 0.5f) * (gen.nextFloat() + 0.5f) * (gen.nextFloat() + 0.5f);
        e_z = (gen.nextFloat() + 0.5f) * (gen.nextFloat() + 0.5f) * (gen.nextFloat() + 0.5f);
    }

    private int calculateHitpoints() {
        return (int) (scale.magnitude2() * HEALTH_MODIFIER);
    }

    public Asteroid(Vector3f position, Vector3f velocity){
        this();
        this.setPosition(position);
        this.setVelocity(velocity);
        this.modelName = MODEL_NAME;
    }

    public Asteroid(Asteroid other){
        super(other);
        hitPoints = other.hitPoints;
        r_x = other.r_x;
        r_y = other.r_y;
        r_z = other.r_z;
        e_x = other.e_x;
        e_y = other.e_y;
        e_z = other.e_z;
    }

    @Override
    public void handleCollision(Actor other) {
        if(other instanceof Projectile){
            hitPoints -= ((Projectile) other).getDamage();
        } else if ( other instanceof Asteroid || other instanceof actor.ship.Ship){
            // Don't collide with our siblings
            if(other.getParentId() != null && other.getParentId().equals(getParentId()))
                return;
            // "Die" for our next update
            hitPoints = 0;
        }
    }

    @Override
    public void die(){
        if (sound.Manager.enabled) {
            sound.Event effect = new sound.Event(getPosition(), getVelocity(),sound.Library.findByName(SOUND_EFFECT));
            // make bigger asteroids explode bigger
            effect.gain = EFFECT_VOLUME * scale.magnitude2();
            effect.pitch = 0.6f;
            sound.Manager.addEvent(effect);
        }        
        velocity.timesEquals(0);
        if(ParticleSystem.isEnabled())
            ParticleSystem.addEvent((new Explosion<Fire>(Fire.class,this)).setIntensity((int)scale.magnitude2()));

        if(scale.magnitude2() > SHATTER_THRESHOLD){
            // Make a copy of this, but be sure it reset its HP
            Asteroid a = new Asteroid(this);
            a.scale = this.scale.times(0.5f);
            a.hitPoints = a.calculateHitpoints();
            a.id = null; // ID must be null or the actor set wont add it
            // Move it a random direction half the radius for each piece
            Vector3f displacement = Vector3f.newRandom(1).normalize().times(getRadius() * 0.52f);
            Asteroid b = new Asteroid(a);
            a.position.plusEquals(displacement);
            a.velocity.plusEquals(displacement.times(FRAGMENT_SPEED));
            a.setRotation(Quaternion.newRandom(100));
            a.setAngularVelocity(Quaternion.newRandom(MAX_ROTATION_SPEED));
            b.position.plusEquals(displacement.negate());
            b.velocity.plusEquals(displacement.negate().times(FRAGMENT_SPEED));
            b.setRotation(Quaternion.newRandom(100));
            b.setAngularVelocity(Quaternion.newRandom(MAX_ROTATION_SPEED));

            actors.add(a);
            actors.add(b);
        }

        delete();
    }

    public void update(){
        if(hitPoints <= 0)
            die();
        velocity = orbitField(position);
        super.update();
        //TODO: Write code that detects if it is out of the skybox. (or some other boundry).
    }

    private Vector3f orbitField(Vector3f pos) {

        
        Vector3f newVelocity = new Vector3f();
        newVelocity.plusEquals(new Vector3f(e_z * pos.y, - pos.x / e_z, 0).times(r_z / pos.magnitude2()));
        newVelocity.plusEquals(new Vector3f(0, e_x * pos.z, -pos.y / e_x).times(r_x / pos.magnitude2()));
        newVelocity.plusEquals(new Vector3f(- pos.z / e_y, 0, e_y * pos.x).times(r_y / pos.magnitude2()));
        
        
        return newVelocity;
    }
}
