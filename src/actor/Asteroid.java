package actor;

import graphics.Model;
import graphics.particles.FireParticle;
import graphics.particles.ParticleSystem;
import math.Quaternion;
import math.Vector3f;

public class Asteroid extends Actor {
    private static final long serialVersionUID = 916554544709785597L;
    private static final String MODEL_NAME = Model.Models.ASTEROID;
    private static final int MAX_GEN = 2;
    private static final int NUM_BREAK=1;
    private static final float DEFAULT_SCALE=10f;
    private static final float HEALTH_MODIFYER=1/2;
    
    private int generation;
    protected int hitPoints;
    protected game.types.AsteroidField field;

    public Asteroid(){
        super();
        this.angularVelocity = new Quaternion(Vector3f.randomPosition(1), 1);
        this.scale = new Vector3f(DEFAULT_SCALE,DEFAULT_SCALE,DEFAULT_SCALE).plus(Vector3f.randomPosition(1));
        this.hitPoints = (int) (scale.magnitude2()*HEALTH_MODIFYER); 
        this.modelName = MODEL_NAME;
        this.generation=1;
    }

    
    public Asteroid(Vector3f position, Vector3f velocity){
        this();
        this.setPosition(position);
        this.setVelocity(velocity);
        this.modelName=MODEL_NAME;
    }
    
    public Asteroid(Asteroid old){
        this(old.getPosition(),Vector3f.randomPosition(0.1f).plus(old.velocity));
        this.generation=old.generation+1;
        this.scale = old.scale.timesEquals(0.5f);
    }

    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);
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
        if(ParticleSystem.isEnabled())
            for(int i = 0; i < 160; i++){
                ParticleSystem.addParticle( new FireParticle(this,Vector3f.randomPosition(1)));
            }
       
        if(this.generation<MAX_GEN){
            for(int i=0;i<NUM_BREAK;i++){
                game.Game.getActors().add(new Asteroid(this));
            }
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
