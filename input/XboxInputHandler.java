package input;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
public class XboxInputHandler {
	String line;
	StringTokenizer tokenizer;
	String button_string, leftStick_string, rightStick_string, leftTrigger_string, rightTrigger_string;
	Vector leftStick, rightStick;
	int button; 
	double rightTrigger, leftTrigger;
	Process p;
	BufferedReader input;
	private boolean playerAlive;
	public XboxInputHandler() throws IOException {
		playerAlive = true;
		
	}
	public void update() {

	}
	public void startInputListener() throws IOException {
		if(isUnix() == true) {
		}

		else if (isWindows() == true) {
			Process p = Runtime.getRuntime().exec("XboxControllerInput.exe");
			input =new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			while ((line = input.readLine()) != null) {
				tokenizer = new StringTokenizer(line, ",");
				button_string = tokenizer.nextToken();
				leftStick_string = tokenizer.nextToken();
				rightStick_string = tokenizer.nextToken();
				leftTrigger_string = tokenizer.nextToken();
				rightTrigger_string = tokenizer.nextToken();
			}
		}

		else if (isMac() == true) {

		}
	}
	public double getRightTriggerState() {
		rightTrigger = round(Double.parseDouble(rightTrigger_string));
		return rightTrigger;
	}
	public double getLeftTriggerState() {
		leftTrigger = round(Double.parseDouble(leftTrigger_string));
		return leftTrigger;
	}
	public Vector getRightStickState() {
		
	}
	public int getButtonState() {
		button = Integer.parseInt(button_string); 
		return button;
	}
	public void stopInputListener() throws IOException {
		input.close();
		clearControllerState();
	}
	public void clearControllerState() {
		line = null;
	}
	public double round(double d) {
		int temp=(int)((d*Math.pow(10,2)));
		return (((double)temp)/Math.pow(10,2));
	}
	public static boolean isWindows(){
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf( "win" ) >= 0); 
	}

	public static boolean isMac(){
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf( "mac" ) >= 0); 
	}

	public static boolean isUnix(){
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);

	}
	public static void main(String []args) throws IOException {
		new XboxInputHandler();
	}

}
