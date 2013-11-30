package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import json.JSONFailureException;
import service.UserService;
import ttable.User;

/**
 * The class ScoreREport, a populated BackgroundPanel.
 * 
 * @author Taylor Calder
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ScoreReportF extends BackgroundPanel {

	/**
	 * Instantiates a PasswordReset instance.
	 * 
	 */
	
	private static int score;
	public String scoreStr;
	public Font font;
	
	
	public ScoreReportF(FGame game, int s) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		font = new Font(Font.SERIF, Font.BOLD, 60);
		
		score = s;
		
		//Create the components
		JLabel score1 = new JLabel("Your score is:");

		scoreStr = String.format("%05d", score);

		repaint();
		
		JLabel score2 = new JLabel("Congrats!");
		
		JButton fbB = new JButton("Post your score to Facebook!");

		fbB.setMaximumSize(new Dimension(200,20));
		fbB.setMinimumSize(new Dimension(200,20));
		fbB.setPreferredSize(new Dimension(200,20));
		fbB.addActionListener(new PostToFacebookF(this));
		
		score1.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
		score2.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
		
		
		//Add the components to the view
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,50,25,50);
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		add(score1, c);		//Score message 1
		
		c.gridy += 1;
		add(new JLabel(""), c);
		c.gridy += 1;
		add(new JLabel(""), c);
		c.gridy += 1;
		add(new JLabel(""), c);
		
		add(score2, c);		//Score message 2

		c.gridwidth = 1;
		c.gridy++;
		c.gridy++;
		add(fbB, c);		// Facebook button


	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(scoreStr, 326, 235);
		g.drawString(scoreStr, 324, 235);
		g.drawString(scoreStr, 324, 236);
		g.drawString(scoreStr, 324, 234);
		g.setColor(Color.black);
		g.drawString(scoreStr, 325, 235);

	
	}
	
	/**
	 * Posts the user's score to Facebook
	 * 
	 * @author Taylor
	 */
	class PostToFacebookF implements ActionListener {
		
		ScoreReportF scoreReport;
		
		public PostToFacebookF(ScoreReportF report) {
			this.scoreReport = report;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			try {
				UserService.postMessage("" + Controller.getCurrentProgeny().getFirstName() + " just scored " + scoreReport.score + " points on the level game!");
			} catch (JSONFailureException e1) {
				// TODO Auto-generated catch block
				new GeneralDialogue(e1.getMessages(), "JSON Error", 1);
			}
			
		}


	}
	
}
