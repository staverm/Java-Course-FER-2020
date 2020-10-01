package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface that models a node visitor used in Visitor design pattern.
 * 
 * @author staver
 *
 */
public interface INodeVisitor {
	/**
	 * Visit text node(performs some operation using given text node).
	 * 
	 * @param node node to be visited
	 */
	public void visitTextNode(TextNode node);
	/**
	 * Visit for loop node(performs some operation using given for loop node).
	 * 
	 * @param node node to be visited
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * Visit echo node(performs some operation using given echo node).
	 * 
	 * @param node node to be visited
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * Visit document node(performs some operation using given document node).
	 * 
	 * @param node node to be visited
	 */
	public void visitDocumentNode(DocumentNode node);
}
