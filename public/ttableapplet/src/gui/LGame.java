package gui;

import java.awt.GridBagLayout;

public class LGame extends BackgroundPanel {
	
	private int level;
	
	public LGame(Controller controller, int level) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		this.level = level;
	}
}
