package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Class that demonstrates an example use of SimpleHashtable class. The class
 * shows how to iterate through the Hashtable.
 * 
 * @author Mauro Staver
 *
 */
public class SimpleHashtableDemo {

	/**
	 * Main method that gets called when the program starts. The method shows how to
	 * iterate through the Hashtable.
	 * 
	 * @param args command line arguments - not used.
	 */
	public static void main(String[] args) {

		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", 2);
		table.put("Kristina", 5);
		table.put("Ivana", 5); // overwrites old value

		for (SimpleHashtable.TableEntry<String, Integer> pair : table) {
			System.out.println(pair.getKey() + " => " + pair.getValue());
		}
		
		System.out.printf("%ntoString: %s%n", table.toString());
		
		System.out.printf("%nCartesian product:%n");
		for (SimpleHashtable.TableEntry<String, Integer> pair1 : table) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : table) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}
	}
}
