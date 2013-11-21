package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import service.UserService;
import json.JSONFailureException;

/**
 * The class PasswordReset, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Taylor Calder
 * @version 2.0
 */
@SuppressWarnings("serial")
public class PasswordReset extends BackgroundPanel {

	/**
	 * Instantiates a PasswordReset instance.
	 * 
	 */
	public PasswordReset(ArrayList<String> errors) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		//Create the components
		JLabel instruct = new JLabel("Please complete the following to reset your password.");
		JLabel oldLabel = new JLabel("Old password: ");
		JLabel newLabel = new JLabel("New password: ");
		JLabel retypeLabel = new JLabel("Retype password: ");
		JPasswordField oldField = new JPasswordField("000000");
		JPasswordField newField = new JPasswordField("000000");
		JPasswordField retypeField = new JPasswordField("000000");
		JButton update = new JButton("Update");

		//Add action listeners
		oldField.addMouseListener(new SelectAllTextOnClick(oldField));
		newField.addMouseListener(new SelectAllTextOnClick(newField));
		retypeField.addMouseListener(new SelectAllTextOnClick(retypeField));
		update.addActionListener(new PressUpdate3(oldField, newField, retypeField));

		//Limit the number of characters that can be input into each field
		((AbstractDocument) oldField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
		((AbstractDocument) newField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
		((AbstractDocument) retypeField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));

		//Add the components to the view
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(25,50,0,50);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(instruct, c);		//Label with instructions for the user

		c.insets = new Insets(0,50,0,0);
		c.anchor = GridBagConstraints.EAST;
		c.gridy = 1;
		add(oldLabel, c);		//Label for the old password field

		c.gridy = 2;
		add(newLabel, c);		//Label for the new password field

		c.gridy = 3;
		add(retypeLabel, c);	//Label for the field to retype the password

		c.insets = new Insets(25,0,0,50);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 1;
		add(oldField, c);		//The old password field

		c.insets = new Insets(0,0,0,50);
		c.gridy = 2;
		add(newField, c);		//The new password field

		c.gridy = 3;
		add(retypeField, c);	//The field to retype the new password to confirm

		c.insets = new Insets(0,0,0,50);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 4;
		add(update, c);			//The update button

		//Loop through the error messages and print them to screen
		c.gridx = 0;
		c.gridwidth = 2;
		if (!errors.equals(null)) {
			for (int i = 0; i < errors.size(); i++) {
				//Set the y alignment down one line each time.
				JLabel label = new JLabel(errors.get(i).toString());
				label.setForeground(Color.RED);
				label.setFont(Controller.getFont().deriveFont(Font.PLAIN, 18));
				add(label, c);
				c.gridy++;
			}
		}
	}
	
	/**
	 * Constructor without error messages.
	 * 
	 */
	public PasswordReset() {
		this(new ArrayList<String>());
	}
}

/**
 * The class PressUpdate3, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressUpdate3 implements ActionListener {
		
	/** The old password field. */
	private JPasswordField oldField;
	
	/** The new password field. */
	private JPasswordField newField;
	
	/** The field to retype the new password to confirm. */
	private JPasswordField retypeField;

	/**
	 * Instantiates a PressUpdate3 instance.
	 * 
	 * @param oldField		the field to enter the old password
	 * @param newField		the field to enter the new password
	 * @param retypeField	the field to re-enter the new password to confirm
	 */
	public PressUpdate3(JPasswordField oldField, JPasswordField newField, JPasswordField retypeField) {
		super();
		this.oldField = oldField;
		this.newField = newField;
		this.retypeField = retypeField;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		//Create character arrays to hold passwords
		char[] oldPwd = oldField.getPassword();
		char[] newPwd = newField.getPassword();
		char[] retypePwd = retypeField.getPassword();

		//Convert the passwords to strings and zero out the old arrays
		String oldPwdS = "";
		for (int i = 0; i < oldPwd.length; i++) {
			oldPwdS += oldPwd[i];
			oldPwd[i] = 0;
		}

		String newPwdS = "";
		for (int i = 0; i < newPwd.length; i++) {
			newPwdS += newPwd[i];
			newPwd[i] = 0;
		}

		String retypePwdS = "";
		for (int i = 0; i < retypePwd.length; i++) {
			retypePwdS += retypePwd[i];
			retypePwd[i] = 0;
		}

		// Create an array to hold the error messages to be passed on exception to the new screen
		ArrayList<String> errors = new ArrayList<String>();
		if (newPwdS.equals(retypePwdS)) {
			try {
				UserService.resetPassword(oldPwdS, newPwdS);
				Controller.setScreen(new MainMenu());
				oldPwdS = "000000";
				newPwdS = "000000";
				retypePwdS = "000000";
			} catch (JSONFailureException e) {
				errors = e.getMessages();
				Controller.setScreen(new PasswordReset());
			}

		} else {
			newField.setBackground(Color.PINK);
			retypeField.setBackground(Color.PINK);
			errors.add("Passwords do not match");
			Controller.setScreen(new PasswordReset());
		}
	}

}
