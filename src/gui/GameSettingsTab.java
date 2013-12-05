package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import json.JSONFailureException;
import service.LevelService;
import service.ProgenyService;
import ttable.Level;
import ttable.Progeny;
import ttable.User;

/**
 * The class LevelSettingsTab, a populated JPanel.
 * 
 * @author James Anderson
 * @author Taylor Calder
 * @version 3.0
 */
@SuppressWarnings("serial")
public class GameSettingsTab extends JPanel {

	/** The child selection drop down. */
	private JComboBox<String> childSelector;

	/** The drop down for setting the time. */
	private JComboBox<String> time;

	/** The level selection drop down. */
	private JComboBox<String> levelSelector;

	/** The drop down for setting the errors allowed for that level. */
	private JComboBox<String> errors;

	/** The drop down containing the possible themes. */
	private JComboBox<String> themes;

	/**
	 * Instantiates a LevelSettingsTab instance.
	 * 
	 */
	public GameSettingsTab(Settings settings) {
		super(new GridBagLayout());
		setOpaque(false);

		// Creates a list of the levels
		Vector<String> levels = new Vector<String>();
		for (int i = 1; i <= 12; i++) {
			levels.add("Level " + i);
		}

		// Create list children
		ArrayList<Progeny> progenyList = new ArrayList<Progeny>();
		try {
			progenyList = ProgenyService.getProgenies();
		} catch (JSONFailureException e) {
			new GeneralDialogue(e.getMessages(), "JSON Error", 1);
		}

		// Create a list of child names
		Vector<String> childNames = new Vector<String>();
		for (int i = 0; i < progenyList.size(); i++) {
			childNames.add(progenyList.get(i).getFirstName());
		}

		// Creates a list of times in 5 second intervals for populating time
		// limit drop down
		Vector<String> t = new Vector<String>();
		for (int i = 0; i < 24; i++) {
			t.add(((i + 1) * 5) + " sec");
		}

		// Creates a list of numbers from 0 to 5 for populating allowable errors
		// drop down
		Vector<String> err = new Vector<String>();
		for (int i = 0; i <= 5; i++) {
			err.add("" + i);
		}

		//Creates a list of themes
		Vector<String> tList = new Vector<String>();
		tList.add("Default");
		tList.add("Airplanes");
		tList.add("Castles");

		// Create components
		JLabel sect1 = new JLabel("Child Specific Drill Settings: ");
		JLabel childLabel = new JLabel("Select a child to set: ");
		childSelector = new JComboBox<String>(childNames);
		JLabel timeLabel = new JLabel("Select the time per level: ");
		time = new JComboBox<String>(t);
		JLabel sect2 = new JLabel("Level Specific Drill Settings: ");
		JLabel levelLabel = new JLabel("Select a level to set: ");
		levelSelector = new JComboBox<String>(levels);
		JLabel errorsLabel = new JLabel("Select number of errors allowed: ");
		errors = new JComboBox<String>(err);
		JLabel testingLabel = new JLabel("Testing mode: ");
		JRadioButton testOff = new JRadioButton(" Off  ");
		JRadioButton testOn = new JRadioButton(" On");
		JButton update1 = new JButton("Update");
		JButton update2 = new JButton("Update");
		JLabel themeLabel = new JLabel("Select a Theme: ");
		themes = new JComboBox<String>(tList);

		// Create button group to link testing mode toggle buttons
		ButtonGroup testState = new ButtonGroup();
		testState.add(testOff);
		testState.add(testOn);

		// Set initial selections to current values
		if (Controller.getTestMode()) {
			testOn.setSelected(true);
		} else {
			testOff.setSelected(true);
		}
		
			setTimeSelection();
			setErrorsSelection();

		themes.setSelectedIndex(User.drillSkin);

		// Add action listeners
		childSelector.addActionListener(new SelectChild(this));
		levelSelector.addActionListener(new SelectLevel(this));
		update1.addActionListener(new PressUpdate1(childSelector, time));
		update2.addActionListener(new PressUpdate2(levelSelector, errors));
		ChangeTestState testListener = new ChangeTestState();
		testOn.addActionListener(testListener);
		testOff.addActionListener(testListener);
		themes.addActionListener(new UpdateTheme(this));

		// Add components to view
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 50, 0, 0);
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
		c.insets = new Insets(30, 50, 0, 0);
		c.gridy = 3;
		add(sect2, c);

		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0, 50, 0, 0);
		c.gridy = 4;
		add(levelLabel, c);

		c.gridy = 5;
		add(errorsLabel, c);

		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(30, 50, 0, 0);
		c.gridwidth = 1;
		c.gridy = 6;
		add(testingLabel, c);		

		c.gridx = 3;
		add(themeLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 0, 0, 0);
		c.gridx = 1;
		add(testOff, c);

		c.gridx = 2;
		add(testOn, c);

		c.gridx = 4;
		add(themes, c);

		c.insets = new Insets(0, 0, 0, 50);
		c.gridx = 3;
		c.gridy = 1;
		add(childSelector, c);

		c.gridy = 2;
		add(time, c);

		c.gridy = 4;
		add(levelSelector, c);

		c.gridy = 5;
		add(errors, c);

		c.insets = new Insets(0, 0, 0, 50);
		c.gridx = 4;
		c.gridy = 2;
		add(update1, c);

		c.gridy = 5;
		add(update2, c);
	}

	/**
	 * Select the time per level
	 */
	void setTimeSelection() {
		Progeny child;
		try {

			if (childSelector.getSelectedIndex() > -1) {
				child = ProgenyService.getProgenies().get(childSelector.getSelectedIndex());
				time.setSelectedItem(child.getTimeAllowed() + " sec");
			}

		} catch (JSONFailureException e1) {
			new GeneralDialogue(e1.getMessages(), "JSON Error", 1);
		} catch (Exception e) {
			new GeneralDialogue("Oops! Something funky is going on:S", "Unknown Error", 1);
		}
	}

	/**
	 * Select the number of errors allowed
	 */
	void setErrorsSelection() {
		Level level;
		try {
			ArrayList<Level> levels = LevelService.getLevels();
			if (levels.size() > 0) {	
				level = levels.get(levelSelector.getSelectedIndex());
				errors.setSelectedItem("" + level.getMistakesAllowed());
			}
			else {
				new GeneralDialogue("Ooops! It appears we are having trouble communicating:(",
						"Communication Error", 1);
			}
		} catch (JSONFailureException e1) {
			new GeneralDialogue(e1.getMessages(), "JSON Error", 1);
		} catch (Exception e) {
			new GeneralDialogue("Oops! Something funky is going on:S", "Unknown Error", 1);
		}
	}

	/**
	 * Select the theme
	 */
	void updateTheme() {
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

/**
 * The class SelectChild, and action listener responsible for setting the time
 * allowed per level drop down to the current setting for the selected child.
 * 
 * @author James Anderson
 * @version 1.0
 */
class SelectChild implements ActionListener {

	/** The level settings tab of the settings pane. */
	private GameSettingsTab levelSettings;

	/**
	 * Constructs an action listener responsible for setting the time drop down
	 * to the current setting for the selected child each time the child
	 * selection changes.
	 * 
	 * @param childSelector
	 *            the child selection drop down.
	 * @param time
	 *            the drop down for selecting the time the child is allowed per
	 *            level.
	 */
	public SelectChild(GameSettingsTab levelSettings) {
		super();
		this.levelSettings = levelSettings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		levelSettings.setTimeSelection();
	}
}

/**
 * The class SelectLevel, an action listener responsible for setting the errors
 * drop down to the current setting for the number of errors allowed per level
 * 
 * @author James Anderson
 * @version 1.0
 */
class SelectLevel implements ActionListener {

	/** The level settings tab of the settings pane. */
	private GameSettingsTab levelSettings;

	/**
	 * Constructs an action listener responsible for setting the errors drop
	 * down to the current setting on change of the level selected.
	 * 
	 * @param levelSelector
	 *            the level selection drop down.
	 * @param errors
	 *            the drop down for selecting the number of errors allowed per
	 *            level.
	 */
	public SelectLevel(GameSettingsTab levelSettings) {
		super();
		this.levelSettings = levelSettings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		levelSettings.setErrorsSelection();
	}
}

/**
 * The PressUpdate1 class, an action listener responsible for testing for a
 * change in time the selected child is allowed per level, if there is a change
 * it updates the value.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressUpdate1 implements ActionListener {

	/** The child selection drop down. */
	private JComboBox<String> childSelector;

	/** The drop down for selecting the time allowed for level completion. */
	private JComboBox<String> time;

	/**
	 * Constructs an action listener for the time the child is allowed per
	 * level's update button.
	 * 
	 * @param childSelector
	 *            the child selection drop down.
	 * @param time
	 *            the drop down for selecting the time allowed for level
	 *            completion
	 */
	public PressUpdate1(JComboBox<String> childSelector, JComboBox<String> time) {
		super();
		this.childSelector = childSelector;
		this.time = time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Progeny child = ProgenyService.getProgenies().get(
					childSelector.getSelectedIndex());
			int newTime = (time.getSelectedIndex() + 1) * 5;
			if (child.getTimeAllowed() != newTime) {
				ProgenyService.setTimeAllowed(child, newTime);
			}

			Controller.refreshCurrentProgeny();

			// childSelector.setSelectedIndex(0);
			new GeneralDialogue("Time allowed was updated successfully.",
					"Success!", 3);
		} catch (JSONFailureException e1) {
			new GeneralDialogue(e1.getMessages(), "JSON Error", 1);
		}
	}

}

/**
 * The class PressUpdate2, an action listener responsible for testing for change
 * in errors allowed for the specified level and updating the value if it has
 * changed.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressUpdate2 implements ActionListener {

	/** The level selection drop down. */
	private JComboBox<String> levelSelector;

	/** The drop down for the number of errors allowed per level. */
	private JComboBox<String> errors;

	/**
	 * Constructs an action listener for the errors per level setting's update
	 * button.
	 * 
	 * @param levelSelector
	 *            the level selection drop down.
	 * @param errors
	 *            the drop down for selecting the number of errors allowed per
	 *            level.
	 */
	public PressUpdate2(JComboBox<String> levelSelector,
			JComboBox<String> errors) {
		super();
		this.levelSelector = levelSelector;
		this.errors = errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Level level = LevelService.getLevels().get(
					levelSelector.getSelectedIndex());
			int newMistakesAllowed = errors.getSelectedIndex();
			if (level.getMistakesAllowed() != newMistakesAllowed) {
				LevelService.changeMistakesAllowed(level, newMistakesAllowed);
			}
			// levelSelector.setSelectedIndex(0);
			new GeneralDialogue("Mistakes allowed was updated successfully.",
					"Success!", 3);
		} catch (JSONFailureException e1) {
			new GeneralDialogue(e1.getMessages(), "JSON Error", 1);
		}
	}
}

/**
 * The class ChangeTestState, an action listener that detects toggling of test
 * mode setting buttons and causes a toggle in the test mode boolean.
 * 
 * @author James Anderson
 * @version 1.0
 */
class ChangeTestState implements ActionListener {

	/**
	 * Constructs default action listener for the toggling of the test mode
	 * buttons.
	 * 
	 */
	public ChangeTestState() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Controller.toggleTestMode();
	}
}

class UpdateTheme implements ActionListener {

	/** The related security tab instance. */
	private GameSettingsTab levelSettings;

	/**
	 * The action listener for the update button on the security tab.
	 * 
	 * @param security			the related security tab instance.
	 */
	public UpdateTheme (GameSettingsTab levelSettings) {
		this.levelSettings = levelSettings;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		levelSettings.updateTheme();
	}
}

