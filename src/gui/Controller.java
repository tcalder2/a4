package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import json.JSONFailureException;
import ttable.Progeny;
import ttable.Services;
import ttable.User;

/**
 * The class Controller.
 * 
 * @author James Anderson
 * @version 1.3
 */
public class Controller {
	
	/** The split pane. */
	private JSplitPane splitPane;
	
	/** The font. */
	private Font font;
	
	/** The current user. */
	private User user;
	
	/** The current progeny. */
	private Progeny currentProgeny;

	
	/**
	 * Instantiates a Controller instance.
	 * 
	 * @throws JSONFailureException		the JSON failure exception
	 */
	public Controller() throws JSONFailureException {
		
		//Create the split pane
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerSize(0);
		
		//Download and set the font, if download fails set to a standard backup font
		try {
			URL url = new URL("http://jbaron6.cs2212.ca/fonts/GiddyupStd.otf");
			URLConnection urlcon = url.openConnection();
			urlcon.setDoInput(true);
			urlcon.setUseCaches(false);
			font = Font.createFont(Font.TRUETYPE_FONT, urlcon.getInputStream());
		} catch (FontFormatException | IOException e) {
			font = new Font("Serif", Font.BOLD, 18);
		}
		
		//Get the current user
		user = Services.getUser();
		
		//Set the current progeny to null to be set at a later date
		currentProgeny = null;
	}
	
	/**
	 * Sets the banner pane.
	 *
	 * @param banner	the new banner
	 */
	public void setBanner(JComponent banner) {
		splitPane.setTopComponent(banner);
	}
	
	/**
	 * Sets the main screen panel.
	 *
	 * @param screen	the new screen panel
	 */
	public void setScreen(JComponent screen) {
		splitPane.setBottomComponent(screen);
	}
	
	/**
	 * Gets the split pane.
	 *
	 * @return	the split pane
	 */
	public JSplitPane getPane() {
		return splitPane;
	}
	
	/**
	 * Sets the frame colour.
	 *
	 * @param colour	the new frame colour
	 */
	public void setFrameColour(Color colour) {
		splitPane.setBackground(colour);
	}
	
	/**
	 * Gets the font.
	 *
	 * @return	the font
	 */
	public Font getFont() {
		return font;
	}
	
	/**
	 * Gets the current user.
	 *
	 * @return	the current user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the current progeny.
	 *
	 * @param newCurrentProgeny		the progeny to be set as active
	 */
	public void setCurrentProgeny(Progeny newCurrentProgeny) {
		currentProgeny = newCurrentProgeny;
	}
	
	/**
	 * Gets the current progeny.
	 *
	 * @return 	the current progeny
	 */
	public Progeny getCurrentProgeny() {
		return currentProgeny;
	}
}
