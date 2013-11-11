package gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;

import json.JSONFailureException;
import ttable.Progeny;
import ttable.User;

/**
 * The Class Settings.
 */
public class Settings extends BackgroundPanel {

	private JTabbedPane tabPane;
	
	/**
	 * Instantiates a new settings.
	 *
	 * @param controller the controller
	 */
	public Settings(Controller controller) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,150,10,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		try	{
			Image img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/titles/settings.png"));
			add(new JLabel(new ImageIcon(img)), c);
		} catch (IOException e) {
			add(new JLabel("Settings"), c);
		}

		c.gridy = 1;
		c.insets = new Insets(0,50,0,50);
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.add("Child Settings", new ChildSettingsTab(controller, this));
		tabPane.add("Level Settings", levelSetTab(controller));
		tabPane.add("Security Settings", securityTab(controller));
		add(tabPane, c);
	}
	
	public void changeTabContent(int index, JPanel newContent) {
		tabPane.setComponentAt(index, newContent);
	}
	
	public JPanel levelSetTab(Controller controller) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,25,0,0);
		c.gridwidth = 2;
		c.gridy = 1;
		c.gridx = 0;
		panel.add(new JLabel("Drill Settings:"), c);

		c.gridwidth = 1;
		c.gridy = 2;
		panel.add(new JLabel("Time per question:"), c);

		Vector<String> t = new Vector<String>();
		for (int i = 5; i <= 120; i += 5) {
			t.add(i + " sec");
		}
		JComboBox<String> time = new JComboBox<String>(t);
		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		panel.add(time, c);

		c.gridwidth = 2;
		c.gridx = 2;
		panel.add(new JLabel("Number of Errors per Level:"), c);

		Vector<String> err = new Vector<String>();
		for (int i = 0; i < 10; i++) {
			err.add("" + i);
		}
		JComboBox<String> errors = new JComboBox<String>(err);
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,75);
		c.gridx = 4;
		panel.add(errors, c);

		c.insets = new Insets(0,75,0,0);
		c.gridx = 0;
		c.gridy = 3;
		panel.add(new JLabel("Testing mode:"), c);

		ButtonGroup teststate = new ButtonGroup();

		JRadioButton testoff = new JRadioButton("Off");
		teststate.add(testoff);
		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		panel.add(testoff, c);

		JRadioButton teston = new JRadioButton("On");
		teststate.add(teston);
		c.gridx = 2;
		panel.add(teston, c);

		JButton apply = new JButton("Apply");
		c.gridy = 4;
		c.gridx = 4;
		panel.add(apply, c);
		
		return panel;
	}
	
	public JPanel securityTab(Controller controller) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		return panel;
	}
}