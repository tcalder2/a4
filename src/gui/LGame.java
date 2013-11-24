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
	private int answer=10;
	
	
	
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
		lblNum1= new JLabel("5");
		lblNum2= new JLabel("2");
		lblx= new JLabel(" x");
		txtAnswer = new JTextField();
		tmrBomb = new Timer(1000, new Listener(this));
		tmrDelay = new Timer(1000, new DelayTimer(this));
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE))){
			e.consume();
		}
		System.out.println(txtAnswer.getText());
		int text = Integer.valueOf("2");
		if (text==answer){
			tmrBomb.stop();
			tmrDelay.start();
		}
		
	}

}
class DelayTimer implements ActionListener{
	private LGame lgame;
	private int time;
	private static int DELAY_TIME = 2;
	
	public DelayTimer(LGame game) {
		this.lgame = game;
		this.time = 0;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (time < (DELAY_TIME +1)) {
			time++;
		}else{
			lgame.getDelayTimer().stop();
			lgame.getBombTimer().start();
		}
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
