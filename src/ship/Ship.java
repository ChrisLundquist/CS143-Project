package ship;

import graphics.Model.Model_Enum;

import java.util.List;

import ship.shield.Shield;
import ship.weapon.Weapon;
import actor.Actor;

public abstract class Ship extends Actor {
    private static final long serialVersionUID = -7048308038567858490L;

    protected List<Weapon> weapons;
    protected List<Shield> shields; /* If we want to have different shield generators so front and rear shields are different */
    protected int selectedWeapon;
   
    public Ship(Model_Enum model){
        super(model);
        selectedWeapon = 0;
        weapons = new java.util.ArrayList<Weapon>();
        shields = new java.util.ArrayList<Shield>();
        
    }
    
    public void shoot(){
        weapons.get(selectedWeapon).shoot(this);
    }
    
    @Override
    public void handleCollision(Actor other) {
        System.err.println("Collision Detected Between " + other + " and " + this);

        if(other instanceof actor.Projectile) {
            //shield testing code
            actor.Projectile projectile = (actor.Projectile) other;
            shields.get(0).takeDamage(projectile.getDamage());
            if(shields.get(0).getStatus() == false){
                //delete();
            }
            System.out.println(shields.get(0).getStrength());
        }
        else if (other instanceof Ship) {

        }
    }
}
