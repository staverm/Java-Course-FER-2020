package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Interface that models an operation performed by a calculator.
 * 
 * @author staver
 *
 */
public interface Operation {

	/**
	 * Executes this operation on the given calculator model.
	 * 
	 * @param model calculator model on which the operation should be executed
	 */
	void execute(CalcModel model);

}
