package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * A class that shows an example use of the List class.
 * 
 * @author Mauro Staver
 *
 */
public class ListDemo {

	/**
	 * Main method that gets called when the program starts. It shows an example use
	 * of of the List class.
	 * 
	 * @param args command line arguments - not used here.
	 */
	public static void main(String[] args) {
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");

		Collection col3 = col1;
		Collection col4 = col2;

		col1.get(0);
		col2.get(0);
		// col3.get(0); // Compiler error
		// col4.get(0); // Compiler error
		// Compiler error because Collection doesn't have the method get(int index)!

		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println);
	}
}
