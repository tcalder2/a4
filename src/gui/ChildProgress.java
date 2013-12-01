package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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

import ttable.LevelProgeny;
import ttable.Progeny;

/**
 * The class ChildProgress, a screen displaying the specified child's progress in the game
 * in tabular form.
 * 
 * @author James Anderson
 * @author Taylor Calder
 * @version 2.0
 */
@SuppressWarnings("serial")
public class ChildProgress extends JPanel {

	/**
	 * Constructor requiring the superseding settings pane, the alternately displayed child
	 * settings tab, and the child to be displayed to be passed as arguments.
	 *
	 * @param settingsPane		the superseding settings pane.
	 * @param childSettings		the child settings tab that the progress screen is to replace.
	 * @param child				the progeny object for the child whose progress is to be
	 * 							displayed.
	 */
	public ChildProgress(Settings settingsPane, ChildSettingsTab childSettings, Progeny child) {

		//Create the panel with a GridBagLayout
		super(new GridBagLayout());

		//Set the panel to be transparent
		setOpaque(false);
		
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
		backArrow.addActionListener(new BackToSettings(settingsPane, childSettings));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/b_arrow.png"));
			backArrow.setIcon(new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			backArrow.setText("<--");
		}
		add(backArrow, c);

		//Add a label to state the child whose progress is currently being displayed
		JLabel title = new JLabel(child.getFirstName() + "'s Progress");
		title.setFont(new Font("Serif", Font.BOLD, 26));
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,75,5,75);
		c.gridy = 1;
		add(title, c);

		//Create vector of column headers
		Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"Level","Attempts","Final Time","Final Mistakes"}));

		//Create a vector structure containing the child's progress details
		Vector<Vector<String>> progress = new Vector<Vector<String>>();
		
		//Create and populate table showing details of child progress
		DefaultTableModel tableModel = new DefaultTableModel(progress, columnNames);
		tableModel.setNumRows(12);
		
		Object[][] array = new Object[12][4];
		String[] header = {"Level","Attempts","Final Time","Final Mistakes"};
		JTable table = new JTable(array, header);
		
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
		
		LevelProgeny hold = null;
		
		// I don't know how any of that vectorized string shit works so I'm just using an array
		// Sorry for overwriting some of your work :-/
		
		for (int i = 0; i < 12; i++) {
			
			hold = child.getLevelProgenys().get(i);
			// Level
			array[i][0] = new String("" + (i+1));
			
			// Attempts
			array[i][1] = new String("" + hold.getAttempts());
			
			// Final Time
			if (hold.getCompletionTime() == 0) {
				array[i][2] = new String("-");

			}
			else {
				array[i][2] = new String("" + (hold.getCompletionTime()/60) + ":" + (hold.getCompletionTime()%60));
			}
				
						
			// Final Mistakes
			if (hold.getCompletionTime() == 0) {
				array[i][3] = new String("-");
			}
			else {
				array[i][3] = new String("" + hold.getMistakes());
				
			}
			
		}
		
		table.setOpaque(false);
		table.setRowHeight(18);
		table.setShowGrid(false);
		table.setDragEnabled(false);
		table.setRowSorter(null);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setEnabled(false);
		table.setCellSelectionEnabled(false);
		table.setFont(new Font("Serif", Font.PLAIN, 14));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setDefaultRenderer(renderer);
		table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 14));

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
	}

}

/**
 * The BackToSettings class, an action listener responsible for swapping the contents of the
 * tab containing the child progress, back to the child settings screen.
 * 
 * @author James Anderson
 * @version 1.0
 */
class BackToSettings implements ActionListener {

	/** The superseding settings pane. */
	private Settings settingsPane;

	/** The child settings tab that alternatively occupies the current tab. */
	private ChildSettingsTab childSettingsTab;

	/**
	 * Constructor requiring the superseding settings pane and the alternatively displayed
	 * child settings tab be passed as arguments.
	 * 
	 * @param settingsPane		the current settings pane instance
	 * @param childSettings	the current child settings tab instance
	 */
	public BackToSettings(Settings settingsPane, ChildSettingsTab childSettings) {
		super();
		this.settingsPane = settingsPane;
		this.childSettingsTab = childSettings;
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
