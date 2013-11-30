package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JSplitPane;

import json.JSONFailureException;
import service.ProgenyService;
import ttable.Progeny;

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

	/** The current progeny. */
	private static Progeny currentProgeny;

	/** Status of test mode. */
	private static boolean testMode;

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

		//Set the current progeny to null to be set at a later date
		currentProgeny = null;

		//Defaults the test mode to be off
		testMode = false;

	}

	/**
	 * Sets the banner pane.
	 *
	 * @param banner	the new banner
	 */
	public static void setBanner(BackgroundPanel banner) {
		splitPane.setTopComponent(banner);
	}

	/**
	 * Sets the main screen panel and whether the new screen has a close action.
	 *
	 * @param screen	the new screen panel
	 */
	public static void setScreen(BackgroundPanel screen) {
		BackgroundPanel current = (BackgroundPanel) splitPane.getBottomComponent();
		if (current != null) {
			current.close();
		}
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
	 * Refreshes the applet data for the current progeny, to ensure it is in sync with the
	 * data on the server.
	 * 
	 * @return		the refreshed progeny instance, or null on error
	 */
	public static Progeny refreshCurrentProgeny() {
		if (currentProgeny != null) {
			try {
				ArrayList<Progeny> progenyList = ProgenyService.getProgenies();
				Progeny child = null;
				Progeny current = getCurrentProgeny();
				for (int i = 0; i < progenyList.size(); i++) {
					child = progenyList.get(i);
					if (child.getId().equals(current.getId())) {
						setCurrentProgeny(child);
						break;
					}
				}
				return child;

			} catch (JSONFailureException e) {
				new GeneralDialogue(e.getMessages(), "JSON Error", 1);
			}
		}
		return null;
	}

	/**
	 * Gets the current status of test mode.
	 * 
	 * @return true if test mode is on, false if it is off.
	 */
	public static boolean getTestMode() {
		return testMode;
	}

	/**
	 * Toggles the test mode setting to the opposite. In other words, if test mode is off it turns
	 * it on and if test is on it turns it off.
	 */
	public static void toggleTestMode() {
		testMode = !testMode;
	}
}
