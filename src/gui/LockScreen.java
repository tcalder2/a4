package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The Class LockScreen.
 */
public class LockScreen extends BackgroundPanel {
	
	/**
	 * Instantiates a new lock screen.
	 *
	 * @param controller the controller
	 */
	public LockScreen(Controller controller) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0,100,0);
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/lock.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("<html>Oops!<br>"
					+ "It seems we are having trouble communicating!</html>"), c);
		}			
		c.weightx = 0;
		c.weighty = 0;
		c.gridy = 0;

		JLabel pwdLabel = new JLabel("Password: ");
		pwdLabel.setFont(controller.getFont().deriveFont(Font.BOLD, 60));
		c.insets = new Insets(100,200,50,5);
		c.gridx = 0;
		add(pwdLabel, c);

		JButton ok = new JButton("Ok");
		ok.setContentAreaFilled(false);
		ok.setBorderPainted(false);
		ok.addActionListener(new toDrillMode(controller));
		try {
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/ok.png"));
			ok.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			ok.setText("Ok");
		}

		JPasswordField passwordField = new JPasswordField("000000");
		passwordField.addKeyListener(new EnterListener(ok));
		c.insets = new Insets(100,5,50,5);
		c.gridx = 1;
		add(passwordField, c);
		
		ok.addActionListener(new PressOk(controller, passwordField));
		c.insets = new Insets(100,5,50,200);
		c.gridx = 2;
		add(ok, c);
	}
}
class PressOk implements ActionListener {
	private int errors;
	private Controller controller;
	private JPasswordField pwdf;
	
	public PressOk(Controller control, JPasswordField passwordField) {
		super();
		controller = control;
		pwdf = passwordField;
	}
	
	public void actionPerformed(ActionEvent evt) {
		char[] pwd = null;
		pwd = pwdf.getPassword();
		
		if (pwd[0] == 'c') {
			Settings screen = new Settings(controller);
			controller.setScreen(screen);
		}
		else {
			LockScreen screen = new LockScreen(controller);
			controller.setScreen(screen);

		}
	}
}
