package gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
/**
 * Renders a JTextArea within a JTable, allowing you to display multiple lines in one cell
 * @author Taylor Calder
 */
public class JTextRenderer extends JTextArea implements TableCellRenderer {

	// Set rendering style
	public JTextRenderer() {

		setWrapStyleWord(true);
		setLineWrap(true);

	}

	/**
	 * Renders component
	 * 
	 * @param table
	 *            the table
	 * @param str
	 *            the string
	 * @param isSelected
	 *            whether the cell is selected
	 * @param hasFocus
	 *            whether the cell has focus
	 * @param row
	 *            the row number of the cell
	 * @param col
	 *            the column number of the cell
	 * @return the new table component for the cell
	 */
	public Component getTableCellRendererComponent(JTable table, Object str,
			boolean isSelected, boolean hasFocus, int row, int column) {

		// Set the text for the cell
		setText((String) str);

		// height of the table
		int tableHeight;

		tableHeight = (int) this.getPreferredSize().getHeight();

		// Adjust the height of the row if it is not enough to accomodate all
		// the text
		if (table.getRowHeight(row) < tableHeight) {
			table.setRowHeight(row, tableHeight);
		}

		return this;
	}
}
