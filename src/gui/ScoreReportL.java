package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import ttable.LevelProgeny;
/**
 * The class ScoreREport, a populated BackgroundPanel.
 * 
 * @author Yaqzan Ali
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ScoreReportL extends BackgroundPanel {

	private JLabel lblResult;
	private JButton butTryAgain;
	private JButton butContinue;
	private JLabel lblPicture;
	
	private int level;
	
	public ScoreReportL(boolean pass, int level){
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.level = level;
		//Result
		Font font = (Controller.getFont().deriveFont(Font.BOLD, 40));
		lblResult = new JLabel();
		lblResult.setFont(font);
		c.gridx=0;
		c.gridy=0;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(lblResult, c);
		
		//Try Again button
		butTryAgain = new JButton("Try Again");
		butTryAgain.addActionListener(new TryAgain());
		c.gridy=2;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth=1;
		add(butTryAgain, c);
		
		//Continue Button
		butContinue = new JButton("Continue");
		c.gridx = 2;
		butTryAgain.addActionListener(new Continue());
		add(butContinue, c);
		
		if (pass){
			lblResult.setText("You safely defused the Bomb!");
			butTryAgain.setVisible(false);
		}else{
			lblResult.setText("You couldn't defuse the bomb in time...");
			butTryAgain.setVisible(true);
		}
	}
	class Continue implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
		}
		
	}
	
	class TryAgain implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			LGame screen;
			try {
			screen = new LGame(Controller.getCurrentProgeny().getLevels().get(level - 1));
			}
			catch (Exception e2) {
				LevelProgeny prog = new LevelProgeny();
				prog.setLevelNumber(level);
				screen = new LGame(prog);
			}
			Controller.setScreen(screen);
		}


	}

}
