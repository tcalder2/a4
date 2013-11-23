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
 * Dialogue for display of general messages such as success/error display.
 * 
 * @author James Anderson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GeneralDialogue extends JDialog {

	public GeneralDialogue(ArrayList<String> messages, String title, int type) {
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = messages.size();
		c.insets = new Insets(20,20,0,15);
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
        c.anchor = GridBagConstraints.WEST;
        add(label, c);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        for (int i = 0; i < messages.size(); i++) {
        	JLabel name = new JLabel(messages.get(i));
            name.setFont(new Font("Serif", Font.BOLD, 13));
            panel.add(name);
        }

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        add(panel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(Box.createGlue(), c);
        
        JButton close = new JButton("Close");
        close.addActionListener(new DialogueClose(this));
        c.insets = new Insets(0,0,15,20);   
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.SOUTH;
        c.weightx = 0;
        c.weighty = 0;
        c.gridy = 1;
        c.gridx = 2;
		add(close, c);
		
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(380,170);
		setVisible(true);
	}
	
	public GeneralDialogue(String message, String title, int type) {
		this(new ArrayList<String>(Collections.singletonList(message)), title, type);
	}
}

class DialogueClose implements ActionListener {
	
	private GeneralDialogue dialogue;
	
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
