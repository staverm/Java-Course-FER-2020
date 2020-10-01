package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Command to be executed by a Turtle.
 * 
 * @author Mauro Staver
 *
 */
public interface Command {

	/**
	 * Executes the command.
	 * 
	 * @param ctx     Context stack of Turtle states.
	 * @param painter Painter object used for drawing lines.
	 */
	void execute(Context ctx, Painter painter);
}
