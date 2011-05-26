package input;
import game.Updateable;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import settings.Settings;

public class KeyboardListener implements KeyListener, Updateable {
    
    private final int HOLD_TIME = 50;

    boolean[] currentKeys = new boolean[KeyEvent.KEY_LAST];
    int[] waitTime = new int[KeyEvent.KEY_LAST];
    long[] prevHit = new long[KeyEvent.KEY_LAST];

    private long curTime = 0;

    public KeyboardListener() {
        System.out.println("==="+KeyEvent.KEY_LAST+" Keys Mapped===");
        
        waitTime[Settings.Profile.Keys.nextWeapon]=HOLD_TIME;
//      waitTime[Settings.Profile.Keys.previousWeapon]=HOLD_TIME;
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
        curTime++;
        
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
        }
        if(currentKeys[Settings.Profile.Keys.nextWeapon]){
            if(curTime>prevHit[Settings.Profile.Keys.nextWeapon]+waitTime[Settings.Profile.Keys.nextWeapon]) {
                InputRouter.sendAction(InputRouter.Interaction.NEXT_WEAPON);
                prevHit[Settings.Profile.Keys.nextWeapon] = curTime;
            }
        }
    }
}