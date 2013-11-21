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
import ttable.User;
/**
 * The class Controller.
 * 
 * @author James Anderson
 * @version 1.3
 */
public class Controller {
	
	/** The split pane. */
	private static JSplitPane splitPane;
	
	/** The font. */
	private static Font font;
	
	/** The current user. */
	private static User user;
	
	/** The current progeny. */
	private static Progeny currentProgeny;
	
	/** Status of test mode. */
	private static boolean testMode  = true;

	
	/**
	 * Initialises the Controller variables.
	 * 
	 * @throws JSONFailureException		the JSON failure exception
	 */
	public static void initialise() throws JSONFailureException {
		
		//Create the split pane
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerSize(0);
		
		//Download and set the font, if download fails set to a standard backup font
		try {
			URL url = new URL("http://jbaron6.cs2212.ca/fonts/teenage-angst.regular.ttf");
			URLConnection urlcon = url.openConnection();
			urlcon.setDoInput(true);
			urlcon.setUseCaches(false);
			font = Font.createFont(Font.TRUETYPE_FONT, urlcon.getInputStream());
		} catch (FontFormatException | IOException e) {
			font = new Font("Serif", Font.BOLD, 18);
		}
		
		//Get the current user
		user = User.getInstance();
		
		//Set the current progeny to null to be set at a later date
		currentProgeny = null;
	}
	
	/**
	 * Sets the banner pane.
	 *
	 * @param banner	the new banner
	 */
	public static void setBanner(JComponent banner) {
		splitPane.setTopComponent(banner);
	}
	
	/**
	 * Sets the main screen panel.
	 *
	 * @param screen	the new screen panel
	 */
	public static void setScreen(JComponent screen) {
		splitPane.setBottomComponent(screen);
	}
	
	/**
	 * Gets the split pane.
	 *
	 * @return	the split pane
	 */
	public static JSplitPane getPane() {
		return splitPane;
	}
	
	/**
	 * Sets the frame colour.
	 *
	 * @param colour	the new frame colour
	 */
	public static void setFrameColour(Color colour) {
		splitPane.setBackground(colour);
	}
	
	/**
	 * Gets the font.
	 *
	 * @return	the font
	 */
	public static Font getFont() {
		return font;
	}
	
	/**
	 * Gets the current user.
	 *
	 * @return	the current user
	 */
	public static User getUser() {
		return user;
	}
	
	/**
	 * Sets the current progeny.
	 *
	 * @param newCurrentProgeny		the progeny to be set as active
	 */
	public static void setCurrentProgeny(Progeny newCurrentProgeny) {
		currentProgeny = newCurrentProgeny;
	}
	
	/**
	 * Gets the current progeny.
	 *
	 * @return 	the current progeny
	 */
	public static Progeny getCurrentProgeny() {
		return currentProgeny;
	}
	
	/**
	 * Gets whether the test mode is activated or not.
	 * 
	 * @return true if test mode is activated, false otherwise.
	 */
	public static boolean getTestMode() {
		return testMode;
	}
	
	/**
	 * Set whether test mode is on or not.
	 * 
	 * @param testMode				the test mode boolean containing whether test mode is activated
	 */
	public static void setTestMode(boolean testSetting) {
		testMode = testSetting;
	}
}
