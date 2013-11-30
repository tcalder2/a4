package gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class JTextRenderer extends JTextArea implements TableCellRenderer {

  public JTextRenderer() {
	  
	setWrapStyleWord(true);
	  
    setLineWrap(true);
    
  }

  public Component getTableCellRendererComponent(JTable table, Object str, boolean isSelected, boolean hasFocus, int row, int column) {
    setText((String)str);
    
    int tableHeight;
    
    tableHeight = (int) this.getPreferredSize().getHeight();
    
    if (table.getRowHeight(row) < tableHeight) {
        table.setRowHeight(row, tableHeight);
    }
    
    return this;
  }
}
