package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Command that moves the Turtle.
 * 
 * @author Mauro Staver
 *
 */
public class SkipCommand implements Command {

	private double step;

	/**
	 * Moves the turtle in the direction of the current TurtleState direction member
	 * variable by step*effectiveUnitLength steps. Doesn't draw a line, think of
	 * this as a jump.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getPosition();

		Vector2D scaledDirection = currentState.getDirection().scaled(step * currentState.getEffectiveUnitLength());
		currentPosition.translate(scaledDirection);
	}

	/**
	 * Constructs and initializes a SkipCommand object using the specified step
	 * value. The length of the jump is calculated with step*effectiveUnitLength.
	 * 
	 * @param step the value used for calculating the length of the Turtles jump.
	 *             (Length = step*effectiveUnitLength)
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

}
