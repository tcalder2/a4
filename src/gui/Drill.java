package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import ttable.LevelProgeny;

/**
 * The class Drill, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Taylor Calder
 * @version 1.2
 */
@SuppressWarnings("serial")
public class Drill extends BackgroundPanel {

	/** Array of questions **/
	private int[] questions = new int[12];

	/** Array of answers **/
	private int[] answers = new int[12];
	
	/** Default time **/
	final private static int DEFAULT_TIME = 30;
	
	/** The Current Question the child is on **/
	private int currentQ = 0;
	
	/** The controller. */
	private Controller controller;

	/** The level. */
	private LevelProgeny level;
	
	/** The number of lives remaining. */
	private int lives;

	/** The number of questions answered correctly. */
	private static int correct;

	/** The number of questions answered incorrectly. */
	private int incorrect;

	/** The timer. */
	private JLabel timer;

	/** The incorr counter. */
	private JLabel incorrCounter;

	/** The corr counter. */
	private JLabel corrCounter;

	/** The mark img. */
	private JLabel markImg = new JLabel();

	/** The solution. */
	private JLabel solution;

	/** The next. */
	private JButton next;

	/** The submit. */
	private JButton submit;

	/** The answer field. */
	static JTextField answerField;
	
	/** Random Generator **/
	private Random rand = new Random();

	/** The clock. */
	private Timer clock;

	/** The current lives counter **/
	private JLabel livesCount = new JLabel();
	
	/** The current question **/
	private JLabel question = new JLabel();
	
	/** Time is added on correct answer **/
	static boolean addTime = false;
	
	/** Correct answer image **/
	private ImageIcon imgIconS;
	
	/** Incorrect answer image **/
	private ImageIcon imgIconF;
	
	/** Heart Icon **/
	private ImageIcon heart;
	
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

		//JLabel livesCount = new JLabel();
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill/heart.png"));
			heart = new ImageIcon(img);
			livesCount.setText(" x " + lives);
			livesCount.setIcon(heart);
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

		//JLabel question = new JLabel("");
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
		
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/faces/rface.png"));
			imgIconS = new ImageIcon(img);
		} catch (Exception e) {

		}
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/faces/xface.png"));
			imgIconF = new ImageIcon(img);
		} catch (Exception e) {

		}
		
		// ----------------------------------------
		// 				GAME LOGIC
		// ----------------------------------------
		
		
		/** Setup the questions **/
		for (int i = 0; i < 12; i++) {
			
			questions[i] = i+1;
			
		}

		/** Randomize the order **/
		int r1;
		int r2;
		int store;
		for (int i = 0; i < 12; i++) {
			r1 = rand.nextInt(11);
			r2 = rand.nextInt(11);
			store = questions[r1];
			questions[r1] = questions[r2];
			questions[r2] = store;
		}
		
		/** Set up the answers **/
		for (int i = 0; i < 12; i++) {
			answers[i] = questions[i] * level.getLevel();	
		}
		question.setText(level.getLevel() + " x " + questions[currentQ]);
	}
	
	/** Gets the number of correct answers **/
	public static int getCorrect() {	
		return correct;
	}
	
	
	/** updates the game state **/
	public void update() {
		
		currentQ++;
		try {
			//Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill/heart.png"));
			livesCount.setText(" x " + lives);
			livesCount.setIcon(heart);
		} catch (Exception e) {
			livesCount.setText("<3 x " + lives);
		}
		
		// Random choose the order of the two operands in the problem
		int r1 = rand.nextInt(1);
		if (r1 == 1) {
			question.setText(level.getLevel() + " x " + questions[currentQ]);
		}
		else {
			question.setText(questions[currentQ] + " x " + level.getLevel());
		}
	}
	
	/** Turn off Time Adding **/
	public static void setAddTime(boolean t) {
		
		addTime = t;
		
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

		int correctanswer = answers[currentQ];
		//submit.setVisible(false);
		//clock.stop();
		
		
		if (answerField.getText().equals("" + correctanswer)) {
			//clock.getListeners(TimerAction.class)[1].addTime(20);
			clock = new Timer(1000, new TimerAction(controller, this));
			try {
				markImg.setIcon(imgIconS);
			} catch (Exception e) {
				markImg.setText("CORRECT");
			}
			correct++;
			solution.setText("");
			Drill.setAddTime(true);
			
		}
		else {
			try {
				markImg.setIcon(imgIconF);
			} catch (Exception e) {
				markImg.setText("Wrong. Try Again!");
			}
			solution.setText("Answer: " + correctanswer);
			solution.setFont(Controller.getFont().deriveFont(Font.BOLD, 35));
			lives--;
			incorrect++;
		}
		
		answerField.setText("");
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

		/**
		 * Stop the game when you have answered all 12 questions
		 */
		
		if (correct + incorrect >= 12 || lives <= 0) {
			
			clock.stop();
			next.addKeyListener(new EnterListener(next));
			next.addActionListener(new Next());
			next.setVisible(true);
			submit.setVisible(false);
			answerField.setVisible(false);
			question.setVisible(false);
		}
		else {
		
			update();
		}
	}

	/**
	 * Gets the default time for a level if no other time is specified
	 * @return DEFAULT_TIME	the default time
	 */
	public static int getDefaultTime() {
		return DEFAULT_TIME;
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
		try {
			this.timeRemaining = Controller.getCurrentProgeny().getTimeAllowed();
		}
		catch (Exception e) {
			this.timeRemaining = Drill.getDefaultTime();
		}
		drill.setTime(timeRemaining);
	}

	/**
	 * Set the amount of time remaining for this time
	 * 
	 * @param time	the new amount of time remaining
	 */
	public void setTime(int time) {
		
		timeRemaining = time;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (Drill.addTime == true) {
		
			try {
				setTime(Controller.getCurrentProgeny().getTimeAllowed());
			}
			catch (Exception e2) {
				setTime( Drill.getDefaultTime());
			}

			Drill.addTime = false;
			
		}
		
		if (timeRemaining > 0) {
			timeRemaining--;
			drill.setTime(timeRemaining);
		}
		else {
			drill.setTime(0);
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
		
		if (!Drill.answerField.getText().equals("")) {
			drill.checkAnswer();
		}
	}
}

/**
 * The class Reset, an action listener.
 * 
 * @author Taylor Calder
 * @version 1.0
 */
class Next implements ActionListener {

	private Controller controller;
	
	public void actionPerformed(ActionEvent e) {
		Controller.setScreen(new ScoreReport(controller, Drill.getCorrect(), 2));
	}
}