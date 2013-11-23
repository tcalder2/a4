package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;

import json.JSONFailureException;
import service.GameService;
import service.ProgenyService;
import ttable.Progeny;

/**
 * The Class ChildSettingsTab.
 * 
 * @author James Anderson
 * @version 2.0
 */
@SuppressWarnings("serial")
public class ChildSettingsTab extends JPanel {

	/** The settings pane. */
	private Settings settings;

	/** The list of currently added progeny. */
	private ArrayList<Progeny> progenyList;

	/** The name input field for adding a new child. */
	private JTextField nameInput;

	/** The birth-year drop down for adding new children. */
	private JComboBox<String> year;

	/** The birth-month drop down for adding new children. */
	private JComboBox<String> month;

	/** The birth-day drop down for adding new children. */
	private JComboBox<String> day;

	/** The children list drop down. */
	private JComboBox<String> childSelect;

	/** The birth-year drop down for updating a child. */
	private JComboBox<String> year2;

	/** The birth-month drop down for updating a child. */
	private JComboBox<String> month2;

	/** The birth-day drop down for updating a child. */
	private JComboBox<String> day2;

	/** The level drop down for updating a child. */
	private JComboBox<String> level;

	/** The child details for the table. */
	private Vector<Vector<String>> tableData;

	/** The table model. */
	private DefaultTableModel tableModel;

	/**
	 * Instantiates an ChildSettingsTab instance.
	 * 
	 * @param Controller	the Controller
	 * @param settings		the settings pane
	 */
	public ChildSettingsTab(Settings settings) {

		//Instantiate the JPanel with a GridBagLayout
		super(new GridBagLayout());
		this.settings = settings;

		//Makes panel transparent
		setOpaque(false);

		try {

			//Create instance of grid bag constraints to control layout
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(25,0,0,3);
			c.gridx = 4;
			c.gridy = 0;
			c.gridwidth = 1;

			//Create vector containing column headers for the child info table
			Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"Name"," ","Date of Birth"," ","Age","Level"}));

			//Create vector structure containing table data
			progenyList = ProgenyService.getProgenies();
			tableData = new Vector<Vector<String>>();
			for (int i = 0; i < progenyList.size(); i++) {
				Vector<String> v = new Vector<String>();
				Progeny p = progenyList.get(i);
				v.add(p.getFirstName());

				Date birthday = p.getBirthDate();
				DateFormat format = new SimpleDateFormat("d");
				v.add(format.format(birthday));

				format = new SimpleDateFormat("MMMM");
				v.add(format.format(birthday));

				format = new SimpleDateFormat("yyyy");
				v.add(format.format(birthday));

				v.add("" + ProgenyService.getAge(p.getBirthDate()));
				v.add("" + (p.getLevelNumber()));
				tableData.add(v);
			}

			//Create and populate the table
			tableModel = new DefaultTableModel(tableData, columnNames);
			JTable table = new JTable(tableModel) {
				public boolean isCellEditable(int rowIndex, int colIndex) {
					return false;
				}
			};

			//Set view attributes of table
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			renderer.setOpaque(false);
			TableColumn col = null;
			for (int i = 0; i < table.getColumnCount(); i++) {
				col = table.getColumnModel().getColumn(i);
				col.setCellRenderer(renderer);
			}
			table.setAutoCreateRowSorter(true);
			table.setOpaque(false);
			table.setRowHeight(24);
			table.setShowGrid(false);
			table.getTableHeader().setDefaultRenderer(renderer);
			table.setFont(Controller.getFont().deriveFont(Font.BOLD, 18));
			table.getTableHeader().setFont(Controller.getFont().deriveFont(Font.BOLD, 18));

			//Put the table into a scroll pane to allow scrolling when the table is too large to fit
			JScrollPane scroll = new JScrollPane(table);
			scroll.setOpaque(false);
			scroll.getViewport().setOpaque(false);

			//CREATE DATA TO POPULATE JCOMBOBOXES

			//List of potential birth years
			Vector<String> y = new Vector<String>();
			for (int i = (new GregorianCalendar().get(Calendar.YEAR)); i > 1979; i--) {
				y.add("" + i);
			}

			//Create List of child names
			Vector<String> childNames = new Vector<String>();
			for (int i = 0; i < progenyList.size(); i++) {
				childNames.add(progenyList.get(i).getFirstName());
			}

			//List of months (full name)
			Vector<String> m = new Vector<String>();
			DateFormat f1 = new SimpleDateFormat("M");
			DateFormat f2 = new SimpleDateFormat("MMMM");
			Date date = new Date();
			for (int i = 1; i < 13; i++) {
				try {
					date = f1.parse("" + i);
					m.add(f2.format(date));
				} catch (ParseException e) {
					/*NULL BODY*/
				}
			}

			//Create components
			JLabel addChild = new JLabel("Add Child:");
			JButton add = new JButton("Add");
			nameInput = new JTextField("--First Name--");
			JLabel editChild = new JLabel("Review and Modify Child:");
			year = new JComboBox<String>(y);
			month = new JComboBox<String>();
			day = new JComboBox<String>();
			childSelect = new JComboBox<String>(childNames);
			year2 = new JComboBox<String>(y);
			month2 = new JComboBox<String>(m);
			day2 = new JComboBox<String>();
			level = new JComboBox<String>();
			JButton progress = new JButton("View Progress");
			JButton remove = new JButton("Remove");
			JButton update = new JButton("Update");

			//Limit the number of characters that can be input into the name input field
			((AbstractDocument) nameInput.getDocument()).setDocumentFilter(new DocumentLengthFilter(20));

			//Populate the months drop-down with correct number of days
			updateDaysInMonth(year2, month2, day2);

			//Populates the months list according to selected child
			populateLevelList();

			//Sets the default selections to the current values for selected child
			setSelections();

			//Add action listeners
			nameInput.addKeyListener(new EnterListener(add));
			nameInput.addMouseListener(new SelectAllTextOnClick(nameInput));
			year.addActionListener(new YearSelected(year, month, day));
			month.addActionListener(new MonthSelected(year,month,day));
			add.addActionListener(new PressAdd(this));
			childSelect.addActionListener(new ChildSelected(this));
			year2.addActionListener(new YearSelected(year2, month2, day2));
			month2.addActionListener(new MonthSelected(year2,month2,day2));
			progress.addActionListener(new PressProgress(settings, this, childSelect));
			remove.addActionListener(new PressRemove(this));
			update.addActionListener(new PressUpdate(this));

			//Add components to panel
			c.insets = new Insets(25,50,0,50);
			c.gridwidth = 6;
			c.gridy = 1;
			c.gridx = 0;
			c.ipady = 80;
			add(scroll,c); //Scroll pane contains table

			c.gridwidth = 2;
			c.ipady = 0;
			c.insets = new Insets(10,50,0,0);
			c.gridy = 2;
			c.gridx = 0;
			add(addChild, c); //Add child section label

			c.insets = new Insets(0,50,0,0);
			c.gridwidth = 2;
			c.ipadx = 180;
			c.gridy = 3;
			c.gridx = 0;
			add(nameInput, c);  //Add, name input field

			c.gridwidth = 1;
			c.ipadx = 0;
			c.insets = new Insets(0,0,0,0);
			c.gridx = 2;
			add(year, c);  //Add: birth-year drop down

			c.gridx = 3;
			add(month, c);  //Add: birth-month drop down

			c.gridx = 4;
			add(day, c);  //Add: birth-day drop down

			c.insets = new Insets(0,0,0,50);
			c.gridx = 5;
			add(add, c);  //The add button

			c.insets = new Insets(20,50,0,0);
			c.gridwidth = 2;
			c.gridy = 4;
			c.gridx = 0;
			add(editChild, c);  //Modify child section label

			c.insets = new Insets(0,50,0,0);
			c.gridy = 5;
			c.gridx = 0;
			add(childSelect, c);  //Modify: child selection drop down

			c.insets = new Insets(0,0,0,0);
			c.gridwidth = 1;
			c.gridx = 2;
			add(year2, c);  //Modify: birth-year drop down

			c.gridx = 3;
			add(month2, c);  //Modify: birth-month drop down

			c.gridx = 4;
			add(day2, c);  //Modify: birth-day drop down

			c.insets = new Insets(0,0,0,50);
			c.gridx = 5;
			add(level, c);  //Modify: current level drop down

			c.insets = new Insets(15,0,25,70);
			c.gridy = 6;
			c.gridx = 2;
			c.gridwidth = 2;
			add(progress, c);  //The button to display progress of selected child

			c.insets = new Insets(15,0,25,0);
			c.gridx = 4;
			c.gridwidth = 1;
			add(remove, c);  //The button to remove the selected child

			c.insets = new Insets(15,0,25,50);
			c.gridx = 5;
			add(update, c);  //The button to update the selected child with selected details
		} catch (JSONFailureException e) {
			JPanel screen = new JPanel();
			screen.setLayout(new BoxLayout(screen, BoxLayout.PAGE_AXIS));
			ArrayList<String> messages = e.getMessages();
			for (int i = 0; i < messages.size(); i++) {
				JLabel error = new JLabel();
				error.setForeground(Color.RED);
				error.setFont(Controller.getFont().deriveFont(Font.PLAIN, 18));
				screen.add(error);
			}
			Controller.setScreen(screen);
		}
	}

	/**
	 * Populates the level selection combo box
	 * 
	 * @param childSelect	the child selection combo box
	 * @param level			the level selection combo box to be populated
	 */
	public void populateLevelList() {

		//Determine the max range for the level drop down list
		try {
			int maxLevel = 1;
			ArrayList<Progeny> progeny = ProgenyService.getProgenies();
			if (progeny.size() > 0) {
				maxLevel = progeny.get(childSelect.getSelectedIndex()).getLevelNumber();
			}


			//Create the vector for use populating the level drop down
			Vector<String> l = new Vector<String>();
			for (int i = 1; i <= maxLevel; i++) {
				l.add("Level " + i);
			}

			//Populate the level drop down
			level.setModel(new DefaultComboBoxModel<String>(l));
		} catch (JSONFailureException e) {
			//TODO: popup
		}
	}

	/**
	 * Sets the default selections for all of the birthday drop downs, and the level drop down to match
	 * the currently selected child.
	 * 
	 */
	public void setSelections() {
		try {
			//Get the currently selected child's birthday
			Date birth = ProgenyService.getProgenies().get(childSelect.getSelectedIndex()).getBirthDate();

			//Set the year, month and day fields to the current birthday values
			DateFormat f = new SimpleDateFormat("yyyy");
			year2.setSelectedItem(f.format(birth));
			f = new SimpleDateFormat("MMMM");
			month2.setSelectedItem(f.format(birth));
			f = new SimpleDateFormat("d");
			day2.setSelectedItem(f.format(birth));

			//Get the current level achieved by the child and set the level drop down to it
			level.setSelectedIndex(level.getItemCount() - 1);

		} catch (ArrayIndexOutOfBoundsException e) {
			/*NULL BODY*/
		} catch (JSONFailureException e) {
			//TODO: popup
		}
	}

	/**
	 * Populates the birth-day drop down with the correct day range for selected year and month
	 * 
	 * @param year		the birth-year drop down
	 * @param month		the birth-month drop down
	 * @param day		the birth-day drop down
	 */
	public static void updateDaysInMonth(JComboBox<String> year, JComboBox<String> month,
			JComboBox<String> day) {

		//Calculate the year selected
		GregorianCalendar nowCal = new GregorianCalendar();
		int yr = nowCal.get(Calendar.YEAR) - year.getSelectedIndex();

		//Get the last day of the month for the month and year selected
		GregorianCalendar cal = new GregorianCalendar(yr,(month.getSelectedIndex() + 1),1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		//Create the list of days
		Vector<String> d = new Vector<String>();
		for (int i = 1; i <= lastDay; i++) {
			d.add("" + i);
		}

		//Save the currently selected index
		int index = day.getSelectedIndex();

		//Update the list of days with the new list
		day.setModel(new DefaultComboBoxModel<String>(d));

		//If the old selection is in the new range and was in fact a selection, set the selection back to that day
		if (index < day.getItemCount()  && index >= 0) {
			day.setSelectedIndex(index);
		}
	}

	public void updateChild() {
		try {
			//Boolean set to false if any values are invalid
			boolean valid = true;

			//Get the list of progeny
			ArrayList<Progeny> progenyList = ProgenyService.getProgenies();

			//Get index of selected child
			int index = childSelect.getSelectedIndex();

			//Get the progeny instance for the selected child
			Progeny child = progenyList.get(index);

			//Check the birth-day is valid and add to string
			int dd = 0;
			if (day2.getItemCount() > 0 && day2.getSelectedIndex() > -1) {
				dd = day2.getSelectedIndex() + 1;
				day2.setForeground(Color.BLACK);
			}
			else {
				day2.setForeground(Color.RED);
				valid = false;
			}

			//Check the birth-month is valid and add to string
			int mm = 0;
			if (month2.getItemCount() > 0 && month2.getSelectedIndex() > -1) {
				mm = month2.getSelectedIndex() + 1;
				month2.setForeground(Color.BLACK);
			}
			else {
				month.setForeground(Color.RED);
				valid = false;
			}

			//Check the birth-year is valid and add to string
			int yyyy = 0;
			try {
				if (year2.getItemCount() > 0 && year2.getSelectedIndex() > -1) {
					yyyy = Integer.parseInt((String) year2.getSelectedItem());
					year2.setForeground(Color.BLACK);
				}
				else {
					year2.setForeground(Color.RED);
					valid = false;
				}
			} catch (NumberFormatException e1) {
				year2.setForeground(Color.RED);
				valid = false;
			}

			//Check the level is valid
			int newLevel = 1;
			if (level.getSelectedIndex() > -1) {
				newLevel = level.getSelectedIndex() + 1;
			}
			else {
				level.setForeground(Color.RED);
				valid = false;
			}

			//Create the final date instance
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String birthdayStr = dd + "/" + mm + "/" + yyyy;
			Date birthdate = format.parse(birthdayStr);

			//If all values are valid, actually go ahead and update
			if (valid) {
				//If the date of birth has changed, update progeny's birthday
				if (!child.getBirthDate().equals(birthdate)) {
					ProgenyService.changeBirthDate(child, birthdate);
				}

				//If the level number has changed, update the progeny's level
				if (child.getLevelNumber() != newLevel) {
					//TODO: add a warning popup to inform that there is no going back and data will be lost
					GameService.setLevel(child, newLevel);
				}

				//Update the screen
				settings.changeTabContent(0, new ChildSettingsTab(settings));
			}
		} catch (ParseException e1) {
			return;
		} catch (JSONFailureException e1) {
			//TODO: popup
		}
	}

	public void addChild() {
		String firstName = "";
		year.setForeground(Color.RED);

		//Check name input is valid
		try {	
			firstName = nameInput.getText();
			if (firstName.equals("--Name--") || firstName.equals("")) {
				throw new Exception();
			}
			nameInput.setBackground(Color.WHITE);
		} catch (Exception e1) {
			nameInput.setBackground(Color.PINK);
		}

		//Check the birth-day is valid and add to string
		int dd = 0;
		if (day.getItemCount() > 0 && day.getSelectedIndex() > -1) {
			dd = day.getSelectedIndex() + 1;
			day.setForeground(Color.BLACK);
		}
		else {
			day.setForeground(Color.RED);
		}

		//Check the birth-month is valid and add to string
		int mm = 0;
		if (month.getItemCount() > 0 && month.getSelectedIndex() > -1) {
			mm = month.getSelectedIndex() + 1;
			month.setForeground(Color.BLACK);
		}
		else {
			month.setForeground(Color.RED);
		}

		//Check the birth-year is valid and add to string
		int yyyy = 0;
		try {
			if (year.getItemCount() > 0 && year.getSelectedIndex() > -1) {
				yyyy = Integer.parseInt((String) year.getSelectedItem());
				year.setForeground(Color.BLACK);
			}
			else {
				year.setForeground(Color.RED);
			}
		} catch (NumberFormatException e1) {
			year.setForeground(Color.RED);
		}

		try {
			//Create the final date instance
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String birthdayStr = dd + "/" + mm + "/" + yyyy;
			Date birthdate = format.parse(birthdayStr);

			//Actually add the new child (with default level time of 30 seconds)
			ProgenyService.addProgeny(firstName, birthdate, 30);

			//Update the screen
			settings.changeTabContent(0, new ChildSettingsTab(settings));
			
		} catch (ParseException e1) {
			System.out.print("Add error - parse");
			//TODO: add exception handling, popup?
		} catch (JSONFailureException e1) {
			ArrayList<String> messages = e1.getMessages();
			for (int i = 0; i < messages.size(); i++) {
				System.out.print(messages.get(i));
			}
			//TODO: add exception handling, popup??
		}
	}

	/**
	 * Removes the selected progeny.
	 * 
	 */
	public void removeProgeny() {
		//Get the selected child index
		int index = childSelect.getSelectedIndex();

		//Remove the selected child's entry in the database
		try {
			Progeny toRemove = progenyList.get(index);
			if (toRemove.getFirstName().equals(childSelect.getSelectedItem())) {
				ProgenyService.removeProgeny(toRemove);
			}
			else {
				throw new Exception("Unknown Error");
			}
		} catch (JSONFailureException e1) {
			//TODO: popup
		} catch (Exception e1) {
			System.out.print(e1.getMessage());
			//TODO
		}

		//Update the screen
		settings.changeTabContent(0, new ChildSettingsTab(settings));
	}



}

/**
 * The Class PressProgress, an Action Listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressProgress implements ActionListener {

	/** The current instance of the settings pane. */
	private Settings settingsPane;

	/** The child settings tab of the settings pane. */
	private ChildSettingsTab childSettings;

	/** the child selection drop down list by name. */
	private JComboBox<String> childSelect;

	/**
	 * Instantiates a new PressProgress action listener.
	 * 
	 * @param settingsPane		the current settings panes instance.
	 * @param childSelect		the child selection drop down list by name.
	 */
	public PressProgress(Settings settingsPane, ChildSettingsTab childSettings,
			JComboBox<String> childSelect) {
		super();
		this.settingsPane = settingsPane;
		this.childSettings = childSettings;
		this.childSelect = childSelect;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			//Get the list of current user's progeny
			ArrayList<Progeny> progenyList = ProgenyService.getProgenies();

			//Get the selected child's progeny instance
			Progeny child = progenyList.get(childSelect.getSelectedIndex());

			//Create a new ChildProgress screen and swap it for the current tab
			ChildProgress screen = new ChildProgress(settingsPane, childSettings, child);
			settingsPane.changeTabContent(0, screen);

		} catch (NullPointerException e1) {
			/*NULL BODY*/		//If there is no child selected, do nothing
		} catch (JSONFailureException e1) {
			//TODO: popup
		}
	}
}

/**
 * The Class MonthSelected, and Action Listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class MonthSelected implements ActionListener {

	/** The birth-year drop down. */
	private JComboBox<String> year;

	/** The birth-month drop down. */
	private JComboBox<String> month;

	/** The birth-day drop down. */
	private JComboBox<String> day;

	/**
	 * Instantiates a MonthSelected instance.
	 * 
	 * @param year		the birth-year drop down.
	 * @param month		the birth-month drop down.
	 * @param day		the birth-day drop down.
	 */
	public MonthSelected(JComboBox<String> year, JComboBox<String> month, JComboBox<String> day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		//Update the list of days in the day drop down to match the date range for the selected month
		ChildSettingsTab.updateDaysInMonth(year,month,day);
	}
}

/**
 * The class YearSelected, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class YearSelected implements ActionListener {

	/** The birth-year drop down. */
	private JComboBox<String> year;

	/** The birth-month drop down. */
	private JComboBox<String> month;

	/** The birth-day drop down. */
	private JComboBox<String> day;

	/**
	 * Instantiates an instance of the YearSelected action listener.
	 * 
	 * @param year		the birth-year drop down.
	 * @param month		the birth-month drop down.
	 * @param day		the birth-day drop down.
	 */
	public YearSelected(JComboBox<String> year, JComboBox<String> month, JComboBox<String> day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		//If the month drop down is not populated, populate it
		if (month.getItemCount() <= 1) {
			Vector<String> m = new Vector<String>();
			for (int i = 0; i < 12; i++) {
				m.add(new DateFormatSymbols().getMonths()[i]);
			}
			month.setModel(new DefaultComboBoxModel<String>(m));
		}

		//If the month drop down is populated
		else {
			// and February is selected
			if (month.getSelectedIndex() == 1) {
				//Update the days in the month incase it is a leap year
				ChildSettingsTab.updateDaysInMonth(year, month, day);
			}
		}
	}
}

/**
 * The class ChildSelected, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class ChildSelected implements ActionListener {

	/** The child settings tab of the settings pane. */
	private ChildSettingsTab childSettings;

	/**
	 * Instantiates a ChildSelected instance.
	 * 
	 * @param childSettings			the child settings tab of the settings pane.
	 * 
	 */
	public ChildSelected(ChildSettingsTab childSettings) {
		super();
		this.childSettings = childSettings;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		//Populate the level drop down with a range matching selected child
		childSettings.populateLevelList();

		//Set the default selections to the current values for selected child
		childSettings.setSelections();
	}
}

/**
 * The class PressAdd, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressAdd implements ActionListener {

	/** The child settings tab of the settings pane. */
	private ChildSettingsTab childSettings;

	/**
	 * Instantiates a PressAdd instance.
	 * 
	 * @param childSettings		the child settings tab of the settings pane.
	 * 
	 */
	public PressAdd(ChildSettingsTab childSettings) {
		super();
		this.childSettings = childSettings;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		childSettings.addChild();
	}
}

/**
 * The class PressRemove, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressRemove implements ActionListener {

	/** The child settings tab of the settings pane. */
	private ChildSettingsTab childSettings;

	/**
	 * Instantiates a PressRemove instance.
	 * 
	 * @param childSettings		the child settings tab of the settings pane.
	 */
	public PressRemove(ChildSettingsTab childSettings) {
		this.childSettings = childSettings;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		childSettings.removeProgeny();
	}
}

/**
 * The class PressUpdate, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressUpdate implements ActionListener {

	/** The child selection drop down. */
	private ChildSettingsTab childSettings;

	/**
	 * Instantiates a PressUpdate instance.
	 * 
	 * @param childSettings		the child settings tab of the settings pane.
	 */
	public PressUpdate(ChildSettingsTab childSettings) {
		super();
		this.childSettings = childSettings;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		childSettings.updateChild();
	}
}