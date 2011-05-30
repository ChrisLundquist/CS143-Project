package input;

import game.Game;
import game.Player;

public class InputRouter{
    
    public enum Interaction {
        SHOOT_PRIMARY,
        SHOOT_SECONDARY,
        FORWARD,
        BACK,
        PITCH_UP,
        PITCH_DOWN,
        YAW_LEFT,
        YAW_RIGHT,
        ROLL_LEFT,
        ROLL_RIGHT,
        CHANGE_WEAPON,
        NEXT_WEAPON,
        PREVIOUS_WEAPON,
        ENERGY_GUN_UP,
        ENERGY_GUN_DOWN,
        ENERGY_SHIELD_UP,
        ENERGY_SHIELD_DOWN,
        ENERGY_SPEED_UP,
        ENERGY_SPEED_DOWN,
        OPEN_MENU,
        MENU_UP,
        MENU_DOWN,
        MENU_SELECT,
    }

    public static void sendAction(Interaction i) {
        Player player = Game.getPlayer();
        player.input(i);
    }
}
