<<<<<<< HEAD
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

import ttable.LevelProgeny;
import ttable.User;

/**
 * The class LGameMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class LGameMenu extends BackgroundPanel {

	private boolean allUnlocked;
	
	/**
	 * Instantiates a LGameMenu instance.
	 *
	 */
	public LGameMenu() {

		//Calls superclass constructor to create the background panel
		super(User.getBackground(), new GridBagLayout());

		//Creates a GridBagConstraints instance to control layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(40,150,5,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		// If the completion time for the last drill level is > -1, then it means you have completed it
		// And can access all level games

		try {
			allUnlocked = !(Controller.getCurrentProgeny().getLevelProgenys().get(11).getCompletionTime() == 0);
		}
		catch (NullPointerException e) {
			;
		}
		
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

		//Get the current level of the child
		int level = Controller.getCurrentProgeny().getLevel();
				
		//Loop through adding the level buttons with custom button graphics
		int position = 0;
		int[] order = {4,5,1,3,2,6,11,10,7,12,9,8};
		for (int i = 0; i < 4; i++) {
			c.gridx = i;
			if (i == 1) {
				c.insets = new Insets(5,15,5,15);
			}
			else if (i == 3) {
				c.insets = new Insets(5,15,5,75);
			}
			for (int j = 1; j <= 3; j++) {
				JButton button = new JButton();
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				
				//Default icon is locked, with no action listener
				String lockStatus = "_l";
				
				//If the level being added should be unlocked, change icon to unlocked and add action listener
				if (order[position] < level || allUnlocked) {
					button.addActionListener(new SelectLGame(order[position]));
					lockStatus = "_u";
				}
				
				try {
					Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/levels/lvl" + order[position] + lockStatus + ".png"));
					button.setIcon(new ImageIcon(img));
				} catch (IOException e) {
					button.setText("Level " + order[position]);  //If the custom button fails to download a text placeholder is added
				}
				c.gridy = j;
				add(button, c);
				position++;
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

	/** The level number. */
	private int level;

	/**
	 * Instantiates a ChildProgress instance.
	 * 
	 * @param level			the level number
	 */
	public SelectLGame(int level) {
		super();
		this.level = level;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Instructions inst;
		try {
		inst = new Instructions(Controller.getCurrentProgeny().getLevels().get(level - 1));
		}
		catch (Exception e2) {
			LevelProgeny prog = new LevelProgeny();
			prog.setLevelNumber(level);
			inst = new Instructions(prog);
		}
		Controller.setScreen(inst);
	}
}
=======
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

import ttable.LevelProgeny;
import ttable.User;

/**
 * The class LGameMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class LGameMenu extends BackgroundPanel {

	private boolean allUnlocked;
	
	/**
	 * Instantiates a LGameMenu instance.
	 *
	 */
	public LGameMenu() {

		//Calls superclass constructor to create the background panel
		super(User.getBackgroundCode(), new GridBagLayout());

		//Creates a GridBagConstraints instance to control layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(40,150,5,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		// If the completion time for the last drill level is > -1, then it means you have completed it
		// And can access all level games

		try {
			allUnlocked = !(Controller.getCurrentProgeny().getLevelProgenys().get(11).getCompletionTime() == 0);
		}
		catch (NullPointerException e) {
			//NULL BODY
		}
		
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

		//Get the current level of the child
		int level = Controller.getCurrentProgeny().getLevel();
				
		//Loop through adding the level buttons with custom button graphics
		int position = 0;
		int[] order = {4,5,1,3,2,6,11,10,7,12,9,8};
		for (int i = 0; i < 4; i++) {
			c.gridx = i;
			if (i == 1) {
				c.insets = new Insets(5,15,5,15);
			}
			else if (i == 3) {
				c.insets = new Insets(5,15,5,75);
			}
			for (int j = 1; j <= 3; j++) {
				JButton button = new JButton();
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				
				//Default icon is locked, with no action listener
				String lockStatus = "_l";
				
				//If the level being added should be unlocked, change icon to unlocked and add action listener
				if (order[position] < level || allUnlocked) {
					button.addActionListener(new SelectLGame(order[position]));
					lockStatus = "_u";
				}
				
				try {
					Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/levels/lvl" + order[position] + lockStatus + ".png"));
					button.setIcon(new ImageIcon(img));
				} catch (IOException e) {
					button.setText("Level " + order[position]);  //If the custom button fails to download a text placeholder is added
				}
				c.gridy = j;
				add(button, c);
				position++;
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

	/** The level number. */
	private int level;

	/**
	 * Instantiates a ChildProgress instance.
	 * 
	 * @param level			the level number
	 */
	public SelectLGame(int level) {
		super();
		this.level = level;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Instructions inst;
		try {
		inst = new Instructions(Controller.getCurrentProgeny().getLevels().get(level - 1));
		}
		catch (Exception e2) {
			LevelProgeny prog = new LevelProgeny();
			prog.setLevelNumber(level);
			inst = new Instructions(prog);
		}
		Controller.setScreen(inst);
	}
}
>>>>>>> branch 'master' of ssh://taylor@jbaron6.cs2212.ca/git/ttable
