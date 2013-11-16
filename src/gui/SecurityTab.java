package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

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
