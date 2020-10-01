package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Command that draws a line and updates the Turtle's position using the Painter
 * class.
 * 
 * @author Mauro Staver
 *
 */
public class DrawCommand implements Command {

	private double step;

	/**
	 * Draws a line (using the specified Painter object) of length
	 * step*effectiveUnitLength in the direction of the current TurtleState
	 * direction member variable. Updates the Turtle's position to the end of the
	 * line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getPosition();

		Vector2D scaledDirection = currentState.getDirection().scaled(step * currentState.getEffectiveUnitLength());
		Vector2D nextPosition = currentPosition.translated(scaledDirection);

		painter.drawLine(currentPosition.getX(), currentPosition.getY(), nextPosition.getX(), nextPosition.getY(),
				currentState.getColor(), 1);

		currentPosition.translate(scaledDirection);
	}

	/**
	 * Constructs and initializes a DrawCommand object using the specified step
	 * value. The length of the line is calculated with step*effectiveUnitLength.
	 * 
	 * @param step the value used for calculating the length of the line. (Length =
	 *             step*effectiveUnitLength)
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
}
