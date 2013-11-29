package gui;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * Dialogue for display of general messages such as success/error display. Program execution
 * will continue in the background with this kind of dialogue.
 * 
 * @author James Anderson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GeneralDialogue extends JDialog {

	/**
	 * Constructor for multi-line messages.
	 * 
	 * @param messages		the lines of message to display.
	 * @param title			the title to display.
	 * @param type			the type of dialogue: 1 for error, 2 for warning, 3 for success.
	 */
	public GeneralDialogue(ArrayList<String> messages, String title, int type) {
		
		//Create the label with the specified image type
       	Image img = null;
		try {
			switch(type) {
				case 1:
					img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/error.png"));
					break;
				case 2:
					img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/warning.png"));
					break;
				case 3:
					img = ImageIO.read(new URL("http://jbaron6.cs2212.ca/img/success.png"));
					break;
			}
		} catch (IOException e) {
			//NULL BODY
		}
        JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(90, 90, Image.SCALE_SMOOTH)));
        
		//Create the scrollable text area containing the message
		JTextArea text = new JTextArea();
		text.setFont(new Font("Serif", Font.BOLD, 13));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setOpaque(false);
		text.setEditable(false);
		for (int i = 0; i < messages.size(); i++) {
			text.append(messages.get(i) + "\n");
		}
		JScrollPane scroll = new JScrollPane(text);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		
        //Create the close button and set its action listener
        JButton close = new JButton("Close");
        close.addActionListener(new DialogueClose(this));
        
        //Add components to display
        setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = messages.size();
		c.insets = new Insets(20,20,0,0);
        c.anchor = GridBagConstraints.WEST;
        add(label, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.gridx = 1;
        add(scroll, c);						//The panel containing the message

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(Box.createGlue(), c);			//A blank space to ensure correct layout
        
        c.insets = new Insets(5,0,15,20);   
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.SOUTH;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.gridy = 1;
        c.gridx = 2;
		add(close, c);						//The close button
		
		//Sets the focus to the close button
        close.requestFocus();

		//Sets the window attributes
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(380,170);
		setVisible(true);		//Causes the dialogue to be displayed
	}
	
	/**
	 * Constructor for single line messages.
	 * 
	 * @param message		the single line message to display.
	 * @param title			the title to display.
	 * @param type			the type of dialogue: 1 for error, 2 for warning, 3 for success.
	 */
	public GeneralDialogue(String message, String title, int type) {
		this(new ArrayList<String>(Collections.singletonList(message)), title, type);
	}
}

/**
 * The class DialogueClose, an action listener that closes the dialogue when the button
 * is clicked.
 * 
 * @author James Anderson
 * @version 1.0
 */
class DialogueClose implements ActionListener {
	
	/** The dialogue that is to be closed on action. */
	private GeneralDialogue dialogue;
	
	/**
	 * Constructor.
	 * 
	 * @param dialogue		the dialogue box to be closed on action.
	 */
	public DialogueClose(GeneralDialogue dialogue) {
		super();
		this.dialogue = dialogue;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		dialogue.dispose();
	}
}
