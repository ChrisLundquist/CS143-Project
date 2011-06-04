package input;
/**
 * @author Tim Mikeladze
 * Gives access to XboxControllerInput.exe output
 */

import game.Updateable;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.StringTokenizer;

public class XboxInputListener extends Thread implements Updateable {
    String line;    
    StringTokenizer tokenizer;
    //strings to hold output
    String button_string, leftStick_string, rightStick_string,leftTrigger_string, rightTrigger_string;
    //Point2D holds stick coordinates with Double precision
    Point2D leftStick, rightStick;
    //holds button keycode
    int button;
    //holds trigger state
    double rightTrigger, leftTrigger;
    Process p;
    BufferedReader input;


    /**
     * Sends actions to InputRouter
     */
    public void update(boolean pasued) {
        
        /**
         * Shooting
         */
        if(getRightTriggerState() >= .2) {
            InputRouter.sendAction(InputRouter.Interaction.SHOOT_PRIMARY);
            System.err.println("Shooting Primary");
        }
        //TODO Allow shooting secondary weapon
        if(getLeftTriggerState() >= .2){
            InputRouter.sendAction(InputRouter.Interaction.SHOOT_SECONDARY);
            System.err.println("Shooting Secondary");
        } 
      
        /**
         * Energy
         */
        if(getButtonState() == 1) {
            InputRouter.sendAction(InputRouter.Interaction.ENERGY_GUN_UP);
            System.err.println("Gun Up");
        }
        if(getButtonState() == 3) {
            InputRouter.sendAction(InputRouter.Interaction.ENERGY_GUN_DOWN);
            System.err.println("Gun Down");
        } 
        if(getButtonState() == 2) {
            InputRouter.sendAction(InputRouter.Interaction.ENERGY_SPEED_UP);
            System.err.println("Speed Up");
        }     
        if(getButtonState() == 4) {
            InputRouter.sendAction(InputRouter.Interaction.ENERGY_SPEED_DOWN);
            System.err.println("Speed Down");
        }     
        //TODO add modifier for seperate shields
        if(getButtonState() == 11) {
            InputRouter.sendAction(InputRouter.Interaction.ENERGY_SHIELD_UP);
            System.err.println("Shield Up");
        }
        if(getButtonState() == 13) {
            InputRouter.sendAction(InputRouter.Interaction.ENERGY_SHIELD_DOWN);
            System.err.println("Shield Down");
        } 
        /**
         * Movement
         */
        if(getButtonState() == 5) {
            InputRouter.sendAction(InputRouter.Interaction.ROLL_RIGHT);
            System.err.println("Rolling Right");
        }
        if(getButtonState() == 6) {
            InputRouter.sendAction(InputRouter.Interaction.ROLL_LEFT);
            System.err.println("Rolling Left");
        }

        if(getLeftStickState().getY() > 0 && getLeftStickState().getY() <= 1) {
            InputRouter.sendAction(InputRouter.Interaction.FORWARD);
            System.err.println("Forward");
        }
        if(getLeftStickState().getY() >= -1 && getLeftStickState().getY() < 0) {
            InputRouter.sendAction(InputRouter.Interaction.BACK);
            System.err.println("Backward");
        }
        if(getLeftStickState().getX() > 0 && getLeftStickState().getX() <= 1 ) {
            InputRouter.sendAction(InputRouter.Interaction.YAW_RIGHT);
            System.err.println("Yaw Right");
        }     
        if(getLeftStickState().getX() >= -1 && getLeftStickState().getX() < 0 ) {
            InputRouter.sendAction(InputRouter.Interaction.YAW_LEFT);
            System.err.println("Yaw Left");
        }        
        if(getRightStickState().getY() >= -1 && getRightStickState().getY() < 0) {
            InputRouter.sendAction(InputRouter.Interaction.PITCH_UP);
            System.err.println("Pitch Up");
        }
        if(getRightStickState().getY() > 0 && getRightStickState().getY() <= 1 ) {
            InputRouter.sendAction(InputRouter.Interaction.PITCH_DOWN);
            System.err.println("Pitch Down");
        }

        /**
         * Menu    
         */
        if(getButtonState() == 9) {
            InputRouter.sendAction(InputRouter.Interaction.OPEN_MENU);
            System.err.println("Menu Opened");
        }

        //debug messages
        System.out.println("Button " + getButtonState());
        System.out.println("LeftStick " + getLeftStickState());
        System.out.println("RightStick " + getRightStickState());
        System.out.println("LeftTrigger " + getLeftTriggerState());
        System.out.println("RightTrigger " + getRightTriggerState());
    }

    /**
     * Starts the InputListener
     * @throws IOException
     */
    public void run() {
        
        //TODO check OS

        Process p = null;
        try {
           // p = Runtime.getRuntime().exec("./sshscript.sh");
           p = Runtime.getRuntime().exec("XboxControllerInputConsole.exe");
            
        } catch (IOException e) {
            System.out.println("Couldn't start Xbox Controller Drivers, make sure you have the drivers and the xna runtime installed");
            e.printStackTrace();
        }
        input =new BufferedReader(new InputStreamReader(p.getInputStream()));

        try {
            while ((line = input.readLine()) != null)
            {
                tokenizer = new StringTokenizer(line, ",");
                button_string = tokenizer.nextToken();
                leftStick_string = tokenizer.nextToken();
                rightStick_string = tokenizer.nextToken();
                leftTrigger_string = tokenizer.nextToken();
                rightTrigger_string = tokenizer.nextToken();
                update(true);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*else {
        System.out.println("No Linux or Apple support, blame Microsoft");
    }*/


    /**
     * Gets coordinates of right stick
     * @return rightStick coordinates
     */
    public Point2D getRightStickState() {
        //splits the rightStick_string and assigns values
        String[] xy = SplitReplace(rightStick_string);
        double x = Double.parseDouble(xy[0]);
        double y = Double.parseDouble(xy[1]);
        //rounds to 10^-2 precision
        x = round(x);
        y = round(y);
        //creates a new Point2D
        rightStick = new Point2D.Double(x, y);
        return rightStick;
    }
    
    /**
     * Gets coordinates of left stick
     * @return leftStick coordinates
     */
    public Point2D getLeftStickState() {
        String[] xy = SplitReplace(leftStick_string);
        double x = Double.parseDouble(xy[0]);
        double y = Double.parseDouble(xy[1]);
        x = round(x);
        y = round(y);
        leftStick = new Point2D.Double(x, y);
        return leftStick;
    }
    /**
     * Gets Right Trigger position
     * @return rightTrigger position
     */
    public double getRightTriggerState() {
        //parses and rounds
        rightTrigger = round(Double.parseDouble(rightTrigger_string));
        return rightTrigger;
    }
    /**
     * Gets Left Trigger position
     * @return leftTrigger position
     */
    public double getLeftTriggerState() {
        leftTrigger = round(Double.parseDouble(leftTrigger_string));
        return leftTrigger;
    }
    /**
     * Gets the currently pressed Button
     * @return button
     */
    public int getButtonState() {
        button = Integer.parseInt(button_string);
        return button;
    }
    /**
     * Stops the InputListener and clears the Controller
     * @throws IOException
     */
    public void stopInputListener() throws IOException {
        input.close();
        clearControllerState();
    }
    /**
     * Clears Controller
     */
    public void clearControllerState() {
        line = null;
    }
    /**
     * Splits and Replaces strings
     * @param data
     * @return values
     */
    private String[] SplitReplace(String data) {
        String[] values = data.split("");
        data = data.replace("{", "");
        data = data.replace(":", "");
        data = data.replace("X", "");
        data = data.replace("Y", "");
        data = data.replace(":", "");
        data = data.replace("}", "");

        values = data.split(" ");
        return values;
    }
    /**
     * Rounds double to 10^-2 precision
     * @param d
     * @return
     */
    private double round(double d) {
        int temp=(int)((d*Math.pow(10,2)));
        return (((double)temp)/Math.pow(10,2));
    }
    /**
     * Checks if Windows
     * @return
     */
    public static boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "win" ) >= 0);
    }
    /**
     * Checks if Mac
     * @return
     */
    public static boolean isMac(){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "mac" ) >= 0);
    }
    /**
     * Checks if Unix/Linux
     * @return
     */
    public static boolean isUnix(){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);

    }

}
