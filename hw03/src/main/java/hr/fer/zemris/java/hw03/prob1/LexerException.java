package hr.fer.zemris.java.hw03.prob1;

/**
 * An exception that gets thrown when an error occurs while Lexer processing.
 * @author Mauro Staver
 *
 */
public class LexerException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Exception constructor.
	 * @param message message of the occurred exception.
	 */
	public LexerException(String message) {
		super(message);
	}
	
}
