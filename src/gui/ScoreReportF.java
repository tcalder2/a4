package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
	
	public ScoreReportF(FGame game, int s) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		score = s;
		
		//Create the components
		JLabel score1 = new JLabel("Your score is " + score + "!!" );
		JLabel score2 = new JLabel("Your previous high score was " + "<highscore goes here>");
		
		JButton fbB = new JButton("Post your score to Facebook!");

		fbB.setMaximumSize(new Dimension(200,20));
		fbB.setMinimumSize(new Dimension(200,20));
		fbB.setPreferredSize(new Dimension(200,20));
		fbB.addActionListener(new PostToFacebookF());
		
		score1.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
		score2.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
		
		
		//Add the components to the view
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,50,25,50);
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		add(score1, c);		//Score message 1

		c.gridy++;
		add(score2, c);		//Score message 2

//		c.gridy++;
//		add(score3, c);		//Score message 3

		c.gridwidth = 1;
		c.gridy++;
		c.gridy++;
		add(fbB, c);		// Facebook button


	}
	
	
	class PostToFacebookF implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			// post to facebook code goes here
		}


	}
	
}
