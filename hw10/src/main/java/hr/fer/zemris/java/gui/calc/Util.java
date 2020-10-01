package hr.fer.zemris.java.gui.calc;

import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

/**
 * Utility class that implements static methods for retrieving all operations
 * and operators used in Calculator.
 * 
 * @author staver
 *
 */
 public interface Util {
	 
	 /**
	  * Stack used for push and pop operatins.
	  */
	Stack<Double> stack = new Stack<>();

	/**
	 * Returns an operation for calculating sin of the current value.
	 * 
	 * @return an operation for calculating sin of the current value.
	 */
	 static Operation sin() {
		return model -> model.setValue(Math.sin(model.getValue()));
	}

	/**
	 * Returns an operation for calculating arcsin of the current value.
	 * 
	 * @return an operation for calculating arcsin of the current value.
	 */
	 static Operation arcsin() {
		return model -> model.setValue(Math.asin(model.getValue()));
	}

	/**
	 * Returns an operation for calculating arccos of the current value.
	 * 
	 * @return an operation for calculating arccos of the current value.
	 */
	 static Operation arccos() {
		return model -> model.setValue(Math.acos(model.getValue()));
	}

	/**
	 * Returns an operation for calculating arctan of the current value.
	 * 
	 * @return an operation for calculating arctan of the current value.
	 */
	 static Operation arctan() {
		return model -> model.setValue(Math.atan(model.getValue()));
	}

	/**
	 * Returns an operation for calculating cos of the current value.
	 * 
	 * @return an operation for calculating cos of the current value.
	 */
	 static Operation cos() {
		return model -> model.setValue(Math.cos(model.getValue()));
	}

	/**
	 * Returns an operation for calculating tan of the current value.
	 * 
	 * @return an operation for calculating tan of the current value.
	 */
	 static Operation tan() {
		return model -> model.setValue(Math.tan(model.getValue()));
	}

	/**
	 * Returns an operation for calculating ctg of the current value.
	 * 
	 * @return an operation for calculating ctg of the current value.
	 */
	 static Operation ctg() {
		return model -> model.setValue(1 / Math.tan(model.getValue()));
	}

	/**
	 * Returns an operation for calculating arcctg of the current value.
	 * 
	 * @return an operation for calculating arcctg of the current value.
	 */
	 static Operation arcctg() {
		return model -> model.setValue(Math.atan(1 / model.getValue()));
	}

	/**
	 * Returns an operation for calculating reciprocal of the current value.
	 * 
	 * @return an operation for calculating reciprocal of the current value.
	 */
	 static Operation reciprocal() {
		return model -> model.setValue(1 / model.getValue());
	}

	/**
	 * Returns an operation for calculating log(base 10) of the current value.
	 * 
	 * @return an operation for calculating log(base 10) of the current value.
	 */
	 static Operation log() {
		return model -> model.setValue(Math.log10(model.getValue()));
	}

	/**
	 * Returns an operation for calculating natural log of the current value.
	 * 
	 * @return an operation for calculating natural log of the current value.
	 */
	 static Operation ln() {
		return model -> model.setValue(Math.log(model.getValue()));
	}

	/**
	 * Returns an operation for calculating 10^(the current value).
	 * 
	 * @return an operation for calculating 10^(the current value).
	 */
	 static Operation expBase10() {
		return model -> model.setValue(Math.pow(10.0, model.getValue()));
	}

	/**
	 * Returns an operation for calculating e^(the current value).
	 * 
	 * @return an operation for calculating e^(the current value).
	 */
	 static Operation expBaseE() {
		return model -> model.setValue(Math.pow(Math.E, model.getValue()));
	}

	/**
	 * Returns an operation for clearing the calculator's screen.
	 * 
	 * @return an operation for clearing the calculator's screen.
	 */
	 static Operation clr() {
		return model -> model.clear();
	}

	/**
	 * Returns an operation for reseting the calculator.
	 * 
	 * @return an operation for reseting the calculator.
	 */
	 static Operation reset() {
		return model -> {
			stack.clear();
			model.clearAll();
		};
	}

	/**
	 * Returns an operation for pushing the current value to the stack.
	 * 
	 * @return an operation for pushing the current value to the stack.
	 */
	 static Operation push() {
		return model -> stack.push(model.getValue());
	}

	/**
	 * Returns an operation for popping the current value from the stack.
	 * 
	 * @return an operation for popping the current value from the stack.
	 */
	 static Operation pop() {
		return model -> model.setValue(stack.pop());
	}

	/**
	 * Returns an operation for swapping current value's sign.
	 * 
	 * @return an operation for swapping current value's sign.
	 */
	 static Operation swapSign() {
		return model -> model.swapSign();
	}

	/**
	 * Returns an operation for inserting the given digit in the calculator model.
	 * 
	 * @param digit digit to be inserted
	 * @return an operation for inserting the given digit in the calculator model.
	 */
	 static Operation insertDigit(int digit) {
		return model -> model.insertDigit(digit);
	}

	/**
	 * Returns an operation for inserting a decimal point in the calculator model.
	 * 
	 * @return an operation for inserting a decimal point in the calculator model.
	 */
	 static Operation insertDecimalPoint() {
		return model -> model.insertDecimalPoint();
	}

	/**
	 * Returns a binary operator for adding.
	 * 
	 * @return a binary operator for adding.
	 */
	 static DoubleBinaryOperator addOperator() {
		return (left, right) -> left + right;
	}

	/**
	 * Returns a binary operator for subtracting.
	 * 
	 * @return a binary operator for subtracting.
	 */
	 static DoubleBinaryOperator subOperator() {
		return (left, right) -> left - right;
	}

	/**
	 * Returns a binary operator for multiplying.
	 * 
	 * @return a binary operator for multiplying.
	 */
	 static DoubleBinaryOperator mulOperator() {
		return (left, right) -> left * right;
	}

	/**
	 * Returns a binary operator for dividing.
	 * 
	 * @return a binary operator for dividing.
	 */
	 static DoubleBinaryOperator divOperator() {
		return (left, right) -> left / right;
	}

	/**
	 * Returns a binary operator for exponentiation.
	 * 
	 * @return a binary operator for exponentiation.
	 */
	 static DoubleBinaryOperator powNOperator() {
		return (left, right) -> Math.pow(left, right);
	}

	/**
	 * Returns a binary operator for calculating the root.
	 * 
	 * @return a binary operator for calculating the root.
	 */
	 static DoubleBinaryOperator nthRootOperator() {
		return (left, right) -> Math.pow(left, 1 / right);
	}

	/**
	 * Returns an operation for applying the pending binary operation to the active
	 * operand(as left operand) and current value(as right operand).
	 * 
	 * @return an operation for applying the pending binary operation to the active
	 *         operand(as left operand) and current value(as right operand).
	 */
	 static Operation equals() {
		return model -> {
			double leftOperand = model.getActiveOperand();
			double rightOperand = model.getValue();
			model.setValue(model.getPendingBinaryOperation().applyAsDouble(leftOperand, rightOperand));
			model.clearActiveOperand();
		};
	}
}
