package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import ttable.LevelProgeny;

/**
 * The class Drill, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Drill extends BackgroundPanel {

	/** The controller. */
	private Controller controller;

	/** The level. */
	private LevelProgeny level;

	/** The number of lives remaining. */
	private int lives;

	/** The number of questions answered correctly. */
	private int correct;

	/** The number of questions answered incorrectly. */
	private int incorrect;

	/** The timer. */
	private JLabel timer;

	/** The incorr counter. */
	private JLabel incorrCounter;

	/** The corr counter. */
	private JLabel corrCounter;

	/** The mark img. */
	private JLabel markImg;

	/** The solution. */
	private JLabel solution;

	/** The next. */
	private JButton next;

	/** The submit. */
	private JButton submit;

	/** The answer field. */
	private JTextField answerField;

	/** The clock. */
	private Timer clock;

	/**
	 * Instantiates a new drill.
	 *
	 * @param controller the controller
	 * @param level the level
	 */
	public Drill(Controller controller, LevelProgeny level) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		this.controller = controller;
		this.level = level;
		lives = 5;
		correct = 0;
		incorrect = 0;

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.insets = new Insets(0,50,0,50);
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;

		JLabel livesCount = new JLabel();
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill/heart.png"));
			livesCount.setText(" x lives");
			livesCount.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			livesCount.setText("<3 x " + lives);
		}
		livesCount.setFont(Controller.getFont().deriveFont(Font.BOLD, 35));
		add(livesCount);

		timer = new JLabel("", SwingConstants.RIGHT);
		timer.setFont(Controller.getFont().deriveFont(Font.BOLD, 35));
		setTime(10);
		c.gridx = 4;
		c.anchor = GridBagConstraints.EAST;
		add(timer, c);

		JLabel question = new JLabel("1 x 12 = ");
		question.setFont(Controller.getFont().deriveFont(Font.BOLD, 60));
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(50,50,0,0);
		add(question, c);

		submit = new JButton("Submit");

		answerField = new JTextField();
		answerField.setFont(Controller.getFont().deriveFont(Font.BOLD, 60));
		answerField.addKeyListener(new EnterListener(submit));
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1;
		c.ipadx = 100;
		c.gridx = 2;
		c.insets = new Insets(50,0,0,0);
		add(answerField, c);

		submit.addActionListener(new Submit(this, answerField));
		c.gridx = 3;
		c.ipadx = 0;
		c.insets = new Insets(50,0,0,50);
		add(submit, c);

		clock = new Timer(1000, new TimerAction(controller, this));
		clock.start();
		JLabel markImg = new JLabel(" ");
		c.ipady = 60;
		c.gridx = 2;
		c.gridy = 2;
		c.insets = new Insets(0,0,0,0);
		add(markImg, c);

		solution = new JLabel(" ");
		solution.setFont(Controller.getFont().deriveFont(Font.BOLD, 35));
		c.ipady = 50;
		c.gridy = 3;
		add(solution, c);

		next = new JButton("Next");
		next.setVisible(false);
		c.gridy = 4;
		add(next, c);

		corrCounter = new JLabel("" + correct);
		corrCounter.setForeground(Color.GREEN);
		corrCounter.setFont(Controller.getFont().deriveFont(Font.BOLD, 35));
		corrCounter.setVerticalAlignment(SwingConstants.BOTTOM);
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(0,50,0,50);
		add(corrCounter, c);

		incorrCounter = new JLabel("" + incorrect);
		incorrCounter.setForeground(Color.RED);
		incorrCounter.setFont(Controller.getFont().deriveFont(Font.BOLD, 35));
		incorrCounter.setVerticalAlignment(SwingConstants.BOTTOM);
		c.gridx = 4;
		add(incorrCounter, c);
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(int time) {
		timer.setText(time + "sec");
		timer.setAlignmentX(SwingConstants.RIGHT);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,50,0,50);
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 4;
		c.gridy = 0;
		add(timer, c);
	}


	/**
	 * Check answer.
	 *
	 * @param answer the answer
	 */
	public void checkAnswer() {

		int correctanswer = 12;
		submit.setVisible(false);
		clock.stop();

		if (answerField.getText().equals("" + correctanswer)) {
			try {
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/faces/rface.png"));
				markImg = new JLabel(new ImageIcon(img));
			} catch (IOException e) {
				markImg = new JLabel("CORRECT");
			}
			correct++;
		}
		else {
			try {
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/faces/xface.png"));
				markImg = new JLabel(new ImageIcon(img));
			} catch (IOException e) {
				markImg = new JLabel("WRONG");
			}
			solution = new JLabel("Answer: " + correctanswer);
			solution.setFont(Controller.getFont().deriveFont(Font.BOLD, 35));
			incorrect++;
		}
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		add(markImg, c);

		c.gridy = 3;
		add(solution, c);

		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(0,50,0,50);
		corrCounter.setText("" + correct);
		add(corrCounter, c);

		c.gridx = 4;
		incorrCounter.setText("" + incorrect);
		add(incorrCounter, c);

		next.setVisible(true);
	}
}

/**
 * The class TimerAction, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class TimerAction implements ActionListener {

	/** The drill. */
	private Drill drill;

	/** The the number of seconds remaining. */
	private int timeRemaining;

	/**
	 * Instantiates the Timer Action.
	 * 
	 * @param drill		the drill pane
	 * @param answer	the answer text field
	 * @param submit	the submit button
	 */
	public TimerAction(Controller controller, Drill drill) {
		this.drill = drill;
		this.timeRemaining = Controller.getCurrentProgeny().getTimeAllowed();
		drill.setTime(timeRemaining);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (timeRemaining > 0) {
			timeRemaining--;
			drill.setTime(timeRemaining);
		}
		else {
			drill.checkAnswer();
		}
	}
}

/**
 * The class Submit, an action listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
class Submit implements ActionListener {

	/** The drill. */
	private Drill drill;

	/**
	 * Instantiates a Submit instance.
	 * 
	 * @param drill		the drill pane
	 * @param answer	the answer field
	 */
	public Submit(Drill drill, JTextField answer) {
		this.drill = drill;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		drill.checkAnswer();
	}
}