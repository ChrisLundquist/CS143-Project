package actor;

import graphics.Model;
import graphics.particles.FireParticle;
import graphics.particles.ParticleSystem;
import math.Quaternion;
import math.Vector3f;

public class Asteroid extends Actor {
    private static final long serialVersionUID = 916554544709785597L;
    private static final String MODEL_NAME = Model.Models.ASTEROID;
    private static final int SHATTER_THRESHOLD = 4;
    private static final float DEFAULT_SCALE = 10f;
    private static final float HEALTH_MODIFIER= 1.0f / 2.0f;
    private static final String SOUND_EFFECT = "explode.wav";
    private static final float EFFECT_VOLUME = 10.5f;
    private static final float FRAGMENT_SPEED = 0.01f;

    protected int hitPoints;
    protected game.types.AsteroidField field;

    public Asteroid(){
        super();
        this.angularVelocity = new Quaternion(Vector3f.newRandom(1), 1);
        this.scale = new Vector3f(DEFAULT_SCALE,DEFAULT_SCALE,DEFAULT_SCALE).plus(Vector3f.newRandom(1));
        this.hitPoints = calculateHitpoints(); 
        this.modelName = MODEL_NAME;
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
        field = other.field;
    }

    @Override
    public void handleCollision(Actor other) {
        if(other instanceof Projectile){
            hitPoints -= ((Projectile) other).getDamage();
            if(hitPoints < 0)
                die();
        }
        bounce(other);
    }

    @Override
    public void die(){
        if(field != null)
            field.asteroidDied();

        sound.Event effect = new sound.Event(getPosition(), getVelocity(),sound.Library.findByName(SOUND_EFFECT));
        // make bigger asteroids explode bigger
        effect.gain = EFFECT_VOLUME * scale.magnitude2();
        effect.pitch = 0.6f;
        sound.Manager.addEvent(effect);

        if(ParticleSystem.isEnabled())
            for(int i = 0; i < 160; i++){
                ParticleSystem.addParticle( new FireParticle(this,Vector3f.newRandom(1)));
            }

        if(scale.magnitude2() > SHATTER_THRESHOLD){
            // Make a copy of this, but be sure it reset its HP
            Asteroid a = new Asteroid(this);
            a.scale = this.scale.times(0.5f);
            a.hitPoints = a.calculateHitpoints();
            a.id = null; // ID must be null or the actor set wont add it
            // Move it a random direction half the radius for each piece
            Vector3f displacement = Vector3f.newRandom(1).normalize().times(getRadius() * 0.51f);
            Asteroid b = new Asteroid(a);
            a.position.plusEquals(displacement);
            a.velocity.plusEquals(displacement.times(FRAGMENT_SPEED));
            b.position.plusEquals(displacement.negate());
            b.velocity.plusEquals(displacement.negate().times(FRAGMENT_SPEED));
            // TODO randomize rotation rates
            actors.add(a);
            actors.add(b);
        }

        delete();
    }

    public void setAsteroidField(game.types.AsteroidField asteroidField) {
        // Don't let it change if it points somewhere
        if(field == null){
            field = asteroidField;
        }
    }

    public void update(){
        super.update();
        //TODO: Write code that detects if it is out of the skybox. (or some other boundry).
    }
}
