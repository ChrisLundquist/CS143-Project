package weapon;

public class TwinLinkedMachinegun extends ship.TwinLinkedWeapon {
    final double damage = 10.0;
    
    protected
    long getShotCoolDown() {
        return 100;
    }
    
    protected double getDamage() {
        return damage;
    }
}
