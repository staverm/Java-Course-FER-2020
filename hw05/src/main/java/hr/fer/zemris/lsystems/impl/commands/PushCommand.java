package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command that creates a copy of the current state and pushes it on the Context
 * stack.
 * 
 * @author Mauro Staver
 *
 */
public class PushCommand implements Command {

	/**
	 * Creates a copy of the current state and pushes it on the specified Context.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		ctx.pushState(currentState.copy());
	}

}
