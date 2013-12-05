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
import service.GameService;
import service.UserService;

/**
 * The class ScoreREport, a populated BackgroundPanel.
 * Displays a report of the child's score on the final4
 * game
 * 
 * @author Taylor Calder
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ScoreReportF extends BackgroundPanel {

	/**
	 * Instantiates a ScoreReportF
	 */
	private int score;
	private int highscore;
	public String scoreStr, highScoreStr;
	public Font font;
	
	
	public ScoreReportF(FGame game, int s) {

		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());

		font = new Font(Font.SERIF, Font.BOLD, 60);
		
		score = s;

		//Create the components
		JLabel score1 = new JLabel("Your score is:");
		scoreStr = String.format("%05d", score);
		highScoreStr = "";
		
		// Gets theoir current high score if applicable
		try {
			highscore = Controller.getCurrentProgeny().getFinalGameHighScore();
		}
		catch (NullPointerException e) {
			highscore = -1;
		}
		
		// Choses appropriate message depending on progeny performance
		if (score > highscore && highscore >= 0) {
			Controller.getCurrentProgeny().setFinalGameHighScore(score);
			highScoreStr = ("You beat your old high score! (" + String.format("%05d", highscore) + ")");
			try {
				GameService.saveFinalGame(Controller.getCurrentProgeny(), score);
				Controller.refreshCurrentProgeny();
			} catch (JSONFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (score <= highscore && highscore >= 0){
			highScoreStr = ("Try to beat your high score next time. (" + String.format("%05d", highscore) + ")");
		}
		else {
			highScoreStr = ("Try to beat your high score next time.");
		}
		
		
		JLabel score2 = new JLabel(highScoreStr);
		repaint();
		
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
	
	/**
	 * Draws the progeny's score
	 */
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
				UserService.postMessage(new String("" + Controller.getCurrentProgeny().getFirstName() + " just scored " + score + " points on the final game!"));
			} catch (JSONFailureException e1) {
				// TODO Auto-generated catch block
				new GeneralDialogue(e1.getMessages(), "JSON Error", 1);
			}
			
		}


	}
	
}