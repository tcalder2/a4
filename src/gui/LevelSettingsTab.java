package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import json.JSONFailureException;
import service.ProgenyService;
import service.UserService;
import ttable.Level;
import ttable.Progeny;

/**
 * The class LevelSettingsTab, a populated JPanel.
 * 
 * @author James Anderson
 * @version 2.0
 */
@SuppressWarnings("serial")
public class LevelSettingsTab extends JPanel {

	/**
	 * Instantiates a LevelSettingsTab instance.
	 * 
	 */
	public LevelSettingsTab(Settings settings) {
		super(new GridBagLayout());
		setOpaque(false);

		//Creates a list of the levels
		Vector<String> levels = new Vector<String>();
		for (int i = 1; i <= 12; i++) {
			levels.add("Level " + i);
		}

		//Create List of child names
		ArrayList<Progeny> progenyList = new ArrayList<Progeny>();
		try {
			progenyList = ProgenyService.getProgenies();
		} catch (JSONFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<String> childNames = new Vector<String>();
		for (int i = 0; i < progenyList.size(); i++) {
			childNames.add(progenyList.get(i).getFirstName());
		}

		//Creates a list of times in 5 second intervals for populating time limit drop down
		Vector<String> t = new Vector<String>();
		for (int i = 5; i <= 120; i += 5) {
			t.add(i + " sec");
		}

		//Creates a list of numbers from 0 to 5 for populating allowable errors drop down
		Vector<String> err = new Vector<String>();
		for (int i = 0; i <= 5; i++) {
			err.add("" + i);
		}

		//Create components
		JLabel sect1 = new JLabel("Child Specific Drill Settings:");
		JLabel childLabel = new JLabel("Select a child to set :");
		JComboBox<String> childSelector = new JComboBox<String>(childNames);
		JLabel timeLabel = new JLabel("Select the time per level:");
		JComboBox<String> time = new JComboBox<String>(t);
		JLabel sect2 = new JLabel("Level Specific Drill Settings:");
		JLabel levelLabel = new JLabel("Select a level to set :");
		JComboBox<String> levelSelector = new JComboBox<String>(levels);
		JLabel errorsLabel = new JLabel("Select the number of errors allowed at this level: ");
		JComboBox<String> errors = new JComboBox<String>(err);
		JLabel testingLabel = new JLabel("Testing mode:");
		JRadioButton testOff = new JRadioButton("Off");
		JRadioButton testOn = new JRadioButton("On");
		JButton update1 = new JButton("Update");
		JButton update2 = new JButton("Update");
		JButton update3 = new JButton("Update");

		//Add action listeners
		

		//Create button group to link testing mode toggle buttons
		ButtonGroup teststate = new ButtonGroup();
		teststate.add(testOff);
		teststate.add(testOn);

		//Add components to view
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,50,0,0);
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 3;
		c.gridy = 0;
		c.gridx = 0;
		add(sect1, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.gridy = 1;
		add(childLabel, c);
		
		c.gridy = 2;
		add(timeLabel, c);

		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(30,50,0,0);
		c.gridy = 3;
		add(sect2, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0,50,0,0);
		c.gridy = 4;
		add(levelLabel, c);
		
		c.gridy = 5;
		add(errorsLabel, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(30,50,0,0);
		c.gridwidth = 1;
		c.gridy = 6;
		add(testingLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30,0,0,0);
		c.gridx = 1;
		add(testOff, c);
		
		c.gridx = 2;
		add(testOn, c);
		
		c.insets = new Insets(0,0,0,50);
		c.gridx = 3;
		c.gridy = 1;
		add(childSelector, c);
		
		c.gridy = 2;
		add(time, c);

		c.gridy = 4;
		add(levelSelector, c);
		
		c.gridy = 5;
		add(errors, c);

		c.insets = new Insets(0,0,0,50);
		c.gridx = 4;
		c.gridy = 2;
		add(update1, c);
		
		c.gridy = 5;
		add(update2, c);
		
		c.insets = new Insets(30,0,0,50);
		c.gridy = 6;
		add(update3, c);
	}

}
