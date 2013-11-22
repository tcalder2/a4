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
 * @author Taylor Calder
 * @author James Anderson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FGame extends BackgroundPanel implements Runnable {

	private int x, y, op1, op2, a1, a2, a3, ansCorrect, a1x, a1y, a2x, a2y,
			a3x, a3y, score, incorrect, lockout;
	private Random rand = new Random();
	private boolean left, right, up, down;
	int fallCount = 0;
	private boolean isQ = false;
	private String question, ans1, ans2, ans3, rightCounter, wrongCounter;
	private boolean answerRight, answerWrong;
	private Font font = new Font(Font.SERIF, Font.BOLD, 30);

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
		score = 0;
		incorrect = 0;
		String.format("%08d", score);
		rightCounter = ("Score " + score);
		wrongCounter = ("Mistakes: " + incorrect);

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
		a3x = rand.nextInt(600) + 50;
		a3y = rand.nextInt(300) + 100;

		a2x = rand.nextInt(600) + 50;
		while ((a3x - a2x) < 50 && (a3x - a2x) > -50) {
			a2x = rand.nextInt(600) + 50;
		}
		a2y = rand.nextInt(300) + 100;
		while ((a3y - a2y) < 50 && (a3y - a2y) > -50) {
			a2y = rand.nextInt(300) + 100;
		}

		a1x = rand.nextInt(600) + 50;
		while (((a3x - a1x) < 50 && (a3x - a1x) > -50)
				&& ((a2x - a1x) < 50 && (a2x - a1x) > -50)) {
			a1x = rand.nextInt(600) + 50;
		}
		a1y = rand.nextInt(300) + 100;
		while (((a3y - a1y) < 50 && (a3y - a1y) > -50)
				&& ((a2y - a1y) < 50 && (a2y - a1y) > -50)) {
			a1y = rand.nextInt(300) + 100;
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
			score += 50;
			isQ = false;
			rightCounter = ("Score: " + score);
			wrongCounter = ("Mistakes: " + incorrect);

		} else if (answerWrong == true) {

			answerRight = false;
			answerWrong = false;
			score -= 25;
			incorrect++;
			isQ = false;
			rightCounter = ("Score: " + score);
			wrongCounter = ("Mistakes: " + incorrect);

		}

		if (((x - a3x) < 15 && (x - a3x) > -30)
				&& ((y - a3y) < 15 && (y - a3y) > -30) && lockout == 0) {
			answerRight = true;
			answerWrong = false;
			lockout = 70;
			newQuestion();
		} else if (((x - a2x) < 15 && (x - a2x) > -30)
				&& ((y - a2y) < 15 && (y - a2y) > -30) && lockout == 0) {
			answerRight = false;
			answerWrong = true;
			lockout = 70;
			newQuestion();
		} else if (((x - a1x) < 15 && (x - a1x) > -30)
				&& ((y - a1y) < 15 && (y - a1y) > -30) && lockout == 0) {
			answerRight = false;
			answerWrong = true;
			lockout = 70;
			newQuestion();
		}

		if (left) {

			if (up) {
				x--;
				y--;
			} else if (down) {
				x--;
				y++;
			} else {
				x--;
				x--;
			}
			left = true;
			right = false;
		}
		if (right) {
			if (up) {
				x++;
				y--;
			} else if (down) {
				x++;
				y++;
			} else {
				x++;
				x++;
			}
			left = false;
			right = true;
		}
		if (up) {

			if (right) {
				x++;
				y--;
			} else if (left) {
				x--;
				y--;
			} else {
				y--;
				y--;
			}
			up = true;
			down = false;
		}
		if (down) {
			if (right) {
				x++;
				y++;
			} else if (left) {
				x--;
				y++;
			} else {
				y++;
				y++;
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
			} else {
				fallCount--;
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();// preserve the message
				return;// Stop doing whatever I am doing and terminate
			}

		}
	}

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
		g.drawString(wrongCounter, 540, 55);
	}

	public class ButtonHandler extends KeyAdapter {

		public ButtonHandler() {
			// System.out.println(" Button handler initialised! ");

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

}
