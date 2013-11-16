package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

/**
 * The class EnterListener, a key listener.
 * 
 * @author James Anderson
 * @version 1.0
 */
public class EnterListener implements KeyListener {
	
	/** The button to act on. */
	private JButton button;
	
	/**
	 * Instantiates an EnterListener instance.
	 *
	 * @param button	the button
	 */
	public EnterListener(JButton button) {
		super();
		this.button = button;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			button.doClick();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		/*NULL BODY*/
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		/*NULL BODY*/
	}
}
