package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.*;


/**
 * The Class Controller.
 */
public class Controller {
	
	/** The split pane. */
	private JSplitPane splitPane;
	
	/** The font. */
	private Font font;
	
	/** The current_progeny. */
	private int current_progeny;
	//private Progeny currentProgeny;
	
	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerSize(0);
		try {
			URL url = new URL("http://jbaron6.cs2212.ca/fonts/GiddyupStd.otf");
			URLConnection urlcon = url.openConnection();
			urlcon.setDoInput(true);
			urlcon.setUseCaches(false);
			font = Font.createFont(Font.TRUETYPE_FONT, urlcon.getInputStream());
		} catch (FontFormatException | IOException e) {
			font = new Font("Serif", Font.BOLD, 18);
		}
	}
	
	/**
	 * Sets the header.
	 *
	 * @param header the new header
	 */
	public void setHeader(JComponent header) {
		splitPane.setTopComponent(header);
	}
	
	/**
	 * Sets the screen.
	 *
	 * @param screen the new screen
	 */
	public void setScreen(JComponent screen) {
		splitPane.setBottomComponent(screen);
	}
	
	/**
	 * Gets the pane.
	 *
	 * @return the pane
	 */
	public JSplitPane getPane() {
		return splitPane;
	}
	
	/**
	 * Sets the frame colour.
	 *
	 * @param colour the new frame colour
	 */
	public void setFrameColour(Color colour) {
		splitPane.setBackground(colour);
	}
	
	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}
	
	/*
	public Progeny getCurrentProgeny() {
		return currentProgeny;
	}
	*/
}
