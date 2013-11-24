package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The class FGame, a populated BackgroundPanel.
 * 
 * @author Taylor Calder
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FGame extends BackgroundPanel implements Runnable {

	private int x, y, op1, op2, a1, a2, ansCorrect, a1x, a1y, a2x, a2y, a3x,
			a3y, score, cooldowm, timeLeft;
	final private int MIN_X = 100;
	final private int MIN_Y = 100;
	private Random rand = new Random();
	private boolean left, right, up, down;
	int fallCount = 0;
	private String question, ans1, ans2, ans3, rightCounter, timeCounter;
	private boolean answerRight, answerWrong;
	private Font font = new Font(Font.SERIF, Font.BOLD, 30);
	private Timer clock;

	/** The background graphic. */
	private Image background;

	/**
	 * Instantiates a FGame instance.
	 * 
	 */
	public FGame() {
		// Calls superclass constructor to create the background panel
		super("http://jbaron6.cs2212.ca/img/default_background.png",
				new GridBagLayout());

		this.setFocusable(true);
		this.requestFocusInWindow();

		timeLeft = 60;
		cooldowm = 0; // the cool down between when you can select answers
		score = 0;
		String.format("%08d", score);
		rightCounter = ("Score " + score);
		timeCounter = ("Time: " + timeLeft + "s");
		clock = new Timer(1000, new TimerActionF(this, timeLeft));

		try {
			// Load the graphic and set the dimensions of the panel
			Image img = ImageIO.read(new URL(
					"http://jbaron6.cs2212.ca/img/default_background.png"));
			background = new ImageIcon(img).getImage();
			Dimension d = new Dimension(background.getWidth(null),
					background.getHeight(null));
			setPreferredSize(d);
			setMinimumSize(d);
			setMaximumSize(d);
			setSize(d);
		} catch (IOException e) {
			// If there is an error loading the graphic, set the background
			// white and display an error message
			setBackground(Color.WHITE);
			JLabel message = (new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"));
			message.setFont(new Font("Serif", Font.BOLD, 35));
			add(message);
		}

		newQuestion();

		x = 300;
		y = 300;
		this.addKeyListener(new ButtonHandler());
		clock.start();
		new Thread(this).start();

	}

	/**
	 * Setup the game state
	 * 
	 */
	public void newQuestion() {

		op1 = rand.nextInt(11) + 1;
		op2 = rand.nextInt(11) + 1;

		ansCorrect = op1 * op2;
		a1 = (op1 + (rand.nextInt(2) + 1)) * op2;
		a2 = ansCorrect - (rand.nextInt(6) + 3);

		// A3 is the correct answer
		a3x = rand.nextInt(600) + 50;
		a3y = rand.nextInt(300) + 100;

		// Generate Question Positions

		a3x = rand.nextInt(600) + 50;
		a3y = rand.nextInt(300) + 100;

		a2x = rand.nextInt(600) + 50;
		a2y = rand.nextInt(300) + 100;

		while (((a3x - a2x) < MIN_X && (a3x - a2x) > -MIN_X)
				&& ((a3y - a2y) < MIN_Y && (a3y - a2y) > -MIN_Y)) {
			a2x = rand.nextInt(600) + 50;
			a2y = rand.nextInt(300) + 100;
		}

		a1x = rand.nextInt(600) + 50;
		a1y = rand.nextInt(300) + 100;
		while ((((a3x - a1x) < MIN_X && (a3x - a1x) > -MIN_X) && ((a2x - a1x) < MIN_X && (a2x - a1x) > -MIN_X))
				&& (((a3y - a1y) < MIN_Y && (a3y - a1y) > -MIN_Y) && ((a2y - a1y) < MIN_Y && (a2y - a1y) > -MIN_Y))) {
			a1x = rand.nextInt(600) + 50;
			a1y = rand.nextInt(300) + 100;
		}

		ans1 = ("" + a1);
		ans2 = ("" + a2);
		ans3 = ("" + ansCorrect);

		question = (op1 + " x " + op2);

	}

	public void setTime(int time) {
		timeLeft = time;

	}

	/**
	 * Update the game state
	 */
	public void update() {


		// After answering a question there is a cooldown before the next question can be answered
		// Helps prevent you accidentally selecting an answer that spawns close to you
		if (cooldowm > 0) {
			cooldowm--;
		}
		
		// Update timer
		if (timeLeft >= 0) {
			timeCounter = ("Time: " + timeLeft + "s");
		} 
		// End game if timer is < 0
		else if (timeLeft < 0) {
			end(false, score);
			return;
		}

		// + 50 points for correct answer
		if (answerRight == true) {
			answerRight = false;
			answerWrong = false;
			score += 50;
			rightCounter = ("Score: " + score);

		} 
		// - 25 points for incorrect answer
		else if (answerWrong == true) {

			answerRight = false;
			answerWrong = false;
			score -= 25;
			rightCounter = ("Score: " + score);
		}

		// Collision detection
		if (((x - a3x) < 60 && (x - a3x) > -30)
				&& ((y - a3y) < 20 && (y - a3y) > -50) && cooldowm == 0) {
			answerRight = true;
			answerWrong = false;
			cooldowm = 80;
			newQuestion();
		} else if (((x - a2x) < 60 && (x - a2x) > -30)
				&& ((y - a2y) < 20 && (y - a2y) > -50) && cooldowm == 0) {
			answerRight = false;
			answerWrong = true;
			cooldowm = 80;
			newQuestion();
		} else if (((x - a1x) < 60 && (x - a1x) > -30)
				&& ((y - a1y) < 20 && (y - a1y) > -50) && cooldowm == 0) {
			answerRight = false;
			answerWrong = true;
			cooldowm = 80;
			newQuestion();
		}

		// ************************************
		// Movement Control
		// ************************************

		// Moving Left
		if (left && !right) {
			left = true;
			right = false;
			if (up) {
				x -= 2;
				y -= 2;
			} else if (down) {
				x -= 2;
				y += 2;
			} else {
				x -= 3;
			}
		}

		// Moving Right
		else if (right && !left) {
			left = false;
			right = true;
			if (up) {
				x += 2;
				y -= 2;
			} else if (down) {
				x += 2;
				y += 2;
			} else {
				x += 3;
			}

		}

		// Moving Up
		if (up) {

			if (!right && !left) {
				y -= 3;
			}
			up = true;
			down = false;

		}

		// Moving Down
		else if (down) {
			if (!right && !left) {
				y += 3;
			}
			up = false;
			down = true;
		}

		if (x > 750) {
			x = 750;
		}
		if (x < 50) {
			x = 50;
		}
		if (y > 450) {
			y = 450;
		}
		if (y < 55) {
			y = 55;
		}

	}

	/**
	 * Runs the game state
	 */
	public void run() {
		while (true) {

			update();
			repaint();
			
			this.addKeyListener(new ButtonHandler());

			this.setFocusable(true);
			this.requestFocusInWindow();

			// The player moves 1 pixel down every 3 tics or 30ms
			if (fallCount <= 0) {
				y++;
				fallCount = 2;
			} else {
				fallCount--;
			}

			// Game Loop
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// End the thread in case of exception
				Thread.currentThread().interrupt();
				return;
			}

		}
	}

	/**
	 * Ends the game
	 * @param win	whether the player lost before time ran out
	 * @param score	the players score
	 */
	public void end(boolean win, int score) {

		Thread.currentThread().interrupt();
		Controller.setScreen(new ScoreReportF(this, score));

	}

	/**
	 * Repaints the graphics
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.setFont(font);
		g.drawImage(background, 0, 0, null);
		g.setColor(Color.black);
		g.drawRect(x, y, 20, 20);

		g.fillRect(112, 31, 180, 30); // score
		g.fillRect(532, 31, 160, 30); // mistakes
		g.fillRect(359, 31, 95, 30); // question

		g.drawString(ans1, a1x, a1y);
		g.drawString(ans2, a2x, a2y);
		g.drawString(ans3, a3x, a3y);

		g.setColor(Color.white);
		g.drawString(question, 365, 55);
		g.drawString(rightCounter, 120, 55);

		// Timer turns red if the player has 10 seconds or less left
		if (timeLeft <= 10) {
			
			
			g.drawString(timeCounter, 539, 55);
			g.drawString(timeCounter, 541, 55);
			g.drawString(timeCounter, 540, 54);
			g.drawString(timeCounter, 540, 56);
			g.setColor(Color.red);
		}
		g.drawString(timeCounter, 540, 55);
	}
	/**
	 * Handles Key input 
	 * @author Taylor Calder
	 * @version 1.0
	 */
	public class ButtonHandler extends KeyAdapter {

		public ButtonHandler() {

		}

		// This function will be used as soon as a key is released.

		public void keyPressed(KeyEvent key) {

			switch (key.getKeyCode()) {
			case KeyEvent.VK_UP:
				up = true;
				break;
			case KeyEvent.VK_DOWN:
				down = true;
				break;
			case KeyEvent.VK_LEFT:
				left = true;
				break;
			case KeyEvent.VK_RIGHT:
				right = true;
				break;
			}
		}

		// This function will be used as soon as a key is released. they
		// KeyEvent key we can use to determine what key we just released
		public void keyReleased(KeyEvent key) {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_UP:
				up = false;
				break;
			case KeyEvent.VK_DOWN:
				down = false;
				break;
			case KeyEvent.VK_LEFT:
				left = false;
				break;
			case KeyEvent.VK_RIGHT:
				right = false;
				break;

			}
		}

	}

	/**
	 * The class TimerAction, an action listener.
	 * 
	 * @author Taylor Calder
	 * @version 1.0
	 */
	class TimerActionF implements ActionListener {

		/** The the number of seconds remaining. */
		private int time;
		private FGame fgame;

		/**
		 * Instantiates the Timer Action.
		 * 
		 * @param int the time left
		 */
		public TimerActionF(FGame game, int timeLeft) {
			this.time = timeLeft;
			this.fgame = game;
			fgame.setTime(time);
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
			if (time > -1) {
				time--;
			}
			fgame.setTime(time);
		}
	}

}
