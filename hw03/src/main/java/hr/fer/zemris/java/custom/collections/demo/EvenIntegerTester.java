package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * A Tester class that tests if the given integers are even.
 * 
 * @author Mauro Staver
 *
 */
public class EvenIntegerTester implements Tester {

	@Override
	public boolean test(Object obj) {
		if (!(obj instanceof Integer))
			return false;

		Integer i = (Integer) obj;
		return i % 2 == 0;
	}

	/**
	 * Main method that gets called when the program starts. It shows an example use
	 * of the EvenIntegerTester class.
	 * 
	 * @param args command line arguments - not used here.
	 */
	public static void main(String[] args) {
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}
}
