package weapon;

public class TwinLinkedMachinegun extends ship.TwinLinkedWeapon {
    final double damage = 10.0;
    
    @Override
    public long getShotCoolDown() {
        return 100;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }
}
