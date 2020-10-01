package hr.fer.zemris.java.gui.layouts;

/**
 * Exception that gets thrown when an error occurs in the calculator layout.
 * 
 * @author staver
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new CalcLayoutException with the specified message.
	 * 
	 * @param message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}
