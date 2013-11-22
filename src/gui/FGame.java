package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
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

/**
 * The class FGame, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 0.1
 */
@SuppressWarnings("serial")
public class FGame extends BackgroundPanel implements Runnable {

	private int x, y, op1, op2, a1, a2, a3, ansCorrect, a1x, a1y, a2x, a2y, a3x, a3y, correct, incorrect, lockout;
	private Random rand = new Random();
	private boolean left, right, up, down;
	int fallCount = 0;
	private boolean isQ = false;
	private String question, ans1, ans2, ans3, rightCounter, wrongCounter;
	private boolean answerRight, answerWrong;
	

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

		lockout = 0; // the cool down between when you can select answers
		correct = 0;
		incorrect = 0;
		rightCounter = ("" + correct);
		wrongCounter = ("" + incorrect);
		
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
		a3x = rand.nextInt(700) + 50;
		a3y = rand.nextInt(400) + 50;
		
		a2x = rand.nextInt(700) + 50;
		while ((a3x - a2x) < 50 &&  (a3x - a2x) > -50) {
			a2x = rand.nextInt(700) + 50;
		}
		a2y = rand.nextInt(400) + 50;
		while ((a3y - a2y) < 50 &&  (a3y - a2y) > -50) {
			a2y = rand.nextInt(400) + 50;
		}

		a1x = rand.nextInt(700) + 50;
		while ((a3x - a1x) < 50 &&  (a3x - a1x) > -50) {
			a1x = rand.nextInt(700) + 50;
		}
		a1y = rand.nextInt(400) + 50;
		while ((a3y - a1y) < 50 &&  (a3y - a1y) > -50) {
			a1y = rand.nextInt(400) + 50;
		}
		
		ans1 = ("" + a1);
		ans2 = ("" + a2);
		ans3 = ("" + ansCorrect);
		
		question = (op1 + " x " + op2);
		System.out.println(question);
		
	}

	/**
	 * Update the game state
	 * 
	 */
	public void update() {

		if (lockout > 0) {
			lockout--;
		}
		
		if (answerRight == true) {
			
			answerRight = false;
			answerWrong = false;
			correct++;
			isQ = false;
			rightCounter = ("" + correct);
			wrongCounter = ("" + incorrect);
			
		}
		else if (answerWrong == true) {
			
			answerRight = false;
			answerWrong = false;
			incorrect++;
			isQ = false;
			rightCounter = ("" + correct);
			wrongCounter = ("" + incorrect);
			
		}
		
		if (((x - a3x) < 30 && (x - a3x) > -30) && ((y - a3y) < 30 && (y - a3y) > -30) && lockout == 0) {
			answerRight = true;
			answerWrong = false;
			lockout = 70;
			newQuestion();
		}
		else if (((x - a2x) < 30 && (x - a2x) > -30) && ((y - a2y) < 30 && (y - a2y) > -30) && lockout == 0) {
			answerRight = false;
			answerWrong = true;
			lockout = 70;
			newQuestion();
		}
		else if (((x - a1x) < 30 && (x - a1x) > -30) && ((y - a1y) < 30 && (y - a1y) > -30) && lockout == 0) {
			answerRight = false;
			answerWrong = true;
			lockout = 70;
			newQuestion();
		}
		
		
		if (left) {
			x--;
			x--;
			left = true;
			right = false;
		}
		if (right) {
			x++;
			x++;
			left = false;
			right = true;
		}
		if (up) {
			y--;
			y--;
			up = true;
			down = false;
		}
		if (down) {
			y++;
			y++;
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

	public void run() {
		while (true) {

			update();
			repaint();
			this.addKeyListener(new ButtonHandler());

			this.setFocusable(true);
			this.requestFocusInWindow();
			
			if (fallCount <= 0) {
				y++;
				fallCount = 2;
			}
			else {
				fallCount--;
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void paintComponent(Graphics g) {
		
		
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		g.setColor(Color.black);
		g.drawRect(x, y, 20, 20);
		g.drawString(question, 360, 55);
		g.drawString(ans1, a1x, a1y);
		g.drawString(ans2, a2x, a2y);
		g.drawString(ans3, a3x, a3y);
		g.drawString(rightCounter, 200, 55);
		g.drawString(wrongCounter, 550, 55);
	}

	public class ButtonHandler extends KeyAdapter {

		public ButtonHandler() {
			//System.out.println(" Button handler initialised! ");

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
				//System.out.println(" Released UP!");
				break;
			case KeyEvent.VK_DOWN:
				//System.out.println(" Released DOWN!");
				down = false;
				break;
			case KeyEvent.VK_LEFT:
				//System.out.println(" Released LEFT!");
				left = false;
				break;
			case KeyEvent.VK_RIGHT:
				//System.out.println(" Released RIGHT!");
				right = false;
				break;

			}
		}

	}

}
