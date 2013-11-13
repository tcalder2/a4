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
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import json.JSONFailureException;
import ttable.Progeny;
import ttable.User;

/**
 * The class Welcome, a populated BackgroundPanel.
 * 
 * @author James Anderson
 *
 */
@SuppressWarnings("serial")
public class Welcome extends BackgroundPanel {
	
	/**
	 * Instantiates a Welcome instance.
	 * 
	 * @param controller	the controller
	 */
	public Welcome(Controller controller) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		
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
		controller.getUser();
		try {
			ArrayList<Progeny> progenyList = User.getProgeny();
			Vector<String> names = new Vector<String>();
			for (int i = 0; i < progenyList.size(); i++) {
				names.add(progenyList.get(i).getFirstName());
			}
			JComboBox<String> nameSelector = new JComboBox<String>(names);
			if (names.size() > 0) {
				nameSelector.setFont(controller.getFont().deriveFont(Font.PLAIN, 18));
				add(nameSelector, c);
			}
			else {
				JLabel label = new JLabel("Please click settings to add children to the game.");
				label.setFont(controller.getFont().deriveFont(Font.PLAIN, 26));
				add(label, c);
				//ok.setVisible(false);
			}
			
			c.gridy = 2;
			c.insets = new Insets(0,100,0,100);
			ok.setContentAreaFilled(false);
			ok.setBorderPainted(false);
			ok.addActionListener(new SelectProgeny(controller, nameSelector, progenyList));
			try {
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/buttons/ok.png"));
				ok.setIcon(new ImageIcon(img));
			} catch (IOException e) {
				ok.setText("Ok");
			}
			add(ok, c);
			
			
		} catch (JSONFailureException e) {
			JPanel screen = new JPanel();
			int gridY = 1;
			ArrayList<String> errors = e.getMessages();
			Iterator<String> error = errors.iterator();
			c.gridx = 1;
			c.gridy = gridY;
			screen.add(new JLabel(error.toString()));
			while (error.hasNext()) {
				gridY++;
				c.gridy = gridY;
				JLabel label = new JLabel(error.next().toString());
				label.setForeground(Color.RED);
				label.setFont(controller.getFont().deriveFont(Font.PLAIN, 18));
				screen.add(label, c);
			}
			controller.setScreen(screen);
		}
	}
}

/**
 * The class SelectProgeny, an action listener.
 * 
 * @author James Anderson
 *
 */
class SelectProgeny implements ActionListener {
	
	/** The controller */
	private Controller controller;
	
	/** The progeny selection drop down */
	private JComboBox<String> progenySelector;
	
	/** The array of progeny */
	private ArrayList<Progeny> progenyList;
	
	/**
	 * Instantiates a SelectProgeny instance.
	 * 
	 * @param controller		the controller
	 * @param progenySelect		the progeny selector
	 * @param progenyList		the progeny array
	 */
	public SelectProgeny(Controller controller, JComboBox<String> progenySelector, ArrayList<Progeny> progenyList) {
		this.controller = controller;
		this.progenyList = progenyList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*TODO: Reveal this code once the ability to add a child works
		
		int selection = progenySelector.getSelectedIndex();
		controller.setCurrentProgeny(progenyList.get(selection));
		
		*/
		controller.setScreen(new MainMenu(controller));
	}
}
