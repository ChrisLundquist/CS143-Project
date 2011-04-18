package editor.ty;

import java.awt.EventQueue;
import java.util.ArrayList;

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
		
        
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        Actor ship = new PlayerShip();
        ship.setPosition(new Vector3(50,20,-20));
        ship.setSize(10);
        actors.add(ship);
        
        grids.updateActors(actors);
		
		frame.setBounds(300, 300, 900, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
