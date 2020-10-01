package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * A node representing a command which generates some textual output
 * dynamically.
 * 
 * @author Mauro Staver
 *
 */
public class EchoNode extends Node {

	private Element[] elements;

	/**
	 * Constructor. Constructs and initializes a new EchoNode with the given Element
	 * array.
	 * 
	 * @param elements array used for initialization
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Returns the elements of the EchoNode.
	 * 
	 * @return an Element array of elements of the EchoNode.
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof EchoNode) {
			EchoNode otherNode = (EchoNode) other;
			
			//compare all elements, if all are equal return true
			for (int i = 0; i < elements.length; ++i) {
				if (!elements[i].equals(otherNode.getElements()[i])) {
					return false;
				}
			}
			
			return true;
		}
		return false;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
