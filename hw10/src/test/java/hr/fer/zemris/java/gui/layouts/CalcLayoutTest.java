package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

/**
 * Tests for CalcLayout class.
 * 
 * @author staver
 *
 */
class CalcLayoutTest {

	CalcLayout calc = new CalcLayout(3);

	@Test
	void addLayoutComponentTestRowLessThanOne() {
		assertThrows(CalcLayoutException.class,
				() -> calc.addLayoutComponent(new JLabel("text"), new RCPosition(0, 3)));
	}

	@Test
	void addLayoutComponentTestRowMoreThanFive() {
		assertThrows(CalcLayoutException.class,
				() -> calc.addLayoutComponent(new JLabel("text"), new RCPosition(6, 3)));
	}

	@Test
	void addLayoutComponentTestColLessThanOne() {
		assertThrows(CalcLayoutException.class,
				() -> calc.addLayoutComponent(new JLabel("text"), new RCPosition(2, 0)));
	}

	@Test
	void addLayoutComponentTestColMoreThanSeven() {
		assertThrows(CalcLayoutException.class,
				() -> calc.addLayoutComponent(new JLabel("text"), new RCPosition(2, 8)));
	}

	@Test
	void addLayoutComponentTestIllegalStretchedComponent() {
		assertThrows(CalcLayoutException.class,
				() -> calc.addLayoutComponent(new JLabel("text"), new RCPosition(1, 2)));
	}

	@Test
	void addLayoutComponentTestIllegalStretchedComponent2() {
		assertThrows(CalcLayoutException.class,
				() -> calc.addLayoutComponent(new JLabel("text"), new RCPosition(1, 5)));
	}

	@Test
	void addLayoutComponentTestMultipleComponentsForSamePosition() {
		assertThrows(CalcLayoutException.class, () -> {
			calc.addLayoutComponent(new JLabel("text"), new RCPosition(2, 3));
			calc.addLayoutComponent(new JLabel("text2"), new RCPosition(2, 3));
		});
	}

	@Test
	void getPrefferedSizeTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 30));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	void getPrefferedSizeTest2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();

		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

}
