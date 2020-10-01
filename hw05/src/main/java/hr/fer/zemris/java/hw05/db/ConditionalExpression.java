package hr.fer.zemris.java.hw05.db;

/**
 * Class that models conditional expressions.
 * 
 * @author Mauro Staver
 *
 */
public class ConditionalExpression {

	private IComparisonOperator comparisonOperator;
	private IFieldValueGetter fieldGetter;
	String stringLiteral;

	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Returns the comparison operator of this conditional expression.
	 * 
	 * @return IComparisonOperator that is a comparison operator of this conditional
	 *         expression.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Returns a field getter of a record field used as an operand in this
	 * conditional expression.
	 * 
	 * @return IFieldValueGetter that is a field getter of a record field used as an
	 *         operand in this conditional expression.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns a string literal used as an operand in this conditional expression.
	 * 
	 * @return a string literal used as an operand in this conditional expression.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
}
