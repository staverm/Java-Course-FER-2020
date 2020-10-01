package hr.fer.zemris.java.hw05.db;

/**
 * Interface responsible for obtaining a requested field value from a given
 * StudentRecord.
 * 
 * @author Mauro Staver
 *
 */
public interface IFieldValueGetter {
	/**
	 * Returns a requested field value from a given StudentRecord.
	 * 
	 * @param record StudentRecord from which the requested field value is returned.
	 * @return a requested field value from a given StudentRecord.
	 */
	public String get(StudentRecord record);
}
