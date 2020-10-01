package hr.fer.zemris.java.custom.collections.demo;

import java.util.ConcurrentModificationException;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Class that shows an example use of the ElementsGetter interface with an
 * ArrayIndexedCollection object.
 * 
 * @author Mauro Staver
 */
public class ElementsGetterDemo {

	/**
	 * Main method that gets called when the program starts. The method demonstrates
	 * the use of the ElementsGetter interface with an ArrayIndexedCollection
	 * object.
	 * 
	 * @param args command line arguments - not used here
	 */
	public static void main(String[] args) {
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new LinkedListIndexedCollection();

		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");

		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();

		System.out.println("Jedan element: " + getter1.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ana
		System.out.println("Jedan element: " + getter2.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter3.getNextElement()); // Jasmina
		System.out.println("Jedan element: " + getter3.getNextElement()); // Štefanija

		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");

		ElementsGetter getter = col.createElementsGetter();

		System.out.println("Jedan element: " + getter.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter.getNextElement()); // Ana

		col.clear();

		try {
			System.out.println("Jedan element: " + getter.getNextElement()); // throws ConcurrentModificationException
		} catch (ConcurrentModificationException ex) {
			System.out.println("ConcurrentModificationException caught.");
		}

		Collection col3 = new ArrayIndexedCollection();
		col3.add("Ivo");
		col3.add("Ana");
		col3.add("Jasna");

		ElementsGetter getter4 = col3.createElementsGetter();
		getter4.getNextElement();
		getter4.processRemaining(System.out::println); // Ana Jasna

	}
}
