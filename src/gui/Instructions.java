package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import ttable.LevelProgeny;


/**
 * The class LGame, a populated BackgroundPanel.
 * 
 * @author Yaqzan Ali
 * @version 0.1
 */
@SuppressWarnings("serial")
public class Instructions extends BackgroundPanel{
	
	private LevelProgeny level;
	private JLabel title;
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JButton play;
	/**
	 * Instantiates an Instructions instance.
	 *
	 * @param level			the level
	 */
	public Instructions(LevelProgeny level) {

		//Calls superclass constructor to create the background panel
		super(0, new GridBagLayout());
		
		this.level = level;
		GridBagConstraints c = new GridBagConstraints();
		
		// Add the title
		c.anchor= GridBagConstraints.CENTER;
		c.gridy=0;
		title = new JLabel("Defuse The Bomb!");
		Font font = new Font("Stencil", Font.BOLD, 60);
		title.setFont(font);
		title.setForeground(Color.red);
		add(title,c);
		
		// Add Lines of instructions
		c.gridy=1;
		l1 = new JLabel("- Answer questions to defuse the bomb, before it explodes.");
		l2 = new JLabel("- Getting a question right will freeze the bomb temporarily.");
		l3 = new JLabel("- While the bomb is frozen, getting more questions right increases the multiplier.");
		l4 = new JLabel("- Multipliers makes bomb defusal faster");
		l1.setFont(Controller.getFont().deriveFont(Font.BOLD, 20));
		l2.setFont(Controller.getFont().deriveFont(Font.BOLD, 20));
		l3.setFont(Controller.getFont().deriveFont(Font.BOLD, 20));
		l4.setFont(Controller.getFont().deriveFont(Font.BOLD, 20));
		add(l1, c);
		c.gridy=2;
		add(l2, c);
		c.gridy=3;
		add(l3, c);
		c.gridy=4;
		add(l4, c);
		
		// Add button
		c.gridy=5;
		play = new JButton("Play!");
		play.addActionListener(new Play());
		add(play, c);
	}

	
	class Play implements ActionListener{
		
	
		public void actionPerformed(ActionEvent arg0) {
			LGame screen;
			try {
			screen = new LGame(level);
			}
			catch (Exception e2) {
				LevelProgeny prog = new LevelProgeny();
				prog.setLevelNumber(2);
				screen = new LGame(prog);
			}
			Controller.setScreen(screen);
			
		}
	}
}
