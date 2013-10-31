package gui;

import java.awt.GridBagLayout;

public class Drill extends BackgroundPanel {
	
	private int level;
	
	public Drill(Controller controller, int level) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		this.level = level;
	}
}
