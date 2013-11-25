package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
 * The class LGame, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Yaqzan Ali
 * @version 0.1
 */
@SuppressWarnings("serial")
public class LGame extends BackgroundPanel implements KeyListener{

	/** The level progeny holding the level details. */
	private LevelProgeny level;
	private static int BOMB_TIME = 100;
	private JLabel lblBombTimer;
	private JLabel lblScore;
	private JLabel lblNum1;
	private JLabel lblNum2;
	private JLabel lblx;
	private JTextField txtAnswer;
	private Timer tmrBomb;
	private Timer tmrDelay;
	private Random rnd;
	private boolean end;
	private int intLevel;
	private int answer;
	private int oldQ;
	private DelayTimer delay;
	
	/**
	 * Instantiates an LGame instance.
	 *
	 * @param level			the level
	 */
	public LGame(LevelProgeny level) {

		//Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());

		this.level = level;
		//TODO: complete class
		
		// Initialize

		lblBombTimer = new JLabel();
		lblScore= new JLabel();
		lblNum1= new JLabel("");
		lblNum2= new JLabel("");
		lblx= new JLabel(" x ");
		txtAnswer = new JTextField();

		rnd = new Random();
		
		newQuestion();
		
		oldQ = -1;
		
		tmrBomb = new Timer(1000, new Listener(this));
		delay = new DelayTimer(this);
		tmrDelay = new Timer(1000, delay);
		rnd = new Random();
		end= false;
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		// Multiplication sign
		c.gridx = 2;
		c.gridy = 0;
		add(lblx, c);
		lblx.setFont(Controller.getFont().deriveFont(Font.BOLD, 80));
		
		//Numbers
		c.gridx=1;
		add(lblNum1, c);
		lblNum1.setFont(Controller.getFont().deriveFont(Font.BOLD, 140));
		c.gridx=3;
		add(lblNum2, c);
		lblNum2.setFont(Controller.getFont().deriveFont(Font.BOLD, 140));
		
		// Textfield
		c.gridx=1;
		c.gridy=1;
		c.gridwidth = 3;
		add(txtAnswer, c);
		txtAnswer.setFont(Controller.getFont().deriveFont(Font.BOLD, 60));
		txtAnswer.addKeyListener(this);
		((AbstractDocument) txtAnswer.getDocument()).setDocumentFilter(new DocumentLengthFilter(3));
		
		//Timer
		c.gridx=2;
		c.gridy=2;
		c.gridwidth=1;
		add(lblBombTimer, c);
		lblBombTimer.setFont(Controller.getFont().deriveFont(Font.BOLD, 90));
		tmrBomb.start();
		
	}
	public void setBombTime(int time) {
		lblBombTimer.setText(":"+ time);
		if (time == 0) {
			tmrBomb.stop();
			end = true;
		}
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

	/**
	 * Creates and sets a new question
	 */
	public void newQuestion() {
		
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
					newQuestion();
					//if not delayed
					if(tmrBomb.isRunning()){
						tmrBomb.stop();
						delay.start();
						tmrDelay.start();
						lblBombTimer.setForeground(Color.white);
					}else{
						//If delay is already on, extend it
						delay.increaseTime();	
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

}
class DelayTimer implements ActionListener{
	private LGame lgame;
	private int time;
	private static int DELAY_TIME = 1;
	
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
			lgame.getBombLabel().setForeground(Color.black);
		}
	}
	public void increaseTime(){
		this.time+= DELAY_TIME;
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
