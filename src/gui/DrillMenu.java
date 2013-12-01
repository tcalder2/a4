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
 * The class DrillMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class DrillMenu extends BackgroundPanel {	
	
	/**
	 * Instantiates a new drill menu.
	 *
	 */
	public DrillMenu() {

		//Calls superclass constructor to create the background panel
		
		super(User.backgroundMenuCode, new GridBagLayout());

		//Create a GridBagConstraints instance to control layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(40,150,5,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		//Download and display the drill menu title graphic 
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/drillmenu.png"));
			add(new JLabel(new ImageIcon(img)), c);

			//If download fails display text placeholder instead
		} catch (IOException e) {
			add(new JLabel("Drill Menu"), c);
		}

		//Update layout settings
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
				if (order[position] <= level) {
					button.addActionListener(new StartDrill(order[position]));
					lockStatus = "_u";
				}
				
				try {
					if (User.drillSkin == 1) {
						Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/themes/theme_1/levels/lvl" + order[position] + lockStatus + ".png"));
						button.setIcon(new ImageIcon(img));
					}
					else if (User.drillSkin == 2) {
						Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/themes/theme_2/levels/lvl" + order[position] + lockStatus + ".png"));						
						button.setIcon(new ImageIcon(img));
					}
					else {
						Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/levels/lvl" + order[position] + lockStatus + ".png"));
						button.setIcon(new ImageIcon(img));
					}
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
 * The class StartDrill, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class StartDrill implements ActionListener {

	/** The level number. */
	private int levelNum;

	/**
	 * Instantiates a StartDrill instance.
	 * 
	 * @param levelNum		the level number
	 */
	public StartDrill(int levelNum) {
		super();
		this.levelNum = levelNum;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Drill screen;
		try {
		screen = new Drill(Controller.getCurrentProgeny().getLevels().get(levelNum - 1));
		}
		catch (Exception e2) {
			LevelProgeny prog = new LevelProgeny();
			prog.setLevelNumber(levelNum);
			screen = new Drill(prog);
		}
		Controller.setScreen(screen);
	}
}
