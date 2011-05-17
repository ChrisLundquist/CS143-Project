package weapon;

public class Machinegun extends weapon.Weapon {
    
    final double damage = 5.0;
    
    @Override
    public long getShotCoolDown() {
        return 100;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }
}
