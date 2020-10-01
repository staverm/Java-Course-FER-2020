package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.DoubleBinaryOperator;

/**
 * ValueWrapper that wraps an object and offers arithmetic operations such as
 * +,-,*,/ with other given objects, BUT only if they are of type
 * String(parsable to Integer/Double), Integer, Double or null. Also provides a
 * method for comparing the wrapped value with some object in a similar manner.
 * 
 * @author staver
 *
 */
public class ValueWrapper {

	/**
	 * Functional interface that represents an operation upon two
	 * {@code integer}-valued operands and producing a {@code integer}-valued
	 * result.
	 * 
	 * @author staver
	 */
	@FunctionalInterface
	private interface IntegerBinaryOperator {
		public int applyAsInt(int val1, int val2);
	}

	private Object value; // wrapped value

	/**
	 * ValueWrapper constructor. Wraps the given object into.
	 * 
	 * @param value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Performs addition on this object's wrapped value and the given value. The
	 * result is stored as this object's wrapped value.
	 * 
	 * @param incValue Value to be added
	 * @throws RuntimeException if unable to perform addition on the given types
	 */
	public void add(Object incValue) {
		executeOperation(incValue, (a, b) -> a + b, (a, b) -> a + b);
	}

	/**
	 * Performs subtraction on this object's wrapped value and the given value. The
	 * result is stored as this object's wrapped value.
	 * 
	 * @param incValue Value to be subtracted
	 * @throws RuntimeException if unable to perform subtraction on the given types
	 */
	public void subtract(Object decValue) {
		executeOperation(decValue, (a, b) -> a - b, (a, b) -> a - b);
	}

	/**
	 * Performs multiply on this object's wrapped value and the given value. The
	 * result is stored as this object's wrapped value.
	 * 
	 * @param incValue Value to be multiplied
	 * @throws RuntimeException if unable to perform multiply on the given types
	 */
	public void multiply(Object mulValue) {
		executeOperation(mulValue, (a, b) -> a * b, (a, b) -> a * b);
	}

	/**
	 * Performs division on this object's wrapped value and the given value. The
	 * result is stored as this object's wrapped value.
	 * 
	 * @param incValue Value to be used as a divisor
	 * @throws RuntimeException if unable to perform division on the given types
	 */
	public void divide(Object divValue) {
		executeOperation(divValue, (a, b) -> a / b, (a, b) -> a / b);
	}

	/**
	 * Compares this object's wrapped value and the given value. Returns an integer
	 * larger than zero if currently stored value is smaller than the argument, an
	 * integer greater than zero if the currently stored value is larger than the
	 * argument, or an integer 0 if they are equal.
	 * 
	 * @param withValue Value to be compared with
	 * @return integer larger than zero if currently stored value is smaller than
	 *         the argument, an integer greater than zero if the currently stored
	 *         value is larger than the argument, or an integer 0 if they are equal
	 * @throws RuntimeException if unable to compare objects
	 */
	public int numCompare(Object withValue) {
		Number val1 = transform(value);
		Number val2 = transform(withValue);

		if (val1 instanceof Double || val2 instanceof Double) {
			return Double.compare(val1.doubleValue(), val2.doubleValue());
		} else {
			return Integer.compare(val1.intValue(), val2.intValue());
		}
	}

	/**
	 * Returns this object's wrapped value.
	 * 
	 * @return this object's wrapped value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets this object's wrapped value to the given value.
	 * 
	 * @value value to be set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Applies a given double or integer operator depending on the given argument(if
	 * wrapped value or argument is Double - double operator is used, else integer
	 * operator is used) and sets this objects value to the result.
	 * 
	 * @param otherValue Value to be used in operation
	 * @param opDouble   Binary operator to be applied
	 * @param opInt      Integer operator to be applied
	 */
	private void executeOperation(Object otherValue, DoubleBinaryOperator opDouble, IntegerBinaryOperator opInt) {
		Number val1 = transform(value);
		Number val2 = transform(otherValue);

		if (val1 instanceof Double || val2 instanceof Double) {
			value = opDouble.applyAsDouble(val1.doubleValue(), val2.doubleValue());
		} else {
			value = opInt.applyAsInt(val1.intValue(), val2.intValue());
		}
	}

	/**
	 * Transforms the given object into some instance of Number(Integer or Double).
	 * First tries to parse the given value into Integer, then into Double, if
	 * nothing is successful throws Exception.
	 * 
	 * @param val value to be transformed
	 * @return given value as some instance of Number(Integer or Double).
	 * @throws RuntimeException if unable to parse.
	 */
	private Number transform(Object val) {
		if (val == null) {
			return Integer.valueOf(0);
		}

		if (val instanceof Integer) {
			return (Integer) val;
		}

		if (val instanceof Double) {
			return (Double) val;
		}

		if (val instanceof String) {
			// parses value into Integer if possible, if not into Double (may throw
			// exception)
			try {
				Integer IntRetValue = Integer.parseInt((String) val);
				return IntRetValue;
			} catch (NumberFormatException ex) {
				Double DoubleRetValue = Double.parseDouble((String) val);
				return DoubleRetValue;
			}
		}

		throw new RuntimeException("Can't parse values!");
	}

}
