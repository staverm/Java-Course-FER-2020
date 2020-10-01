package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * A JTabbedPane that implements an editor system for JNotepad++. It supports
 * operations for working with multiple documents, where each document is in
 * it's own tab.
 * 
 * @author staver
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	/**
	 * List of documents in this model
	 */
	private List<SingleDocumentModel> documents = new ArrayList<>();
	/**
	 * Document whose tab is currently active
	 */
	private SingleDocumentModel currentDocument;
	/**
	 * List of listeners that listen for changes of this model
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	/**
	 * Frame used to display messages to user
	 */
	private JFrame frame;
	/**
	 * Localization provider for localizing messages
	 */
	private ILocalizationProvider lp;
	/**
	 * Listener for updating tab icon, tab title and tab tooltip
	 */
	private SingleDocumentListener documentListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			DefaultMultipleDocumentModel.this.setIconAt(documents.indexOf(model), getIcon(model.isModified()));
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			DefaultMultipleDocumentModel.this.setTitleAt(documents.indexOf(model),
					model.getFilePath().getFileName().toString());
			DefaultMultipleDocumentModel.this.setToolTipTextAt(documents.indexOf(model),
					model.getFilePath().toString());
		}

	};

	/**
	 * DefaultMultipleDocumentModel constructor. 
	 * 
	 * @param frame frame used to display messages to user
	 * @param lp localization provider for localizing messages
	 */
	public DefaultMultipleDocumentModel(JFrame frame, ILocalizationProvider lp) {
		this.frame = frame;
		this.lp = lp;

		addChangeListener(new ChangeListener() { // gets triggered when selected tab changes
			@Override
			public void stateChanged(ChangeEvent e) {

				if (DefaultMultipleDocumentModel.this.getSelectedIndex() == -1) {
					return;
				}

				SingleDocumentModel previousDocument = currentDocument;
				// update currentDocument
				currentDocument = documents.get(DefaultMultipleDocumentModel.this.getSelectedIndex());
				fireCurrentDocumentChanged(previousDocument, currentDocument);
			}
		});
	}

	/**
	 * Returns an icon for modified or unmodified state(depending on the given
	 * boolean value).
	 * 
	 * @param modified if set to true, returns the icon for modified state, else
	 *                 returns icon for unmodified state
	 * @return icon for the given state
	 */
	private Icon getIcon(boolean modified) {
		String iconFileName = modified ? "redDisk.png" : "blueDisk.png";
		try (InputStream is = this.getClass().getResourceAsStream("icons/" + iconFileName)) {
			byte[] bytes = is.readAllBytes();
			return new ImageIcon(bytes);
		} catch (IOException e) {
			System.out.println("Error while reading icons.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns an iterator for iterating over SingleDocumentModel objects.
	 */
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}
	
	/**
	 * Creates a new document, adds it into the list of documents, opens a new tab for it
	 * and switches to it.
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(null, "");
		addDocument(newDoc);
		return newDoc;
	}

	/**
	 * Adds the given document into the list of documents, opens a new tab for it
	 * and switches to it, adds listener for updating tab icon, tab title, and tab
	 * tooltip.
	 * 
	 * @param newDoc document to be added
	 */
	private void addDocument(SingleDocumentModel newDoc) {
		documents.add(newDoc);
		currentDocument = newDoc;

		Path filePath = newDoc.getFilePath() != null ? newDoc.getFilePath() : Paths.get("(unnamed)");

		addTab(filePath.getFileName().toString(), new JScrollPane(newDoc.getTextComponent()));
		setToolTipTextAt(documents.size() - 1, filePath.toString());
		setIconAt(documents.size() - 1, getIcon(false));
		setSelectedIndex(documents.size() - 1);

		newDoc.addSingleDocumentListener(documentListener);

		fireDocumentAdded(newDoc);
	}
	
	/**
	 * Returns currently selected document.
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}
	
	/**
	 * Loads a document from the given path, adds it into the list of documents, opens a new tab for it
	 * and switches to it.
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		if (path == null) {
			throw new NullPointerException("Path to load document can't be null!");
		}

		StringBuilder sb = new StringBuilder();

		try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
			stream.forEach(s -> sb.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// if document already exists, switch to it
		for (int i = 0; i < documents.size(); i++) {
			SingleDocumentModel doc = documents.get(i);
			if (doc.getFilePath() != null && doc.getFilePath().equals(path)) {
				currentDocument = doc;
				this.setSelectedIndex(i);
				doc.getTextComponent().setText(sb.toString());
				setIconAt(i, getIcon(false));
				return doc;
			}
		}

		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(path, sb.toString());

		addDocument(newDoc);

		if (documents.size() == 2 && documents.get(0).getFilePath() == null && !documents.get(0).isModified()) {
			documents.remove(0);
			this.remove(0);
		}

		return newDoc;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) {
			newPath = model.getFilePath();
		}

		// check if opened tab already exists with the same path as newPath
		for (SingleDocumentModel doc : documents) {
			if (doc.getFilePath() != null && !doc.getFilePath().equals(model.getFilePath())
					&& doc.getFilePath().equals(newPath)) {

				JOptionPane.showMessageDialog(frame,
						String.format(lp.getString("fileAlreadyOpened"), newPath.toString()), lp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, data);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(frame,
					String.format(lp.getString("errorWhileWriting"), newPath.toFile().getAbsolutePath()),
					lp.getString("error"), JOptionPane.ERROR_MESSAGE);
			return;
		}

		model.setModified(false);
		model.setFilePath(newPath);

		JOptionPane.showMessageDialog(frame, lp.getString("fileSaved"), lp.getString("information"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		documents.remove(model);
		model.removeSingleDocumentListener(documentListener);
		remove(DefaultMultipleDocumentModel.this.getSelectedIndex());

		if (documents.size() == 0) {
			createNewDocument();
		}

		fireDocumentRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	/**
	 * Triggers <code>currentDocumentChanged()</code> method of all listeners.
	 * 
	 * @param previousModel previously current document
	 * @param currentModel  current document
	 */
	private void fireCurrentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		for (MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(previousModel, currentModel);
		}
	}

	/**
	 * Triggers <code>documentAdded()</code> method of all listeners.
	 * 
	 * @param model added document
	 */
	private void fireDocumentAdded(SingleDocumentModel model) {
		for (MultipleDocumentListener listener : listeners) {
			listener.documentAdded(model);
		}
	}

	/**
	 * Triggers <code>documentRemoved()</code> method of all listeners.
	 * 
	 * @param model removed document
	 */
	private void fireDocumentRemoved(SingleDocumentModel model) {
		for (MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(model);
		}
	}

}
