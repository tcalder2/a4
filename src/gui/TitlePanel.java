package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import gui.Controller;

/**
 * The class TitlePanel, a populated BackgroundPanel.
 * 
 * @author James Anderson
 * @version 1.1
 */
@SuppressWarnings("serial")
public class TitlePanel extends BackgroundPanel {	
	
	/**
	 * Instantiates a TitlePanel instance.
	 *
	 * @param controller	the controller
	 */
	public TitlePanel(Controller controller) {
		
		super("http://jbaron6.cs2212.ca/img/topbanner.png", new BorderLayout());

		JButton toMain = new JButton();
		toMain.setContentAreaFilled(false);
		toMain.setBorderPainted(false);
		toMain.addActionListener(new PressMain(controller));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/back.png"));
			toMain.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			toMain.setText("main");
		}
		add(toMain, BorderLayout.WEST);
			
		JButton toSettings = new JButton();
		toSettings.setContentAreaFilled(false);
		toSettings.setBorderPainted(false);
		toSettings.addActionListener(new PressSettings(controller));
		try {
			Image img1 = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/settings.png"));
			toSettings.setIcon(new ImageIcon(img1));
		} catch (IOException e) {
			toSettings.setText("setup");
		}
		add(toSettings, BorderLayout.EAST);
			
		try {
			Image img2 = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/apptitle.png"));
			add(new JLabel(new ImageIcon(img2)));
		} catch (IOException e) {
			add(new JLabel("UWOTTABLE"));
		}
	}
}

/**
 * The Class PressMain.
 */
class PressMain implements ActionListener {
	
	/** The controller */
	private Controller controller;
	
	/**
	 * Instantiates the press main listener
	 * 
	 * @param the controller
	 */
	public PressMain(Controller control) {
		super();
		controller = control;
	}
	
	/**
	 * Performs the action on event.
	 * 
	 * @param the action event
	 */
	public void actionPerformed(ActionEvent e) {
		if (Controller.getCurrentProgeny() != null) {
			MainMenu screen = new MainMenu(controller);
			Controller.setScreen(screen);
		}
		else {
			Welcome screen = new Welcome(controller);
			Controller.setScreen(screen);
		}
	}
}

/**
 * The Class PressSettings.
 */
class PressSettings implements ActionListener {
	
	/** The controller */
	private Controller controller;
	
	/**
	 * Instantiates the press settings listener
	 * 
	 * @param the controller
	 */
	public PressSettings(Controller control) {
		super();
		controller = control;
	}
	
	/**
	 * Performs the action on event.
	 * 
	 * @param the action event
	 */
	public void actionPerformed(ActionEvent e) {
		LockScreen screen = new LockScreen(controller);
		Controller.setScreen(screen);
	}
}