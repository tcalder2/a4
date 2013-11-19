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
public class ScoreReport extends BackgroundPanel {

	/**
	 * Instantiates a PasswordReset instance.
	 * 
	 * @param controller	the controller
	 */
	public ScoreReport(Controller controller, int correct, int level) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		//Create the components
		JLabel score1 = new JLabel("You got " + correct + " out of 12 questions right.");
		JLabel score2 = new JLabel("<Old Score Goes Here> ");
		JLabel score3 = new JLabel("<Post to Facebook Goes Here>");
		
		score1.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
		score2.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
		score3.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
		
		
		//Add the components to the view
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(25,50,0,50);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(score1, c);		//Score message 1

		c.insets = new Insets(0,50,0,0);
		c.anchor = GridBagConstraints.EAST;
		c.gridy = 1;
		add(score2, c);		//Score message 2

		c.gridy = 2;
		add(score3, c);		//Score message 3

	}
	
}