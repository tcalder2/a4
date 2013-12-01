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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.text.AbstractDocument;

import service.UserService;
import json.JSONFailureException;

/**
 * The class LockScreen, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class LockScreen extends BackgroundPanel {

	/**
	 * Instantiates a basic LockScreen instance.
	 *
	 */
	public LockScreen() {

		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());
		
		//Add the components to the panel
		this.setup();  

		//Add the lock image
		this.addImg(1);
	}

	/**
	 * Instantiate a LockScreen instance that displays error messages.
	 * 
	 * @param errors		the list of errors
	 */
	public LockScreen(ArrayList<String> errors) {

		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());

		//Add the components to the panel
		setup();

		//Loop through the error messages and print them to screen
		int gridY = 2;
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		for (int i = 0; i < errors.size(); i++) {
			//Set the y alignment down one line each time.
			c.gridy = gridY;
			JLabel label = new JLabel(errors.get(i).toString());
			label.setForeground(Color.RED);
			label.setFont(Controller.getFont().deriveFont(Font.PLAIN, 18));
			add(label, c);
			gridY++;
		}

		//Add the lock image under the error messages
		addImg(gridY);
	}

	/**
	 * Populates the view with basic components in common between the versions of this display
	 * 
	 */
	public void setup() {

		//Create a GridBagConstraints instance to control the layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;

		//Create the components
		JLabel pwdLabel = new JLabel("Password: ");
		JButton ok = new JButton();
		JPasswordField passwordField = new JPasswordField("000000");
		JButton reset = new JButton();

		//Limit the number of characters that can be input into the password field
		((AbstractDocument) passwordField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
		
		//Set component attributes and graphics
		pwdLabel.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));

		ok.setContentAreaFilled(false);
		ok.setBorderPainted(false);
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/buttons/ok.png"));
			ok.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			ok.setText("Ok");
		}

		reset.setContentAreaFilled(false);
		reset.setBorderPainted(false);
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/buttons/reset.png"));
			reset.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			reset.setText("Reset...");
		}
		
		//Add action listeners
		passwordField.addKeyListener(new EnterListener(ok));
		passwordField.addMouseListener(new SelectAllTextOnClick(passwordField));
		ok.addActionListener(new PressOk(passwordField));
		reset.addActionListener(new PressReset());

		//Add the components to the view
		c.insets = new Insets(100,20,50,0);
		c.gridx = 0;
		add(pwdLabel, c);		//The password field label

		c.insets = new Insets(100,0,50,0);
		c.gridx = 1;
		add(passwordField, c);	//The password field

		c.insets = new Insets(100,0,50,0);
		c.gridx = 2;
		add(ok, c);				//The OK button

		c.insets = new Insets(100,0,50,20);
		c.gridx = 3;
		add(reset, c);			//The reset button
	}

	/**
	 * Adds the lock image to the screen.
	 * 
	 * @param gridY		the Y alignment for the added image
	 */
	public void addImg(int gridY) {

		//Create a GridBagConstraints instance to control the layout
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,100,0);
		c.gridx = 1;
		c.gridy = gridY;

		//Download and add the lock graphic to the display
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/lock.png"));
			add(new JLabel(new ImageIcon(img)), c);

		} catch (IOException e) {
			//If there is an error loading the graphic, add a text placeholder
			add(new JLabel("Lock Graphic"), c);
		}
	}
}

/**
 * The class PressOk, an action listener.
 * 
 * @author James Anderson
 *
 */
class PressOk implements ActionListener {
	
	/** The password field. */
	private JPasswordField pwdf;

	/**
	 * Instantiates a PressOk instance.
	 * 
	 * @param passwordField		the password field
	 */
	public PressOk(JPasswordField passwordField) {
		super();
		pwdf = passwordField;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		//Get the password as a character array
		char[] pwds = pwdf.getPassword();

		//Convert the password to a string
		String pwd = "";
		for (int i=0; i < pwds.length; i++) {
			pwd += pwds[i];
			pwds[i] = 0;
		}

		try {
			//Check if the password is correct, will throw if not
			UserService.authenticate(pwd);
			
			//On successful login go to the settings screen
			Controller.setScreen(new Settings());

		} catch (JSONFailureException e1) {
			//If exception is thrown it means authentication failed

			//If more than three failed attempts go to the password reset with security question validation
			if (e1.getMessages().get(0).compareTo("Too many login attempts") == 0) {
				Controller.setScreen(new PasswordReset(2));
			}
			
			//If there are attempts remaining return to lock screen but add error messages to display
			else {
				Controller.setScreen(new LockScreen(e1.getMessages()));
			}
		}
	}
}

/**
 * The class PressReset, an action listener.
 * 
 * @author James Anderson
 *
 */
class PressReset implements ActionListener {

	/**
	 * Instantiates a PressReset instance.
	 * 
	 */
	public PressReset() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		//Swap the screen for the password reset screen
		Controller.setScreen(new PasswordReset(1));
	}
}
