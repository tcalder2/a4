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
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
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
import service.GameService;
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
	 * @param settingsPane the settings pane
	 */
	public ChildProgress(Settings settingsPane, ChildSettingsTab childSettingsTab, Progeny child) {

		//Create the panel with a GridBagLayout
		super(new GridBagLayout());

		//Set the panel to be transparent
		setOpaque(false);

		try {
		
		//Create instance of GridBagConstraints to control layout
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0,5,0,0);
		c.gridwidth = 1;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		//Create the back button (with back arrow graphic) to allow quick return to the child settings tab
		JButton backArrow = new JButton();
		backArrow.setContentAreaFilled(false);
		backArrow.setBorderPainted(false);
		backArrow.addActionListener(new BackToSettings(settingsPane, childSettingsTab));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/b_arrow.png"));
			backArrow.setIcon(new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
		} catch (IOException e) {
			backArrow.setText("<--");
		}
		add(backArrow, c);

		//Add a label to state the child whose progress is currently being displayed
		JLabel title = new JLabel(child.getFirstName() + "'s Progress");
		title.setFont(Controller.getFont().deriveFont(Font.BOLD, 32));
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,75,5,75);
		c.gridy = 1;
		add(title, c);

		//Create vector of column headers
		Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"Level","Attempts","Final Time","Final Mistakes"}));

		//Create a vector structure containing the child's progress details
		Vector<Vector<String>> progress = new Vector<Vector<String>>();
		ArrayList<LevelProgeny> levels = GameService.getLevels(child);
		for (int i = 0; i < levels.size(); i++) {
			Vector<String> v = new Vector<String>();
			v.add("" + levels.get(i).getLevelNumber());
			v.add("" + levels.get(i).getAttempts());
			v.add("" + levels.get(i).getCompletionTime());
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
		table.setFont(Controller.getFont().deriveFont(Font.BOLD, 18));
		table.getTableHeader().setFont(Controller.getFont().deriveFont(Font.BOLD, 18));

		//Embed the table in a scroll pane and add to panel.
		JScrollPane scroll = new JScrollPane(table);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		c.insets = new Insets(0,75,10,75);
		c.gridwidth = 1;
		c.gridy = 2;
		c.ipady = 192;
		c.ipadx = 500;
		add(scroll,c);
		
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
