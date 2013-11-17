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
import java.util.Iterator;
import java.util.Vector;

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
import service.ProgenyService;
import ttable.Progeny;
import ttable.User;

/**
 * The Class ChildSettingsTab.
 * 
 * @author James Anderson
 * @version 2.0
 */
@SuppressWarnings("serial")
public class ChildSettingsTab extends JPanel {

	/**
	 * Instantiates an ChildSettingsTab instance.
	 * 
	 * @param controller	the controller
	 * @param settings		the settings pane
	 */
	public ChildSettingsTab(Controller controller, Settings settings) {
		
		//Instantiate the JPanel with a GridBagLayout
		super(new GridBagLayout());
		
		//Makes panel transparent
		setOpaque(false);

		//Create instance of grid bag constraints to control layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(25,0,0,3);
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;

		//TODO: remove once sure it is not necessary
		/* TO BE REMOVED AFTER TESTING OF COLUMN HEADER SORT (NEED PROGENY CLASS FILLED OUT FIRST)
		String[] sortOptions = {"Age","Name"};
		JComboBox<String> sortMenu = new JComboBox<String>(sortOptions);
		add(sortMenu, c);

		c.insets = new Insets(10,0,0,75);
		c.gridx = 5;
		JButton sortButton = new JButton("Sort");
		add(sortButton, c);
		 */
		try {
			//Create vector containing column headers for the child info table
			Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"Name"," ","Birthday"," ","Age","Level"}));

			//Create vector structure containing table data
			ArrayList<Progeny> progenyList = User.getProgeny();
			Vector<Vector<String>> tableData = new Vector<Vector<String>>();
			for (int i = 0; i < progenyList.size(); i++) {
				Vector<String> v = new Vector<String>();
				Progeny p = progenyList.get(i);
				v.add(p.getFirstName());

				Date birthday = p.getBirthday();
				DateFormat format = new SimpleDateFormat("d");
				v.add(format.format(birthday));

				format = new SimpleDateFormat("MMMM");
				v.add(format.format(birthday));

				format = new SimpleDateFormat("yyyy");
				v.add(format.format(birthday));

				v.add("" + p.getAge());
				v.add("" + (p.getLevel()));
				tableData.add(v);
			}

			//Create and populate the table
			DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
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
			table.setFont(controller.getFont().deriveFont(Font.BOLD, 18));
			table.getTableHeader().setFont(controller.getFont().deriveFont(Font.BOLD, 18));

			//Put the table into a scroll pane to allow scrolling when the table is too large to fit
			JScrollPane scroll = new JScrollPane(table);
			scroll.setOpaque(false);
			scroll.getViewport().setOpaque(false);

			//CREATE DATA TO POPULATE JCOMBOBOXES
			
			//List of potential birth years
			Vector<String> y = new Vector<String>();
			for (int i = (new GregorianCalendar().get(Calendar.YEAR)); i > 1980; i--) {
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
			JTextField nameInput = new JTextField("--First Name--");
			JLabel editChild = new JLabel("Review and Modify Child:");
			JComboBox<String> year = new JComboBox<String>(y);
			JComboBox<String> month = new JComboBox<String>();
			JComboBox<String> day = new JComboBox<String>();
			JComboBox<String> childSelect = new JComboBox<String>(childNames);
			JComboBox<String> year2 = new JComboBox<String>(y);
			JComboBox<String> month2 = new JComboBox<String>(m);
			JComboBox<String> day2 = new JComboBox<String>();
			JComboBox<String> level = new JComboBox<String>();
			JButton progress = new JButton("View Progress");
			JButton remove = new JButton("Remove");
			JButton update = new JButton("Update");

			//Limit the number of characters that can be input into the name input field
			((AbstractDocument) nameInput.getDocument()).setDocumentFilter(new DocumentLengthFilter(20));
			
			//Populate the months drop-down with correct number of days
			updateDaysInMonth(year2, month2, day2);
			
			//Populates the months list according to selected child
			populateLevelList(childSelect, level);

			//Sets the default selections to the current values for selected child
			setSelections(childSelect, year, month, day, level);

			//Add action listeners
			nameInput.addKeyListener(new EnterListener(add));
			year.addActionListener(new YearSelected(year, month, day));
			month.addActionListener(new MonthSelected(year, month, day));
			add.addActionListener(new PressAdd(nameInput, year, month, day, tableData, tableModel));
			childSelect.addActionListener(new ChildSelected(childSelect, year2, month2, day2, level));
			year2.addActionListener(new YearSelected(year2, month2, day2));
			month2.addActionListener(new MonthSelected(year2, month2, day2));
			progress.addActionListener(new PressProgress(childSelect, controller, settings, this));
			remove.addActionListener(new PressRemove(childSelect, tableModel, tableData));
			update.addActionListener(new PressUpdate(childSelect, year2, month2, day2, level, tableModel, tableData));

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

		} catch(JSONFailureException e) {
			//If communication error occurs, panel with error messages is displayed instead
			ArrayList<String> errors = e.getMessages();
			int gridY = 0;
			Iterator<String> error = errors.iterator();
			c.gridx = 0;
			c.gridy = gridY;
			add(new JLabel(error.toString()));
			while (error.hasNext()) {
				gridY++;
				c.gridy = gridY;
				JLabel label = new JLabel(error.next().toString());
				label.setForeground(Color.RED);
				label.setFont(controller.getFont().deriveFont(Font.PLAIN, 18));
				add(label, c);
			}
		}
	}

	/**
	 * Populates the level selection combo box
	 * 
	 * @param childSelect	the child selection combo box
	 * @param level			the level selection combo box to be populated
	 */
	public static void populateLevelList(JComboBox<String> childSelect, JComboBox<String> level) {
		
		//Determine the max range for the level drop down list
		int maxLevel = 1;
		try {
			ArrayList<Progeny> progeny = User.getProgeny();
			if (progeny.size() > 0) {
				maxLevel = progeny.get(childSelect.getSelectedIndex()).getLevel();
			}
		} catch (JSONFailureException e) {
			//TODO: add exception handling, popup?
		}
		
		//Create the vector for use populating the level drop down
		Vector<String> l = new Vector<String>();
		for (int i = 1; i <= maxLevel; i++) {
			l.add("Level " + i);
		}
		
		//Populate the level drop down
		level.setModel(new DefaultComboBoxModel<String>(l));
	}

	/**
	 * Sets the default selections for all of the birthday drop downs, and the level drop down to match
	 * the currently selected child
	 * 
	 * @param childSelect	the child selection drop down
	 * @param year			the birth-year drop down
	 * @param month			the birth-month drop down
	 * @param day			the birth-day drop down
	 * @param level			the current level drop down
	 */
	public static void setSelections(JComboBox<String> childSelect, JComboBox<String> year, JComboBox<String> month, JComboBox<String> day, JComboBox<String> level) {
		try {
			//Get the currently selected child's birthday
			Date birth = User.getProgeny().get(childSelect.getSelectedIndex()).getBirthday();
			
			//Set the year, month and day fields to the current birthday values
			DateFormat f = new SimpleDateFormat("yyyy");
			year.setSelectedItem(f.format(birth));
			f = new SimpleDateFormat("MMMM");
			month.setSelectedItem(f.format(birth));
			f = new SimpleDateFormat("d");
			day.setSelectedItem(f.format(birth));
			
			//Get the current level achieved by the child and set the level drop down to it
			level.setSelectedIndex(level.getItemCount() - 1);
			
		} catch (ArrayIndexOutOfBoundsException e) {
			/*NULL BODY*/
		} catch (JSONFailureException e) {
			//TODO: add exception handling, popup?
		}
	}

	/**
	 * Populates the birth-day drop down with the correct day range for selected year and month
	 * 
	 * @param year		the birth-year drop down
	 * @param month		the birth-month drop down
	 * @param day		the birth-day drop down
	 */
	public static void updateDaysInMonth(JComboBox<String> year, JComboBox<String> month, JComboBox<String> day) {
		
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
}

/**
 * The Class PressProgress, an Action Listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressProgress implements ActionListener {

	/** The controller. */
	private Controller controller;

	/** The child selector combo box. */
	private JComboBox<String> childSelector;

	/** The current instance of the settings pane. */
	private Settings settingsPane;

	/** The current instance of the child settings tab. */
	private ChildSettingsTab childSettingsTab;

	/**
	 * Instantiates a new PressProgress action listener.
	 * 
	 * @param childSelector		the child selection drop down
	 * @param controller		the controller
	 * @param settingsPane		the current settings panes instance
	 * @param childSettingsTab	the current child settings tab instance
	 */
	public PressProgress(JComboBox<String> childSelector, Controller controller, Settings settingsPane, ChildSettingsTab childSettingsTab) {
		super();
		this.controller = controller;
		this.childSelector = childSelector;
		this.settingsPane = settingsPane;
		this.childSettingsTab = childSettingsTab;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//Create an ArrayList to hold errors if an exception is thrown so they can be passed to the next display
			ArrayList<String> errors = new ArrayList<String>();
			
			Progeny child = null;
			try {
				//Get the list of current user's progeny
				ArrayList<Progeny> progenyList = User.getProgeny();
				
				//Get the selected child's progeny instance
				child = progenyList.get(childSelector.getSelectedIndex());
				
			} catch (JSONFailureException e1) {
				//Get error messages to pass to next display
				errors = e1.getMessages();
			}
			
			//Create a new ChildProgress screen and swap it for the current tab
			ChildProgress screen = new ChildProgress(controller, settingsPane, childSettingsTab, child, errors);
			settingsPane.changeTabContent(0, screen);
			
		} catch (NullPointerException e1) {
			/*NULL BODY*/		//If there is no child selected, do nothing
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
	 * @param year		the birth-year drop down
	 * @param month		the birth-month drop down
	 * @param day		the birth-day drop down
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
	 * @param year		the birth-year drop down
	 * @param month		the birth-month drop down
	 * @param day		the birth-day drop down
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

	/** The child selection drop down. */
	private JComboBox<String> childSelect;
	
	/** The birth-year drop down. */
	private JComboBox<String> year;
	
	/** The month-year drop down. */
	private JComboBox<String> month;
	
	/** The day-year drop down.*/
	private JComboBox<String> day;
	
	/** The current level drop down. */
	private JComboBox<String> level;

	/**
	 * Instantiates a ChildSelected instance.
	 * 
	 * @param childSelect	the child selection drop down
	 * @param year			the birth-year drop down
	 * @param month			the birth-month drop down
	 * @param day			the birth-day drop down
	 * @param level			the current level drop down
	 */
	public ChildSelected(JComboBox<String> childSelect, JComboBox<String> year, JComboBox<String> month, JComboBox<String> day, JComboBox<String> level) {
		super();
		this.childSelect = childSelect;
		this.year = year;
		this.month = month;
		this.day = day;
		this.level = level;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Populate the level drop down with a range matching selected child
		ChildSettingsTab.populateLevelList(childSelect, level);
		
		//Set the default selections to the current values for selected child
		ChildSettingsTab.setSelections(childSelect, year, month, day, level);
	}
}

/**
 * The class PressAdd, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressAdd implements ActionListener {
	
	/** The name input field. */
	private JTextField nameInput;
	
	/** The birth-year drop down. */
	private JComboBox<String> year;
	
	/** The birth-month drop down. */
	private JComboBox<String> month;
	
	/** The birth-day drop down. */
	private JComboBox<String> day;
	
	/** The child details for the table. */
	private Vector<Vector<String>> tableData;
	
	/** The table model. */
	private DefaultTableModel tableModel;

	/**
	 * Instantiates a PressAdd instance.
	 * 
	 * @param nameInput		the name input field
	 * @param year			the birth-year drop down
	 * @param month			the birth-month drop down
	 * @param day			the birth-day drop down
	 * @param tableData		the child details for the table
	 * @param tableModel	the table model
	 */
	public PressAdd(JTextField nameInput, JComboBox<String> year, JComboBox<String> month, JComboBox<String> day, Vector<Vector<String>> tableData, DefaultTableModel tableModel) {
		super();
		this.nameInput = nameInput;
		this.year = year;
		this.month = month;
		this.day = day;
		this.tableData = tableData;
		this.tableModel = tableModel;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String firstName = "";
		year.setForeground(Color.RED);

		//Check name input is valid
		try {	
			firstName = nameInput.getText();
			if (!firstName.contains("--")) {
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

			//Actually add the new child
			ProgenyService.(firstName, birthdate);

			//Read info from newly added progeny
			ArrayList<Progeny> progenyList = User.getProgeny();
			Progeny newProgeny = null;
			for (int i = 0; i < progenyList.size(); i++) {
				if (progenyList.get(i).getFirstName().equals(firstName)) {
					newProgeny = progenyList.get(i);
					break;
				}
			}
			Vector<String> v = new Vector<String>();
			v.add(newProgeny.getFirstName());

			Date birthday = newProgeny.getBirthday();
			format = new SimpleDateFormat("d");
			v.add(format.format(birthday));

			format = new SimpleDateFormat("MMMM");
			v.add(format.format(birthday));

			format = new SimpleDateFormat("yyyy");
			v.add(format.format(birthday));

			v.add("" + newProgeny.getAge());
			v.add("" + newProgeny.getLevel());

			//Update the child details table with details of newly added child
			tableData.add(v);
			tableModel.fireTableDataChanged();
		} catch (ParseException e1) {
			//TODO: add exception handling, popup?
		} catch (JSONFailureException e1) {
			//TODO: add exception handling, popup??
		}
	}
}

/**
 * The class PressRemove, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressRemove implements ActionListener {

	/** The child selection drop down. */
	private JComboBox<String> childSelect;
	
	/** The table model. */
	private DefaultTableModel tableModel;
	
	/** The child details for the table. */
	private Vector<Vector<String>> tableData;

	/**
	 * Instantiates a PressRemove instance.
	 * 
	 * @param childSelect	the child selection drop down
	 * @param tableModel	the child details for the table
	 * @param tableData		the table model
	 */
	public PressRemove(JComboBox<String> childSelect, DefaultTableModel tableModel, Vector<Vector<String>> tableData) {
		this.childSelect = childSelect;
		this.tableModel = tableModel;
		this.tableData = tableData;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Get the selected child index
		int index = childSelect.getSelectedIndex();
		
		try {
			//Get the list of progeny
			ArrayList<Progeny> progenyList = User.getProgeny();
			
			//Remove the selected child's entry in the database
			User.removeProgeny(progenyList.get(index));
			
			//Remove the selected child's entry in the table
			tableData.remove(index);
			
			//Update the table display
			tableModel.fireTableDataChanged();
			
		} catch (JSONFailureException e1) {
			// TODO: add exception handling, popup??
		}
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
	private JComboBox<String> childSelect;
	
	/** The birth-year drop down. */
	private JComboBox<String> year;
	
	/** The birth-month drop down. */
	private JComboBox<String> month;
	
	/** The birth-day drop down. */
	private JComboBox<String> day;
	
	/** The current level drop down. */
	private JComboBox<String> level;
	
	/** The table model. */
	private DefaultTableModel tableModel;
	
	/** The child details for the table. */
	private Vector<Vector<String>> tableData;

	/**
	 * Instantiates a PressUpdate instance.
	 * 
	 * @param childSelect		the child selection drop down
	 * @param year				the birth-year drop down
	 * @param month				the birth-month drop down
	 * @param day				the birth-day drop down
	 * @param level				the current level drop down
	 * @param tableModel		the table model
	 * @param tableData			the child details for the table
	 */
	public PressUpdate(JComboBox<String> childSelect, JComboBox<String> year, JComboBox<String> month, JComboBox<String> day, JComboBox<String> level, DefaultTableModel tableModel, Vector<Vector<String>> tableData) {
		super();
		this.childSelect = childSelect;
		this.year = year;
		this.month = month;
		this.day = day;
		this.level = level;
		this.tableModel = tableModel;
		this.tableData = tableData;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			//Get the list of progeny
			ArrayList<Progeny> progenyList = User.getProgeny();
			
			//Get index of selected child
			int index = childSelect.getSelectedIndex();
			
			//Get the progeny instance for the selected child
			Progeny child = progenyList.get(index);

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

			//Check the level is valid
			int currentLevel = 1;
			if (level.getSelectedIndex() > -1) {
				currentLevel = level.getSelectedIndex() + 1;
			}
			else {
				level.setForeground(Color.RED);
			}

			try {
				//Create the final date instance and update progeny's birthday
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String birthdayStr = dd + "/" + mm + "/" + yyyy;
				Date birthdate = format.parse(birthdayStr);
				child.setBirthday(birthdate);

				//Update the progeny's level
				child.setLevel(currentLevel);
				
				//Read info from newly updated progeny
				ArrayList<Progeny> progenyListUpdated = User.getProgeny();
				String name = (String) childSelect.getSelectedItem();
				Progeny newProgeny = null;
				for (int i = 0; i < progenyListUpdated.size(); i++) {
					if (progenyListUpdated.get(i).getFirstName().equals(name)) {
						newProgeny = progenyListUpdated.get(i);
						break;
					}
				}
				
				//Create new entry to replace old in table
				Vector<String> entry = new Vector<String>();
				Date birthday = newProgeny.getBirthday();
				format = new SimpleDateFormat("d");
				entry.set(1, format.format(birthday));

				format = new SimpleDateFormat("MMMM");
				entry.set(2, format.format(birthday));

				format = new SimpleDateFormat("yyyy");
				entry.set(3, format.format(birthday));

				entry.set(4, "" + newProgeny.getAge());
				entry.set(5, "" + newProgeny.getLevel());

				//Replace the old entry in the table with the new one
				tableData.set(index, entry);
				tableModel.fireTableDataChanged();
			} catch (ParseException e1) {
				return;
			}
		} catch (JSONFailureException e1) {
			//TODO: add exception handling, perhaps a popup?
		}
	}
}