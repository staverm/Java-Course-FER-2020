package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that models a listener for MultipleDocumentModel.
 * 
 * @author staver
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * Gets triggered when current document changes.
	 * 
	 * @param previousModel previous document
	 * @param currentModel current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Gets triggered when a document gets added to the model.
	 * @param model added document
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Gets triggered when a document gets removed from the model.
	 * @param model removed document
	 */
	void documentRemoved(SingleDocumentModel model);
}
