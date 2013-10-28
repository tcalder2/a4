package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import json.Json.JSONFailureException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import user.User;

public class Test extends JApplet {

	private JPanel outer_panel;
	private JLabel test_label;
	JTextArea text_area;

	public void init() {

		// Panel layout in absolute form
		outer_panel = new JPanel();

		text_area = new JTextArea();

		JScrollPane areaScrollPane = new JScrollPane(text_area);
		areaScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));

		areaScrollPane.setSize(500, 250);

		// Make the panel layout absolute
		outer_panel.setLayout(null);

		test_label = new JLabel();
		test_label.setLayout(null);
		test_label.setBounds(0, 260, 300, 35);
		test_label.setVerticalAlignment(JLabel.TOP);

		outer_panel.add(test_label);
		outer_panel.add(areaScrollPane);
		add(outer_panel);

		setSize(new Dimension(500, 300));

		beginTest();

	}

	private void appendTestMessage(String message) {
		text_area.append(message + "\n");
	}

	private void appendTestMessages(ArrayList<String> messages) {

		Iterator<String> messages_it = messages.iterator();

		while (messages_it.hasNext()) {
			appendTestMessage((String) messages_it.next());
		}
	}

	public void beginTest() {
		User user = new User();

		int succeeded_count = 0;
		int failed_count = 0;
		boolean succeeded = false;
		ArrayList<String> messages = new ArrayList<String>();

		// TEST AUTH1
		appendTestMessage("AUTH1: Attempting to authenticate with VALID password \"cs2212\"");

		try {
			user.authenticate("cs2212");
			++succeeded_count;
			appendTestMessage("TEST PASSED: AUTH1");
		} catch (JSONFailureException e) {
			appendTestMessage("TEST FAILED: AUTH1");
			appendTestMessages(e.getMessages());
			++failed_count;
		}

		// blank line
		appendTestMessage("");

		// TEST AUTH2
		appendTestMessage("AUTH2: Attempting to authenticate with INVALID password \"group4\"");

		try {
			user.authenticate("group4");
			++failed_count;
			appendTestMessage("TEST FAILED: AUTH2");
		} catch (JSONFailureException e) {

			if (e.getMessages().get(0).compareTo("Could not authenticate user") == 0) {
				++succeeded_count;
				appendTestMessage("TEST PASSED: AUTH2");
			} else {
				++failed_count;
				appendTestMessage("TEST FAILED: AUTH2");
			}

			appendTestMessages(e.getMessages());
		}

		// blank line
		appendTestMessage("");

		// TEST CHPAS1
		appendTestMessage("CHPAS1: Attempting to set the password more than 6 characters.");
		succeeded = false;
		messages = new ArrayList<String>();

		try {
			user.setPassword("something very long");
		} catch (JSONFailureException e) {
			messages = e.getMessages();
			if (messages.get(0).compareTo(
					"The password can have no more than 6 characters") == 0) {
				++succeeded_count;
				succeeded = true;
				appendTestMessage("TEST PASSED: CHPAS1");
			}
		}

		if (!succeeded) {
			appendTestMessage("TEST FAILED: CHPAS1");
			++failed_count;
		}

		appendTestMessages(messages);

		appendTestMessage("");

		// TEST CHPAS2
		appendTestMessage("CHPAS2: Attempting to set the password less than 3 characters.");
		succeeded = false;
		messages = new ArrayList<String>();

		try {
			user.setPassword("s");
		} catch (JSONFailureException e) {
			messages = e.getMessages();
			if (messages.get(0).compareTo(
					"The password can have no fewer than 3 characters") == 0) {
				++succeeded_count;
				succeeded = true;
				appendTestMessage("TEST PASSED: CHPAS2");
			}
		}

		if (!succeeded) {
			appendTestMessage("TEST FAILED: CHPAS2");
			++failed_count;
		}

		appendTestMessages(messages);

		appendTestMessage("");

		// TEST CHPAS3
		appendTestMessage("CHPAS3: Attempting to set the password to a legal password \"group4\".");
		succeeded = false;
		messages = new ArrayList<String>();

		try {
			user.setPassword("group4");
			succeeded = true;
			++succeeded_count;
			appendTestMessage("TEST PASSED: CHPAS3");
		} catch (JSONFailureException e) {
			succeeded = false;
			appendTestMessage("TEST FAILED: CHPAS3");
			++failed_count;
			appendTestMessages(e.getMessages());
		}

		appendTestMessage("");

		// TEST AUTH3
		appendTestMessage("AUTH3: Attempting to authenticate with password \"group4\"");

		try {
			user.authenticate("group4");
			succeeded = true;
			++succeeded_count;
			appendTestMessage("TEST PASSED: AUTH3");
		} catch (JSONFailureException e) {
			appendTestMessage("TEST FAILED: AUTH3");
			++failed_count;
			appendTestMessages(e.getMessages());
		}

		// blank line
		appendTestMessage("");

		// TEST CHPAS4
		appendTestMessage("CHPAS4: Attempting to set the password to a legal password \"cs2212\".");
		succeeded = false;
		messages = new ArrayList<String>();

		try {
			user.setPassword("cs2212");
			succeeded = true;
			++succeeded_count;
			appendTestMessage("TEST PASSED: CHPAS4");
		} catch (JSONFailureException e) {
			succeeded = false;
			appendTestMessage("TEST FAILED: CHPAS4");
			appendTestMessages(e.getMessages());
			++failed_count;
		}

		appendTestMessage("");

		// TEST AUTH4
		appendTestMessage("AUTH4: Attempting to authenticate with VALID password \"cs2212\"");

		try {
			user.authenticate("cs2212");
			++succeeded_count;
			appendTestMessage("TEST PASSED: AUTH4");
		} catch (JSONFailureException e) {
			appendTestMessage("TEST FAILED: AUTH4");
			appendTestMessages(e.getMessages());
			++failed_count;
		}

		// blank line
		appendTestMessage("");

		// TEST QUES1
		appendTestMessage("QUES1: Getting questions");

		try {
			ArrayList<String> questions = User.getQuestions();
			++succeeded_count;
			appendTestMessage("TEST PASSED: QUES1");
			appendTestMessages(questions);
		} catch (JSONFailureException e) {
			appendTestMessage("TEST FAILED: QUES1");
			appendTestMessages(e.getMessages());
			++failed_count;
		}


		// blank line
		appendTestMessage("");

		appendTestMessage("QUES2: Attempting to set the question to INVALID value \"4\"");
		
		try {
			User.setQuestion("4");
			++failed_count;
			appendTestMessage("TEST FAILED: QUES2");
		} catch (JSONFailureException e) {
			
			if(e.getMessages().get(0).compareTo("The input is not between \u00270\u0027 and \u00272\u0027, inclusively") == 0)
			{
				appendTestMessage("TEST PASSED: QUES2");
				++succeeded_count;
			}
			else
			{
				appendTestMessage("TEST FAILED: QUES2");
				++failed_count;
			}
			
			appendTestMessages(e.getMessages());
		}

		appendTestMessage("");

		appendTestMessage("QUES3: Attempting to set the question to INVALID value \"-1\"");
		
		try {
			User.setQuestion("-1");
			++failed_count;
			appendTestMessage("TEST FAILED: QUES3");
		} catch (JSONFailureException e) {
			if(e.getMessages().get(0).compareTo("The input is not between \u00270\u0027 and \u00272\u0027, inclusively") == 0)
			{
				appendTestMessage("TEST PASSED: QUES2");
				++succeeded_count;
			}
			else
			{
				appendTestMessage("TEST FAILED: QUES2");
				++failed_count;
			}
			
			appendTestMessages(e.getMessages());
		}
		
		appendTestMessage("");

		appendTestMessage("QUES4: Attempting to set the question to VALID value \"1\"");
		
		try {
			User.setQuestion("1");
			++succeeded_count;
			appendTestMessage("TEST PASSED: QUES4");
		} catch (JSONFailureException e) {
			appendTestMessage("TEST FAILED: QUES4");
			appendTestMessages(e.getMessages());
			++failed_count;
		}



		test_label.setText("Tests Passed: " + succeeded_count
				+ "  Tests Failed: " + failed_count);

	}

}
