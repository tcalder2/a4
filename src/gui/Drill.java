package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AbstractDocument;

import json.JSONFailureException;
import service.LevelService;
import ttable.LevelProgeny;
import ttable.User;

/**
 * The class Drill, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Taylor Calder
 * @version 1.5
 */
@SuppressWarnings("serial")
public class Drill extends BackgroundPanel {

	/** Array of questions **/
	private ArrayList<Integer> questions = new ArrayList<Integer>();

	/** Default time **/
	final private static int DEFAULT_TIME = 30;

	/** The level. */
	private LevelProgeny level;

	/** The number of lives remaining. */
	private static int lives;

	/** The max time allowed **/
	private int timeMax;
	
	/** The time remaining **/
	private int timeLeft;
	
	/** The number of questions answered correctly. */
	private static int correct;

	/** The number of questions answered incorrectly. */
	private static int incorrect;

	/** The timer. */
	private JLabel timer;

	/** The incorr counter. */
	private JLabel incorrCounter;

	/** The corr counter. */
	private JLabel corrCounter;

	/** The solution. */
	private JLabel solution;

	/** The submit. */
	private JButton submit;

	/** The answer field. */
	static JTextField answerField;

	/** Random number generator */
	private Random rand;

	/** The clock. */
	private Timer clock;

	/** The label displaying lives remaining. */
	private JLabel livesCount;

	/** The question text. */
	private JLabel question;

	/** Correct answer image label. */
	private JLabel correctImg;

	/** Incorrect answer image label. */
	private JLabel incorrImg;

	/** Heart icon. */
	private ImageIcon heart;

	/** The current question number. */
	private int currentQ;
	
	/** Tells whether the end of the drill has been reached. */
	private boolean end;
	
	/** Whether people won **/
	private boolean win;
	
	/** Timer dictating how long the answer is shown **/
	private Timer tmrAnswer;
	
	private AnswerTimer delay;

	/**
	 * Instantiates a new drill.
	 *
	 * @param level the level
	 */
	public Drill(LevelProgeny level) {
		super(User.backgroundCode, new GridBagLayout());
		
		// Refresh child values
		Controller.refreshCurrentProgeny();
		
		//Initialise values
		this.level = level;
		try {
			lives = LevelService.getLevel(level.getLevelNumber()).getMistakesAllowed() + 1;
		} catch (JSONFailureException e1) {
			lives = 3;
		}
		
		correct = 0;
		incorrect = 0;
		end = false;
		win = true;

		try {
			setTimeMax(Controller.getCurrentProgeny().getTimeAllowed());
		}
		catch (Exception e) {
			setTimeMax(Drill.getDefaultTime());
		}
		
		//Create components
		rand = new Random();
		livesCount = new JLabel();
		timer = new JLabel("", SwingConstants.RIGHT);
		question = new JLabel();
		answerField = new JTextField(2);
		submit = new JButton("Submit");
		clock = new Timer(1000, new TimerAction(this));
		delay = new AnswerTimer(this);
		tmrAnswer = new Timer(1000, delay);
		solution = new JLabel(" ");
		corrCounter = new JLabel("" + correct);
		incorrCounter = new JLabel("" + incorrect);
		JPanel imageSpace = new JPanel();
		
		//Set component display attributes
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/heart.png"));
			heart = new ImageIcon(img);
			livesCount.setIcon(heart);
			livesCount.setText(" x " + lives);
		} catch (IOException e) {
			livesCount.setText("<3 x " + lives);
		}
		livesCount.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));

		timer.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));
		timer.setMinimumSize(new Dimension(90,30));
		timer.setMaximumSize(new Dimension(90,30));
		timer.setPreferredSize(new Dimension(90,30));

		question.setFont(Controller.getFont().deriveFont(Font.BOLD, 60));
		question.setMinimumSize(new Dimension(220,65));
		question.setMaximumSize(new Dimension(220,65));
		question.setPreferredSize(new Dimension(220,65));
		
		answerField.setFont(Controller.getFont().deriveFont(Font.BOLD, 60));
		((AbstractDocument) answerField.getDocument()).setDocumentFilter(new DocumentLengthFilter(3));

		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill_right.png"));
			correctImg = new JLabel(new ImageIcon(img));
		} catch (Exception e) {
			correctImg = new JLabel("CORRECT!");
		}
		correctImg.setVisible(false);
		
		Dimension d;
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill_wrong.png"));
			ImageIcon icon = new ImageIcon(img);
			incorrImg = new JLabel(icon);
			d = new Dimension(icon.getIconWidth(), icon.getIconHeight() + 20);
		} catch (Exception e) {
			d = new Dimension(200,40);
			incorrImg = new JLabel("WRONG:(");
		}
		incorrImg.setVisible(false);
		
		imageSpace.setMinimumSize(d);
		imageSpace.setMaximumSize(d);
		imageSpace.setPreferredSize(d);
		imageSpace.setOpaque(false);
		imageSpace.add(correctImg);
		imageSpace.add(incorrImg);
		
		solution.setFont(Controller.getFont().deriveFont(Font.BOLD, 35));

		d = new Dimension(80,65);
		submit.setMaximumSize(d);
		submit.setMinimumSize(d);
		submit.setPreferredSize(d);
		
		corrCounter.setForeground(Color.GREEN);
		corrCounter.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));
		corrCounter.setVerticalAlignment(SwingConstants.BOTTOM);
		
		incorrCounter.setForeground(Color.RED);
		incorrCounter.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));
		incorrCounter.setVerticalAlignment(SwingConstants.BOTTOM);

		//Add action listeners
		answerField.addKeyListener(new EnterListener(submit));
		submit.addActionListener(new Submit(this, answerField));
		
		//Add components to view
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,25,0,25);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		add(livesCount);

		c.gridx = 4;
		c.anchor = GridBagConstraints.EAST;
		add(timer, c);

		c.gridy = 1;
		c.gridx = 1;
		c.insets = new Insets(50,50,0,0);
		add(question, c);

		c.anchor = GridBagConstraints.WEST;
		c.gridx = 2;
		c.insets = new Insets(50,0,0,0);
		add(answerField, c);

		c.gridx = 3;
		c.insets = new Insets(50,0,0,50);
		add(submit, c);

		c.gridx = 2;
		c.gridy = 2;
		c.insets = new Insets(0,0,0,0);
		add(imageSpace, c);

		c.gridy = 3;
		add(solution, c);

		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(0,50,0,50);
		add(corrCounter, c);

		c.gridx = 4;
		add(incorrCounter, c);
		
		//Set the question display
		setup();
		
		//Start the clock
		clock.start();
		
		answerField.requestFocus();
	}

	/**
	 * Updates the Drill Game state.
	 * 
	 */
	public void update() {

					
		answerField.setText("");
		answerField.requestFocus();

		//Randomize display of the question
		//Random.nextInt is already from 0 to n-1 your correction causes error, changed back
		currentQ = rand.nextInt(questions.size());
		if (rand.nextInt(2) > 0) {
			question.setText(level.getLevelNumber() + " x " + questions.get(currentQ) + " =");
		}
		else {
			question.setText(questions.get(currentQ) + " x " + level.getLevelNumber() + " =");
		}
	}

	public boolean isValid (String answer){
		int answerInt = 0;
		
		   try{
		      answerInt = Integer.parseInt(answer);
		   } catch (NumberFormatException nfe) {
		      return false;
		   }
		   
		   if (answerInt > 0) {
			   return true;
		   }
		   else {
			   return false;
		   }
		   
		}
	
	public void setWin(boolean win) {
		this.win = win;
	}
	
	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(int time) {
		timer.setText(time + "sec");
		setTimeLeft(time);
		if (time == 0) {
			clock.stop();
			end = true;
			setWin(false);
			Controller.setScreen(new ScoreReport(isWin(), getTimeMax(), getTimeLeft(), level.getLevelNumber(), incorrect));
		}
	}
	
	/**
	 * Gets whether this is the end of the drill or not.
	 * 
	 * @return		true if the end of the drill has been reached, false otherwise
	 */
	public boolean isEnd() {
		return end;
	}

	/**
	 * Check answer.
	 *
	 * @param answer the answer
	 */
	public void checkAnswer() {

		// Check to see if the user entered numeric input
		
		// If they did, check the answer
		
		if (isValid(answerField.getText()) == true) {
		
		//Calculate the answer
		int answer = questions.get(currentQ) * level.getLevelNumber();

		if (answerField.getText().equals("" + answer)) {
			questions.remove(currentQ);
			if (questions.size() == 0) {
				clock.stop();
				setWin(true);
			}
			incorrImg.setVisible(false);
			correctImg.setVisible(true);
			solution.setText(" ");
			correct++;
			if (questions.size() == 0) {
				end = true;
			}
		}
		else {
			//If the answer is wrong add a second instance of the question in the list
			questions.add(questions.get(currentQ));
			correctImg.setVisible(false);
			incorrImg.setVisible(true);
			solution.setText("Answer: " + answer);
			if (lives > 0) {
				lives--;
			}
			incorrect++;
		}
		
		corrCounter.setText("" + correct);
		incorrCounter.setText("" + incorrect);
		
		try {
			livesCount.setText(" x " + lives);
			livesCount.setIcon(heart);
		} catch (Exception e) {
			livesCount.setText("<3 x " + lives);
		}
		
		if (lives <= 0) {
			clock.stop();
			Controller.setScreen(new ScoreReport(false, getTimeMax(), getTimeLeft(), level.getLevelNumber(), incorrect));
		}
		
		if (isEnd()) {
			clock.stop();
			Controller.setScreen(new ScoreReport(isWin(), getTimeMax(), getTimeLeft(), level.getLevelNumber(), incorrect));
		}
		else {
			update();
		}
		
		answerField.requestFocus();
		delay.reset();
		if(!tmrAnswer.isRunning())
			tmrAnswer.start();
		
		}
		
		// If they did not, clear the text field
		
		else {
			
			answerField.setText("");
			
		}
		
	}
		

	/**
	 * Sets up the questions array.
	 * 
	 */
	public void setup() {
		
		// Populate the question list
		questions = new ArrayList<Integer>();
		int max = 12;
		if (Controller.getTestMode()) {
			max = 4;
		}
		for (int i = 1; i <= max; i++) {
			questions.add(i);
		}

		update();
		
	}
	
	/**
	 * Gets the default time for a level if no other time is specified
	 * 
	 * @return DEFAULT_TIME	the default time
	 */
	public static int getDefaultTime() {
		return DEFAULT_TIME;
	}
	
	/**
	 * Gets the questions array.
	 * 
	 * @return the questions array.
	 */
	public ArrayList<Integer> getQuestions() {
		return questions;
	}
	
	/**
	 * Gets the number of correct answers
	 * 
	 * @return
	 */
	public static int getCorrect() {	
		return correct;
	}

	/**
	 * Gets the number of incorrect answers
	 * 
	 * @return
	 */
	public static int getIncorrect() {	
		return incorrect;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public int getTimeMax() {
		return timeMax;
	}

	public void setTimeMax(int timeMax) {
		this.timeMax = timeMax;
	}

	public boolean isWin() {
		return win;
	}

	class AnswerTimer implements ActionListener{

		private int time;
		private int ANSWER_TIME = 2;
		
		public AnswerTimer(Drill drill) {
			this.time = ANSWER_TIME;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (time > 0) {
				time--;
			}else{
				correctImg.setVisible(false);
				incorrImg.setVisible(false);
				solution.setText(" ");
				tmrAnswer.stop();
			}
		}
		public void reset(){
			this.time = ANSWER_TIME;
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
			//Purposefully removed your safeguard as it prevents end of game click on time out
			tmrAnswer.start();
			drill.checkAnswer();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gui.BackgroundPanel#close()
	 */
	@Override
	public void close() {
		clock.stop();
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
	 */
	public TimerAction(Drill drill) {
		this.drill = drill;
		try {
			this.timeRemaining = Controller.getCurrentProgeny().getTimeAllowed();
			if (timeRemaining > 120) {
				throw new Exception();
			}
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
		if (timeRemaining > 0) {
			timeRemaining--;
		}
		drill.setTime(timeRemaining);
	}
}





