package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface that models a single document.
 * 
 * @author staver
 *
 */
public interface SingleDocumentModel {
	
	/**
	 * Returns text component used for editing this document.
	 * 
	 * @return text component used for editing this document
	 */
	JTextArea getTextComponent();
	
	/**
	 * Returns file path of this document.
	 * 
	 * @return file path of this document
	 */
	Path getFilePath();
	
	/**
	 * Sets file path of this document.
	 * 
	 * @param path new path to be set
	 */
	void setFilePath(Path path);
	
	/**
	 * Returns if this document has been modified(without saving the changes).
	 * 
	 * @return true if this document has been modified, false if unmodified
	 */
	boolean isModified();
	
	/**
	 * Sets the modified status of this document to the given value
	 * 
	 * @param modified value to be set
	 */
	void setModified(boolean modified);
	
	/**
	 * Registers the given SingleDocumentListener to this model.
	 * 
	 * @param l listener to be registered
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * Unregisters the given SingleDocumentListener from this model.
	 * 
	 * @param l listener to be unregistered
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
