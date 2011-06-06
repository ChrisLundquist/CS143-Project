package actor.ship;

import actor.ship.projectile.Projectile;
import actor.ship.weapon.Weapon;

public abstract class PlayerShip extends Ship {
    private static final long serialVersionUID = 1L;
    private static final float MAX_SPEED = 0.8f;

    protected abstract float getRollRate();
    protected abstract float getPitchRate();
    protected abstract float getYawRate();

    protected abstract float getAdditiveSpeed();
    protected abstract float getNegativeSpeed();

    protected abstract float getAngularDampening();
    protected abstract String getLocalModelName();
    public final Energy energy;
    protected float speed;

    public PlayerShip(){
        super();
        energy = new Energy(this);
        modelName = this.getLocalModelName();
        speed = 0;
    }

    public void forwardThrust() {
        speed += getAdditiveSpeed();
        speed = Math.min(speed, MAX_SPEED);
    }
    public void reverseThrust() {
        speed -= getNegativeSpeed();
        speed = Math.max(speed, -MAX_SPEED / 4.0f);
    }
    public void pitchUp(){
        changePitch(getPitchRate());
    }
    public void pitchDown(){
        changePitch(-getPitchRate());
    }
    public void yawLeft() {
        changeYaw(getYawRate());
    }
    public void yawRight() {
        changeYaw(-getYawRate());
    }
    public void rollLeft() {
        changeRoll(getRollRate());
    }
    public void rollRight() {
        changeRoll(-getRollRate());
    }

    public void nextWeapon() {
        setWeapon((selectedWeapon + 1) % weapons.size());
    }

    public void previousWeapon() {
        setWeapon((selectedWeapon - 1) % weapons.size());
    }

    public void setWeapon(int weaponNumber){
        selectedWeapon = weaponNumber % weapons.size();
        System.out.println("Switching to " + getWeapon());
    }

    public Weapon<? extends Projectile> getWeapon() {
        return weapons.get(selectedWeapon);
    }

    @Override
    public void update(){
        velocity = getDirection().times(speed);
        super.update();
        dampenAngularVelocity(getAngularDampening());
    }

    public void die(){
        if (actors != null)
            actors.remove(this);
    }
}
