/**
 * 
 */
package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * @author James Anderson
 *
 */
public class LevelSettingsTab extends JPanel {
	
	public LevelSettingsTab(Controller controller) {
		super(new GridBagLayout());
		setOpaque(false);
		
		Vector<String> t = new Vector<String>();
		for (int i = 5; i <= 120; i += 5) {
			t.add(i + " sec");
		}
		
		Vector<String> err = new Vector<String>();
		for (int i = 0; i < 10; i++) {
			err.add("" + i);
		}
		
		JLabel drillLabel = new JLabel("Drill Settings:");
		JLabel timeLabel = new JLabel("Time per question:");
		JComboBox<String> time = new JComboBox<String>(t);
		JLabel errorsLabel = new JLabel("Number of errors per level: ");
		JComboBox<String> errors = new JComboBox<String>(err);
		JLabel testingLabel = new JLabel("Testing mode:");
		JRadioButton testoff = new JRadioButton("Off");
		JRadioButton teston = new JRadioButton("On");
		JButton apply = new JButton("Apply");

		
		ButtonGroup teststate = new ButtonGroup();
		teststate.add(testoff);
		teststate.add(teston);

		
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,25,0,0);
		c.gridwidth = 2;
		c.gridy = 1;
		c.gridx = 0;
		add(drillLabel, c);

		c.gridwidth = 1;
		c.gridy = 2;
		add(timeLabel, c);

		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		add(time, c);

		c.gridwidth = 2;
		c.gridx = 2;
		add(errorsLabel, c);

		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,75);
		c.gridx = 4;
		add(errors, c);

		c.insets = new Insets(0,75,0,0);
		c.gridx = 0;
		c.gridy = 3;
		add(testingLabel, c);

		c.gridwidth = 1;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 1;
		add(testoff, c);

		c.gridx = 2;
		add(teston, c);

		c.gridy = 4;
		c.gridx = 4;
		add(apply, c);
	}

}
