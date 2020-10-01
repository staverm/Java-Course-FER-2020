package hr.fer.zemris.java.hw06.shell;

/**
 * Exception for errors that occur during IO operations with MyShell.
 * Subclass of RuntimeException.
 * 
 * @author Mauro Staver
 *
 */
public class ShellIOException extends RuntimeException{
	
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new ShellIOException with the specified detail message.
     * 
	 * @param message the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
	 */
	public ShellIOException(String message) {
        super(message);
    }

}
