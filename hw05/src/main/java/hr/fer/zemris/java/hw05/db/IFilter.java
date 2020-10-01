package hr.fer.zemris.java.hw05.db;

/**
 * Filter for instances of StudentRecord.
 * 
 * @author Mauro Staver
 *
 */
public interface IFilter {
	/**
	 * Returns true if some condition for the given StudentRecord is satisfied.
	 * 
	 * @param record StudentRecord on which the condition is tested
	 * @return true if some condition for the given StudentRecord is satisfied,
	 *         otherwise false.
	 */
	public boolean accepts(StudentRecord record);
}
