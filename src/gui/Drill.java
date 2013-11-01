package gui;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Drill extends BackgroundPanel {
	
	private int level;
	private int lives;
	private int correct;
	private int incorrect;
	
	public Drill(Controller controller, int level) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		this.level = level;
		lives = 5;
		correct = 0;
		incorrect = 0;
		
		JLabel lives = new JLabel();
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill/heart.png"));
			lives.setIcon(new ImageIcon(img));
			lives.setText(" x " + lives);
		} catch (IOException e) {
			lives.setText(lives + " lives");
		}
		
	}
}
