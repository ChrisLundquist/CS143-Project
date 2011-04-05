package game;
import javax.swing.*;

public class BannerPanel extends JPanel {
	private static final long serialVersionUID = 4751777519006353657L;
	
	public BannerPanel() {
		JLabel l = new JLabel("Asteroids");
		add(l);
		GUI.colorize(this);
	}
	
	public BannerPanel(ImageIcon d) {
		add(new JLabel(d));
		GUI.colorize(this);
	}
}

