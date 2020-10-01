package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that gets thrown when the Parser occurs an error while parsing the
 * input string.
 * 
 * @author Mauro Staver
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new SmartScriptParserException with the specified message.
	 * 
	 * @param message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
