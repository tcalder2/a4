package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

public class EnterListener implements KeyListener {
	
	private JButton button;
	
	public EnterListener(JButton button) {
		super();
		this.button = button;
	}
	
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			button.doClick();
		}
	}

	public void keyPressed(KeyEvent e) {
		/*NULL BODY*/
	}

	public void keyReleased(KeyEvent e) {
		/*NULL BODY*/
	}
}
