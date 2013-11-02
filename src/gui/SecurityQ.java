package gui;

import java.awt.Font;

import javax.swing.*;

public class SecurityQ extends BackgroundPanel {
	
	public SecurityQ(Controller controller) {
		super("http://jbaron6.cs2212.ca/img/default_background.png");
		
		String theQ = "Security question ,..?";
		
		JLabel instruct1 = new JLabel("The password has been entered incorrectly three times.");
		instruct1.setFont(controller.getFont().deriveFont(Font.BOLD, 60));
		
		JLabel instruct2 = new JLabel("Please answer the following security question:");
		instruct2.setFont(controller.getFont().deriveFont(Font.BOLD, 60));
		
		JLabel question = new JLabel(theQ);
		question.setFont(controller.getFont().deriveFont(Font.BOLD, 60));
		
		JTextField answer = new JTextField();
		answer.setFont(controller.getFont().deriveFont(Font.BOLD, 60));
		
		JButton ok = new JButton("Ok");
		

	}
}
