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
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import service.ProgenyService;
import ttable.Progeny;

/**
 * The class Welcome, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class Welcome extends BackgroundPanel {

	/**
	 * Instantiates a Welcome instance.
	 * 
	 */
	public Welcome() {
		super(0, new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/welcome.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("Welcome!"), c);
		}

		JButton ok = new JButton();

		c.gridy = 1;
		c.insets = new Insets(25,100,0,100);
		ArrayList<Progeny> progenyList;
		try {
			progenyList = ProgenyService.getProgenies();
		}
		catch (Exception e) {
			progenyList = new ArrayList<Progeny>();
		}
		Vector<String> names = new Vector<String>();
		if (progenyList != null) {
			for (int i = 0; i < progenyList.size(); i++) {
				names.add(progenyList.get(i).getFirstName());
			}
		}
		JComboBox<String> nameSelector = new JComboBox<String>(names);
		if (names.size() > 0) {
			JLabel label = new JLabel("Please select a player to begin.");
			label.setFont(Controller.getFont().deriveFont(Font.PLAIN, 26));
			add(label,c);
			
			//nameSelector.setFont(Controller.getFont().deriveFont(Font.PLAIN, 26));
			c.gridy = 2;
			add(nameSelector, c);
		}
		else {
			JLabel label = new JLabel("Please click settings to add children to the game.");
			label.setFont(Controller.getFont().deriveFont(Font.PLAIN, 26));
			add(label, c);
			ok.setVisible(false);
		}

		c.gridy = 3;
		c.insets = new Insets(0,100,0,100);
		ok.setContentAreaFilled(false);
		ok.setBorderPainted(false);
		ok.addActionListener(new SelectProgeny(nameSelector, progenyList));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/buttons/ok.png"));
			ok.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			ok.setText("Ok");
		}
		add(ok, c);
	}
}

/**
 * The class SelectProgeny, an action listener.
 * 
 * @author James Anderson
 *
 */
class SelectProgeny implements ActionListener {

	/** The progeny selection drop down */
	private JComboBox<String> progenySelector;

	/** The array of progeny */
	private ArrayList<Progeny> progenyList;

	/**
	 * Instantiates a SelectProgeny instance.
	 * 
	 * @param progenySelector		the progeny selector
	 * @param progenyList			the progeny array
	 */
	public SelectProgeny(JComboBox<String> progenySelector, ArrayList<Progeny> progenyList) {
		this.progenyList = progenyList;
		this.progenySelector = progenySelector;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
			int selection = progenySelector.getSelectedIndex();
			Controller.setCurrentProgeny(progenyList.get(selection));
			Controller.setScreen(new MainMenu());		
	}
}
