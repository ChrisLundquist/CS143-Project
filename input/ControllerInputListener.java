package input;

import java.io.*;
import java.util.StringTokenizer;
public class ControllerInputListener {

	public ControllerInputListener() throws IOException {
			String line;
			StringTokenizer tokenizer;
			
			String button, leftStick, rightStick, leftTrigger, rightTrigger;
			Process p = Runtime.getRuntime().exec("XboxControllerInput.exe");
			BufferedReader input =new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			while ((line = input.readLine()) != null) {
				tokenizer= new StringTokenizer(line, ",");
				button = tokenizer.nextToken();
				leftStick = tokenizer.nextToken();
				rightStick = tokenizer.nextToken();
				leftTrigger = tokenizer.nextToken();
				rightTrigger = tokenizer.nextToken();
			}
			input.close();
	}
	public static void main(String []args) throws IOException {
		new ControllerInputListener();
	}
	
}
