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

	/** The old password field. */
	private JPasswordField oldField;

	/** The new password field. */
	private JPasswordField newField;

	/** The field to retype the new password to confirm. */
	private JPasswordField retypeField;

	/** Security question answer. */
	private JTextField answerField;

	/** The type of password reset, 1 is using the old password, 2 is using the security question. */
	private int version;

	/**
	 * Instantiates a PasswordReset instance.
	 * 
	 */
	public PasswordReset(int version, ArrayList<String> errors) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		try {
			//Create the components
			JLabel instruct = new JLabel("Please complete the following");
			JLabel instruct2 = new JLabel("to reset your password:");
			JLabel oldLabel = new JLabel("Old password: ");
			JLabel newLabel = new JLabel("New password: ");
			JLabel retypeLabel = new JLabel("Retype password: ");
			oldField = new JPasswordField("000000");
			newField = new JPasswordField("000000");
			retypeField = new JPasswordField("000000");
			JLabel question = new JLabel(UserService.getSecurityQuestions().get(UserService.getQuestionIndex()));
			answerField = new JTextField("-- Answer --");
			JButton reset = new JButton();
			
			//Add action listeners
			oldField.addMouseListener(new SelectAllTextOnClick(oldField));
			newField.addMouseListener(new SelectAllTextOnClick(newField));
			retypeField.addMouseListener(new SelectAllTextOnClick(retypeField));
			answerField.addMouseListener(new SelectAllTextOnClick(answerField));
			reset.addActionListener(new PressUpdate5(this));
			if (version == 1) {
				retypeField.addKeyListener(new EnterListener(reset));
			}
			else {
				answerField.addKeyListener(new EnterListener(reset));
			}

			//Set label alignment
			oldField.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
			newField.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
			retypeField.setAlignmentX(JLabel.RIGHT_ALIGNMENT);

			//Limit the number of characters that can be input into each field
			((AbstractDocument) oldField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
			((AbstractDocument) newField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
			((AbstractDocument) retypeField.getDocument()).setDocumentFilter(new DocumentLengthFilter(6));
			((AbstractDocument) answerField.getDocument()).setDocumentFilter(new DocumentLengthFilter(30));
			
			//Set label font
			instruct.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
			instruct2.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
			oldLabel.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
			newLabel.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
			retypeLabel.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
			question.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));

			//Setup the reset button
			reset.setContentAreaFilled(false);
			reset.setBorderPainted(false);
			try {
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/buttons/reset.png"));
				reset.setIcon(new ImageIcon(img));
			} catch (IOException e) {
				reset.setText("Reset...");
			}

			//Add the components to the view
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(25,50,0,50);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 0;
			add(instruct, c);		//Label with instructions for the user
			
			c.insets = new Insets(10,50,0,50);
			c.gridy = 1;
			add(instruct2, c);		//Second line of instructions for user

			c.insets = new Insets(25,50,0,0);
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.EAST;
			c.gridwidth = 1;

			if (version == 1) {
				c.gridy = 2;
				add(oldLabel, c);		//Label for the old password field
				
				c.insets = new Insets(10,50,0,0);
			}

			c.gridy = 3;
			add(newLabel, c);		//Label for the new password field

			c.gridy = 4;
			add(retypeLabel, c);	//Label for the field to retype the password

			c.insets = new Insets(25,0,0,50);
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 2;
			c.gridx = 1;

			if (version == 1) {
				c.gridy = 2;
				add(oldField, c);		//The old password field
				
				c.insets = new Insets(10,0,0,50);
			}

			c.gridy = 3;
			add(newField, c);		//The new password field

			c.gridy = 4;
			add(retypeField, c);	//The field to retype the new password to confirm

			if (version == 2) {
				c.insets = new Insets(25,50,0,50);
				c.gridwidth = 3;
				c.gridy = 5;
				c.gridx = 0;
				add(question, c);
				
				c.insets = new Insets(10,50,0,50);
				c.gridy = 6;
				add(answerField, c);
			}

			c.insets = new Insets(10,0,0,50);
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.EAST;
			c.gridwidth = 1;
			c.gridx = 2;
			c.gridy = 7;
			add(reset, c);			//The update button

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
		} catch (Exception e) {
			JPanel screen = new JPanel();
			JLabel error = new JLabel(e.getMessage());
			error.setForeground(Color.RED);
			error.setFont(Controller.getFont().deriveFont(Font.PLAIN, 18));
			screen.add(error);
			Controller.setScreen(screen);
		}
	}

	/**
	 * Constructor without error messages.
	 * 
	 * @param version		the type of password reset
	 */
	public PasswordReset(int version) {
		this(version, new ArrayList<String>());
	}

	/**
	 * Evaluates the input and processes the update
	 */
	public void evaluate() {

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

		// Create an array to hold the error messages to be passed on exception to the new screen
		ArrayList<String> errors = new ArrayList<String>();
		
		//If old and new password match
		if (newPwdS.equals(retypePwdS)) {
			try {
				if (version == 1) {
					UserService.resetPassword(oldPwdS, newPwdS);
				}
				else {
					UserService.setPasswordWithQ(answer, newPwdS);
				}
				
				new GeneralDialogue("Password was successfully reset.", "Success", 3);
				
				oldPwdS = "000000";
				newPwdS = "000000";
				retypePwdS = "000000";
				Controller.setScreen(new LockScreen());
			} catch (JSONFailureException e) {
				if (version == 1) {
					Controller.setScreen(new PasswordReset(version, e.getMessages()));
				}
				else if (version == 2) {
					Controller.setScreen(new MainMenu());
					new GeneralDialogue("Authenification failed, password reset to default.", "Passord Reset", 1);
				}
			}

		} else {
			newField.setBackground(Color.PINK);
			retypeField.setBackground(Color.PINK);
			errors.add("Passwords do not match");
			Controller.setScreen(new PasswordReset(version, errors));
		}
	}
}

/**
 * The class PressUpdate3, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class PressUpdate5 implements ActionListener {

	/** The security pane. */
	private PasswordReset screen;

	/**
	 * Instantiates a PressUpdate3 instance.
	 * 
	 * @param security		the password reset screen.
	 */
	public PressUpdate5(PasswordReset screen) {
		super();
		this.screen = screen;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		screen.evaluate();
	}

}
