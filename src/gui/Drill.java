package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Drill extends BackgroundPanel {
	
	private Controller controller;
	private int level;
	private int lives;
	private int correct;
	private int incorrect;
	private JLabel timer;
	private JLabel incorrCounter;
	private JLabel corrCounter;
	private JLabel markImg;
	private JLabel solution;
	private JButton next;
	private JButton submit;
	private Timer clock;
	
	public Drill(Controller controller, int level) {
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
		
		JLabel livesCount = new JLabel();
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill/heart.png"));
			livesCount.setText(" x lives");
			livesCount.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			livesCount.setText("<3 x " + lives);
		}
		livesCount.setFont(controller.getFont().deriveFont(Font.BOLD, 35));
		add(livesCount);
		
		timer = new JLabel("", SwingConstants.RIGHT);
		timer.setFont(controller.getFont().deriveFont(Font.BOLD, 35));
		setTime(10);
		c.gridx = 4;
		c.anchor = GridBagConstraints.EAST;
		add(timer, c);
		
		JLabel question = new JLabel("1 x 12 = ");
		question.setFont(controller.getFont().deriveFont(Font.BOLD, 60));
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 1;
		c.insets = new Insets(50,50,0,0);
		add(question, c);
		
		submit = new JButton("Submit");
		
		JTextField answerField = new JTextField();
		answerField.setFont(controller.getFont().deriveFont(Font.BOLD, 60));
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
		
		clock = new Timer(1000, new TimerAction(this, answerField, submit));
		clock.start();
		JLabel markImg = new JLabel(" ");
		c.ipady = 60;
		c.gridx = 2;
		c.gridy = 2;
		c.insets = new Insets(0,0,0,0);
		add(markImg, c);
		
		solution = new JLabel(" ");
		solution.setFont(controller.getFont().deriveFont(Font.BOLD, 35));
		c.ipady = 50;
		c.gridy = 3;
		add(solution, c);
		
		next = new JButton("Next");
		next.setVisible(false);
		c.gridy = 4;
		add(next, c);
		
		corrCounter = new JLabel("" + correct);
		corrCounter.setForeground(Color.GREEN);
		corrCounter.setFont(controller.getFont().deriveFont(Font.BOLD, 35));
		corrCounter.setVerticalAlignment(SwingConstants.BOTTOM);
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 0;
		c.gridy = 5;
		c.insets = new Insets(0,50,0,50);
		add(corrCounter, c);
		
		incorrCounter = new JLabel("" + incorrect);
		incorrCounter.setForeground(Color.RED);
		incorrCounter.setFont(controller.getFont().deriveFont(Font.BOLD, 35));
		incorrCounter.setVerticalAlignment(SwingConstants.BOTTOM);
		c.gridx = 4;
		add(incorrCounter, c);
	}
	
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
	
	
	public void checkAnswer(String answer) {
		
		int correctanswer = 12;
		
		submit.setVisible(false);
		clock.stop();
		
		if (answer.equals("" + correctanswer)) {
			try {
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill/x.png"));
				markImg = new JLabel(new ImageIcon(img));
			} catch (IOException e) {
				markImg = new JLabel("CORRECT");
			}
			correct++;
		}
		else {
			try {
				Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/drill/check.png"));
				markImg = new JLabel(new ImageIcon(img));
			} catch (IOException e) {
				markImg = new JLabel("WRONG");
			}
			solution = new JLabel("Answer: " + correctanswer);
			solution.setFont(controller.getFont().deriveFont(Font.BOLD, 35));
			incorrect++;
		}
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
		
		next.setVisible(true);
	}
}

class TimerAction implements ActionListener {
	private Drill drill;
	private JButton submit;
	private int timeLimit = 10;
	private int count;

	public TimerAction(Drill drill, JTextField answer, JButton submit) {
		this.drill = drill;
		this.submit = submit;
		int time = (timeLimit - count);
		drill.setTime(time);
	}

	public void actionPerformed(ActionEvent e) {
		if (count < timeLimit) {
			count++;
			int time = (timeLimit - count);
			drill.setTime(time);
		}
		else {
			submit.doClick();
		}
	}
}

class Submit implements ActionListener {
	private Drill drill;
	private JTextField answer;
	
	public Submit(Drill drill, JTextField answer) {
		this.drill = drill;
		this.answer = answer;
	}
	
	public void actionPerformed(ActionEvent e) {
		drill.checkAnswer(new String(answer.getText()));
	}
}