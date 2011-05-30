package input;
import game.Updateable;
import graphics.Hud;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import settings.Settings;

public class KeyboardListener implements KeyListener, Updateable {

    private final int HOLD_TIME = 50;

    boolean[] currentKeys = new boolean[KeyEvent.KEY_LAST];
    boolean[] previousKeys= new boolean[KeyEvent.KEY_LAST];

    private long curTime = 0;

    public KeyboardListener() {
        System.out.println("==="+KeyEvent.KEY_LAST+" Keys Mapped===");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentKeys[e.getKeyCode()]=true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKeys[e.getKeyCode()]=false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void update() {


        if(currentKeys[Settings.Profile.Keys.forward]){
            InputRouter.sendAction(InputRouter.Interaction.FORWARD);
        }
        if(currentKeys[Settings.Profile.Keys.backward]){
            InputRouter.sendAction(InputRouter.Interaction.BACK);
        }
        //Pitch
        if(currentKeys[Settings.Profile.Keys.pitchDown]){
            InputRouter.sendAction(InputRouter.Interaction.PITCH_DOWN);
        }
        if(currentKeys[Settings.Profile.Keys.pitchUp]){
            InputRouter.sendAction(InputRouter.Interaction.PITCH_UP);
        }
        //Roll
        if(currentKeys[Settings.Profile.Keys.rollLeft]){
            InputRouter.sendAction(InputRouter.Interaction.ROLL_LEFT);
        }
        if(currentKeys[Settings.Profile.Keys.rollRight]){
            InputRouter.sendAction(InputRouter.Interaction.ROLL_RIGHT);
        }
        //Yaw
        if(currentKeys[Settings.Profile.Keys.yawLeft]){
            InputRouter.sendAction(InputRouter.Interaction.YAW_LEFT);
        }
        if(currentKeys[Settings.Profile.Keys.yawRight]){
            InputRouter.sendAction(InputRouter.Interaction.YAW_RIGHT);
        }

        //Shoot
        if(currentKeys[Settings.Profile.Keys.shoot]){
            InputRouter.sendAction(InputRouter.Interaction.SHOOT_PRIMARY);
         //   Hud.flashHealthCross();
        }
        if(currentKeys[Settings.Profile.Keys.nextWeapon]){
            if(!previousKeys[Settings.Profile.Keys.nextWeapon]) {
                InputRouter.sendAction(InputRouter.Interaction.NEXT_WEAPON);
            }
        }

        //Energy handling
        if(currentKeys[Settings.Profile.Keys.energyGun]){
            if(!previousKeys[Settings.Profile.Keys.energyGun]){
                if(currentKeys[Settings.Profile.Keys.energyModifier]){
                    InputRouter.sendAction(InputRouter.Interaction.ENERGY_GUN_DOWN);
                }
                else{
                    InputRouter.sendAction(InputRouter.Interaction.ENERGY_GUN_UP);
                }
            }
        }
        if(currentKeys[Settings.Profile.Keys.energyShield]){
            if(!previousKeys[Settings.Profile.Keys.energyShield]){
                if(currentKeys[Settings.Profile.Keys.energyModifier]){
                    InputRouter.sendAction(InputRouter.Interaction.ENERGY_SHIELD_DOWN);
                }
                else{
                    InputRouter.sendAction(InputRouter.Interaction.ENERGY_SHIELD_UP);
                }
            }
        }
        if(currentKeys[Settings.Profile.Keys.energySpeed]){
            if(!previousKeys[Settings.Profile.Keys.energySpeed]){
                if(currentKeys[Settings.Profile.Keys.energyModifier]){
                    InputRouter.sendAction(InputRouter.Interaction.ENERGY_SPEED_DOWN);
                }
                else{
                    InputRouter.sendAction(InputRouter.Interaction.ENERGY_SPEED_UP);
                }
            }
        }

        for(int i=0;i<currentKeys.length;i++){
            previousKeys[i]=currentKeys[i];
        }
    }
}