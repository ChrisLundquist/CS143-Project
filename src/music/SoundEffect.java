package music;


// TODO What if someone wanted to play with sound off?
public class SoundEffect {
    static private boolean enabled;

    // TODO make this sexy. This looks like copy pasta.
    private static final String BULLET_SHOT_FILE = "laser_effect.mp3";
    private static final String BULLET_HIT_FILE = "explode.mp3";
    private static final String LARGE_ASTEROID_DEATH_FILE = "large_asteroid_explosion.mp3";
    private static final String SMALL_ASTEROID_DEATH_FILE = "small_asteroid_explosion.mp3";
    private static final String PLAYER_DEATH_FILE = "explode.mp3";
    private static final String POWER_UP_EFFECT_FILE = "powerup_effect.mp3";


    // We only want to make one copy of each sound effect and play the
    // same sound multiple times. So we have them as static members/fields
    private static boolean loaded;
    private static Sound bulletShot;
    private static Sound bulletHit;
    private static Sound largeAsteroidDeath;
    private static Sound smallAsteroidDeath;
    private static Sound playerDeath;
    private static Sound powerUpEffect;

    // Initializes our sound effects
    public static void init(boolean isEnabled) {
        loaded = false;
        if(isEnabled){
            loadSounds();
        } else {
            System.err.println("Sound Disabled");
            enabled = false;
        }
    }

    static private void loadSounds() {
        System.err.println("Initializing Sound");
        enabled = true;
       /* bulletShot = new Sound(BULLET_SHOT_FILE);
        bulletHit = new Sound(BULLET_HIT_FILE);
        largeAsteroidDeath = new Sound(LARGE_ASTEROID_DEATH_FILE);
        smallAsteroidDeath = new Sound(SMALL_ASTEROID_DEATH_FILE);
        playerDeath = new Sound(PLAYER_DEATH_FILE);
        powerUpEffect = new Sound(POWER_UP_EFFECT_FILE);	
        */
        loaded = true;
    }

    static public boolean isEnabled() {
        return enabled;
    }

    static public boolean isEnabled(boolean toggle) {
        enabled = toggle;

        if (enabled && !loaded)
            loadSounds();

        return enabled;
    }

    // Returns our sound for shooting bullets
    static public Sound forBulletShot() {
        return bulletShot;
    }

    // Returns our sound for when bullets hit things
    static public Sound forBulletHit() {
        return bulletHit;
    }

    static public Sound forPowerUp() {
        return powerUpEffect;
    }

    // Returns our sound for when Asteroids blow up / die
    static public Sound forLargeAsteroidDeath(){
        return largeAsteroidDeath;
    }

    // Returns our sound for when Asteroids blow up / die
    static public Sound forSmallAsteroidDeath(){
        return smallAsteroidDeath;
    }
    // Returns our sound for when players blow up /die
    static public Sound forPlayerDeath(){
        return playerDeath;
    }

    static public void main(String[] args) {
        SoundEffect.init(false);
        SoundEffect.init(true);
        SoundEffect.forSmallAsteroidDeath().play();
        SoundEffect.forPlayerDeath().play();
        SoundEffect.forBulletShot().play();
        SoundEffect.forBulletHit().play();
    }
}
