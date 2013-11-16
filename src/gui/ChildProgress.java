package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import json.JSONFailureException;
import ttable.LevelProgeny;
import ttable.Progeny;

/**
 * The ChildProgress class.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class ChildProgress extends JPanel {

	/**
	 * Instantiates a new child progress.
	 *
	 * @param controller the controller
	 * @param settingsPane the settings pane
	 */
	public ChildProgress(Controller controller, Settings settingsPane, ChildSettingsTab childSettingsTab, Progeny child, ArrayList<String> errors) {
		
		//Create the panel with a GridBagLayout
		super(new GridBagLayout());
		
		//Set the panel to be transparent
		setOpaque(false);

		//Create instance of GridBagConstraints to control layout
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		try {
			//If errors list is populated then go to exception handling
			if (errors.size() > 0) {
				throw new JSONFailureException(errors);
			}

			//Create the back button (with back arrow graphic) to allow quick return to the child settings tab
			JButton backArrow = new JButton();
			backArrow.setContentAreaFilled(false);
			backArrow.setBorderPainted(false);
			backArrow.addActionListener(new BackToSettings(settingsPane, childSettingsTab));
			try {
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/main/backarr.png"));
				backArrow.setIcon(new ImageIcon(img));
			} catch (IOException e) {
				backArrow.setText("<--");
			}
			add(backArrow, c);

			//Add a label to state the child whose progress is currently being displayed
			JLabel title = new JLabel(child.getFirstName() + "'s Progress");
			title.setFont(controller.getFont().deriveFont(Font.BOLD, 32));
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(10,75,5,75);
			c.gridy = 1;
			add(title, c);

			//Create vector of column headers
			Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"Level","Attempts","Final Time (sec)","Final Mistakes"}));

			//Create a vector structure containing the child's progress details
			Vector<Vector<String>> progress = new Vector<Vector<String>>();
			ArrayList<LevelProgeny> levels = controller.getCurrentProgeny().getLevels();
			for (int i = 0; i < levels.size(); i++) {
				Vector<String> v = new Vector<String>();
				v.add("" + levels.get(i).getLevel());
				v.add("" + levels.get(i).getAttempts());
				v.add("" + levels.get(i).getFinalCompletionTime());
				v.add("" + levels.get(i).getMistakes());
				progress.add(v);
				Vector<String> v1 = new Vector<String>();
				v1.add("2");
				v1.add("5");
				v1.add("26");
				v1.add("2");
				progress.add(v1);
			}

			//Create and populate table showing details of child progress
			DefaultTableModel tableModel = new DefaultTableModel(progress, columnNames);
			tableModel.setNumRows(12);
			JTable table = new JTable(tableModel) {
				public boolean isCellEditable(int rowIndex, int colIndex) {
					return false;
				}
			};

			//Set table appearance attributes
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			renderer.setOpaque(false);
			TableColumn col = null;
			for (int i = 0; i < table.getColumnCount(); i++) {
				col = table.getColumnModel().getColumn(i);
				col.setCellRenderer(renderer);
				col.setWidth(100);
			}
			table.setOpaque(false);
			table.setRowHeight(24);
			table.setShowGrid(false);
			table.getTableHeader().setDefaultRenderer(renderer);
			table.setFont(controller.getFont().deriveFont(Font.BOLD, 18));
			table.getTableHeader().setFont(controller.getFont().deriveFont(Font.BOLD, 18));

			//Embed the table in a scroll pane and add to panel.
			JScrollPane scroll = new JScrollPane(table);
			scroll.setOpaque(false);
			scroll.getViewport().setOpaque(false);
			c.insets = new Insets(0,75,0,75);
			c.gridwidth = 1;
			c.gridy = 2;
			c.ipady = 300;
			c.ipadx = 500;
			add(scroll,c);

		} catch (JSONFailureException e) {
			//If there is a communication error, display a panel detailing errors instead
			ArrayList<String> errors2 = e.getMessages();
			int gridY = 0;
			Iterator<String> error = errors2.iterator();
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

/**
 * The BackToSettings class, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class BackToSettings implements ActionListener {

	/** The current settings pane instance. */
	private Settings settingsPane;

	/** The current child settings tab instance. */
	private ChildSettingsTab childSettingsTab;

	/**
	 * Instantiates a new BackToSettings listener.
	 * 
	 * @param settingsPane		the current settings pane instance
	 * @param childSettingsTab	the current child settings tab instance
	 */
	public BackToSettings(Settings settingsPane, ChildSettingsTab childSettingsTab) {
		super();
		this.settingsPane = settingsPane;
		this.childSettingsTab = childSettingsTab;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		settingsPane.changeTabContent(0, childSettingsTab);
	}
}
