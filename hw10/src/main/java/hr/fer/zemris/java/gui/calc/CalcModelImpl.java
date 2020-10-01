package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * A simple calculator model - class that models the internal calculator state. It provides methods for the
 * user to interact with the internal state. 
 * 
 * @author staver
 *
 */
public class CalcModelImpl implements CalcModel {
	
	/**
	 * Flag: is model editable
	 */
	private boolean isEditable = true;
	/**
	 * Flag: is current value positive
	 */
	private boolean isPositive = true;
	/**
	 * current value as string
	 */
	private String value = "";
	/**
	 * current value as double
	 */
	private double valueNum = 0;
	/**
	 * Frozen value(used to be displayed on screen when inputing binary operations)
	 */
	private String frozen = null;
	/**
	 * current active operand 
	 */
	private double activeOperand;
	/**
	 * binary operation to be executed
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * Flag: is active operand set
	 */
	private boolean isActiveOperandSet = false;
	
	/**
	 * List of listeners
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public String toString() {
		if (frozen != null) {
			return frozen;
		}

		if (value.length() == 0) {
			if (isPositive) {
				return "0";
			}
			return "-0";
		}

		if (isPositive) {
			return value;
		}

		return "-" + value;
	}

	@Override
	public double getValue() {
		if (isPositive) {
			return valueNum;
		}
		return (-1) * valueNum;
	}

	@Override
	public void setValue(double value) {
		if (value >= 0) {
			isPositive = true;
		} else {
			isPositive = false;
		}

		valueNum = Math.abs(value);
		this.value = Double.toString(valueNum);
		isEditable = false;
		frozen = null;
		alertListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		isEditable = true;
		value = "";
		valueNum = 0;
		isPositive = true;
		alertListeners();
	}

	@Override
	public void clearAll() {
		isEditable = true;
		value = "";
		valueNum = 0;
		isPositive = true;
		isActiveOperandSet = false;
		pendingOperation = null;
		frozen = null;
		alertListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("Current input is not editable.");
		}
		frozen = null;
		isPositive = !isPositive;
		alertListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("Current input is not editable.");
		}

		if (value.indexOf('.') != -1) {
			throw new CalculatorInputException("Decimal point already exists.");
		}

		if (value == "") {
			throw new CalculatorInputException("Can't insert decimal point: current value is empty.");
		}

		value += ".";
		valueNum = Double.parseDouble(value);
		frozen = null;
		alertListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException("Model is not editable.");
		}

		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Digit is not in range [0,9]");
		}

		if (value.equals("0")) {
			if (digit == 0) {
				return; // avoid multiple zeros
			} else {
				value = ""; // ignore leading zeros
			}
		}

		String inputCopy = value;
		inputCopy += digit;

		if (Double.parseDouble(inputCopy) > Double.MAX_VALUE) {
			throw new CalculatorInputException("Too large number.");
		}

		try {
			valueNum = Double.parseDouble(inputCopy);
			value = inputCopy;
			frozen = null;
			alertListeners();
		} catch (NumberFormatException ex) {
			throw new CalculatorInputException("Cant parse input to double.");
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet) {
			throw new IllegalStateException("Active operand is not set.");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isActiveOperandSet = true;
	}

	@Override
	public void clearActiveOperand() {
		isActiveOperandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	/**
	 * Freezes the given string.
	 * 
	 * @param value string to be frozen.
	 */
	public void freezeValue(String value) {
		frozen = value;
	}
	
	/**
	 * Returns true if the model has a frozen value, false otherwise.
	 * 
	 * @return true if the model has a frozen value, false otherwise.
	 */
	public boolean hasFrozenValue() {
		return frozen == null ? false : true;
	}
	
	/**
	 * Alerts all listeners.
	 */
	private void alertListeners() {
		listeners.forEach(l -> l.valueChanged(this));
	}

}
