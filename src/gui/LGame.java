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
 * The class LGame, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @author Yaqzan Ali
 * @version 0.1
 */
@SuppressWarnings("serial")
public class LGame extends BackgroundPanel {

	/** The level progeny holding the level details. */
	private LevelProgeny level;
	private static int BOMB_TIME = 10;
	private JLabel lblBombTimer;
	private JLabel lblScore;
	private JLabel lblNum1;
	private JLabel lblNum2;
	private JLabel lblx;
	private JTextField txtAnswer;
	private Timer tmrBomb;
	private Random rnd;
	private boolean end;
	private int intLevel;
	
	
	
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
		lblx= new JLabel("x");
		txtAnswer = new JTextField();
		tmrBomb = new Timer(1000, new Listener(this));
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
		
		//Timer
		c.gridx=2;
		c.gridy=2;
		c.gridwidth=1;
		add(lblBombTimer, c);
		lblBombTimer.setFont(Controller.getFont().deriveFont(Font.BOLD, 90));
		
	}
	public void setTime(int time) {
		lblBombTimer.setText(":"+ time);
		if (time == 0) {
			tmrBomb.stop();
			end = true;
		}
	}
	
	public static int getBombTime(){
		return BOMB_TIME;
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
	
		game.setTime(timeRemaining);
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
		lgame.setTime(timeRemaining);
	}
}
