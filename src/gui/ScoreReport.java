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
import service.FriendService;
import service.GameService;
import service.ProgenyService;
import service.UserService;
import ttable.LevelProgeny;

/**
 * The class ScoreReport, a populated BackgroundPanel.
 * Displays the results of a drill game
 * 
 * @author Taylor Calder
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ScoreReport extends BackgroundPanel {

	/**
	 * Instantiates a ScoreReport instance.
	 * 
	 */
	@SuppressWarnings("unused")
	private int level, time, timeLeft, average;
	private String averageMessage;

	public ScoreReport(boolean win, int timeArg, int timeLeftArg, int levelArg,
			int incorrect) {

		// Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());


		// If the player beat the level, try to update progress
		if (win) {
			try {
				GameService.saveGame(Controller.getCurrentProgeny(), levelArg,
						incorrect, 0, timeLeftArg);

			} catch (JSONFailureException e) {
				// Error pop-up if attempt is unsuccessful
				new GeneralDialogue(e.getMessages(), "Progress Not Saved :(", 1);
			}
		}

		// Update the progeny's level if they beat a level higher than their current level
		if (levelArg >= Controller.getCurrentProgeny().getLevel() && win && levelArg != 12) {
			try {
				ProgenyService.changeLevel(Controller.getCurrentProgeny(), levelArg+1);
			} catch (JSONFailureException e) {
				new GeneralDialogue(e.getMessages(), "Couldn't Update Level :(", 1);
			}
			Controller.refreshCurrentProgeny();
		}

		level = levelArg;
		time = timeArg;
		timeLeft = timeLeftArg;

		// Create the components
		JLabel score1;
		if (win) {
			score1 = new JLabel("You finished in " + (timeArg - timeLeftArg)
					+ " seconds!!");
		} else {
			score1 = new JLabel("You didn't manage to finish...");
		}
		
		// Calculate average time for this level for the progeny's age group
		average = FriendService.getAverageDrillTime(level, ProgenyService.getAge(Controller.getCurrentProgeny().getBirthDate()));
		
		if (average > 0) {
			averageMessage = ("The average time for your age and this level is " + average
					+ " seconds.");
		}
		else {
			averageMessage = ("Sorry, we weren't able to compare to you anyone!");
		}
		
		// Display message with the average
		JLabel score2 = new JLabel(averageMessage);

		JButton fbB = new JButton("Post this to Facebook!");
		JButton levelB;

		// Allow progeny to play the level game only if they beat the drill game
		if (win) {
			levelB = new JButton("Play the Level Game!");
			levelB.addActionListener(new ToLevelGame());
		} else {
			levelB = new JButton("Try Again?");
			levelB.addActionListener(new TryAgain(level));
		}

		// Setup level game button
		levelB.setMaximumSize(new Dimension(200, 20));
		levelB.setMinimumSize(new Dimension(200, 20));
		levelB.setPreferredSize(new Dimension(200, 20));

		// Setup post to facebook button
		fbB.setMaximumSize(new Dimension(200, 20));
		fbB.setMinimumSize(new Dimension(200, 20));
		fbB.setPreferredSize(new Dimension(200, 20));
		fbB.addActionListener(new PostToFacebook(this));

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
//	private static int getLevel() {
//		// TODO Auto-generated method stub
//		return level;
//	}

	/**
	 * The class ToLevelGame, an action listener.
	 * 
	 * @author Taylor Calder
	 * @version 1.0
	 */
	class ToLevelGame implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Instructions inst;
			try {
			inst = new Instructions(Controller.getCurrentProgeny().getLevels()
						.get(level - 1));
			}
			catch (Exception e2) {
				LevelProgeny prog = new LevelProgeny();
				prog.setLevelNumber(level);
				inst = new Instructions(prog);
			}
			Controller.setScreen(inst);
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
						.get(levelNum - 1));
			} catch (Exception e2) {
				LevelProgeny prog = new LevelProgeny();
				prog.setLevelNumber(levelNum);
				screen = new Drill(prog);
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

		ScoreReport scoreReport;
		
		public PostToFacebook(ScoreReport report) {
			this.scoreReport = report;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			try {
				UserService.postMessage(new String("" + Controller.getCurrentProgeny().getFirstName() + " just mastered the number " + level + " times table!"));
			} catch (JSONFailureException e1) {
				new GeneralDialogue(e1.getMessages(), "JSON Error", 1);
			}
			
		}

	}

}
