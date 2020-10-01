package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Class that holds all JNotepad++ action implementations. Each action has a
 * getter method that creates a new instance of that action and returns it.
 * 
 * @author staver
 *
 */
public class ActionsGetter {

	/**
	 * JNotepad++'s tabbed pane
	 */
	private MultipleDocumentModel tabbedPane;
	/**
	 * Frame used to display messages to the user
	 */
	private JFrame frame;
	/**
	 * Localization provider used to localize messages
	 */
	private ILocalizationProvider lp;
	/**
	 * Clipboard for cut/copy/paste
	 */
	private String clipboard;

	/**
	 * ActionsGetter constructor.
	 * 
	 * @param tabbedPane JNotepad++'s tabbed pane
	 * @param frame      frame used to display messages to the user
	 * @param lp         localization provider used to localize messages
	 */
	public ActionsGetter(MultipleDocumentModel tabbedPane, JFrame frame, ILocalizationProvider lp) {
		this.tabbedPane = tabbedPane;
		this.frame = frame;
		this.lp = lp;
	}

	/**
	 * Returns an action for opening documents. The action opens a file chooser and
	 * opens the chosen file in JNotepad++'s tabbed pane.
	 * 
	 * @return an action for opening documents
	 */
	public Action getOpenDocumentAction() {
		return new LocalizableAction("openDocumentAction", KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle(lp.getString("openFile"));
				if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();
				if (!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(frame,
							String.format(lp.getString("fileDoesntExist"), fileName.getAbsolutePath()),
							lp.getString("error"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				tabbedPane.loadDocument(filePath);
			}

		};
	}

	/**
	 * Returns an action for saving current document to it's current location.
	 * 
	 * @return an action for saving current document to it's current location
	 */
	public Action getSaveDocumentAction() {
		return new LocalizableAction("saveDocumentAction", KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Path savePath = tabbedPane.getCurrentDocument().getFilePath();
				if (savePath == null) { // unnamed
					ActionsGetter.this.getSaveDocumentAsAction().actionPerformed(e);
				} else {
					tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), null);
				}
			}
		};
	}

	/**
	 * Returns an action for saving current document to a specified location.
	 * 
	 * @return an action for saving current document to a specified location
	 */
	public Action getSaveDocumentAsAction() {
		return new LocalizableAction("saveDocumentAsAction", KeyStroke.getKeyStroke("alt A"), KeyEvent.VK_A, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle(lp.getString("saveDocument"));
				if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(frame, lp.getString("notSaved"), lp.getString("warning"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Path savePath = jfc.getSelectedFile().toPath();

				// if file at chosen path already exists
				if (Files.exists(savePath) && !savePath.equals(tabbedPane.getCurrentDocument().getFilePath())) {
					String[] options = { lp.getString("cancel"), lp.getString("replace") };

					int answer = JOptionPane.showOptionDialog(frame,
							String.format(lp.getString("replaceFileQuestion"), savePath.getFileName(),
									savePath.getParent()),
							lp.getString("question"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							options, options[1]);

					if (answer == 0) {
						JOptionPane.showMessageDialog(frame, lp.getString("noFilesOverwritten"),
								lp.getString("information"), JOptionPane.INFORMATION_MESSAGE);
						return;
					}

				}

				tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), savePath);
			}

		};
	}

	/**
	 * Returns an action for closing current document.
	 * 
	 * @return an action for closing current document
	 */
	public Action getCloseDocumentAction() {
		return new LocalizableAction("closeDocumentAction", KeyStroke.getKeyStroke("control W"), KeyEvent.VK_W, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.closeDocument(tabbedPane.getCurrentDocument());
			}

		};
	}

	/**
	 * Returns an action for creating new document.
	 * 
	 * @return an action for creating new document
	 */
	public Action getNewDocumentAction() {
		return new LocalizableAction("newDocumentAction", KeyStroke.getKeyStroke("control N"), KeyEvent.VK_N, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.createNewDocument();
			}

		};
	}

	/**
	 * Returns an action for deleting selected text and copying it to the clipboard.
	 * 
	 * @return an action for deleting selected text and copying it to the clipboard
	 */
	public Action getCutAction() {
		return new LocalizableAction("cutAction", KeyStroke.getKeyStroke("control X"), KeyEvent.VK_X, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				cutCopyPaste("cut");
			}

		};
	}

	/**
	 * Returns an action for copying selected text to the clipboard.
	 * 
	 * @return an action for copying selected text to the clipboard
	 */
	public Action getCopyAction() {
		return new LocalizableAction("copyAction", KeyStroke.getKeyStroke("control C"), KeyEvent.VK_C, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				cutCopyPaste("copy");
			}

		};
	}

	/**
	 * Returns an action for inserting text from the clipboard in the current caret
	 * location.
	 * 
	 * @return an action for inserting text from the clipboard in the current caret
	 *         location
	 */
	public Action getPasteAction() {
		return new LocalizableAction("pasteAction", KeyStroke.getKeyStroke("control V"), KeyEvent.VK_V, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				cutCopyPaste("paste");
			}

		};
	}

	/**
	 * Returns an action for exiting application.
	 * 
	 * @return an action for exiting application
	 */
	public Action getExitAction() {
		return new LocalizableAction("exitAction", KeyStroke.getKeyStroke("control E"), KeyEvent.VK_E, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exitProgram();
			}

		};
	}

	/**
	 * Returns an action for displaying statistical info of the current document.
	 * 
	 * @return an action for displaying statistical info of the current document
	 */
	public Action getStatisticalInfoAction() {
		return new LocalizableAction("statisticalInfoAction", KeyStroke.getKeyStroke("control I"), KeyEvent.VK_I, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String content = tabbedPane.getCurrentDocument().getTextComponent().getText();

				int nonBlankCnt = content.replaceAll("\\s+", "").length();
				long newLineCnt = content.lines().count();

				JOptionPane.showOptionDialog(frame,
						String.format(lp.getString("statisticalInfoMsg"), content.length(), nonBlankCnt, newLineCnt),
						lp.getString("information"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
						null, null);
			}

		};
	}

	/**
	 * Returns an action that converts selected text to uppercase.
	 * 
	 * @return an action that converts selected text to uppercase
	 */
	public Action getUppercaseAction() {
		return new LocalizableAction("uppercaseAction", KeyStroke.getKeyStroke("control P"), KeyEvent.VK_P, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase("upper");
			}

		};
	}

	/**
	 * Returns an action that converts selected text to lowercase.
	 * 
	 * @return an action that converts selected text to lowercase
	 */
	public Action getLowercaseAction() {
		return new LocalizableAction("lowercaseAction", KeyStroke.getKeyStroke("control L"), KeyEvent.VK_L, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase("lower");
			}

		};
	}

	/**
	 * Returns an action that inverts character case in selected part of text.
	 * 
	 * @return an action that inverts character case in selected part of text
	 */
	public Action getInvertcaseAction() {
		return new LocalizableAction("invertcaseAction", KeyStroke.getKeyStroke("control T"), KeyEvent.VK_T, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase("invert");
			}

		};
	}

	/**
	 * Returns an action that sorts selected text in ascending order.
	 * 
	 * @return an action that sorts selected text in ascending order
	 */
	public Action getSortAscAction() {
		return new LocalizableAction("sortAscAction", KeyStroke.getKeyStroke("alt 1"), 0, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sortLinesOrRemoveDuplicates(true, false);
			}

		};
	}

	/**
	 * Returns an action that sorts selected text in descending order.
	 * 
	 * @return an action that sorts selected text in descending order
	 */
	public Action getSortDescAction() {
		return new LocalizableAction("sortDescAction", KeyStroke.getKeyStroke("alt 2"), 0, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sortLinesOrRemoveDuplicates(false, false);
			}

		};
	}

	/**
	 * Returns an action that removes duplicate lines from selected text.
	 * 
	 * @return an action that removes duplicate lines from selected text
	 */
	public Action getUniqueAction() {
		return new LocalizableAction("uniqueAction", KeyStroke.getKeyStroke("control U"), KeyEvent.VK_U, lp) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sortLinesOrRemoveDuplicates(false, true);
			}

		};
	}

	/**
	 * Changes character casing in selected text of current document depending on
	 * the given string argument. If given argument is "upper" - changes to
	 * uppercase, "lower" - changes to lowercase, "invert" - inverts casing
	 * 
	 * @param mode mode for changing character casing
	 * @throws IllegalArgumentException if mode is not "lower", "upper" or "invert"
	 */
	private void changeCase(String mode) {
		JTextComponent currentComponent = tabbedPane.getCurrentDocument().getTextComponent();
		SelectedText selected = SelectedText.getSelectedText(currentComponent);
		int offset = selected.getOffset();
		int len = selected.getLength();

		Document doc = currentComponent.getDocument();
		try {
			String text = doc.getText(offset, len);
			if (mode.equals("lower")) {
				text = text.toLowerCase(Locale.forLanguageTag(lp.getCurrentLanguage()));
			} else if (mode.equals("upper")) {
				text = text.toUpperCase(Locale.forLanguageTag(lp.getCurrentLanguage()));
			} else if (mode.equals("invert")) {
				text = invertCase(text);
			} else {
				throw new IllegalArgumentException("Unsupported mode.");
			}
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Returns a string created by inverting character casing of the given string.
	 * 
	 * @param text original string
	 * @return string with inverted character casing
	 */
	private String invertCase(String text) {
		char[] znakovi = text.toCharArray();

		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];

			if (Character.isLowerCase(c)) {
				znakovi[i] = Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)) {
				znakovi[i] = Character.toLowerCase(c);
			}
		}
		return new String(znakovi);
	}
	
	/**
	 * Method performs cut, copy or paste depending on the given argument. If given
	 * argument is "cut" - performes cut, if "copy" - performs copy, if "paste" -
	 * performs paste. This method is used to remove code repetition.
	 * 
	 * @param mode if modeis "cut" - performes cut, if "copy" - performs copy, if
	 *             "paste" - performs paste
	 */
	private void cutCopyPaste(String mode) {
		JTextComponent currentComponent = tabbedPane.getCurrentDocument().getTextComponent();
		SelectedText selected = SelectedText.getSelectedText(currentComponent);
		int offset = selected.getOffset();
		int len = selected.getLength();

		if (len == 0 && (mode.equals("copy") || mode.equals("cut"))) {
			return;
		}

		Document doc = currentComponent.getDocument();
		try {
			if (mode.equals("copy") || mode.equals("cut")) {
				clipboard = doc.getText(offset, len);
			}
			if (mode.equals("cut") || mode.equals("paste")) {
				doc.remove(offset, len);
			}
			if (mode.equals("paste")) {
				doc.insertString(offset, clipboard, null);
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * If the given unique flag is set to true, method removes duplicate lines from
	 * selected text(ascending flag is ignored). If unique is set to false, the
	 * method sorts lines from selected text in ascending or descending order,
	 * depending on the given flag.
	 * 
	 * @param ascending if true, will sort in ascending order, descending otherwise.
	 *                  This flag is ignored if unique is set to true.
	 * @param unique    if true, method removes duplicate lines from selected text,
	 *                  otherwise sorts the lines in order depending on the
	 *                  ascending flag
	 */
	private void sortLinesOrRemoveDuplicates(boolean ascending, boolean unique) {
		Locale locale = Locale.forLanguageTag(lp.getCurrentLanguage());
		Collator collator = Collator.getInstance(locale);

		JTextComponent c = tabbedPane.getCurrentDocument().getTextComponent();
		Document doc = c.getDocument();
		Element root = doc.getDefaultRootElement();

		int startRow = root.getElementIndex(c.getSelectionStart());
		int endRow = root.getElementIndex(c.getSelectionEnd());

		List<String> lines = new ArrayList<>();

		for (int i = startRow; i <= endRow; i++) { // add selected lines into list
			int lineStartOffset = root.getElement(i).getStartOffset();
			int lineEndOffset = root.getElement(i).getEndOffset();
			int length = lineEndOffset - lineStartOffset;

			try {
				lines.add(doc.getText(lineStartOffset, length));
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}

		int startOffset = root.getElement(startRow).getStartOffset();
		int endOffset = root.getElement(endRow).getEndOffset();
		int segmentLength = endOffset - startOffset - 1;

		try {
			doc.remove(startOffset, segmentLength); // remove selected lines
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		if (unique) { // remove duplicates from string
			Set<String> seen = new HashSet<>();
			lines = lines.stream().filter(s -> {
				for (String seenStr : seen) {
					if (collator.equals(s, seenStr)) {
						return false;
					}
				}
				seen.add(s);
				return true;
			}).collect(Collectors.toList());

		} else { // sort
			if (ascending) {
				lines.sort(collator);
			} else {
				lines.sort(collator.reversed());
			}
		}

		for (int i = 0; i < lines.size(); i++) { // insert lines into document
			String line = lines.get(i);
			if (i == lines.size() - 1) {
				line = line.substring(0, line.length() - 1); // ignore last \n
			}

			try {
				doc.insertString(startOffset, line, null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			startOffset += line.length();
		}
	}

	/**
	 * Exits the program. The method first asks user to save each unsaved document,
	 * then disposes the frame and the program terminates.
	 */
	public void exitProgram() {
		for (int i = 0; i < tabbedPane.getNumberOfDocuments(); i++) {
			SingleDocumentModel doc = tabbedPane.getDocument(i);
			Path p = doc.getFilePath();

			if (doc.isModified()) {
				String[] options = { lp.getString("save"), lp.getString("discard"), lp.getString("abortClosing") };
				int answer = JOptionPane.showOptionDialog(frame,
						String.format(lp.getString("saveChangesQuestion"),
								(p == null ? "(unnamed)" : doc.getFilePath().toString())),
						lp.getString("information"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
						options, options[0]);
				
				if (answer == 0) { // save
					Path savePath = p;

					if (p == null) { // save as (for unnamed documents)
						JFileChooser jfc = new JFileChooser();
						jfc.setDialogTitle(lp.getString("saveDocument"));
						if (jfc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
							JOptionPane.showMessageDialog(frame, lp.getString("notSaved"), lp.getString("warning"),
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						savePath = jfc.getSelectedFile().toPath();
					}
					tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), savePath);
					
				} else if (answer == 2 || answer == JOptionPane.CLOSED_OPTION) { // abort
					return;
				}
			}
		}

		frame.dispose();
		System.exit(0);
	}
}
