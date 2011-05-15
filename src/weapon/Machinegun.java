package weapon;


public class Machinegun extends ship.Weapon {
    
    final double damage = 5.0;
    
    protected
    long getShotCoolDown() {
        return 100;
    }
    
    protected double getDamage() {
        return damage;
    }
}
