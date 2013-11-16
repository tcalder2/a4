package gui;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * The class DocumentLengthFilter, a document filter to limit number of characters entered.
 * 
 * @author James Anderson
 * @version 1.0
 */
class DocumentLengthFilter extends DocumentFilter {
	private int numCharAllowed;
 
    public DocumentLengthFilter(int numCharAllowed) {
        this.numCharAllowed = numCharAllowed;
    }
 
    public void insertString(FilterBypass fb, int offset, String str, AttributeSet a)
    		throws BadLocationException {
       
    	//If the addition of the string will keep within the range then add the characters
        if ((fb.getDocument().getLength() + str.length()) <= numCharAllowed) {
            super.insertString(fb, offset, str, a);
        }
        
        //Else beep
        else
            Toolkit.getDefaultToolkit().beep();
    }
     
    public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet a)
        throws BadLocationException {
       
    	//If the addition of the string will keep within the range then add the characters
        if ((fb.getDocument().getLength() + str.length() - length) <= numCharAllowed)
            super.replace(fb, offset, length, str, a);
        
        //Else beep
        else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

}
