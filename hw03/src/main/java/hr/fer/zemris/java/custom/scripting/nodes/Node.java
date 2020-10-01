package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * General Node. Can have children nodes, and supports operations for managing
 * children nodes.
 * 
 * @author Mauro Staver
 *
 */
public class Node {

	private ArrayIndexedCollection array;

	/**
	 * Adds the given child Node to an internal array.
	 * 
	 * @param child Node to add
	 */
	public void addChildNode(Node child) {
		if (array == null) {
			array = new ArrayIndexedCollection();
		}

		array.add(child);
	}

	/**
	 * Returns a number of direct children.
	 * 
	 * @return a number of direct children.
	 */
	public int numberOfChildren() {
		if (array == null) {
			return 0;
		}
		return array.size();
	}

	/**
	 * Returns the child node at the specified position.
	 * 
	 * @param index index of the child to return
	 * @return the child node at the specified position.
	 * @throws IndexOutOfBoundsException if index is not in interval [0, size-1]
	 */
	public Node getChild(int index) {
		return (Node) array.get(index);
	}

}
