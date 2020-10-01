package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Class that models persistence subsystem access runtime exceptions.
 * 
 * @author staver
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * DAOException constructor.
	 * 
	 * @see java.lang.RuntimeException#RuntimeException(String message, Throwable cause)
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * DAOException constructor.
	 * 
	 * @see java.lang.RuntimeException#RuntimeException(String message)
	 */
	public DAOException(String message) {
		super(message);
	}
}