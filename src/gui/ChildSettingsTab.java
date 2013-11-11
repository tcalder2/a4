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
			Vector<Vector<String>> children = new Vector<Vector<String>>();
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
				children.add(v);
			}


			DefaultTableModel tableModel = new DefaultTableModel(children, columnNames);
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

			JTextField nameInput = new JTextField("--Name--");
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

			JButton update = new JButton("Update");
			c.insets = new Insets(0,0,25,0);
			c.gridy = 6;
			c.gridx = 3;
			add(update, c);

			JButton remove = new JButton("Remove");
			c.gridx = 4;
			add(remove, c);

			JButton stats = new JButton("Progress");
			stats.addActionListener(new PressProgress(childSelect, controller, settings, this));
			c.insets = new Insets(0,0,25,50);
			c.gridx = 5;
			add(stats, c);

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
			int currLevel = Progeny.getLevels(User.getProgenies().get(childSelect.getSelectedIndex())).size();
			DateFormat f = new SimpleDateFormat("yyyy");
			year.setSelectedItem(f.format(birth));
			f = new SimpleDateFormat("MMMM");
			month.setSelectedItem(f.format(birth));
			f = new SimpleDateFormat("d");
			day.setSelectedItem(f.format(birth));
			level.setSelectedIndex(currLevel);
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
			System.out.print("error");
		}
		GregorianCalendar cal = new GregorianCalendar(yCal.get(Calendar.YEAR),mCal.get(Calendar.MONTH),1);

		Vector<String> d = new Vector<String>();
		for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
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
			String[] months = new String[13];
			months[0] = "Month";
			for (int i = 0; i < 12; i++) {
				months[i+1] = new DateFormatSymbols().getMonths()[i];
			}
			month.setModel(new DefaultComboBoxModel<String>(months));
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