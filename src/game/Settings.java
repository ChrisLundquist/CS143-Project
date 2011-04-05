package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Settings extends JFrame {
	private static final long serialVersionUID = 4568652071397810155L;

	JCheckBox sound;
	JCheckBox particles;
	JCheckBox asteroidsCollide;
	JButton close;
	JButton apply;

	public Settings() {
		setLayout(new BorderLayout());
		setTitle("Settings");

		add(settingsPanel(), BorderLayout.NORTH);

		add(buttonPanel(), BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	private JPanel settingsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));

		sound = new JCheckBox("Enabled Sound");
		//sound.setSelected(SoundEffect.isEnabled());
		panel.add(sound);

		particles = new JCheckBox("Enabled Particle Effects");
		//particles.setSelected(ParticleSystem.isEnabled());
		panel.add(particles);

		asteroidsCollide = new JCheckBox("Enabled Asteroid Collisions");
		//asteroidsCollide.setSelected(Asteroid.isAsteroidCollisionEnabled());
		panel.add(asteroidsCollide);		

		GUI.colorize(panel);
		return panel;
	}

	private JPanel buttonPanel() {
		JPanel panel = new JPanel();

		apply = new JButton("Apply");
		apply.addActionListener(new ButtonHandler(this));
		panel.add(apply);

		close = new JButton("Close");
		close.addActionListener(new ButtonHandler(this));
		panel.add(close);

		GUI.colorize(panel);
		return panel;
	}

	private class ButtonHandler implements ActionListener {
		JFrame window;

		public ButtonHandler(JFrame w) {
			window = w;
		}

		public void actionPerformed(ActionEvent e) {
			 if (e.getSource() == apply) {
				 // TODO
			     //SoundEffect.isEnabled(sound.isSelected());
				 //ParticleSystem.isEnabled(particles.isSelected());
				 //Asteroid.isAsteroidCollisionEnabled(asteroidsCollide.isSelected());

				 window.dispose();
			 } else if (e.getSource() == close) {
				 window.dispose();
			 }
		}

	}
}