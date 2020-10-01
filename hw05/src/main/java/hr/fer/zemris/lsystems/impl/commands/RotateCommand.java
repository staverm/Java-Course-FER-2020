package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Command that rotates the direction vector of the current Turtle's state.
 * 
 * @author Mauro Staver
 *
 */
public class RotateCommand implements Command {

	private double angle; // angle in degrees

	/**
	 * Rotates the direction vector of the current Turtle's state by the angle
	 * provided in the constructor of this object.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D direction = currentState.getDirection();
		direction.rotate(angle); // rotate
	}

	/**
	 * Constructs and initializes a RotateCommand object using the specified angle
	 * value used for rotating the direction vector of the current Turtle's state.
	 * 
	 * @param angle angle in degrees used for rotating the direction vector of the
	 *              current Turtle's state.
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

}
