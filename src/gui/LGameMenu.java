package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * The class LGameMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class LGameMenu extends BackgroundPanel {

	/**
	 * Instantiates a LGameMenu instance.
	 *
	 * @param controller	the controller
	 */
	public LGameMenu(Controller controller) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		//Creates a GridBagConstraints instance to control layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(40,150,5,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		//Loads and adds the level game menu title graphic
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/lgame.png"));
			add(new JLabel(new ImageIcon(img)), c);

			//If there is an error loading graphic, displays a text placeholder
		} catch (IOException e) {
			add(new JLabel("Level Game Menu"), c);
		}

		//Update the layout attributes
		c.insets = new Insets(5,75,5,15);
		c.ipady = 15;
		c.weightx = 0.5;
		c.gridwidth = 1;

		//Loop through adding the level buttons with custom button graphics
		for (int i = 0; i < 4; i++) {
			c.gridx = i;
			if (i == 1) {
				c.insets = new Insets(5,15,5,15);
			}
			else if (i == 3) {
				c.insets = new Insets(5,15,5,75);
			}
			for (int j = 1; j <= 3; j++) {
				int level = (i * 3) + j;

				JButton button = new JButton();
				button.addActionListener(new SelectLGame(controller, level));
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				try {
					Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/levels/lvl" + level + "_u.png"));
					button.setIcon(new ImageIcon(img));
				} catch (IOException e) {
					button.setText("Level " + level);  //If the custom button fails to download a text placeholder is added
				}
				c.gridy = j;
				add(button, c);
			}
		}
	}
}

/**
 * The class SelectLGame, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class SelectLGame implements ActionListener {

	/** The controller. */
	private Controller controller;

	/** The level number. */
	private int level;

	/**
	 * Instantiates a ChildProgress instance.
	 * 
	 * @param controller	the controller
	 * @param level			the level number
	 */
	public SelectLGame(Controller controller, int level) {
		super();
		this.controller = controller;
		this.level = level;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		LGame screen = new LGame(controller, level);
		controller.setScreen(screen);
	}
}
