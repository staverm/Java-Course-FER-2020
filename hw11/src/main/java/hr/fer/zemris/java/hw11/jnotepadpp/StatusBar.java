package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.border.BevelBorder;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

/**
 * A JPanel that implements a status bar displayed at the bottom of JNotepad++
 * GUI. It displays length of current document, caret position(row and column),
 * length of selected text and the current date and time.
 * 
 * @author staver
 *
 */
public class StatusBar extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lengthLabel; // label for current document length
	private JLabel caretInfoLabel; // label for caret position and length of selected text
	private Timer t; // timer for updating the date and time label every second

	/**
	 * Status bar constructor - creates the status bar.
	 */
	public StatusBar() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BorderLayout());

		JPanel currentDocInfo = new JPanel();
		currentDocInfo.setLayout(new BoxLayout(currentDocInfo, BoxLayout.X_AXIS));

		lengthLabel = new JLabel("length:0");
		currentDocInfo.add(lengthLabel);

		currentDocInfo.add(Box.createHorizontalGlue());
		currentDocInfo.add(Box.createHorizontalStrut(40));
		currentDocInfo.add(new JSeparator(SwingConstants.VERTICAL));

		caretInfoLabel = new JLabel("Ln:0 Col:0 Sel:0");
		currentDocInfo.add(caretInfoLabel);
		currentDocInfo.add(Box.createHorizontalStrut(20));
		currentDocInfo.add(new JSeparator(SwingConstants.VERTICAL));

		add(currentDocInfo, BorderLayout.WEST);
	
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		JLabel clockLabel = new JLabel(dtf.format(LocalDateTime.now()));
		add(clockLabel, BorderLayout.EAST);

		t = new Timer(true);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				LocalDateTime now = LocalDateTime.now();
				clockLabel.setText(dtf.format(now));
			}
		}, 0, 1000);
	}

	/**
	 * Sets status bar's labels using the caret position from the given text
	 * component.
	 * 
	 * @param c text component whose caret position is to be used to update status
	 *          bar labels
	 */
	public void setLabels(JTextComponent c) {
		int pos = c.getCaretPosition();
		Document doc = c.getDocument();
		Element root = doc.getDefaultRootElement();

		int row = root.getElementIndex(pos); // zero-based line index
		int col = pos - root.getElement(row).getStartOffset(); // zero-based column index
		int selLen = SelectedText.getSelectedText(c).getLength();

		int length = c.getText().length();

		row++; //1-based line index
		col++; //1-based column index

		lengthLabel.setText("length:" + length);
		caretInfoLabel.setText("Ln:" + row + " Col:" + col + " Sel:" + selLen);
	}

}
