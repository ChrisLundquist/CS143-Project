package input;

import game.Game;
import game.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
    }

    public void sendAction(Interaction i) {
        Player player = Game.getPlayer();
        player.input(i);
    }
}
