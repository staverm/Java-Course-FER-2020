package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JList;

/**
 * Class for PrimListModel class.
 * 
 * @author staver
 *
 */
public class PrimListModelTest {

	PrimListModel primModel = new PrimListModel();
	JList<Integer> list1 = new JList<>(primModel);
	JList<Integer> list2 = new JList<>(primModel);

	@Test
	public void nextPrimeShowsInLists() {
		assertEquals(1, list1.getModel().getElementAt(0));
		assertEquals(1, list2.getModel().getElementAt(0));
		primModel.next();
		assertEquals(2, list1.getModel().getElementAt(1));
		assertEquals(2, list2.getModel().getElementAt(1));
		primModel.next();
		assertEquals(3, list1.getModel().getElementAt(2));
		assertEquals(3, list2.getModel().getElementAt(2));
	}

	@Test
	public void nextGeneratesCorrectPrimes() {
		for (int i = 0; i < 100; i++) {
			primModel.next();
		}
		assertEquals(359, primModel.getElementAt(72));
		assertEquals(457, primModel.getElementAt(88));
		assertEquals(541, primModel.getElementAt(100));
	}

}
