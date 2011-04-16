package editor.ty;

import java.awt.BorderLayout;
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
		
		Grid2d xyGrid =  new Grid2d(Grid2d.Context.XY);
		xyGrid.setBounds(0,0,300,300);
		frame.add(xyGrid,BorderLayout.CENTER);
		
		Grid2d xzGrid =  new Grid2d(Grid2d.Context.XZ);
        xzGrid.setBounds(300,0,300,300);
        frame.add(xzGrid,BorderLayout.CENTER);
        
        Grid2d yzGrid =  new Grid2d(Grid2d.Context.YZ);
        yzGrid.setBounds(600,0,300,300);
        frame.add(yzGrid,BorderLayout.CENTER);
        
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        Actor ship = new PlayerShip();
        ship.setPosition(new Vector3(40,0, -20));
        ship.setSize(50);
        actors.add(ship);
        
        xyGrid.updateActors(actors);
        xzGrid.updateActors(actors);
        yzGrid.updateActors(actors);
        
        
		frame.setBounds(300, 300, 900, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
