package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import json.JSONFailureException;
import service.GameService;
import ttable.LevelProgeny;
import ttable.User;

/**
 * The class ScoreREport, a populated BackgroundPanel.
 * 
 * @author Taylor Calder
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ScoreReport extends BackgroundPanel {

	/**
	 * Instantiates a PasswordReset instance.
	 * 
	 */

	private static int level, time, timeLeft, average;

	public ScoreReport(boolean win, int timeArg, int timeLeftArg, int levelArg,
			int incorrect) {

		// Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png",
				new GridBagLayout());

		// Save the game state
		if (win) {
			try {
				GameService.saveGame(Controller.getCurrentProgeny(), levelArg,
						incorrect, 0, timeLeftArg);
			} catch (JSONFailureException e) {
				// Error pop-up if attempt is unsuccessful
				new GeneralDialogue(e.getMessages(), "JSON Error", 1);
			}
		}

		level = levelArg;
		time = timeArg;
		timeLeft = timeLeftArg;

		// TODO server call to get average score
		average = 31;

		// Create the components
		int max = 12;
		if (Controller.getTestMode()) {
			max = 4;
		}
		JLabel score1;
		if (win) {
			score1 = new JLabel("You finished in " + (time - timeLeft)
					+ " seconds!!");
		} else {
			score1 = new JLabel("You didn't manage to finish...");
		}
		JLabel score2 = new JLabel("The average time is " + average
				+ " seconds.");

		JButton fbB = new JButton("Post this to Facebook!");
		JButton levelB;

		if (win) {
			levelB = new JButton("Play the Level Game!");
			levelB.addActionListener(new ToLevelGame());
		} else {
			levelB = new JButton("Try Again?");
			levelB.addActionListener(new TryAgain(level));
		}

		levelB.setMaximumSize(new Dimension(200, 20));
		levelB.setMinimumSize(new Dimension(200, 20));
		levelB.setPreferredSize(new Dimension(200, 20));

		fbB.setMaximumSize(new Dimension(200, 20));
		fbB.setMinimumSize(new Dimension(200, 20));
		fbB.setPreferredSize(new Dimension(200, 20));
		fbB.addActionListener(new PostToFacebook());

		score1.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));
		score2.setFont(Controller.getFont().deriveFont(Font.PLAIN, 28));

		// Add the components to the view
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 50, 25, 50);
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		add(score1, c); // Score message 1

		c.gridy++;
		add(score2, c); // Score message 2

		// c.gridy++;
		// add(score3, c); //Score message 3

		c.gridwidth = 1;
		c.gridy++;
		c.gridy++;
		add(fbB, c); // Facebook button
		if (!win) {
			fbB.setVisible(false);
		}

		c.gridy++;
		c.gridy++;
		add(levelB, c); // Level game button

	}

	private static int getLevel() {
		// TODO Auto-generated method stub
		return level;
	}

	/**
	 * The class ToLevelGame, an action listener.
	 * 
	 * @author Taylor Calder
	 * @version 1.0
	 */
	class ToLevelGame implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			LGame screen;
			try {
				screen = new LGame(Controller.getCurrentProgeny().getLevels()
						.get(ScoreReport.getLevel() - 1));
			} catch (Exception e2) {
				LevelProgeny prog = new LevelProgeny();
				prog.setLevelNumber(ScoreReport.getLevel());
				screen = new LGame(prog);
			}
			Controller.setScreen(screen);
		}

	}

	/**
	 * The class TryAgain, an action listener.
	 * 
	 * @author Taylor Calder
	 * @version 1.0
	 */
	class TryAgain implements ActionListener {

		/** The level number. */
		private int levelNum;

		/**
		 * Instantiates a StartDrill instance.
		 * 
		 * @param levelNum
		 *            the level number
		 */
		public TryAgain(int levelNum) {
			super();
			this.levelNum = levelNum;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			Drill screen;
			try {
				screen = new Drill(Controller.getCurrentProgeny().getLevels()
						.get(levelNum - 1), User.background);
			} catch (Exception e2) {
				LevelProgeny prog = new LevelProgeny();
				prog.setLevelNumber(levelNum);
				screen = new Drill(prog, User.background);
			}
			Controller.setScreen(screen);
		}
	}

	/**
	 * Posts score to Facebook
	 * 
	 * @author Taylor Calder
	 */
	class PostToFacebook implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// post to facebook code goes here
		}

	}

}
