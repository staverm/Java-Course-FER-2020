package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Stack of Turtle states.
 * 
 * @author Mauro Staver
 *
 */
public class Context {
	private ObjectStack<TurtleState> stack;

	/**
	 * Creates and initializes a new Context object.
	 */
	public Context() {
		stack = new ObjectStack<>();
	}

	/**
	 * Returns the state at the top of the stack.
	 * 
	 * @return object at the top of the stack
	 * @throws EmptyStackException - if stack is empty
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}

	/**
	 * Pushes the state on the stack.
	 * 
	 * @param state state to be pushed on the stack.
	 * @throws NullPointerException - if the given state is null
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Removes the state at the top of the stack.
	 * 
	 * @throws EmptyStackException - if stack is empty
	 */
	public void popState() {
		stack.pop();
	}
}
