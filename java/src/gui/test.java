package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import json.json.JSONFailureException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import user.User;

public class test extends JApplet {

	private JPanel outer_panel;
	private JLabel test_label;
	JTextArea text_area;

	public void init() {
		// Panel layout in absolute form
		outer_panel = new JPanel();

		text_area = new JTextArea();
		
		JScrollPane areaScrollPane = new JScrollPane(text_area);
		areaScrollPane.setVerticalScrollBarPolicy(
		                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));
		
		areaScrollPane.setSize(500, 250);
		
		// Make the panel layout absolute
		outer_panel.setLayout(null);

//		test_label = new JLabel();
//		test_label.setLayout(null);
//		test_label.setBounds(0, 0, 500, 250);
//		test_label.setText("test");
//		test_label.setVerticalAlignment(JLabel.TOP);

		
		
		outer_panel.add(areaScrollPane);
		add(outer_panel);

		setSize(new Dimension(500, 250));

		
		beginTest();

	}

	private void appendTestMessage(String message) {
		text_area.append(message + "\n");
	}

	private void appendTestMessages(ArrayList<String> messages) {
		
		Iterator<String> messages_it = messages.iterator();
		
		while(messages_it.hasNext())
		{
			appendTestMessage((String)messages_it.next());
		}
	}
	
	public void beginTest() {
		User user = new User();

		boolean authenticated = false;
		
		try {
			authenticated = user.Authenticate("cs2212");
		} catch (JSONFailureException e) {
			appendTestMessage("Authentication test 1 failed: ");
			appendTestMessages(e.getMessages());
		}

		if (authenticated)
			appendTestMessage("Authentication test 1 succeeded.");

	}

}
