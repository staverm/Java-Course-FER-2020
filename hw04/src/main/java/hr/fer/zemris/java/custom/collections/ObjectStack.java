package hr.fer.zemris.java.custom.collections;

/**
 * Class that implements a stack. It provides all basic stack operations.
 * 
 * @author staver
 *
 */
public class ObjectStack<E> {

	private ArrayIndexedCollection<E> stack;
	private int top; // position of element at the top of the stack

	/**
	 * Constructor. Initializes empty stack.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection<>();
		top = 0;
	}

	/**
	 * Checks if stack is empty.
	 * 
	 * @return true if stack is empty, otherwise false
	 */
	public boolean isEmpty() {
		if (top == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the size of the stack.
	 * 
	 * @return size of the stack as int
	 */
	public int size() {
		return top;
	}

	/**
	 * Pushes the object on the stack.
	 * 
	 * @param value value to be pushed
	 * @throws NullPointerException if the given object is null
	 */
	public void push(E value) throws NullPointerException {
		stack.add(value);
		top++;
	}

	/**
	 * Removes and returns the object at the top of the stack.
	 * 
	 * @return object at the top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public E pop() throws EmptyStackException {
		if (top == 0) {
			throw new EmptyStackException("Can't pop from an empty stack.");
		}
		E topElement = stack.get(--top);
		stack.remove(top);
		return topElement;
	}

	/**
	 * Returns the object at the top of the stack.
	 * 
	 * @return object at the top of the stack
	 * @throws EmptyStackException if stack is empty
	 */
	public E peek() throws EmptyStackException {
		if (top == 0) {
			throw new EmptyStackException("The stack is empty.");
		}
		return stack.get(top - 1);
	}

	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		stack.clear();
		top = 0;
	}
}
