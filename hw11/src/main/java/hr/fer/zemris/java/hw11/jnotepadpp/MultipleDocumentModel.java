package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface that models a system that maintains multiple documents, where there
 * can be one current document at some moment in time.
 * 
 * @author staver
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new document and adds it to this model.
	 * 
	 * @return created document
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns current document.
	 * 
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a document from the given path and adds it to this model.
	 * 
	 * @param path path used to load a document
	 * @return the loaded document
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * Saves the given document to the specified path.
	 * 
	 * @param model document to be saved
	 * @param newPath path used to save the given document
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Closes(removes) the given document from this model.
	 * 
	 * @param model document to be closed
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Registers the given MultipleDocumentListener to this model.
	 * 
	 * @param l listener to be registered
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Unregisters the given MultipleDocumentListener from this model.
	 * 
	 * @param l listener to be unregistered
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns the number of documents in this model.
	 * 
	 * @return number of documents in this model
	 */
	int getNumberOfDocuments();
	
	/**
	 * Returns document at given index.
	 * 
	 * @param index index of the document to be returned
	 * @return document at given index
	 */
	SingleDocumentModel getDocument(int index);
}
