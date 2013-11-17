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
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * The class FGameMenu, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class FGameMenu extends BackgroundPanel {

	/**
	 * Instantiates a FGame instance.
	 *
	 * @param controller	the controller
	 */
	public FGameMenu(Controller controller) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		//Create a GridBagConstraints instance to control the layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(50,150,5,150);
		c.gridx = 0;
		c.gridy = 0;

		//Load and add final game title graphic
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/fgame.png"));
			add(new JLabel(new ImageIcon(img)), c);

			//If there is an error loading the graphic display a text placeholder
		} catch (IOException e) {
			add(new JLabel("Drill Menu"), c);
		}

		//Create and set display attributes of play button
		JButton play = new JButton();
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/fgame/playgame.png"));
			play.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			play.setText("PLAY GAME!");
		}

		//Add action listener for play button
		play.addActionListener(new StartFinal(controller));


		//Add the play button
		c.ipady = 50;
		c.gridwidth = 1;
		c.insets = new Insets(10,150,0,150);
		c.gridy = 1;
		add(play, c);

		//Load and add the high scores title graphic
		c.gridwidth = GridBagConstraints.CENTER;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10,150,0,150);
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/highscores.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("High Scores"), c);
		}

		//Create table header titles
		Vector<String> columnNames = new Vector<String>(Arrays.asList(new String[]{"High Scores"}));

		//Get child's high scores 
		int[] tmp = controller.getCurrentProgeny().getHighScores();
		Vector<Vector<String>> highScores = new Vector<Vector<String>>();
		for (int i = 0; i < 5; i++) {
			Vector<String> v = new Vector<String>();
			v.add("" + tmp[i]);
			highScores.add(v);
		}

		//Create the high scores table
		DefaultTableModel tableModel = new DefaultTableModel(highScores, columnNames);
		tableModel.setRowCount(5);
		JTable table = new JTable(tableModel) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};

		//Set the high scores table display attributes
		table.setOpaque(false);
		table.setRowHeight(32);
		table.setShowGrid(false);
		table.setFont(controller.getFont().deriveFont(Font.BOLD, 30));
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		renderer.setOpaque(false);
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setCellRenderer(renderer);

		//Add the table to the panel
		c.insets = new Insets(0,150,0,150);
		c.gridy = 3;
		c.ipady = 0;
		add(table,c);
	}
}

/**
 * The class StartFinal, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class StartFinal implements ActionListener {

	/** The controller. */
	private Controller controller;

	/**
	 * Instantiates a StartFinal instance.
	 * 
	 * @param controller	the controller
	 */
	public StartFinal(Controller controller) {
		super();
		this.controller = controller;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		FGame menu = new FGame(controller);
		controller.setScreen(menu);
	}
}
