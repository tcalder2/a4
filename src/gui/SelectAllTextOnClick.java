package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

/**
 * This mouse listener will cause any and all text in the textField to become selected
 * upon being clicked by the mouse.
 * 
 * @author James Anderson
 * @version 1.0
 */
public class SelectAllTextOnClick implements MouseListener {
	
	/**
	 * The text field that the mouse listener will act upon.
	 */
	private JTextField textField;
	
	/**
	 * Constructor that takes the text field the mouse listener will act upon as an argument.
	 * 
	 * @param textField		the text field for the mouse listener to act upon.
	 */
	public SelectAllTextOnClick(JTextField textField) {
		this.textField = textField;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e){
            textField.selectAll();
        }

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// NULL BODY
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// NULL BODY
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// NULL BODY
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// NULL BODY
	}
}
