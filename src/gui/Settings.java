package gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;

import json.JSONFailureException;
import ttable.Progeny;
import ttable.User;

/**
 * The Class Settings.
 */
public class Settings extends BackgroundPanel {

	/**
	 * Instantiates a new settings.
	 *
	 * @param controller the controller
	 */
	public Settings(Controller controller) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(10,150,10,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/settings.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("Settings"), c);
		}

		c.gridy = 1;
		c.insets = new Insets(0,50,0,50);
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.add("Child Settings", childTab(controller));
		tabPane.add("Level Settings", levelSetTab(controller));
		tabPane.add("Security Settings", securityTab(controller));
		add(tabPane, c);
	}

	public JPanel childTab(Controller controller) {
		JPanel childTab = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,0,3);
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;

		String[] sortOptions = {"Age","Name"};
		JComboBox<String> sortMenu = new JComboBox<String>(sortOptions);
		childTab.add(sortMenu, c);

		c.insets = new Insets(10,0,0,75);
		c.gridx = 5;
		JButton sortButton = new JButton("Sort");
		childTab.add(sortButton, c);

		String[] columnNames = {"Name"," ","Birthday"," ","Age","Level"};
		try {
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


			DefaultTableModel tableModel = new DefaultTableModel(children, new Vector<String>(Arrays.asList(columnNames)));
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
			childTab.add(scroll,c);

			JLabel addChild = new JLabel("Add Child:");
			JPanel adpanel = new JPanel();
			adpanel.add(addChild);
			c.gridwidth = 2;
			c.ipady = 0;
			c.insets = new Insets(10,75,0,0);
			c.gridy = 2;
			c.gridx = 0;
			childTab.add(addChild, c);

			JButton add = new JButton("Add");

			JTextField nameInput = new JTextField("--Name--");
			nameInput.addKeyListener(new EnterListener(add));
			c.insets = new Insets(0,75,0,0);
			c.gridwidth = 2;
			c.ipadx = 250;
			c.gridy = 3;
			c.gridx = 0;
			childTab.add(nameInput, c);

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
			childTab.add(days, c);

			String[] months = {"Month","January","February","March","April","May","June","July","August","September","October","November","December"};
			JComboBox<String> month = new JComboBox<String>(months);
			c.gridx = 3;
			childTab.add(month, c);

			Vector<String> y = new Vector<String>();
			y.add("Year");
			for (int i = 2013; i > 1980; i--) {
				y.add("" + i);
			}
			JComboBox<String> years = new JComboBox<String>(y);
			c.gridx = 4;
			childTab.add(years, c);

			c.insets = new Insets(0,0,0,75);
			c.gridx = 5;
			childTab.add(add, c);

			JLabel editChild = new JLabel("Review and Modify Child:");
			c.insets = new Insets(20,75,0,0);
			c.gridwidth = 2;
			c.gridy = 4;
			c.gridx = 0;
			childTab.add(editChild, c);

			Vector<String> childnames = new Vector<String>();
			childnames.add("Wallace");
			childnames.add("Grommit");
			JComboBox<String> childSelect = new JComboBox<String>(childnames);
			c.insets = new Insets(0,75,0,0);
			c.gridy = 5;
			c.gridx = 0;
			childTab.add(childSelect, c);

			JComboBox<String> days2 = new JComboBox<String>(d);
			c.insets = new Insets(0,0,0,0);
			c.gridwidth = 1;
			c.gridx = 2;
			childTab.add(days2, c);

			JComboBox<String> month2 = new JComboBox<String>(months);
			c.gridx = 3;
			childTab.add(month2, c);

			JComboBox<String> years2 = new JComboBox<String>(y);
			c.gridx = 4;
			childTab.add(years2, c);

			Vector<String> l = new Vector<String>();
			for (int i = 1; i < 13; i++) {
				l.add("Level " + i);
			}
			JComboBox<String> levels = new JComboBox<String>(l);
			c.insets = new Insets(0,0,0,75);
			c.gridx = 5;
			childTab.add(levels, c);

			JButton update = new JButton("Update");
			c.insets = new Insets(0,0,0,0);
			c.gridy = 6;
			c.gridx = 3;
			childTab.add(update, c);

			JButton remove = new JButton("Remove");
			c.gridx = 4;
			childTab.add(remove, c);

			JButton stats = new JButton("Progress");
			stats.addActionListener(new PressProgress(childSelect, controller, this));
			c.insets = new Insets(0,0,0,75);
			c.gridx = 5;
			childTab.add(stats, c);

			return childTab;
			
		} catch(JSONFailureException e) {
			JPanel errPanel = new JPanel(new GridBagLayout());
			GridBagConstraints c1 = new GridBagConstraints();
			int gridY = 0;
			ArrayList<String> errors = e.getMessages();
			Iterator<String> error = errors.iterator();
			c1.gridy = gridY;
			errPanel.add(new JLabel(error.toString()));
			while (error.hasNext()) {
				gridY++;
				c1.gridy = gridY;
				JLabel label = new JLabel(error.next().toString());
				label.setForeground(Color.RED);
				label.setFont(controller.getFont().deriveFont(Font.PLAIN, 18));
				errPanel.add(label, c);
			}
			return errPanel;
		}
	}
	
	public JPanel levelSetTab(Controller controller) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,25,0,0);
		c.gridwidth = 2;
		c.gridy = 1;
		c.gridx = 0;
		panel.add(new JLabel("Drill Settings:"), c);

		c.gridwidth = 1;
		c.gridy = 2;
		panel.add(new JLabel("Time per question:"), c);

		Vector<String> t = new Vector<String>();
		for (int i = 5; i <= 120; i += 5) {
			t.add(i + " sec");
		}
		JComboBox<String> time = new JComboBox<String>(t);
		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		panel.add(time, c);

		c.gridwidth = 2;
		c.gridx = 2;
		panel.add(new JLabel("Number of Errors per Level:"), c);

		Vector<String> err = new Vector<String>();
		for (int i = 0; i < 10; i++) {
			err.add("" + i);
		}
		JComboBox<String> errors = new JComboBox<String>(err);
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,75);
		c.gridx = 4;
		panel.add(errors, c);

		c.insets = new Insets(0,75,0,0);
		c.gridx = 0;
		c.gridy = 3;
		panel.add(new JLabel("Testing mode:"), c);

		ButtonGroup teststate = new ButtonGroup();

		JRadioButton testoff = new JRadioButton("Off");
		teststate.add(testoff);
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		panel.add(testoff, c);

		JRadioButton teston = new JRadioButton("On");
		teststate.add(teston);
		c.gridx = 2;
		panel.add(teston, c);

		JButton apply = new JButton("Apply");
		c.gridy = 4;
		c.gridx = 4;
		panel.add(apply, c);
		
		return panel;
	}
	
	public JPanel securityTab(Controller controller) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		return panel;
	}
}

class PressProgress implements ActionListener {

	private Controller controller;
	private JComboBox<String> childnames;
	private Settings settingsPane;

	public PressProgress(JComboBox<String> childnames, Controller controller, Settings settingsPane) {
		super();
		this.controller = controller;
		this.childnames = childnames;
		this.settingsPane = settingsPane;
	}

	public void actionPerformed(ActionEvent e) {
		String name = new String(childnames.getSelectedItem().toString());
		//Use name to lookup progeny
		ChildProgress screen = new ChildProgress(controller, settingsPane/*, Progeny child*/);
		controller.setScreen(screen);
	}
}