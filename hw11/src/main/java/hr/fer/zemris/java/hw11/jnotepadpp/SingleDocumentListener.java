package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface that models a listener for SingleDocumentModel.
 * 
 * @author staver
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Gets triggered when document's modified status changes.
	 * 
	 * @param model document whose modified status changed
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Gets triggered when document's file path changes.
	 * 
	 * @param model document whose file path changed
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
