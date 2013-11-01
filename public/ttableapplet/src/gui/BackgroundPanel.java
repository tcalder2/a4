package gui;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	
	private Image background;
	
	public BackgroundPanel(String imgURL) {
		try	{
			Image img = ImageIO.read(new URL(imgURL));
			background = new ImageIcon(img).getImage();
			Dimension d = new Dimension(background.getWidth(null), background.getHeight(null));
			setPreferredSize(d);
		    setMinimumSize(d);
		    setMaximumSize(d);
		    setSize(d);
		} catch (IOException e) {
			setBackground(Color.WHITE);
			JLabel message = (new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"));
			message.setFont(new Font("Serif", Font.BOLD, 35));
			add(message);
		}
	}
	
	public BackgroundPanel(String imgURL, int width, int height, LayoutManager layout) {
		
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
	    setMinimumSize(d);
	    setMaximumSize(d);
	    setSize(d);
	    try	{
			Image img = ImageIO.read(new URL(imgURL));
			background = new ImageIcon(img).getImage();
		} catch (IOException e) {
			setBackground(Color.WHITE);
			JLabel message = (new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"));
			message.setFont(new Font("Serif", Font.BOLD, 35));
			add(message);
		}
	    setLayout(layout);
	}
	
	public BackgroundPanel(String imgURL, LayoutManager layout) {
		this(imgURL);
		setLayout(layout);
	}
	
	
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
}
