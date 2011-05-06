package ship;

import java.util.List;

import actor.Actor;

public abstract class Ship extends Actor {
    private static final long serialVersionUID = -7048308038567858490L;

    protected List<Weapon> weapons;
    protected List<Shield> shields; /* If we want to have different shield generators so front and rear shields are different */
    protected int selectedWeapon;
    
    public Ship(){
        super();
        selectedWeapon = 0;
        weapons = new java.util.ArrayList<Weapon>();
        shields = new java.util.ArrayList<Shield>();
    }
    
    public void shoot(){
        weapons.get(selectedWeapon).shoot(this);
    }

}
