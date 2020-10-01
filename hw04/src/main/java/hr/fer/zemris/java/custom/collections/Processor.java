package hr.fer.zemris.java.custom.collections;

/**
 * A processor class. It implements a process method which processes the given
 * object.
 * 
 * @author Mauro Staver
 *
 */
public interface Processor<T> {

	/**
	 * Processes the given object.
	 * 
	 * @param value object to be processed
	 */
	void process(T value);

}
