package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.text.JTextComponent;

/**
 * Class that represents selected text from some text component.
 * 
 * @author staver
 *
 */
public class SelectedText {
	
	/**
	 * Selected text offset
	 */
	private int offset; 
	/**
	 * Selected text length
	 */
	private int length;
	
	/**
	 * SelectedText constructor - creates and initializes a new SelectedText object.
	 * 
	 * @param offset selected text offset
	 * @param length selected text length
	 */
	public SelectedText(int offset, int length) {
		this.offset = offset;
		this.length = length;
	}
	
	/**
	 * Returns selected text offset.
	 * 
	 * @return selected text offset.
	 */
	public int getOffset() {
		return offset;
	}
	
	/**
	 * Sets selected text offset to given argument.
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Returns selected text length.
	 * 
	 * @return selected text length.
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Sets selected text length to given argument.
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Returns a new SelectedText object using selected text from the given text component.
	 * 
	 * @param c text component used to create SelectedText object
	 * @return a new SelectedText object using selected text from the given text component
	 */
	public static SelectedText getSelectedText(JTextComponent c) {
		int len = Math.abs(c.getCaret().getDot() - c.getCaret().getMark());
		int offset = Math.min(c.getCaret().getDot(), c.getCaret().getMark());
		
		return new SelectedText(offset, len);
	}

}
