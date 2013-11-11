package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import json.JSONFailureException;
import ttable.Progeny;
import ttable.User;

public class ChildSettingsTab extends JPanel {

	public ChildSettingsTab(Controller controller, Settings settings) {
		super(new GridBagLayout());
		setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,0,0,3);
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
			v.add(p.getBirthday().get(0));
			v.add(p.getBirthday().get(1));
			v.add(p.getBirthday().get(2));
			v.add(p.getAge());
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
			//col.setWidth(100);
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
		c.insets = new Insets(0,75,0,75);
		c.gridwidth = 6;
		c.gridy = 1;
		c.gridx = 0;
		c.ipady = 80;
		add(scroll,c);

		JLabel addChild = new JLabel("Add Child:");
		add(addChild);
		c.gridwidth = 2;
		c.ipady = 0;
		c.insets = new Insets(10,75,0,0);
		c.gridy = 2;
		c.gridx = 0;
		add(addChild, c);

		JButton add = new JButton("Add");

		JTextField nameInput = new JTextField("--Name--");
		nameInput.addKeyListener(new EnterListener(add));
		c.insets = new Insets(0,75,0,0);
		c.gridwidth = 2;
		c.ipadx = 250;
		c.gridy = 3;
		c.gridx = 0;
		add(nameInput, c);

		Vector<String> d = new Vector<String>();
		d.add("Day");
		for (int i = 1; i <= 31; i++) {
			d.add("" + i);
		}
		JComboBox<String> days = new JComboBox<String>(d);
		c.gridwidth = 1;
		c.ipadx = 0;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 2;
		add(days, c);

		String[] months = {"Month","January","February","March","April","May","June","July","August","September","October","November","December"};
		JComboBox<String> month = new JComboBox<String>(months);
		c.gridx = 3;
		add(month, c);

		Vector<String> y = new Vector<String>();
		y.add("Year");
		for (int i = 2013; i > 1980; i--) {
			y.add("" + i);
		}
		JComboBox<String> years = new JComboBox<String>(y);
		c.gridx = 4;
		add(years, c);

		c.insets = new Insets(0,0,0,75);
		c.gridx = 5;
		add(add, c);

		JLabel editChild = new JLabel("Review and Modify Child:");
		c.insets = new Insets(20,75,0,0);
		c.gridwidth = 2;
		c.gridy = 4;
		c.gridx = 0;
		add(editChild, c);

		Vector<String> childnames = new Vector<String>();
		childnames.add("Wallace");
		childnames.add("Grommit");
		JComboBox<String> childSelect = new JComboBox<String>(childnames);
		c.insets = new Insets(0,75,0,0);
		c.gridy = 5;
		c.gridx = 0;
		add(childSelect, c);

		JComboBox<String> days2 = new JComboBox<String>(d);
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 1;
		c.gridx = 2;
		add(days2, c);

		JComboBox<String> month2 = new JComboBox<String>(months);
		c.gridx = 3;
		add(month2, c);

		JComboBox<String> years2 = new JComboBox<String>(y);
		c.gridx = 4;
		add(years2, c);

		Vector<String> l = new Vector<String>();
		for (int i = 1; i < 13; i++) {
			l.add("Level " + i);
		}
		JComboBox<String> levels = new JComboBox<String>(l);
		c.insets = new Insets(0,0,0,75);
		c.gridx = 5;
		add(levels, c);

		JButton update = new JButton("Update");
		c.insets = new Insets(0,0,0,0);
		c.gridy = 6;
		c.gridx = 3;
		add(update, c);

		JButton remove = new JButton("Remove");
		c.gridx = 4;
		add(remove, c);

		JButton stats = new JButton("Progress");
		stats.addActionListener(new PressProgress(childSelect, controller, settings, this));
		c.insets = new Insets(0,0,0,75);
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
