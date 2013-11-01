package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import gui.Controller;

public class MainMenu extends BackgroundPanel {
	
	public MainMenu(Controller controller) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,150,0,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		
		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/mainmenu.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("Main Menu"), c);
		}
		
		c.insets = new Insets(0,150,0,150);
		c.ipady = 20;
		c.weightx = 0.5;
		
		JButton toDrill = new JButton();
		toDrill.setContentAreaFilled(false);
		toDrill.setBorderPainted(false);
		toDrill.addActionListener(new toDrillMode(controller));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/main/drills.png"));
			toDrill.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			toDrill.setText("Drills");
		}
		c.gridy = 1;
		add(toDrill, c);
		
		JButton toLGame = new JButton();
		toLGame.setContentAreaFilled(false);
		toLGame.setBorderPainted(false);
		toLGame.addActionListener(new toLGame(controller));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/main/lgames_l.png"));
			toLGame.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			toLGame.setText("Level Games");
		}
		c.gridy = 2;
		add(toLGame, c);

		JButton toFGame = new JButton();
		toFGame.setContentAreaFilled(false);
		toFGame.setBorderPainted(false);
		toFGame.addActionListener(new toFGame(controller));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/main/fgame_l.png"));
			toFGame.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			toFGame.setText("Final Game");
		}
		c.gridy = 3;
		add(toFGame, c);
		
		c.insets = new Insets(0,150,0,150);
		
		JButton toStats = new JButton();
		toStats.setContentAreaFilled(false);
		toStats.setBorderPainted(false);
		toStats.addActionListener(new toStats(controller));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/main/scores&stats.png"));
			toStats.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			toStats.setText("Scores & Stats");
		}
		c.gridy = 4;
		add(toStats, c);
	}	
}

class toDrillMode implements ActionListener {
	
	private Controller controller;
	
	public toDrillMode(Controller control) {
		super();
		controller = control;
	}
	
	public void actionPerformed(ActionEvent e) {
		DrillMenu menu = new DrillMenu(controller);
		controller.setScreen(menu);
	}
}

class toLGame implements ActionListener {

	private Controller controller;
	
	public toLGame(Controller control) {
		super();
		controller = control;
	}
	
	public void actionPerformed(ActionEvent e) {
		LGameMenu menu = new LGameMenu(controller);
		controller.setScreen(menu);
	}
}

class toFGame implements ActionListener {

	private Controller controller;
	
	public toFGame(Controller control) {
		super();
		controller = control;
	}
	
	public void actionPerformed(ActionEvent e) {
		FGameMenu menu = new FGameMenu(controller);
		controller.setScreen(menu);
	}
}

class toStats implements ActionListener {

	private Controller controller;
	
	public toStats(Controller control) {
		super();
		controller = control;
	}
	
	public void actionPerformed(ActionEvent e) {
		StatsMenu menu = new StatsMenu(controller);
		controller.setScreen(menu);
	}
}