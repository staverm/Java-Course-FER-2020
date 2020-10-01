package hr.fer.zemris.java.hw05.db;

/**
 * Class that implements getters for fields of StudentRecord objects.
 * 
 * @author Mauro Staver
 *
 */
public class FieldValueGetters {
	/**
	 * Calling the get() method on this object returns the first name of the
	 * specified record.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();
	/**
	 * Calling the get() method on this object returns the last name of the
	 * specified record.
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName();
	/**
	 * Calling the get() method on this object returns the jmbag of the specified
	 * record.
	 */
	public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();
}
