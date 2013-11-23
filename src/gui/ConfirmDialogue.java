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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Dialogue of warning user before carrying out risky or irreversible actions, that is capable of
 * getting input to allow the action to proceed or not.  Program execution will pause until this
 * kind of dialogue is closed.
 * 
 * @author James Anderson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ConfirmDialogue extends JDialog {

	/** Whether or not the user has confirmed what is displayed. */
	private boolean confirmed;

	/**
	 * Constructor
	 * 
	 * @param messages
	 * @param title
	 * @param type
	 */
	public ConfirmDialogue(ArrayList<String> messages, String title, int type) {

		//Initialise the confirmed boolean to its default
		confirmed = false;
		
		//Create label with the specified type of image
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
		JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(90, 90, Image.SCALE_DEFAULT)));
		
		//Create the panel containing the message
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		for (int i = 0; i < messages.size(); i++) {
			JLabel name = new JLabel(messages.get(i));
			name.setFont(new Font("Serif", Font.BOLD, 13));
			panel.add(name);
		}
		
		//Create the buttons
		JButton cancel = new JButton("Cancel");
		JButton ok = new JButton("OK");

		//Add action listeners to the buttons
		cancel.addActionListener(new DialogueCancel(this));
		ok.addActionListener(new DialogueOk(this));
		
		//Add components to the display
        setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = messages.size();
		c.insets = new Insets(20,20,0,0);
        c.anchor = GridBagConstraints.WEST;
        add(label, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 3;
        c.gridx = 1;
        add(panel, c);						//The panel containing the message

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(Box.createGlue(), c);			//A blank space to ensure correct layout
        
        c.insets = new Insets(5,0,15,0);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.SOUTH;
        c.weightx = 0;
        c.weighty = 0;
        c.gridy = 1;
        c.gridx = 2;
        add(cancel, c);						//The cancel button
        
        c.insets = new Insets(5,0,15,20);   
        c.gridx = 3;
		add(ok, c);							//The ok button

		//Set focus to the ok button
		ok.requestFocus();
		
		//Set the window attributes
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(380,170);
		setModalityType(JDialog.DEFAULT_MODALITY_TYPE);	//Prevents program from continuing to execute in the background
		setVisible(true);				//Will cause the dialogue to be displayed
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param title
	 * @param type
	 */
	public ConfirmDialogue(String message, String title, int type) {
		this(new ArrayList<String>(Collections.singletonList(message)), title, type);
	}

	/**
	 * Gets whether the user confirmed the action requested in the dialogue.
	 * 
	 * @return		whether or not the user confirmed.
	 */
	public boolean getConfirmation() {
		return confirmed;
	}

	/**
	 * Sets whether the user confirmed the action requested int eh dialogue.
	 * 
	 * @param confirmed		the new state for whether or not the user confirmed.
	 */
	public void setConfirmation(boolean confirmed) {
		this.confirmed = confirmed;
	}
}

/**
 * The class DialogueClose, an action listener that closes the dialogue when the button
 * is clicked.
 * 
 * @author James Anderson
 * @version 1.0
 */
class DialogueCancel implements ActionListener {

	/** The dialogue that is to be closed on action. */
	private ConfirmDialogue dialogue;

	/**
	 * Constructor.
	 * 
	 * @param dialogue		the dialogue box to be closed on action.
	 */
	public DialogueCancel(ConfirmDialogue dialogue) {
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

/**
 * The class DialogueClose, an action listener that closes the dialogue when the button
 * is clicked.
 * 
 * @author James Anderson
 * @version 1.0
 */
class DialogueOk implements ActionListener {

	/** The dialogue that is to be closed on action. */
	private ConfirmDialogue dialogue;

	/**
	 * Constructor.
	 * 
	 * @param dialogue		the dialogue box to be closed on action.
	 */
	public DialogueOk(ConfirmDialogue dialogue) {
		super();
		this.dialogue = dialogue;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		dialogue.setConfirmation(true);
		dialogue.dispose();
	}
}
