package input;

import game.Game;
import game.Player;

public class InputRouter{

    public enum Interaction {
        SHOOT,
        FORWARD,
        BACK,
        PITCH_UP,
        PITCH_DOWN,
        YAW_LEFT,
        YAW_RIGHT,
        ROLL_LEFT,
        ROLL_RIGHT,
        CHANGE_WEAPON, NEXT_WEAPON, PREVIOUS_WEAPON,
    }

    public static void sendAction(Interaction i) {
        Player player = Game.getPlayer();
        player.input(i);
    }
}
