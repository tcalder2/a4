package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AbstractDocument;

import json.JSONFailureException;
import ttable.User;

/**
 * The class PasswordReset, a populated BackgroundPanel.
 * 
 * @author James Anderson
 *
 */
@SuppressWarnings("serial")
public class PasswordReset extends BackgroundPanel {

	/**
	 * Instantiates a PasswordReset instance.
	 * 
	 * @param controller	the controller
	 */
	public PasswordReset(Controller controller, ArrayList<String> errors) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		//Create the components
		JLabel instruct = new JLabel("Please complete the folling to reset your password.");
		JLabel oldLabel = new JLabel("Old password: ");
		JLabel newLabel = new JLabel("New password: ");
		JLabel retypeLabel = new JLabel("Retype password: ");
		JPasswordField oldField = new JPasswordField("000000");
		JPasswordField newField = new JPasswordField("000000");
		JPasswordField retypeField = new JPasswordField("000000");
		JButton update = new JButton("Update");

		//Add action listeners
		update.addActionListener(new PressUpdate3(controller, oldField, newField, retypeField));

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
		add(instruct, c);

		c.insets = new Insets(0,50,0,0);
		c.anchor = GridBagConstraints.EAST;
		c.gridy = 1;
		add(oldLabel, c);

		c.gridy = 2;
		add(newLabel, c);

		c.gridy = 3;
		add(retypeLabel, c);

		c.insets = new Insets(25,0,0,50);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 1;
		add(oldField, c);

		c.insets = new Insets(0,0,0,50);
		c.gridy = 2;
		add(newField, c);

		c.gridy = 3;
		add(retypeField, c);

		c.insets = new Insets(0,0,0,50);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 4;
		add(update, c);

		//Loop through the error messages and print them to screen
		c.gridx = 0;
		c.gridwidth = 2;
		if (!errors.equals(null)) {
			for (int i = 0; i < errors.size(); i++) {
				//Set the y alignment down one line each time.
				JLabel label = new JLabel(errors.get(i).toString());
				label.setForeground(Color.RED);
				label.setFont(controller.getFont().deriveFont(Font.PLAIN, 18));
				add(label, c);
				c.gridy++;
			}
		}
	}
}

class PressUpdate3 implements ActionListener {
	private Controller controller;
	private JPasswordField oldField;
	private JPasswordField newField;
	private JPasswordField retypeField;

	public PressUpdate3(Controller controller, JPasswordField oldField, JPasswordField newField, JPasswordField retypeField) {
		super();
		this.controller = controller;
		this.oldField = oldField;
		this.newField = newField;
		this.retypeField = retypeField;
	}

	public void actionPerformed(ActionEvent evt) {
		char[] oldPwd = oldField.getPassword();
		char[] newPwd = newField.getPassword();
		char[] retypePwd = retypeField.getPassword();

		String oldPwdS = "";
		for (int i = 0; i < oldPwd.length; i++) {
			oldPwdS += oldPwd[i];
		}

		String newPwdS = "";
		for (int i = 0; i < newPwd.length; i++) {
			newPwdS += newPwd[i];
		}

		String retypePwdS = "";
		for (int i = 0; i < retypePwd.length; i++) {
			retypePwdS += retypePwd[i];
		}

		ArrayList<String> errors = new ArrayList<String>();
		if (newPwdS.equals(retypePwdS)) {
			try {
				User.resetPassword(oldPwdS, newPwdS);
				controller.setScreen(new MainMenu(controller));
			} catch (JSONFailureException e) {
				controller.setScreen(new PasswordReset(controller, errors));
			}

		} else {
			newField.setBackground(Color.PINK);
			retypeField.setBackground(Color.PINK);
			errors.add("Passwords do not match");
			controller.setScreen(new PasswordReset(controller, errors));
		}
	}

}
