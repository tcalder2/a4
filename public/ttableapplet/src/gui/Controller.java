package gui;

import java.awt.Color;
import javax.swing.*;

public class Controller {
	
	private JSplitPane splitPane;
	private int current_progeny;
	//private Progeny currentProgeny;
	
	public Controller() {
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerSize(0);
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
	/*
	public Progeny getCurrentProgeny() {
		return currentProgeny;
	}
	*/
}
