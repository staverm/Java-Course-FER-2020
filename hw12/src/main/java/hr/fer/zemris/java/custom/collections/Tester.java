package hr.fer.zemris.java.custom.collections;

/**
 * Interface that tests the given objects for some condition specified in the test method.
 * @author Mauro Staver
 *
 */
public interface Tester {
	
	/**
	 * Tests the given object for some condition and returns true if the object satisfies the condition.
	 * @param obj Object we are testing
	 * @return true if the given objects satisfies the condition, false otherwise.
	 */
	boolean test(Object obj);

}
