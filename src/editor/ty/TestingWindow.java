package editor.ty;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import math.Vector3;
import actor.Actor;
import actor.PlayerShip;


public class TestingWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestingWindow window = new TestingWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestingWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setLayout(null);
		
		GridPacker grids = new GridPacker();
		frame.add(grids);
		
        Map<Actor,Color> actormap = new HashMap<Actor,Color>();
        
        Actor ship = new PlayerShip();
        ship.setPosition(new Vector3(50,20,-20));
        ship.setSize(10);
        actormap.put(ship,Color.RED);
        
        Actor ship2 = new PlayerShip();
        ship2.setPosition(new Vector3(10,-5,20));
        ship2.setSize(10);
        actormap.put(ship2,Color.BLUE);
        
        grids.updateActors(actormap);
		
		frame.setBounds(300, 300, 900, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
