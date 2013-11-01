package gui;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ChildProgress extends BackgroundPanel {
	
	//private Progeny child;
	
	public ChildProgress(/*Progeny child*/) {
		super("http://jbaron6.cs2212.ca/img/default_background.png", new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.CENTER;
		c.insets = new Insets(10,150,5,150);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		
		JLabel title = new JLabel(/*child.getName() + */"'s Progress");
		add(title, c);
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Level");
		columnNames.add("Attempts");
		columnNames.add("Final Time (sec)");
		columnNames.add("Final Mistakes");
		
		Vector<Vector<String>> progress = new Vector<Vector<String>>();
		for (int i = 0; i <10; i++) {
		Vector<String> v = new Vector<String>();
		v.add("1");
		v.add("2");
		v.add("23");
		v.add("0");
		progress.add(v);
		Vector<String> v1 = new Vector<String>();
		v1.add("2");
		v1.add("5");
		v1.add("26");
		v1.add("2");
		progress.add(v1);
		}
		
		DefaultTableModel tableModel = new DefaultTableModel(progress, columnNames);
		tableModel.setNumRows(12);
		JTable table = new JTable(tableModel) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		renderer.setOpaque(false);
		TableColumn col = null;
		for (int i = 0; i < table.getColumnCount(); i++) {
			col = table.getColumnModel().getColumn(i);
			col.setCellRenderer(renderer);
			col.setWidth(100);
		}
		table.setOpaque(false);
		table.setRowHeight(24);
		table.setShowGrid(false);
		table.getTableHeader().setDefaultRenderer(renderer);
		
		try {
			URL url = new URL("http://jbaron6.cs2212.ca/fonts/GiddyupStd.otf");
			URLConnection urlcon = url.openConnection();
			urlcon.setDoInput(true);
			urlcon.setUseCaches(false);
			Font font = Font.createFont(Font.TRUETYPE_FONT, urlcon.getInputStream());
			table.setFont(font.deriveFont(Font.BOLD, 18));
			table.getTableHeader().setFont(font.deriveFont(Font.BOLD, 18));
		} catch (FontFormatException | IOException e) {
			table.setFont(new Font("Serif", Font.BOLD, 18));
		}
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		c.insets = new Insets(0,75,0,75);
		c.gridwidth = 1;
		c.gridy = 2;
		c.ipady = 100;
		add(scroll,c);
		
	}
}
