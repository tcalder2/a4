package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import json.JSONFailureException;
import ttable.User;

/**
 * The class SecurityTab, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 2.0
 */
@SuppressWarnings("serial")
public class SecurityTab extends JPanel {
	
	public SecurityTab() {
		
		//Call the super constructor with a Gridbag
		super(new GridBagLayout());
		
		//Make the panel transparent
		setOpaque(false);
		
		//Create the components
		JLabel oldLabel = new JLabel("Old password: ");
		JLabel newLabel = new JLabel("New password: ");
		JLabel retypeLabel = new JLabel("Retype password: ");
		JPasswordField oldField = new JPasswordField("000000");
		JPasswordField newField = new JPasswordField("000000");
		JPasswordField retypeField = new JPasswordField("000000");
		JLabel chooseQ = new JLabel("Please choose a security question: ");
		JComboBox<String> questions = new JComboBox<String>();
		JTextField answerField = new JTextField("-- Answer --");
		JButton update = new JButton("Update");
		
		//Limit the number of characters that can be input into each field
		((AbstractDocument) oldField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
		((AbstractDocument) newField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
		((AbstractDocument) retypeField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
		((AbstractDocument) answerField.getDocument()).setDocumentFilter(new DocumentLengthFilter(30));

		//Add action listeners
		oldField.addMouseListener(new SelectAllTextOnClick(oldField));
		newField.addMouseListener(new SelectAllTextOnClick(newField));
		retypeField.addMouseListener(new SelectAllTextOnClick(retypeField));
		answerField.addMouseListener(new SelectAllTextOnClick(answerField));
		update.addActionListener(new PressUpdate(oldField, newField, retypeField, answerField));
		
		//Add the components to the view
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(25,50,0,0);
		c.gridx = 0;
		c.gridy = 0;
		add(oldLabel, c);
		
		c.insets = new Insets(0,50,0,0);
		c.gridy = 1;
		add(newLabel, c);
		
		c.gridy = 2;
		add(retypeLabel, c);
		
		c.insets = new Insets(25,50,0,0);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridy = 3;
		add(chooseQ, c);
		
		c.insets = new Insets(0,50,0,50);
		c.gridwidth = 3;
		c.gridy = 4;
		add(questions, c);
		
		c.gridy = 5;
		add(answerField, c);
		
		c.insets = new Insets(25,0,0,50);
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = 0;
		add(oldField, c);
		
		c.insets = new Insets(0,0,0,50);
		c.gridy = 1;
		add(newField, c);
		
		c.gridy = 2;
		add(retypeField, c);
		
		c.insets = new Insets(0,0,0,50);
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 6;
		add(update, c);
	}
	
}

class PressUpdate4 implements ActionListener {

	/**
	 * The GUI applet controller.
	 */
	Controller controller;
	
	/**
	 * The field for the input of the old password.
	 */
	JPasswordField oldField;
	
	/**
	 * The field for the input of the new password.
	 */
	JPasswordField newField;
	
	/**
	 * The field for the input of the new password a second time to confirm correct entry.
	 */
	JPasswordField retypeField;
	
	/**
	 * The field for the input of the new security question answer.
	 */
	JTextField answerField;
	
	/**
	 * 
	 * @param oldField
	 * @param newField
	 * @param retypePassword
	 * @param answerField
	 */
	public PressUpdate4 (Controller controller, JPasswordField oldField, JPasswordField newField, 
			JPasswordField retypeField, JTextField answerField) {
		this.controller = controller;
		this.oldField = oldField;
		this.newField = newField;
		this.retypeField = retypeField;
		this.answerField = answerField;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
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
						User.resetPassword(oldPwdS, newPwdS);
						controller.setScreen(new MainMenu(controller));
						oldPwdS = "000000";
						newPwdS = "000000";
						retypePwdS = "000000";
					} catch (JSONFailureException e) {
						errors = e.getMessages();
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