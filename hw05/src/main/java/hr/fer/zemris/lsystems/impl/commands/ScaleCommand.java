package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command that scales the effective unit length of the current Turtle's state.
 * 
 * @author Mauro Staver
 *
 */
public class ScaleCommand implements Command {

	double factor;

	/**
	 * Updates the effective unit length of the current Turtle's state to
	 * factor*effectiveUnitLength where effectiveUnitLength is the current effective
	 * unit length and factor is a double provided in a constructor of this object.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		double effectiveUnitLength = currentState.getEffectiveUnitLength();
		currentState.setEffectiveUnitLength(factor * effectiveUnitLength);
	}

	/**
	 * Constructs and initializes a ScaleCommand object using the specified factor
	 * value used for scaling the effective unit length of the current Turtle's
	 * state.
	 * 
	 * @param factor double value used for scaling the effective unit length of the
	 *               current Turtle's state.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

}
