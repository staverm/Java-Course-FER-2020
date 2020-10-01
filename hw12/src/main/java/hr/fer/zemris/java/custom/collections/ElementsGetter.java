package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * ElementsGetter interface provides to user the functionality of generating a series of elements, one at a time.
 * Successive calls to the getNextElement method return successive elements of the series. 
 * @author Mauro Staver
 *
 */
public interface ElementsGetter {
	
	/**
	 * Tests if this ElementsGetter contains more elements.
	 * @return true if this ElementsGetter contains at least one more element to provide, false otherwise.
	 */
	boolean hasNextElement();
	
	/**
	 * Returns the next element of this ElementsGetter if it has at least one more element to provide.
	 * @return the next element of this ElementsGetter if exists.
	 * @throws NoSuchElementException if no more elements exist.
	 */
	Object getNextElement() throws NoSuchElementException;
	
	/**
	 * Processes all the remaining elements in this ElementsGetter using the given processor object.
	 * @param p Processor object that processes the remaining elements.
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
