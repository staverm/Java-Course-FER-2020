package hr.fer.zemris.java.hw05.db;

/**
 * Interface that represents a comparison operator for two string literals.
 * 
 * @author Mauro Staver
 *
 */
public interface IComparisonOperator {
	/**
	 * Compares the first string literal to the second using some operator and
	 * returns the result as a true/false.
	 * 
	 * @param value1 String literal used as first operand of comparison.
	 * @param value2 String literal used as second operand of comparison.
	 * @return true if the comparsion is true, false otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
