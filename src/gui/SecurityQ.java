package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import json.JSONFailureException;
import ttable.User;

/**
 * The class SecurityQ, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 2.0
 */
@SuppressWarnings("serial")
public class SecurityQ extends BackgroundPanel {

	/**
	 * Instantiates a SecurityQ instance.
	 * 
	 * @param controller	the controller
	 */
	public SecurityQ(Controller controller) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		
		try{
		//Create components
		JLabel chooseQ = new JLabel("Please answer the following security question: ");
		JLabel question = new JLabel(User.getSecurityQuestions().get(User.getSecurityQuestionNumber()));
		JTextField answerField = new JTextField("-- Answer --");
		JButton update = new JButton("Update");
		
		//Add action listeners
		answerField.addMouseListener(new SelectAllTextOnClick(answerField));
		update.addActionListener(new PressUpdate2(controller, answerField));
		
		//Limit the number of characters that can be input into each field
		((AbstractDocument) answerField.getDocument()).setDocumentFilter(new DocumentLengthFilter(30));


		//Add the components to the view
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50,50,0,50);
		c.gridx = 0;
		c.gridy = 0;
		add(chooseQ, c);
		
		c.insets = new Insets(5,50,0,50);
		c.gridy = 1;
		add(question, c);
		
		c.gridy = 2;
		add(answerField, c);
		
		c.insets = new Insets(0,50,50,50);
		c.fill = GridBagConstraints.NONE;
		c.gridy = 3;
		add(update, c);
		} catch (JSONFailureException e) {
			JPanel screen = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			int gridY = 1;
			ArrayList<String> errors = e.getMessages();
			c.gridx = 0;
			c.gridy = gridY;
			for (int i = 0; i < errors.size(); i++) {
				c.gridy = gridY;
				JLabel label = new JLabel(errors.get(i).toString());
				label.setForeground(Color.RED);
				label.setFont(controller.getFont().deriveFont(Font.PLAIN, 18));
				screen.add(label, c);
				gridY++;
			}
			controller.setScreen(screen);
		}
	}
}

class PressUpdate2 implements ActionListener {
	
	/** The controller. */
	private Controller controller;
	
	/** The answer field. */
	private JTextField answerField;
	
	/**
	 * Instantiates a PressUpdate2 instance.
	 * 
	 * @param controller		the controller
	 * @param answerField		the answer field
	 */
	public PressUpdate2(Controller controller, JTextField answerField) {
		this.controller = controller;
		this.answerField = answerField;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			User.testSecurityQuestion(answerField.getText());
			controller.setScreen(new PasswordReset(controller));
		} catch (JSONFailureException e1) {
			ArrayList<String> errors = e1.getMessages();
			if (errors.get(0).equals("Could not verify answer")) {
				
			}
			else {
				//TODO: add exception handling, popup?
			}
		}
	}
}
