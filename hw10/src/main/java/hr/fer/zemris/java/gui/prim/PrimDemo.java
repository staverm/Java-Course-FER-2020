package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that opens a frame with two lists and a button. When the user clicks
 * the button a new prime is generated and displayed in both lists.
 * 
 * @author staver
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new PrimDemo. Initializes GUI.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}

	/**
	 * Creates two lists with scroll bars and a button and adds them to the content
	 * pane.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		PrimListModel primModel = new PrimListModel();

		JList<Integer> list1 = new JList<>(primModel);
		JList<Integer> list2 = new JList<>(primModel);

		JScrollPane scrollPane1 = new JScrollPane();
		JScrollPane scrollPane2 = new JScrollPane();

		scrollPane1.setViewportView(list1);
		scrollPane2.setViewportView(list2);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));

		panel.add(scrollPane1);
		panel.add(scrollPane2);
		cp.add(panel, BorderLayout.CENTER);

		JButton next = new JButton("next");
		next.addActionListener(l -> primModel.next());
		cp.add(next, BorderLayout.PAGE_END);
	}

	/**
	 * The main method that gets called when the program starts. Invokes the frame
	 * and sets it's window to visible.
	 * 
	 * @param args command line arguments - not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
