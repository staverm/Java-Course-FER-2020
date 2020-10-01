package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Node that represents an entire document. It is used as a root of the node
 * hierarchy.
 * 
 * @author Mauro Staver
 *
 */
public class DocumentNode extends Node {

	@Override
	public boolean equals(Object other) {
		if (other instanceof DocumentNode) {
			DocumentNode otherNode = (DocumentNode) other;

			for (int i = 0; i < this.numberOfChildren(); i++) {
				if (!this.getChild(i).equals(otherNode.getChild(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		printChildren(this, sb);
		return sb.toString();
	}

	/**
	 * Recursively appends all children nodes to the given StringBuilder.
	 * 
	 * @param parent parent node
	 * @param sb     reference to StringBuilder
	 */
	void printChildren(Node parent, StringBuilder sb) {
		for (int i = 0; i < parent.numberOfChildren(); ++i) {
			Node child = parent.getChild(i);

			if (child instanceof TextNode) { // if TextNode
				sb.append(((TextNode) child).toString());

			} else if (child instanceof ForLoopNode) { // if ForLoopNode

				sb.append("{$FOR ").append(((ForLoopNode) child).getVariable().asText() + " ")
						.append(((ForLoopNode) child).getStartExpression().asText() + " ")
						.append(((ForLoopNode) child).getEndExpression().asText());

				if (((ForLoopNode) child).getStepExpression() != null) { // if step expression exists
					sb.append(" " + ((ForLoopNode) child).getStepExpression().asText());
				}

				sb.append("$}");
				printChildren(child, sb);
				sb.append("{$END$}");

			} else if (child instanceof EchoNode) { // if EchoNode
				sb.append("{$=");

				for (Element elem : ((EchoNode) child).getElements()) {
					sb.append(" " + elem.asText());
				}

				sb.append("$}");
			}
		}
	}

}
