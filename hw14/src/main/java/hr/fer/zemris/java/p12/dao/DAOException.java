package hr.fer.zemris.java.p12.dao;

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
	 */
	public DAOException() {
	}

	/**
	 * DAOException constructor.
	 * 
	 * @see java.lang.RuntimeException#RuntimeException(String message, Throwable
	 *      cause, boolean enableSuppression, boolean writableStackTrace)
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
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
	
	/**
	 * DAOException constructor.
	 * 
	 * @see java.lang.RuntimeException#RuntimeException(Throwable cause)
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}
