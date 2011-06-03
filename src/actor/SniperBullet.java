package actor;

public class SniperBullet extends Projectile {
    private static final long serialVersionUID = 7453924541312180985L;

    public SniperBullet(Actor actor) {
        super(actor);
        damage = 40;
        velocity.timesEquals(5);
    }
    
    public static long getShotCoolDown() {
        return DEFAULT_DELAY;
    }
}
