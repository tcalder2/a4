package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * The class Settings, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 2.0
 */
@SuppressWarnings("serial")
public class Settings extends BackgroundPanel {

	/** The tab pane component. */
	private JTabbedPane tabPane;
	
	/**
	 * Instantiates a Settings instance.
	 *
	 */
	public Settings() {
		
		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());
		
		//Create a new GridBagLayout instance to control the layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,50,5,50);
		c.gridx = 0;
		c.gridy = 0;

		try	{
			//Load and add the settings title graphic
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/settings.png"));
			add(new JLabel(new ImageIcon(img)), c);
			
		} catch (IOException e) {
			
			//If there is a communication error, add a text placeholder to display
			add(new JLabel("Settings"), c);
		}
		
		//Create a new tab pane
		tabPane = new JTabbedPane();
		
		//Populate the tabs
		tabPane.add("Child Settings", new ChildSettingsTab(this));
		tabPane.add("Game Settings", new GameSettingsTab(this));
		tabPane.add("Security Settings", new SecurityTab(this));
		
		//Add the tab pane to the view
		c.gridy = 1;
		c.insets = new Insets(0,25,0,25);
		add(tabPane, c);
	}
	
	/**
	 * Swaps the content of the specified tab for the new panel specified.
	 * 
	 * @param index			the index of the tab to change
	 * @param newContent	the new content pane to swap in
	 */
	public void changeTabContent(int index, JPanel newContent) {
		tabPane.setComponentAt(index, newContent);
	}
}