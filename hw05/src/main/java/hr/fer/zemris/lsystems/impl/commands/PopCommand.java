package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Command that pops an element from the Context stack.
 * 
 * @author Mauro Staver
 *
 */
public class PopCommand implements Command {

	/**
	 * Pops an element from the specified Context
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
