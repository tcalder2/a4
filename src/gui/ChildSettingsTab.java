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

import json.JSONFailureException;
import ttable.Progeny;
import ttable.User;

/**
 * The Class ChildSettingsTab.
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
		super(new GridBagLayout());  //Instantiate the JPanel with a GridBagLayout
		setOpaque(false);  //Makes panel transparent

		//Create instance of grid bag constraints to control layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(25,0,0,3);
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;

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
			ArrayList<Progeny> progeny = User.getProgeny();
			Vector<Vector<String>> tableData = new Vector<Vector<String>>();
			for (int i = 0; i < progeny.size(); i++) {
				Vector<String> v = new Vector<String>();
				Progeny p = progeny.get(i);
				v.add(p.getFirstName());

				Date birthday = p.getBirthday();
				DateFormat format = new SimpleDateFormat("d");
				v.add(format.format(birthday));

				format = new SimpleDateFormat("MMMM");
				v.add(format.format(birthday));

				format = new SimpleDateFormat("yyyy");
				v.add(format.format(birthday));

				v.add("" + p.getAge());
				v.add("" + (Progeny.getLevels(p).size() + 1));
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

			//Create data to populate JComboBoxes
			//List of potential birth years
			Vector<String> y = new Vector<String>();
			for (int i = (new GregorianCalendar().get(Calendar.YEAR)); i > 1980; i--) {
				y.add("" + i);
			}

			//List of child names
			ArrayList<Progeny> progenies = User.getProgeny();
			Vector<String> childNames = new Vector<String>();
			for (int i = 0; i < progenies.size(); i++) {
				childNames.add(progenies.get(i).getFirstName());
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
			updateDaysInMonth(year2, month2, day2);					//Populates the months drop-down with correct number of days
			JComboBox<String> level = new JComboBox<String>();
			populateLevelList(childSelect, level); 					//Populates the months list according to selected child
			setSelections(childSelect, year, month, day, level);	//Sets the default selections to the current values for selected child
			JButton progress = new JButton("View Progress");
			JButton remove = new JButton("Remove");
			JButton update = new JButton("Update");

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

			//Error thrown when there is trouble connecting to the server for some reason
			//Causes panel with error messages to be displayed in stead
		} catch(JSONFailureException e) {
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
		int maxLevel = 1;
		try {
			if (User.getProgeny().size() > 0) {
				maxLevel = Progeny.getLevels(User.getProgeny().get(childSelect.getSelectedIndex())).size();
			}
		} catch (JSONFailureException e) {
			/*NULL BODY*/
		}
		Vector<String> l = new Vector<String>();
		for (int i = 1; i <= maxLevel; i++) {
			l.add("Level " + i);
		}
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
			Date birth = User.getProgeny().get(childSelect.getSelectedIndex()).getBirthday();
			DateFormat f = new SimpleDateFormat("yyyy");
			year.setSelectedItem(f.format(birth));
			f = new SimpleDateFormat("MMMM");
			month.setSelectedItem(f.format(birth));
			f = new SimpleDateFormat("d");
			day.setSelectedItem(f.format(birth));
			level.setSelectedIndex(level.getItemCount() - 1);
		} catch (JSONFailureException | ArrayIndexOutOfBoundsException e) {
			/*NULL BODY*/
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
		DateFormat yFormat = new SimpleDateFormat("yyyy");
		DateFormat mFormat = new SimpleDateFormat("MMMM");
		String yStr = (String)year.getSelectedItem();
		String mStr = (String)month.getSelectedItem();
		GregorianCalendar yCal = new GregorianCalendar();
		GregorianCalendar mCal = new GregorianCalendar();
		try {
			yCal.setTime(yFormat.parse(yStr));;
			mCal.setTime(mFormat.parse(mStr));;
		} catch (ParseException e1) {
			//TODO: add exception handling
		}
		GregorianCalendar cal = new GregorianCalendar(yCal.get(Calendar.YEAR),mCal.get(Calendar.MONTH),1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		Vector<String> d = new Vector<String>();
		for (int i = 1; i <= lastDay; i++) {
			d.add("" + i);
		}
		int index = day.getSelectedIndex();
		day.setModel(new DefaultComboBoxModel<String>(d));
		if (index < day.getItemCount()  && index >= 0) {
			day.setSelectedIndex(index);
		}
	}
}

/**
 * The Class PressProgress, an Action Listener.
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

	/**
	 * The action performed upon event.
	 * Switches the tab to display the progress of the selected child
	 * 
	 * @param e		the action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String selected = new String(childSelector.getSelectedItem().toString());
			ArrayList<String> errors = new ArrayList<String>();
			Progeny child = null;
			try {
				ArrayList<Progeny> progeny = User.getProgeny();
				for (int i = 0; i < progeny.size(); i++) {
					String name = progeny.get(i).getFirstName();
					if (name.equals(selected)) {
						child = progeny.get(i);
					}
				}
			} catch (JSONFailureException e1) {
				errors = e1.getMessages();
			}
			ChildProgress screen = new ChildProgress(controller, settingsPane, childSettingsTab, child, errors);
			settingsPane.changeTabContent(0, screen);
		} catch (NullPointerException e1) {
			/*NULL BODY*/
		}
	}
}

/**
 * The Class MonthSelected, and Action Listener.
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

	/**
	 * The action performed upon event.
	 * Populates the days drop down menu with the current day range.
	 * 
	 * @param e		the action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ChildSettingsTab.updateDaysInMonth(year,month,day);
	}
}

class YearSelected implements ActionListener {

	private JComboBox<String> year;
	private JComboBox<String> month;
	private JComboBox<String> day;

	public YearSelected(JComboBox<String> year, JComboBox<String> month, JComboBox<String> day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (month.getItemCount() <= 1) {
			Vector<String> m = new Vector<String>();
			for (int i = 0; i < 12; i++) {
				m.add(new DateFormatSymbols().getMonths()[i]);
			}
			month.setModel(new DefaultComboBoxModel<String>(m));
		}
		else {
			ChildSettingsTab.updateDaysInMonth(year, month, day);
		}
	}
}

class ChildSelected implements ActionListener {

	private JComboBox<String> childSelect;
	private JComboBox<String> year;
	private JComboBox<String> month;
	private JComboBox<String> day;
	private JComboBox<String> level;

	public ChildSelected(JComboBox<String> childSelect, JComboBox<String> year, JComboBox<String> month, JComboBox<String> day, JComboBox<String> level) {
		super();
		this.childSelect = childSelect;
		this.year = year;
		this.month = month;
		this.day = day;
		this.level = level;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ChildSettingsTab.populateLevelList(childSelect, level);
		ChildSettingsTab.setSelections(childSelect, year, month, day, level);
	}
}

class PressAdd implements ActionListener {
	private JTextField nameInput;
	private JComboBox<String> year;
	private JComboBox<String> month;
	private JComboBox<String> day;
	private Vector<Vector<String>> tableData;
	private DefaultTableModel tableModel;

	public PressAdd(JTextField nameInput, JComboBox<String> year, JComboBox<String> month, JComboBox<String> day, Vector<Vector<String>> tableData, DefaultTableModel tableModel) {
		super();
		this.nameInput = nameInput;
		this.year = year;
		this.month = month;
		this.day = day;
		this.tableData = tableData;
		this.tableModel = tableModel;
	}

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
			User.addProgeny(firstName, birthdate);

			//Read info from newly added progeny
			ArrayList<Progeny> progenies = User.getProgeny();
			Progeny newProgeny = null;
			for (int i = 0; i < progenies.size(); i++) {
				if (progenies.get(i).getFirstName().equals(firstName)) {
					newProgeny = progenies.get(i);
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
			v.add("" + (Progeny.getLevels(newProgeny).size() + 1));

			//Update the child details table with details of newly added child
			tableData.add(v);
			tableModel.fireTableDataChanged();
		} catch (ParseException e1) {
			return;
		} catch (JSONFailureException e2) {
			System.out.print("Error");
			//TODO: add exception handling, popup??
		}
	}
}

class PressRemove implements ActionListener {

	private JComboBox<String> childSelect;
	private DefaultTableModel tableModel;
	private Vector<Vector<String>> tableData;

	public PressRemove(JComboBox<String> childSelect, DefaultTableModel tableModel, Vector<Vector<String>> tableData) {
		this.childSelect = childSelect;
		this.tableModel = tableModel;
		this.tableData = tableData;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String firstName = (String) childSelect.getSelectedItem();
		try {
			ArrayList<Progeny> progenies = User.getProgeny();
			for (int i = 0; i < progenies.size(); i++) {
				String name = progenies.get(i).getFirstName();
				if (name.equals(firstName)) {
					User.removeProgeny(progenies.get(i));
					break;
				}
			}
			for (int i = 0; i < tableData.size(); i++) {
				if (tableData.get(i).get(0).equals(firstName)) {
					tableData.remove(i);
					break;
				}
			}
			tableModel.fireTableDataChanged();
		} catch (JSONFailureException e1) {
			// TODO: add exception handling, popup??
		}
	}
}

class PressUpdate implements ActionListener {

	private JComboBox<String> childSelect;
	private JComboBox<String> year;
	private JComboBox<String> month;
	private JComboBox<String> day;
	private JComboBox<String> level;
	private DefaultTableModel tableModel;
	private Vector<Vector<String>> tableData;

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

	@Override
	public void actionPerformed(ActionEvent e) {
		try {	
			String name = (String) childSelect.getSelectedItem();
			ArrayList<Progeny> children = User.getProgeny();
			Progeny child = null;
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getFirstName().equals(name)) {
					child = children.get(i);
					break;
				}
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
				Progeny.setLevel(child, currentLevel);

				//Read info from newly updated progeny
				ArrayList<Progeny> progenies = User.getProgeny();
				Progeny newProgeny = null;
				for (int i = 0; i < progenies.size(); i++) {
					if (progenies.get(i).getFirstName().equals(name)) {
						newProgeny = progenies.get(i);
						break;
					}
				}
				Vector<String> entry = null;
				int entryNum = 0;
				for (; entryNum < tableData.size(); entryNum++) {
					if (tableData.get(entryNum).get(0).equals(name)) {
						entry = tableData.get(entryNum);
					}
				}

				Date birthday = newProgeny.getBirthday();
				format = new SimpleDateFormat("d");
				entry.set(1, format.format(birthday));

				format = new SimpleDateFormat("MMMM");
				entry.set(2, format.format(birthday));

				format = new SimpleDateFormat("yyyy");
				entry.set(3, format.format(birthday));

				entry.set(4, "" + newProgeny.getAge());
				entry.set(5, "" + (Progeny.getLevels(newProgeny).size() + 1));

				//Update the child details table with details of newly updated child
				tableData.set(entryNum, entry);
				tableModel.fireTableDataChanged();
			} catch (ParseException e1) {
				return;
			}
		} catch (JSONFailureException e1) {
			//TODO: add exception handling, perhaps a popup?
		}
	}
}