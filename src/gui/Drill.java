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

	/** Max number of questions per level **/
	private int max;
	
	/** Default time **/
	final private static int DEFAULT_TIME = 30;

	/** The level. */
	private LevelProgeny level;

	/** The number of lives remaining. */
	private static int lives;

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

	/** The next. */
	private JButton next;

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


	/**
	 * Instantiates a new drill.
	 *
	 * @param level the level
	 */
	public Drill(LevelProgeny level) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		//Initialise values
		this.level = level;
		try {
			lives = LevelService.getLevel(level.getLevelNumber()).getMistakesAllowed();
		} catch (JSONFailureException e1) {
			lives = 3;
		}
		correct = 0;
		incorrect = 0;
		end = false;

		// Populate the question list
		questions = new ArrayList<Integer>();
		max = 12;
		if (Controller.getTestMode()) {
			max = 4;
		}

		//Create components
		rand = new Random();
		livesCount = new JLabel();
		timer = new JLabel("", SwingConstants.RIGHT);
		question = new JLabel();
		answerField = new JTextField(2);
		submit = new JButton("Submit");
		clock = new Timer(1000, new TimerAction(this));
		solution = new JLabel(" ");
		corrCounter = new JLabel("" + correct);
		next = new JButton("Next");
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
		question.setMinimumSize(new Dimension(200,65));
		question.setMaximumSize(new Dimension(200,65));
		question.setPreferredSize(new Dimension(200,65));
		
		answerField.setFont(Controller.getFont().deriveFont(Font.BOLD, 60));
		((AbstractDocument) answerField.getDocument()).setDocumentFilter(new DocumentLengthFilter(3));

		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/faces/rface.png"));
			correctImg = new JLabel(new ImageIcon(img));
		} catch (Exception e) {
			correctImg = new JLabel("CORRECT!");
		}
		correctImg.setVisible(false);
		
		Dimension d;
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/faces/xface.png"));
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
		
		next.setMaximumSize(d);
		next.setMinimumSize(d);
		next.setPreferredSize(d);
		next.setVisible(false);
		
		corrCounter.setForeground(Color.GREEN);
		corrCounter.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));
		corrCounter.setVerticalAlignment(SwingConstants.BOTTOM);
		
		incorrCounter.setForeground(Color.RED);
		incorrCounter.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));
		incorrCounter.setVerticalAlignment(SwingConstants.BOTTOM);

		//Add action listeners
		answerField.addKeyListener(new EnterListener(submit));
		submit.addActionListener(new Submit(this, answerField));
		next.addActionListener(new PressNext(this.level.getLevelNumber(), this));
		next.addKeyListener(new EnterListener(next));


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
		add(next, c);

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
	 * Updates the drill state.
	 * 
	 */
	public void update() {

		solution.setText(" ");			
		answerField.setText("");
		answerField.requestFocus();
		submit.setVisible(true);
		if (questions.size() == 1) {
			currentQ = 0;
		}
		else {
			currentQ = rand.nextInt(questions.size()-1);
		}
		//Randomize display of the question
		//currentQ = rand.nextInt(questions.size());
		if (rand.nextInt(2) > 0) {
			question.setText(level.getLevelNumber() + " x " + questions.get(currentQ) + " =");
		}
		else {
			question.setText(questions.get(currentQ) + " x " + level.getLevelNumber() + " =");
		}
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(int time) {
		timer.setText(time + "sec");
		if (time == 0) {
			clock.stop();
			end = true;
			submit.doClick();
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
		
		//Calculate the answer
		int answer = questions.get(currentQ) * level.getLevelNumber();

		if (answerField.getText().equals("" + answer)) {
			questions.remove(currentQ);
			if (questions.size() == 0) {
				clock.stop();
			}
			incorrImg.setVisible(false);
			correctImg.setVisible(true);
			next.setVisible(true);
			submit.setVisible(false);
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
		
		if (isEnd()) {
			next.setText("Finish");
		}
		submit.setVisible(false);
		next.setVisible(true);
		corrCounter.setText("" + correct);
		incorrCounter.setText("" + incorrect);
		
		try {
			livesCount.setText(" x " + lives);
			livesCount.setIcon(heart);
		} catch (Exception e) {
			livesCount.setText("<3 x " + lives);
		}
		next.requestFocus();
	}

	/**
	 * 
	 */
	public void setup() {
		
		/** Setup the questions **/
		for (int i = 0; i < max; i++) {
			
			questions.add(i, i+1);
			
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
	// This fixes an issue where you could submit an empty field and get a wrong answer
	// This way you're not penalized for accidentally clicking submit
	public void actionPerformed(ActionEvent e) {
		if (Drill.answerField.getText().equals("") == false) {
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
class PressNext implements ActionListener {

	/** The level number. */
	private int level;
	
	/** The drill. */
	private Drill drill;

	/**
	 * Constructor requiring the level number be passed as an argument.
	 * 
	 * @param level		the level number.
	 * @param drill		the drill.
	 */
	public PressNext(int level, Drill drill) {
		this.level = level;
		this.drill = drill;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//If there are no more questions, then end the game.  Else, display next question
		if (drill.isEnd()) {
			Controller.setScreen(new ScoreReport(Drill.getCorrect(), level));
		}
		else {
			drill.update();
		}
	}
}