<<<<<<< HEAD
package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AbstractDocument;

import ttable.LevelProgeny;


/**
 * The class LGame, a populated BackgroundPanel.
 * 
 * @author Yaqzan Ali
 * @author Taylor Calder
 * @version 0.1
 */
@SuppressWarnings("serial")
public class LGame extends BackgroundPanel implements KeyListener{

	/** The level progeny holding the level details. */
	private LevelProgeny level;
	private static int BOMB_TIME = 50; 			// Bomb Timer
	private static int SCORE_NEEDED = 20000;	// Score needed to defuse bomb
	private static int BASIC_SCORE= 500;		// Points awarded for getting a right answer
	private JLabel lblBombTimer;		// The label for the bomb timer
	private JLabel lblNum1;				// Label holding the first number
	private JLabel lblNum2;				// Label holding second number
	private JLabel lblx;				// Label holding "x"
	private JLabel lblScore;			// Label holding the score
	private JLabel lblScoreNeeded;		// Label holding the required score
	private JLabel lblMultiplier;		// Label holding the multiplier
	private JLabel lblBombLit;			// Picture of the bomb
	private JLabel lblBombFrz;			// Picture of the frozen bomb	
	private JTextField txtAnswer;		// Answer field
	private Random rnd;					// Random number generator
	private Timer tmrBomb;				// Bomb timer
	private Timer tmrDelay;				// Delay timer
	private int answer;					// Int holding the answer
	private DelayTimer delay;			// ActionListener for the delay
	private int score;					// Int holding the score
	private int multiplier;				// Int holding the multiplier
	private JProgressBar bar;			// Progress bar
	
	
	/**
	 * Instantiates an LGame instance.
	 *
	 * @param level			the level
	 */
	public LGame(LevelProgeny level) {

		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());

		this.level = level;
		
		// Initialize
		lblBombTimer = new JLabel();
		lblScore= new JLabel();
		lblNum1= new JLabel();
		lblNum2= new JLabel();
		lblx= new JLabel(" x ");
		lblMultiplier = new JLabel();
		lblScoreNeeded = new JLabel();
		txtAnswer = new JTextField();
		tmrBomb = new Timer(1000, new Listener(this));
		delay = new DelayTimer(this);
		tmrDelay = new Timer(1000, delay);
		score = 0;
		multiplier = 1;
		bar = new JProgressBar(0, SCORE_NEEDED);
		
		rnd = new Random();
		
		newQuestion();
				
		GridBagConstraints c = new GridBagConstraints();
		
		//Score
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=2;
		c.anchor= GridBagConstraints.LINE_START;
		add(lblScore, c);
		lblScore.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));
		lblScore.setText(""+score);
		lblScore.setForeground(Color.green);
		
		//Numbers
		c.gridx=1;
		c.gridwidth=1;
		c.gridy=0;
		c.anchor= GridBagConstraints.LINE_END;
		add(lblNum1, c);
		lblNum1.setFont(Controller.getFont().deriveFont(Font.BOLD, 200));
		
		c.gridx=4;
		c.anchor= GridBagConstraints.LINE_START;
		add(lblNum2, c);
		lblNum2.setFont(Controller.getFont().deriveFont(Font.BOLD, 200));
		newQuestion();
		
		// Multiplication sign
		c.gridwidth=2;
		c.anchor= GridBagConstraints.CENTER;
		c.gridx = 2;
		add(lblx, c);
		lblx.setFont(Controller.getFont().deriveFont(Font.BOLD, 80));
		
		// Textfield
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy=1;
		add(txtAnswer, c);
		txtAnswer.setFont(Controller.getFont().deriveFont(Font.BOLD, 60));
		txtAnswer.addKeyListener(this);
		((AbstractDocument) txtAnswer.getDocument()).setDocumentFilter(new DocumentLengthFilter(3));
		txtAnswer.requestFocus();
		
		//Progress Bar
		c.gridy=3;
		c.ipady=20;
		c.insets = new Insets(10,0,0,0);
		bar.setValue(0);
		bar.setStringPainted(true);
		add(bar, c);
		bar.setForeground(Color.green);
		
		//Timer
		c.ipady=0;
		c.gridwidth=1;
		c.gridx=2;
		c.gridy=2;
		c.anchor = GridBagConstraints.LINE_START;
		add(lblBombTimer, c);
		Font fntBomb = new Font("Stencil", Font.BOLD, 40);
		lblBombTimer.setFont(fntBomb);
		lblBombTimer.setForeground(Color.red);
		tmrBomb.start();
		
		// Bomb Pictures
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/bomb_lit.png"));
			lblBombLit = new JLabel(new ImageIcon(img));
		} catch (Exception e) {
			lblBombLit = new JLabel("Ticking...");
		}
		
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/bomb_frozen.png"));
			lblBombFrz = new JLabel(new ImageIcon(img));
		} catch (Exception e) {
			lblBombFrz = new JLabel("Frozen");
		}
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx=3;
		add(lblBombLit,c);
		add(lblBombFrz,c);
		lblBombFrz.setVisible(false);
		
		//Multiplier
		c.gridx = 4;
		c.gridy=1;
		c.anchor = GridBagConstraints.LINE_START;
		add(lblMultiplier, c);
		lblMultiplier.setFont(Controller.getFont().deriveFont(Font.BOLD, 90));
		lblMultiplier.setText("x" + multiplier);
		lblMultiplier.setVisible(false);
		
		//Score needed
		c.gridy =3;
		c.gridx=4;
		c.gridwidth=2;
		add(lblScoreNeeded, c);
		lblScoreNeeded.setFont(Controller.getFont().deriveFont(Font.BOLD, 30));
		lblScoreNeeded.setText("Need "+ SCORE_NEEDED);
	}
	
	public void newQuestion(){

		int currentQ = rnd.nextInt(11)+1;
		int level = this.level.getLevelNumber();
		answer = currentQ * level;
		if (rnd.nextInt(2) > 0) {
			lblNum1.setText("" + currentQ);
			lblNum2.setText("" + level);
		}
		else {
			lblNum1.setText("" + level);
			lblNum2.setText("" + currentQ);
		}
		
	}
	public void setBombTime(int time) {
		lblBombTimer.setText("0:"+ time);
		if (time == 0) {
			tmrBomb.stop();
			Controller.setScreen(new ScoreReportL(false, level.getLevelNumber()));
		}
	}
	public void calculateScore(){
		score = score + ((BASIC_SCORE)*multiplier);
		lblScore.setText(""+score);
		lblMultiplier.setText(multiplier+"x");
		
		if (score >= SCORE_NEEDED){
			tmrBomb.stop();
			tmrDelay.stop();
			Controller.setScreen(new ScoreReportL(true, level.getLevelNumber()));
		}
		bar.setValue(score);
	}
	
	public static int getBombTime(){
		return BOMB_TIME;
	}
	public Timer getBombTimer(){
		return tmrBomb;
	}
	public Timer getDelayTimer(){
		return tmrDelay;
	}
	public JLabel getBombLabel(){
		return lblBombTimer;
	}
	public void setMultiplier(int i){
		multiplier = i;
	}
	public JLabel getMultiplierLabel(){
		return lblMultiplier;
	}
	public JLabel getBombLit(){
		return lblBombLit;
	}
	public JLabel getBombFrz(){
		return lblBombFrz;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		try{
			int text = Integer.valueOf(txtAnswer.getText());
			char c = e.getKeyChar();
			
			// Check if key press was a number or backspace, if so:
			if(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE)){ 
				// If right answer
				if (text==answer){
					txtAnswer.setText("");
					calculateScore();
					newQuestion();
					
					//if not delayed
					if(tmrBomb.isRunning()){
						tmrBomb.stop();
						delay.start();
						tmrDelay.start();
						lblBombTimer.setForeground(Color.black);
						lblBombLit.setVisible(false);
						lblBombFrz.setVisible(true);
					}else{
						//If delay is already on, extend it
						delay.increaseTime();	
						multiplier+=1;	
						lblMultiplier.setText(multiplier+"x");
						lblMultiplier.setVisible(true);
					}
				}

			}
		} catch (Exception e3){}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		// Prevents all keys from working except numbers and backspace
		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE))){
			e.consume();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gui.BackgroundPanel#close()
	 */
	@Override
	public void close() {
		tmrBomb.stop();
		tmrDelay.stop();
	}

}
class DelayTimer implements ActionListener{
	private LGame lgame;
	private int time;
	private static int DELAY_TIME = 5;
	
	public DelayTimer(LGame game) {
		this.lgame = game;
		this.time = DELAY_TIME;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (time > 0) {
			time--;
		}else{
			lgame.getDelayTimer().stop();
			lgame.getBombTimer().start();
			lgame.getBombLabel().setForeground(Color.red);
			lgame.setMultiplier(1);
			lgame.getMultiplierLabel().setVisible(false);
			lgame.getBombLit().setVisible(true);
			lgame.getBombFrz().setVisible(false);
		}
	}
	public void increaseTime(){
		this.time+= (DELAY_TIME/2);
	}
	public void start(){
		this.time = DELAY_TIME;
	}
}

class Listener implements ActionListener {

	/** The game. */
	private LGame lgame;

	/** The the number of seconds remaining. */
	private int timeRemaining;


	/**
	 * Instantiates the Timer Action.
	 * 
	 * @param drill		the drill pane
	 */
	public Listener(LGame game) {
		this.lgame = game;

		this.timeRemaining = LGame.getBombTime();
	
		game.setBombTime(timeRemaining);
	}

	/**
	 * Set the amount of time remaining for this time
	 * 
	 * @param time	the new amount of time remaining
	 */
	public void setBombTime(int time) {

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
		lgame.setBombTime(timeRemaining);
	}
}
=======
package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AbstractDocument;

import ttable.LevelProgeny;


/**
 * The class LGame, a populated BackgroundPanel.
 * 
 * @author Yaqzan Ali
 * @author Taylor Calder
 * @version 0.1
 */
@SuppressWarnings("serial")
public class LGame extends BackgroundPanel implements KeyListener{

	/** The level progeny holding the level details. */
	private LevelProgeny level;
	private static int BOMB_TIME = 50; 			// Bomb Timer
	private static int SCORE_NEEDED = 20000;	// Score needed to defuse bomb
	private static int BASIC_SCORE= 500;		// Points awarded for getting a right answer
	private JLabel lblBombTimer;		// The label for the bomb timer
	private JLabel lblNum1;				// Label holding the first number
	private JLabel lblNum2;				// Label holding second number
	private JLabel lblx;				// Label holding "x"
	private JLabel lblScore;			// Label holding the score
	private JLabel lblScoreNeeded;		// Label holding the required score
	private JLabel lblMultiplier;		// Label holding the multiplier
	private JLabel lblBombLit;			// Picture of the bomb
	private JLabel lblBombFrz;			// Picture of the frozen bomb	
	private JTextField txtAnswer;		// Answer field
	private Random rnd;					// Random number generator
	private Timer tmrBomb;				// Bomb timer
	private Timer tmrDelay;				// Delay timer
	private int answer;					// Int holding the answer
	private DelayTimer delay;			// ActionListener for the delay
	private int score;					// Int holding the score
	private int multiplier;				// Int holding the multiplier
	private JProgressBar bar;			// Progress bar
	
	
	/**
	 * Instantiates an LGame instance.
	 *
	 * @param level			the level
	 */
	public LGame(LevelProgeny level) {

		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());

		this.level = level;
		
		// Initialize
		lblBombTimer = new JLabel();
		lblScore= new JLabel();
		lblNum1= new JLabel();
		lblNum2= new JLabel();
		lblx= new JLabel(" x ");
		lblMultiplier = new JLabel();
		lblScoreNeeded = new JLabel();
		txtAnswer = new JTextField();
		tmrBomb = new Timer(1000, new Listener(this));
		delay = new DelayTimer(this);
		tmrDelay = new Timer(1000, delay);
		score = 0;
		multiplier = 1;
		bar = new JProgressBar(0, SCORE_NEEDED);
		
		rnd = new Random();
		
		newQuestion();
				
		GridBagConstraints c = new GridBagConstraints();
		
		//Score
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=2;
		c.anchor= GridBagConstraints.LINE_START;
		add(lblScore, c);
		lblScore.setFont(Controller.getFont().deriveFont(Font.BOLD, 40));
		lblScore.setText(""+score);
		lblScore.setForeground(Color.green);
		
		//Numbers
		c.gridx=1;
		c.gridwidth=1;
		c.gridy=0;
		c.anchor= GridBagConstraints.LINE_END;
		add(lblNum1, c);
		lblNum1.setFont(Controller.getFont().deriveFont(Font.BOLD, 200));
		
		c.gridx=4;
		c.anchor= GridBagConstraints.LINE_START;
		add(lblNum2, c);
		lblNum2.setFont(Controller.getFont().deriveFont(Font.BOLD, 200));
		newQuestion();
		
		// Multiplication sign
		c.gridwidth=2;
		c.anchor= GridBagConstraints.CENTER;
		c.gridx = 2;
		add(lblx, c);
		lblx.setFont(Controller.getFont().deriveFont(Font.BOLD, 80));
		
		// Textfield
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy=1;
		add(txtAnswer, c);
		txtAnswer.setFont(Controller.getFont().deriveFont(Font.BOLD, 60));
		txtAnswer.addKeyListener(this);
		((AbstractDocument) txtAnswer.getDocument()).setDocumentFilter(new DocumentLengthFilter(3));
		txtAnswer.requestFocus();
		
		//Progress Bar
		c.gridy=3;
		c.ipady=20;
		c.insets = new Insets(10,0,0,0);
		bar.setValue(0);
		bar.setStringPainted(true);
		add(bar, c);
		bar.setForeground(Color.green);
		
		//Timer
		c.ipady=0;
		c.gridwidth=1;
		c.gridx=2;
		c.gridy=2;
		c.anchor = GridBagConstraints.LINE_START;
		add(lblBombTimer, c);
		Font fntBomb = new Font("Stencil", Font.BOLD, 40);
		lblBombTimer.setFont(fntBomb);
		lblBombTimer.setForeground(Color.red);
		tmrBomb.start();
		
		// Bomb Pictures
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/bomb_lit.png"));
			lblBombLit = new JLabel(new ImageIcon(img));
		} catch (Exception e) {
			lblBombLit = new JLabel("Ticking...");
		}
		
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/bomb_frozen.png"));
			lblBombFrz = new JLabel(new ImageIcon(img));
		} catch (Exception e) {
			lblBombFrz = new JLabel("Frozen");
		}
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx=3;
		add(lblBombLit,c);
		add(lblBombFrz,c);
		lblBombFrz.setVisible(false);
		
		//Multiplier
		c.gridx = 4;
		c.gridy=1;
		c.anchor = GridBagConstraints.LINE_START;
		add(lblMultiplier, c);
		lblMultiplier.setFont(Controller.getFont().deriveFont(Font.BOLD, 90));
		lblMultiplier.setText("x" + multiplier);
		lblMultiplier.setVisible(false);
		
		//Score needed
		c.gridy =3;
		c.gridx=4;
		c.gridwidth=2;
		add(lblScoreNeeded, c);
		lblScoreNeeded.setFont(Controller.getFont().deriveFont(Font.BOLD, 30));
		lblScoreNeeded.setText("Need "+ SCORE_NEEDED);
	}
	
	public void newQuestion(){

		int currentQ = rnd.nextInt(11)+1;
		int level = this.level.getLevelNumber();
		answer = currentQ * level;
		if (rnd.nextInt(2) > 0) {
			lblNum1.setText("" + currentQ);
			lblNum2.setText("" + level);
		}
		else {
			lblNum1.setText("" + level);
			lblNum2.setText("" + currentQ);
		}
		
	}
	public void setBombTime(int time) {
		lblBombTimer.setText("0:"+ time);
		if (time == 0) {
			tmrBomb.stop();
			Controller.setScreen(new ScoreReportL(false, level.getLevelNumber()));
		}
	}
	public void calculateScore(){
		score = score + ((BASIC_SCORE)*multiplier);
		lblScore.setText(""+score);
		lblMultiplier.setText(multiplier+"x");
		
		if (score >= SCORE_NEEDED){
			tmrBomb.stop();
			tmrDelay.stop();
			Controller.setScreen(new ScoreReportL(true, level.getLevelNumber()));
		}
		bar.setValue(score);
	}
	
	public static int getBombTime(){
		return BOMB_TIME;
	}
	public Timer getBombTimer(){
		return tmrBomb;
	}
	public Timer getDelayTimer(){
		return tmrDelay;
	}
	public JLabel getBombLabel(){
		return lblBombTimer;
	}
	public void setMultiplier(int i){
		multiplier = i;
	}
	public JLabel getMultiplierLabel(){
		return lblMultiplier;
	}
	public JLabel getBombLit(){
		return lblBombLit;
	}
	public JLabel getBombFrz(){
		return lblBombFrz;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		try{
			int text = Integer.valueOf(txtAnswer.getText());
			char c = e.getKeyChar();
			
			// Check if key press was a number or backspace, if so:
			if(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE)){ 
				// If right answer
				if (text==answer){
					txtAnswer.setText("");
					calculateScore();
					newQuestion();
					
					//if not delayed
					if(tmrBomb.isRunning()){
						tmrBomb.stop();
						delay.start();
						tmrDelay.start();
						lblBombTimer.setForeground(Color.black);
						lblBombLit.setVisible(false);
						lblBombFrz.setVisible(true);
					}else{
						//If delay is already on, extend it
						delay.increaseTime();	
						multiplier+=1;	
						lblMultiplier.setText(multiplier+"x");
						lblMultiplier.setVisible(true);
					}
				}

			}
		} catch (Exception e3){
			//NULL BODY
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		// Prevents all keys from working except numbers and backspace
		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE))){
			e.consume();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gui.BackgroundPanel#close()
	 */
	@Override
	public void close() {
		tmrBomb.stop();
		tmrDelay.stop();
	}

}
class DelayTimer implements ActionListener{
	private LGame lgame;
	private int time;
	private static int DELAY_TIME = 5;
	
	public DelayTimer(LGame game) {
		this.lgame = game;
		this.time = DELAY_TIME;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (time > 0) {
			time--;
		}else{
			lgame.getDelayTimer().stop();
			lgame.getBombTimer().start();
			lgame.getBombLabel().setForeground(Color.red);
			lgame.setMultiplier(1);
			lgame.getMultiplierLabel().setVisible(false);
			lgame.getBombLit().setVisible(true);
			lgame.getBombFrz().setVisible(false);
		}
	}
	public void increaseTime(){
		this.time+= (DELAY_TIME/2);
	}
	public void start(){
		this.time = DELAY_TIME;
	}
}

class Listener implements ActionListener {

	/** The game. */
	private LGame lgame;

	/** The the number of seconds remaining. */
	private int timeRemaining;


	/**
	 * Instantiates the Timer Action.
	 * 
	 * @param drill		the drill pane
	 */
	public Listener(LGame game) {
		this.lgame = game;

		this.timeRemaining = LGame.getBombTime();
	
		game.setBombTime(timeRemaining);
	}

	/**
	 * Set the amount of time remaining for this time
	 * 
	 * @param time	the new amount of time remaining
	 */
	public void setBombTime(int time) {

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
		lgame.setBombTime(timeRemaining);
	}
}
>>>>>>> branch 'master' of ssh://taylor@jbaron6.cs2212.ca/git/ttable
