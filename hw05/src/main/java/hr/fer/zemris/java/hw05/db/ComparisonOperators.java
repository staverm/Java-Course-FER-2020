package hr.fer.zemris.java.hw05.db;

/**
 * Class that implements different comparison operators for strings.
 * 
 * @author Mauro Staver
 *
 */
public class ComparisonOperators {

	/**
	 * Compares two string literals. Calling satisfied() method on this object will
	 * return true if the first string literal is less than the second string
	 * literal, otherwise false.
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0 ? true : false;

	/**
	 * Compares two string literals. Calling satisfied() method on this object will
	 * return true if the first string literal is less or equal than the second
	 * string literal, otherwise false.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0 ? true
			: false;

	/**
	 * Compares two string literals. Calling satisfied() method on this object will
	 * return true if the first string literal is greater than the second string
	 * literal, otherwise false.
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0 ? true : false;

	/**
	 * Compares two string literals. Calling satisfied() method on this object will
	 * return true if the first string literal is greater than or equal the second
	 * string literal, otherwise false.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0 ? true
			: false;

	/**
	 * Compares two string literals. Calling satisfied() method on this object will
	 * return true if the first string literal is equal to the second string
	 * literal, otherwise false.
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.compareTo(value2) == 0 ? true : false;

	/**
	 * Compares two string literals. Calling satisfied() method on this object will
	 * return true if the first string literal not equal to the second string
	 * literal, otherwise false.
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0 ? true
			: false;

	/**
	 * Compares the equality of two strings. The second argument(string literal) can
	 * contain a wildcard *.
	 */
	/**
	 * Compares two string literals. Calling satisfied() method on this object will
	 * return true if the first string literal is equal to the second string
	 * literal, otherwise false. The second string literal can contain a wildcard *
	 * which represents any number of any characters.
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {			
		for (int i = 0; i < value2.length(); i++) {
					
			if (value2.charAt(i) == '*') {
				String value2afterWildmark = value2.substring(i + 1);  
				if (value2afterWildmark.contains("*")) {
					throw new IllegalArgumentException("Multiple wildcards are not supported.");
				}
				
				int charsLeft = value2.length() - i - 1;
				if (charsLeft == 0) { //wildcard is the last char in value2
					return true;
				}
				if(charsLeft > value1.length()-i) { //value2 after * has more chars than value1.substring(i-1)
					return false;
				}
				
				// skip chars from [i, value1.length()-charsLeft-1] because of *
				String s1 = value1.substring(value1.length() - charsLeft); // value1 after skipping

				return s1.compareTo(value2afterWildmark) == 0 ? true : false;
			}
			
			if(value1.length() == i) { //end of value1 reached
				return false;
			}
			
			if (value1.charAt(i) != value2.charAt(i)) {
				return false;
			}
		}
		
		return true;
	};

}
