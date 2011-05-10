package input;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import settings.Settings;

public class KeyboardListener implements KeyListener {


    Set<Integer> currentKeys = Collections.synchronizedSet(new HashSet<Integer>());


    public KeyboardListener() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        synchronized(currentKeys){
            currentKeys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        synchronized(currentKeys){
            currentKeys.remove(e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void update() {
        synchronized(currentKeys){
            for(int key:currentKeys){
                //Directions
                if(key==Settings.Profile.Keys.forward){
                    InputRouter.sendAction(InputRouter.Interaction.FORWARD);
                }
                if(key==Settings.Profile.Keys.backward){
                    InputRouter.sendAction(InputRouter.Interaction.BACK);
                }

                //Pitch
                if(key==Settings.Profile.Keys.pitchDown){
                    InputRouter.sendAction(InputRouter.Interaction.PITCH_DOWN);
                }
                if(key==Settings.Profile.Keys.pitchUp){
                    InputRouter.sendAction(InputRouter.Interaction.PITCH_UP);
                }

                //Roll
                if(key==Settings.Profile.Keys.rollLeft){
                    InputRouter.sendAction(InputRouter.Interaction.ROLL_LEFT);
                }
                if(key==Settings.Profile.Keys.rollRight){
                    InputRouter.sendAction(InputRouter.Interaction.ROLL_RIGHT);
                }

                //Yaw
                if(key==Settings.Profile.Keys.yawLeft){
                    InputRouter.sendAction(InputRouter.Interaction.YAW_LEFT);
                }
                if(key==Settings.Profile.Keys.yawRight){
                    InputRouter.sendAction(InputRouter.Interaction.YAW_RIGHT);
                }

                //Shoot
                if(key==Settings.Profile.Keys.shoot){
                    InputRouter.sendAction(InputRouter.Interaction.SHOOT);
                }
                if(key==Settings.Profile.Keys.changeWeapon){
                    InputRouter.sendAction(InputRouter.Interaction.CHANGE_WEAPON);
                }
            }
        }
    }
}