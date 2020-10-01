package hr.fer.zemris.java.custom.collections;

/**
 * Exception that occurs when user tries to retrieve objects from an empty
 * stack.
 * 
 * @author Mauro Staver
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Exception constructor.
	 * 
	 * @param message message of the occurred exception.
	 */
	public EmptyStackException(String message) {
		super(message);
	}

}
