package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command that updates the color of the current Turtle's state.
 * 
 * @author Mauro Staver
 *
 */
public class ColorCommand implements Command {

	private Color color;

	/**
	 * Updates the color of the current Turtle's state using the color provided in
	 * the constructor of this object.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		currentState.setColor(color);
	}

	/**
	 * Constructs and initializes a new ColorCommand objects and initializes it
	 * using the given color value.
	 * 
	 * @param color Color object to be used for updating the Turtle's color.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
}
