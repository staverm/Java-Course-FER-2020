package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * A class that demonstrates the addAllSatisfying method using the
 * EvenIntegerTester.
 * 
 * @author Mauro Staver
 *
 */
public class addAllSatisfyingDemo {

	/**
	 * Main method that gets called when the program starts. It shows an example use
	 * of the addAllSatisfying method using the EvenIntegerTester.
	 * 
	 * @param args command line arguments - not used here.
	 */
	public static void main(String[] args) {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();

		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);

		col2.add(12);

		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println); // 12 2 4 6
	}
}
