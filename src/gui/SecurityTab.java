package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

import service.UserService;
import json.JSONFailureException;

/**
 * The class SecurityTab, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 2.0
 */
@SuppressWarnings("serial")
public class SecurityTab extends JPanel {

	/** The settings screen. */
	private Settings settings;

	/** The field for the input of the old password. */
	private JPasswordField oldField;

	/** The field for the input of the new password. */
	private JPasswordField newField;

	/** The field for the input of the new password a second time to confirm correct entry. */
	private JPasswordField retypeField;

	/** The drop down containing the possible security questions. */
	private JComboBox<String> questions;

	/** The field for the input of the new security question answer. */
	private JTextField answerField;

	/**
	 * Constructor requiring the settings pane that contains this tab be passed, or null if it
	 * is in its own window.
	 * 
	 * @param settings		the settings pane that contains this tab, or null if it is in its
	 * 						own window.
	 */
	public SecurityTab(Settings settings) {

		//Call the super constructor with a GridBagLayout
		super(new GridBagLayout());

		//Store a link to the settings pane that contains this tab, or null if it is in its own window
		this.settings = settings;

		//Make the panel transparent
		setOpaque(false);

		//Populate question list
		Vector<String> qList = new Vector<String>();
		try {
			qList = new Vector<String>(UserService.getSecurityQuestions());
		} catch (JSONFailureException e) {
			new GeneralDialogue(e.getMessages(), "JSON Error", 1);
		}

		//Create the components
		JLabel oldLabel = new JLabel("Old password: ");
		JLabel newLabel = new JLabel("New password: ");
		JLabel retypeLabel = new JLabel("Retype password: ");
		oldField = new JPasswordField("000000");
		newField = new JPasswordField("000000");
		retypeField = new JPasswordField("000000");
		JLabel chooseQ = new JLabel("Please choose a security question: ");
		questions = new JComboBox<String>(qList);
		answerField = new JTextField("-- Answer --");
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
		update.addActionListener(new PressUpdate4(this));

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

	/**
	 * Validates and updates the password and security question settings for the user.
	 * 
	 */
	public void update() {
		//Create character arrays to hold passwords
		char[] oldPwd = oldField.getPassword();
		char[] newPwd = newField.getPassword();
		char[] retypePwd = retypeField.getPassword();

		//Create a string to hold security question answer
		String answer = answerField.getText();

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

		if (answer.equals("") || answer.equals("-- Answer --")) {
			answerField.setBackground(Color.PINK);
		}
		else if (!newPwdS.equals(retypePwdS)) {
			newField.setBackground(Color.PINK);
			retypeField.setBackground(Color.PINK);
		} 
		else {
			if (questions.getSelectedIndex() >= 0 && questions.getSelectedIndex() < 3) {
				try {
					UserService.setQuestion(("" + questions.getSelectedIndex()), oldPwdS);
					UserService.setAnswer(answer, oldPwdS);
					UserService.setPassword(oldPwdS, newPwdS);
					
					new GeneralDialogue("Security information updated successfully.", "Success!", 3);
					
					oldPwdS = "000000";
					newPwdS = "000000";
					retypePwdS = "000000";

					if (settings == null) {
						Controller.setScreen(new Settings());
					}
					else {
						settings.changeTabContent(2, new SecurityTab(settings));
					}

				} catch (JSONFailureException e1) {
					new GeneralDialogue(e1.getMessages(), "JSON Error", 1);
				}
			}
		}
	}
}

class PressUpdate4 implements ActionListener {

	/** The related security tab instance. */
	private SecurityTab security;

	/**
	 * The action listener for the update button on the security tab.
	 * 
	 * @param security			the related security tab instance.
	 */
	public PressUpdate4 (SecurityTab security) {
		this.security = security;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		security.update();
	}
}
