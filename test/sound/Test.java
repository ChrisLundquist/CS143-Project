package sound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import sound.Buffer;
import sound.Event;
import sound.Library;
import sound.Manager;

public class Test {

    private static final float MAX_VAL = 8.0f;

    /**
     * @param args
     */
    public static void main(String[] args) {
        Random gen = new Random();
        gen.setSeed(42);
        
        Manager.initialize();
        float[] pos = {0,0,0};
        float[] vel = {0,0,0};
        float[] ori = {1,0,0};
        Manager.setListenerPosition(pos);
        Manager.setListenerVelocity(vel);
        Manager.setListenerOrientation(ori);
        Manager.checkForALErrorsWithMessage("Setup Failed");
        Buffer sound = Library.findOrCreateByName("Gun1.wav");
        if(sound == null)
            System.err.println("NO SOUND GUN1");
        Manager.addEvent(new Event(pos,vel,sound));
        Manager.processEvents();
        char[] c = new char[1];
        while (c[0] != 'q') {
            try {
                float[] soundPos = {gen.nextFloat() * MAX_VAL,gen.nextFloat() * MAX_VAL,gen.nextFloat() * MAX_VAL};
                float[] soundVel = {gen.nextFloat() * MAX_VAL,gen.nextFloat() * MAX_VAL,gen.nextFloat() * MAX_VAL};

                BufferedReader buf =
                    new BufferedReader(new InputStreamReader(System.in));
                System.out.println(
                "Press a key and hit ENTER: \n");
                buf.read(c);
                switch (c[0]) {
                case 'e' :
                    Manager.addEvent(new Event(soundPos,soundVel,sound));
                    break;
                case 'p' :
                    Manager.processEvents();
                    break;
                case 'q' :
                    System.exit(0);
                    break;
                }
            } catch (IOException e) {
                System.exit(1);
            }
        }
    }
}
