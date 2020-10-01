package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableJButton;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Program that implements a simple Notepad++ like editor called JNotepad++.
 * 
 * @author staver
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * JNotepad++ tabbed editor
	 */
	private MultipleDocumentModel tabbedPane;
	/**
	 * Menu items that should be disabled when there is no text selected.
	 */
	private List<JMenuItem> disableableItems = new ArrayList<>();
	/**
	 * Localization provider
	 */
	private FormLocalizationProvider flp;
	/**
	 * Holds implementations of all JNotepad++ actions.
	 */
	private ActionsGetter actions;

	/**
	 * JNotepadPP constructor. Sets up window frame and initializes GUI.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(1200, 800);

		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		tabbedPane = new DefaultMultipleDocumentModel(this, flp);
		actions = new ActionsGetter(tabbedPane, this, flp);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				actions.exitProgram();
			}
		});

		initGUI();

	}

	/**
	 * Initializes GUI components and adds them to the content pane.
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add((DefaultMultipleDocumentModel) tabbedPane, BorderLayout.CENTER);

		createActions();
		createMenus();
		createToolbar();

		StatusBar statusbar = new StatusBar();
		add(statusbar, BorderLayout.SOUTH);

		CaretListener caretListener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				JTextComponent currentComponent = tabbedPane.getCurrentDocument().getTextComponent();
				statusbar.setLabels(currentComponent); // update status bar

				int selectedLen = SelectedText.getSelectedText(currentComponent).getLength();
				if (selectedLen > 0) {
					disableableItems.forEach((item) -> item.setEnabled(true)); // enable items
				} else {
					disableableItems.forEach((item) -> item.setEnabled(false)); // disable items
				}
			}
		};

		SingleDocumentListener updateTitle = new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) { // update frame title if path changes
				JNotepadPP.this.setTitle(tabbedPane.getCurrentDocument().getFilePath().toString() + " - JNotepad++");
			}

		};

		tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				String pathTitle = currentModel.getFilePath() == null ? "(unnamed)"
						: currentModel.getFilePath().toString();
				setTitle(pathTitle + " - JNotepad++"); // set title

				previousModel.removeSingleDocumentListener(updateTitle);
				currentModel.addSingleDocumentListener(updateTitle);

				statusbar.setLabels(currentModel.getTextComponent());

				previousModel.getTextComponent().removeCaretListener(caretListener);
				currentModel.getTextComponent().addCaretListener(caretListener);
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}
		});

		tabbedPane.createNewDocument(); // starting tab
	}

	private Action openDocumentAction; // action for opening documents
	private Action saveDocumentAction; // action for saving documents
	private Action saveDocumentAsAction; // action for saving documents at specified location
	private Action closeDocumentAction; // action for closing document/tab
	private Action newDocumentAction; // action for creating new document
	private Action cutAction; // action for deleting selected text and copying it to the clipboard
	private Action copyAction; // action for copying selected text to the clipboard
	private Action pasteAction; // action for inserting text from the clipboard in the current caret location
	private Action exitAction; // action for exiting application
	private Action statisticalInfoAction; // action for displaying statistical info of the current document
	private Action uppercaseAction; // action that converts selected text to uppercase
	private Action lowercaseAction; // action that converts selected text to lowercase
	private Action invertcaseAction; // action that inverts character case in selected part of text
	private Action sortAscAction; // action that sorts selected text in ascending order
	private Action sortDescAction; // action that sorts selected text in descending order
	private Action uniqueAction; // action that removes duplicate lines from selected text

	/**
	 * Initializes actions.
	 */
	private void createActions() {
		openDocumentAction = actions.getOpenDocumentAction();
		saveDocumentAction = actions.getSaveDocumentAction();
		saveDocumentAsAction = actions.getSaveDocumentAsAction();
		closeDocumentAction = actions.getCloseDocumentAction();
		newDocumentAction = actions.getNewDocumentAction();
		cutAction = actions.getCutAction();
		copyAction = actions.getCopyAction();
		pasteAction = actions.getPasteAction();
		exitAction = actions.getExitAction();
		statisticalInfoAction = actions.getStatisticalInfoAction();
		uppercaseAction = actions.getUppercaseAction();
		lowercaseAction = actions.getLowercaseAction();
		invertcaseAction = actions.getInvertcaseAction();
		sortAscAction = actions.getSortAscAction();
		sortDescAction = actions.getSortDescAction();
		uniqueAction = actions.getUniqueAction();
	}

	/**
	 * Creates a menu bar and adds it to the GUI.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		// File Menu
		JMenu fileMenu = new LocalizableJMenu("fileMenu", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAsAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(statisticalInfoAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		// Edit Menu
		JMenu editMenu = new LocalizableJMenu("editMenu", flp);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));

		// Tools Menu
		JMenu toolsMenu = new LocalizableJMenu("toolsMenu", flp);
		menuBar.add(toolsMenu);

		// Change case Menu
		JMenu changeCaseSubmenu = new LocalizableJMenu("changeCaseSubmenu", flp);
		JMenuItem uppercaseItem = new JMenuItem(uppercaseAction);
		JMenuItem lowercaseItem = new JMenuItem(lowercaseAction);
		JMenuItem invertcaseItem = new JMenuItem(invertcaseAction);
		uppercaseItem.setEnabled(false);
		lowercaseItem.setEnabled(false);
		invertcaseItem.setEnabled(false);

		changeCaseSubmenu.add(uppercaseItem);
		changeCaseSubmenu.add(lowercaseItem);
		changeCaseSubmenu.add(invertcaseItem);

		disableableItems.add(uppercaseItem);
		disableableItems.add(lowercaseItem);
		disableableItems.add(invertcaseItem);

		toolsMenu.add(changeCaseSubmenu);

		// Sort Menu
		JMenu sortSubmenu = new LocalizableJMenu("sortSubmenu", flp);
		sortSubmenu.add(new JMenuItem(sortAscAction));
		sortSubmenu.add(new JMenuItem(sortDescAction));
		toolsMenu.add(sortSubmenu);

		toolsMenu.add(new JMenuItem(uniqueAction));

		// Languages Menu
		JMenu languagesMenu = new LocalizableJMenu("languagesMenu", flp);
		menuBar.add(languagesMenu);

		JMenuItem germanMenuItem = new JMenuItem("de");
		JMenuItem englishMenuitem = new JMenuItem("en");
		JMenuItem croatianMenuItem = new JMenuItem("hr");

		languagesMenu.add(germanMenuItem);
		languagesMenu.add(englishMenuitem);
		languagesMenu.add(croatianMenuItem);

		setLanguageOnClick(germanMenuItem, "de");
		setLanguageOnClick(englishMenuitem, "en");
		setLanguageOnClick(croatianMenuItem, "hr");

		this.setJMenuBar(menuBar);
	}

	/**
	 * Registers a new action listener to the given menu item that sets the current
	 * language to the given argument when the listener gets triggered.
	 * 
	 * @param item     item on which to register an action listener
	 * @param language language to set when listener gets triggered
	 */
	private void setLanguageOnClick(JMenuItem item, String language) {
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage(language);
			}
		});
	}

	/**
	 * Creates a toolbar and adds it to the content pane.
	 */
	private void createToolbar() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new LocalizableJButton(newDocumentAction, "new.png", flp));
		toolBar.add(new LocalizableJButton(openDocumentAction, "open.png", flp));
		toolBar.add(new LocalizableJButton(saveDocumentAction, "blueDisk.png", flp));
		toolBar.add(new LocalizableJButton(saveDocumentAsAction, "saveAs.png", flp));
		toolBar.add(new LocalizableJButton(closeDocumentAction, "closeTab.png", flp));
		toolBar.addSeparator();
		toolBar.add(new LocalizableJButton(cutAction, "cut.png", flp));
		toolBar.add(new LocalizableJButton(copyAction, "copy.png", flp));
		toolBar.add(new LocalizableJButton(pasteAction, "paste.png", flp));
		toolBar.addSeparator();
		toolBar.add(new LocalizableJButton(statisticalInfoAction, "info.png", flp));
		toolBar.addSeparator();
		toolBar.add(new LocalizableJButton(exitAction, "exit.png", flp));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Main method that gets called when the program starts. Invokes JNotepadPP
	 * frame and and sets it's window to visible.
	 * 
	 * @param args command line arguments - not used here.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
