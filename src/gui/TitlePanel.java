package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import gui.Controller;

/**
 * The Class TitlePanel.
 */
public class TitlePanel extends BackgroundPanel {	
	
	/**
	 * Instantiates a new title panel.
	 *
	 * @param controller the controller
	 */
	public TitlePanel(Controller controller) {
		
		super("http://jbaron6.cs2212.ca/img/topbanner.png", 600, 100, new BorderLayout());

		JButton toMain = new JButton();
		toMain.setContentAreaFilled(false);
		toMain.setBorderPainted(false);
		toMain.addActionListener(new toMainMenu(controller));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/back.png"));
			toMain.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			toMain.setText("main");
		}
		add(toMain, BorderLayout.WEST);
			
		JButton toParentMode = new JButton();
		toParentMode.setContentAreaFilled(false);
		toParentMode.setBorderPainted(false);
		toParentMode.addActionListener(new toParentMode(controller));
		try {
			Image img1 = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/settings.png"));
			toParentMode.setIcon(new ImageIcon(img1));
		} catch (IOException e) {
			toParentMode.setText("setup");
		}
		add(toParentMode, BorderLayout.EAST);
			
		try {
			Image img2 = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/apptitle.png"));
			add(new JLabel(new ImageIcon(img2)));
		} catch (IOException e) {
			add(new JLabel("UWOTTABLE"));
		}
	}
}

class toMainMenu implements ActionListener {
	
	private Controller controller;
	
	public toMainMenu(Controller control) {
		super();
		controller = control;
	}
	
	public void actionPerformed(ActionEvent e) {
		MainMenu menu = new MainMenu(controller);
		controller.setScreen(menu);
	}
}

class toParentMode implements ActionListener {
	
	private Controller controller;
	
	public toParentMode(Controller control) {
		super();
		controller = control;
	}
	
	public void actionPerformed(ActionEvent e) {
		LockScreen screen = new LockScreen(controller);
		controller.setScreen(screen);
	}
}