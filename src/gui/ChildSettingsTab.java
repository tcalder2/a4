package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import json.JSONFailureException;
import ttable.Level;
import ttable.Progeny;
import ttable.User;

public class ChildSettingsTab extends JPanel {

	private Progeny selectedProgeny;

	public ChildSettingsTab(Controller controller, Settings settings) {
		super(new GridBagLayout());
		setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(25,0,0,3);
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;

		/* TO BE REMOVED AFTER TESTING OF COLUMN HEADER SORT
		String[] sortOptions = {"Age","Name"};
		JComboBox<String> sortMenu = new JComboBox<String>(sortOptions);
		add(sortMenu, c);

		c.insets = new Insets(10,0,0,75);
		c.gridx = 5;
		JButton sortButton = new JButton("Sort");
		add(sortButton, c);
		 */
		try {
			Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"Name"," ","Birthday"," ","Age","Level"}));
			ArrayList<Progeny> progeny = User.getProgenies();
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


			DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
			JTable table = new JTable(tableModel) {
				public boolean isCellEditable(int rowIndex, int colIndex) {
					return false;
				}
			};

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

			JScrollPane scroll = new JScrollPane(table);
			scroll.setOpaque(false);
			scroll.getViewport().setOpaque(false);
			c.insets = new Insets(25,50,0,50);
			c.gridwidth = 6;
			c.gridy = 1;
			c.gridx = 0;
			c.ipady = 80;
			add(scroll,c);

			JLabel addChild = new JLabel("Add Child:");
			c.gridwidth = 2;
			c.ipady = 0;
			c.insets = new Insets(10,50,0,0);
			c.gridy = 2;
			c.gridx = 0;
			add(addChild, c);

			JButton add = new JButton("Add");

			JTextField nameInput = new JTextField("--First Name--");
			nameInput.addKeyListener(new EnterListener(add));
			c.insets = new Insets(0,50,0,0);
			c.gridwidth = 2;
			c.ipadx = 180;
			c.gridy = 3;
			c.gridx = 0;
			add(nameInput, c);

			Vector<String> y = new Vector<String>();
			for (int i = (new GregorianCalendar().get(Calendar.YEAR)); i > 1980; i--) {
				y.add("" + i);
			}
			JComboBox<String> year = new JComboBox<String>(y);
			JComboBox<String> month = new JComboBox<String>();
			JComboBox<String> day = new JComboBox<String>();

			year.addActionListener(new YearSelected(year, month, day));
			month.addActionListener(new MonthSelected(year, month, day));
			add.addActionListener(new PressAdd(nameInput, year, month, day, tableData, tableModel));

			c.gridwidth = 1;
			c.ipadx = 0;
			c.insets = new Insets(0,0,0,0);
			c.gridx = 2;
			add(year, c);
			c.gridx = 3;
			add(month, c);
			c.gridx = 4;
			add(day, c);

			c.insets = new Insets(0,0,0,50);
			c.gridx = 5;
			add(add, c);

			JLabel editChild = new JLabel("Review and Modify Child:");
			c.insets = new Insets(20,50,0,0);
			c.gridwidth = 2;
			c.gridy = 4;
			c.gridx = 0;
			add(editChild, c);

			ArrayList<Progeny> progenies = User.getProgenies();
			Vector<String> childNames = new Vector<String>();
			for (int i = 0; i < progenies.size(); i++) {
				childNames.add(progenies.get(i).getFirstName());
			}
			JComboBox<String> childSelect = new JComboBox<String>(childNames);
			JComboBox<String> year2 = new JComboBox<String>(y);

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
			JComboBox<String> month2 = new JComboBox<String>(m);
			JComboBox<String> day2 = new JComboBox<String>();
			updateDaysInMonth(year2, month2, day2);
			int maxLevel = 1;
			if (progenies.size() > 0) {
				maxLevel = Progeny.getLevels(progenies.get(childSelect.getSelectedIndex())).size();
			}
			Vector<String> l = new Vector<String>();
			for (int i = 1; i <= maxLevel; i++) {
				l.add("Level " + i);
			}
			JComboBox<String> level = new JComboBox<String>(l);

			setSelections(childSelect, year, month, day, level);
			year2.addActionListener(new YearSelected(year2, month2, day2));
			month2.addActionListener(new MonthSelected(year2, month2, day2));
			childSelect.addActionListener(new ChildSelected(childSelect, year2, month2, day2, level));

			c.insets = new Insets(0,50,0,0);
			c.gridy = 5;
			c.gridx = 0;
			add(childSelect, c);

			c.insets = new Insets(0,0,0,0);
			c.gridwidth = 1;
			c.gridx = 2;
			add(year2, c);

			c.gridx = 3;
			add(month2, c);

			c.gridx = 4;
			add(day2, c);

			c.insets = new Insets(0,0,0,50);
			c.gridx = 5;
			add(level, c);

			JButton progress = new JButton("View Progress");
			JButton remove = new JButton("Remove");
			JButton update = new JButton("Update");

			progress.addActionListener(new PressProgress(childSelect, controller, settings, this));
			remove.addActionListener(new PressRemove(childSelect, tableModel, tableData));

			c.insets = new Insets(15,0,25,70);
			c.gridy = 6;
			c.gridx = 2;
			c.gridwidth = 2;
			add(progress, c);

			c.insets = new Insets(15,0,25,0);
			c.gridx = 4;
			c.gridwidth = 1;
			add(remove, c);

			c.insets = new Insets(15,0,25,50);
			c.gridx = 5;
			add(update, c);

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

	public static void setSelections(JComboBox<String> childSelect, JComboBox<String> year, JComboBox<String> month, JComboBox<String> day, JComboBox<String> level) {
		try {
			Date birth = User.getProgenies().get(childSelect.getSelectedIndex()).getBirthday();
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

class PressProgress implements ActionListener {

	private Controller controller;
	private JComboBox<String> childSelector;
	private Settings settingsPane;
	private ChildSettingsTab childSettingsTab;

	public PressProgress(JComboBox<String> childSelector, Controller controller, Settings settingsPane, ChildSettingsTab childSettingsTab) {
		super();
		this.controller = controller;
		this.childSelector = childSelector;
		this.settingsPane = settingsPane;
		this.childSettingsTab = childSettingsTab;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			String selected = new String(childSelector.getSelectedItem().toString());
			ArrayList<String> errors = new ArrayList<String>();
			Progeny child = null;
			try {
				ArrayList<Progeny> progeny = User.getProgenies();
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

class MonthSelected implements ActionListener {

	private JComboBox<String> year;
	private JComboBox<String> month;
	private JComboBox<String> day;

	public MonthSelected(JComboBox<String> year, JComboBox<String> month, JComboBox<String> day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}
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

	public void actionPerformed(ActionEvent e) {
		try {
			Vector<String> l = new Vector<String>();
			for (int i = 1; i < Progeny.getLevels(User.getProgenies().get(childSelect.getSelectedIndex())).size(); i++) {
				l.add("Level " + i);
			}
			level.setModel(new DefaultComboBoxModel<String>(l));
			ChildSettingsTab.setSelections(childSelect, year, month, day, level);
		} catch(JSONFailureException e1) {
			/*NULL BODY*/
		}

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

	public void actionPerformed(ActionEvent e) {
		String firstName = "";
		year.setForeground(Color.RED);
		try {
			firstName = nameInput.getText();
			if (firstName.contains("--")) {
				throw new Exception();
			}
			nameInput.setBackground(Color.WHITE);
		} catch (Exception e1) {
			nameInput.setBackground(Color.PINK);
		}
		String birthdayStr = "";
		try {
			DateFormat format = new SimpleDateFormat("d");
			if (day.getItemCount() > 0) {
				Date tmp = format.parse((String)day.getSelectedItem());
				format = new SimpleDateFormat("dd");
				birthdayStr += format.format(tmp) + "/";
				day.setForeground(Color.BLACK);
			}
			else {
				throw new Exception();
			}
		} catch (Exception e1) {
			day.setForeground(Color.RED);
		}
		try {
			if (day.getItemCount() > 0) {
				DateFormat format = new SimpleDateFormat("MMMM");
				Date tmp = format.parse((String)month.getSelectedItem());
				format = new SimpleDateFormat("MM");
				birthdayStr += format.format(tmp) + "/";
				month.setForeground(Color.BLACK);
			}
			else {
				throw new Exception();
			}
		} catch (Exception e1) {
			month.setForeground(Color.RED);
		}
		try {
			if (month.getItemCount() > 0) {
				DateFormat format = new SimpleDateFormat("yyyy");
				Date tmp = format.parse((String)year.getSelectedItem());
				format = new SimpleDateFormat("yyyy");
				birthdayStr += format.format(tmp);
				year.setForeground(Color.BLACK);
			}
			else {
				throw new Exception();
			}
		} catch (Exception e1) {
			year.setForeground(Color.RED);
		}
		try {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date birthdate = format.parse(birthdayStr);
			Progeny.addProgeny(firstName, birthdate);
			ArrayList<Progeny> progenies = User.getProgenies();
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
			tableData.add(v);
			tableModel.fireTableDataChanged();
		} catch (ParseException e1) {
			return;
		} catch (JSONFailureException e2) {
			System.out.print("Error");
			//TODO: add exception handling
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
	
	public void actionPerformed(ActionEvent e) {
		String firstName = (String) childSelect.getSelectedItem();
		try {
			ArrayList<Progeny> progenies = User.getProgenies();
			for (int i = 0; i < progenies.size(); i++) {
				String name = progenies.get(i).getFirstName();
				if (name.equals(firstName)) {
					Progeny.deleteProgeny(progenies.get(i));
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
			// TODO: add exception handling
		}
	}
}