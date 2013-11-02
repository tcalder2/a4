package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.*;

public class Controller {
	
	private JSplitPane splitPane;
	private Font font;
	private int current_progeny;
	//private Progeny currentProgeny;
	
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
	
	public void setHeader(JComponent header) {
		splitPane.setTopComponent(header);
	}
	
	public void setScreen(JComponent screen) {
		splitPane.setBottomComponent(screen);
	}
	
	public JSplitPane getPane() {
		return splitPane;
	}
	
	public void setFrameColour(Color colour) {
		splitPane.setBackground(colour);
	}
	
	public Font getFont() {
		return font;
	}
	
	/*
	public Progeny getCurrentProgeny() {
		return currentProgeny;
	}
	*/
}
