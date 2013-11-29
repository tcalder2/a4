package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import service.UserService;
import ttable.User;
import json.JSONFailureException;

/**
 * The class ThemeTab, a populated BackgroundPanel.
 * 
 * 
 * @author James Anderson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ThemeTab extends JPanel {

	/** The settings screen. */
	private Settings settings;

	/** The drop down containing the possible themes. */
	private JComboBox<String> themes;


	/**
	 * Constructor requiring the settings pane that contains this tab be passed, or null if it
	 * is in its own window.
	 * 
	 * @param settings		the settings pane that contains this tab, or null if it is in its
	 * 						own window.
	 */
	public ThemeTab(Settings settings) {

		//Call the super constructor with a GridBagLayout
		super(new GridBagLayout());

		//Store a link to the settings pane that contains this tab, or null if it is in its own window
		this.settings = settings;

		Vector<String> tList = new Vector<String>();
		tList.add("Default");
		tList.add("Airplanes");
		tList.add("Castles");
		
		themes = new JComboBox<String>(tList);
		themes.addActionListener(new UpdateTheme(this));
		
		if (User.drillSkin == 1) {
			themes.setSelectedItem("Airplanes");
		}
		else if (User.drillSkin == 2) {
			themes.setSelectedItem("Castles");
		}
		
		//Make the panel transparent
		setOpaque(false);

		//Create the components
		JLabel themeLabel = new JLabel("Select a Theme: ");

		//Add the components to the view
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(25,0,0,0);
		c.gridx = 0;
		c.gridy = 0;
		add(themeLabel, c);

		c.gridy = 1;
		add(themes, c);
	}
	
	void update() {
		String selectedTheme = (String) themes.getSelectedItem();
		if (selectedTheme.equals("Default")) {
			User.updateTheme(0);
		}
		else if (selectedTheme.equals("Airplanes")) {
			User.updateTheme(1);
		}
		else if (selectedTheme.equals("Castles")) {
			User.updateTheme(2);
		}
	}

}

class UpdateTheme implements ActionListener {

	/** The related security tab instance. */
	private ThemeTab theme;

	/**
	 * The action listener for the update button on the security tab.
	 * 
	 * @param security			the related security tab instance.
	 */
	public UpdateTheme (ThemeTab theme) {
		this.theme = theme;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		theme.update();
	}
}
