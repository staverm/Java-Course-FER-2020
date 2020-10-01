package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Node that represents a For loop. Holds 4 variables that represent the for
 * loop: variable, startExpression, endExpression and stepExpression.
 * 
 * @author Mauro Staver
 *
 */
public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression; // can be null

	/**
	 * Constructor. Creates and initializes a new ForLoopNode with the given
	 * arguments.
	 * 
	 * @param variable        variable of the for loop
	 * @param startExpression start expression of the for loop
	 * @param endExpression   end expression of the for loop
	 * @param stepExpression  step expression of the for loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Constructor. Creates and initializes a new ForLoopNode with the given
	 * arguments using null for stepExpression.
	 * 
	 * @param variable        variable of the for loop
	 * @param startExpression start expression of the for loop
	 * @param endExpression   end expression of the for loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ForLoopNode) {
			ForLoopNode otherNode = (ForLoopNode) other;

			//if variable, start and end expressions are equal
			if (variable.equals(otherNode.getVariable()) && startExpression.equals(otherNode.getStartExpression())
					&& endExpression.equals(otherNode.getEndExpression())) { 
				
				//if both step expressions are null or equal
				if ((stepExpression == null && otherNode.getStepExpression() == null)
						|| stepExpression != null && stepExpression.equals(otherNode.getStepExpression())) {
					
					//compare the nodes children, if all are equal return true
					for (int i = 0; i < this.numberOfChildren(); i++) {
						if (!this.getChild(i).equals(otherNode.getChild(i))) {
							return false;
						}
					}

					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the variable of the for loop.
	 * 
	 * @return the variable of the for loop
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns the start expression of the for loop.
	 * 
	 * @return the start expression of the for loop
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns the end expression of the for loop.
	 * 
	 * @return the end expression of the for loop
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns the step expression of the for loop.
	 * 
	 * @return the step expression of the for loop
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
