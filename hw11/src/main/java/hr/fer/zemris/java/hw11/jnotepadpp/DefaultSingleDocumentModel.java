package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class that models a single document.
 * 
 * @author staver
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{
	
	/**
	 * Text area used to edit this document
	 */
	private JTextArea textArea; 
	/**
	 * File path of this document
	 */
	private Path filePath;
	/**
	 * List of listeners
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<>();
	/**
	 * Modified status
	 */
	private boolean modified = false;
	
	/**
	 * DefaultSingleDocumentModel constructor.
	 * 
	 * @param filePath document's file path
	 * @param content document's content
	 */
	public DefaultSingleDocumentModel(Path filePath, String content) {
		textArea = new JTextArea(content);
		this.filePath = filePath;
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
			
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}
	
	@Override
	public Path getFilePath() {
		return filePath;
	}
	
	@Override
	public void setFilePath(Path path) {
		if(path == null) {
			throw new NullPointerException("Path can not be null!");
		}
		filePath = path;
		fireFilePathUpdated();
	}
	
	@Override
	public boolean isModified() {
		return modified;
	}
	
	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		fireModifyStatusUpdated(); 
	}
	
	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}
	
	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
	/**
	 * Triggers <code>documentModifyStatusUpdated()</code> method of all listeners.
	 */
	private void fireModifyStatusUpdated() {
		for(SingleDocumentListener listener : listeners) {
			listener.documentModifyStatusUpdated(this);
		}
	}
	
	/**
	 * Triggers <code>documentFilePathUpdated()</code> method of all listeners.
	 */
	private void fireFilePathUpdated() {
		for(SingleDocumentListener listener : listeners) {
			listener.documentFilePathUpdated(this);
		}
	}

}
