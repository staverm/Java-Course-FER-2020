package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that implements an engine for SmartScript. It has an
 * <code>execute()</code> method, which executes the document specified by its
 * tree structure(DocumentNode - created by parsing SmartScript text).
 * 
 * @author staver
 *
 */
public class SmartScriptEngine {

	private DocumentNode documentNode; // parsed tree - represents document to be executed
	private RequestContext requestContext; // used to output generated text
	private ObjectMultistack multistack = new ObjectMultistack(); // used to store variables
	private INodeVisitor visitor = new INodeVisitor() { // node visitor that actually executes nodes

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("Error while writing text node contents to output stream.");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variableName = node.getVariable().asText();
			multistack.push(variableName, new ValueWrapper(node.getStartExpression().asText()));

			while (multistack.peek(variableName).numCompare(node.getEndExpression().asText()) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}

				// increment variable value
				multistack.peek(variableName).add(node.getStepExpression().asText());
			}

			multistack.pop(variableName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<ValueWrapper> tempStack = new Stack<>();
			for (Element e : node.getElements()) {
				if (e instanceof ElementConstantDouble || e instanceof ElementConstantInteger) {
					tempStack.push(new ValueWrapper(e.asText()));
				} else if (e instanceof ElementString) {
					tempStack.push(new ValueWrapper(((ElementString) e).getValue()));
				} else if (e instanceof ElementVariable) {
					// push to temp stack variable peeked from object stack
					tempStack.push(new ValueWrapper(multistack.peek(e.asText()).getValue()));
				} else if (e instanceof ElementOperator) {
					tempStack.push(applyOperator(tempStack.pop(), tempStack.pop(), e.asText()));
				} else if (e instanceof ElementFunction) {
					performFunction(e.asText(), tempStack);
				}

			}

			for (ValueWrapper vw : tempStack) { // iterates in reverse order
				try {
					requestContext.write(vw.getValue().toString());
				} catch (IOException e1) {
					System.out.println("Error while writing echo node contents to output stream.");
				}
			}
		}

		/**
		 * Performs a function specified by given function name. Arguments are popped of
		 * the given stack, and the result is pushed back on the stack.
		 * 
		 * @param name  function name
		 * @param stack stack to be used(for getting arguments, and writing result)
		 */
		private void performFunction(String name, Stack<ValueWrapper> stack) {
			switch (name) {
			case "sin":
				stack.peek().add(Integer.valueOf(0)); // to transform wrapped value to Number
				Object value = stack.peek().getValue();
				stack.peek().setValue(Math.sin(Math.toRadians(((Number) value).doubleValue())));
				return;
			case "decfmt":
				DecimalFormat f = new DecimalFormat(stack.pop().getValue().toString());
				stack.peek().add(Integer.valueOf(0)); // to transform wrapped value to Number
				stack.peek().setValue(f.format(stack.peek().getValue()));
				return;
			case "dup":
				stack.push(new ValueWrapper(stack.peek().getValue()));
				return;
			case "swap":
				ValueWrapper a = stack.pop();
				ValueWrapper b = stack.pop();
				stack.push(a);
				stack.push(b);
				return;
			case "setMimeType":
				requestContext.setMimeType(stack.pop().getValue().toString());
				return;
			case "paramGet":
				ValueWrapper defVal = stack.pop();
				String val = requestContext.getParameter(stack.pop().getValue().toString());
				stack.push(val == null ? defVal : new ValueWrapper(val));
				return;
			case "pparamGet":
				ValueWrapper PdefVal = stack.pop();
				String Pval = requestContext.getPersistentParameter(stack.pop().getValue().toString());
				stack.push(Pval == null ? PdefVal : new ValueWrapper(Pval));
				return;
			case "pparamSet":
				requestContext.setPersistentParameter(stack.pop().getValue().toString(),
						stack.pop().getValue().toString());
				return;
			case "pparamDel":
				requestContext.removePersistentParameter(stack.pop().getValue().toString());
				return;
			case "tparamGet":
				ValueWrapper TdefVal = stack.pop();
				String Tval = requestContext.getTemporaryParameter(stack.pop().getValue().toString());
				stack.push(Tval == null ? TdefVal : new ValueWrapper(Tval));
				return;
			case "tparamSet":
				requestContext.setTemporaryParameter(stack.pop().getValue().toString(),
						stack.pop().getValue().toString());
				return;
			case "tparamDel":
				requestContext.removeTemporaryParameter(stack.pop().getValue().toString());
				return;
			}
		}

		/**
		 * Applies the given operator on the values of the given ValueWrapper objects.
		 * First argument is used as left operand, and second as right. First argument's
		 * value will be changed to the result of the operation. The method returns this
		 * ValueWrapper(first argument) with updated value.
		 * 
		 * @param val1     left operand (value gets updated to result)
		 * @param val2     right operand (value remains the same)
		 * @param operator string: name of the operand - supported operands are
		 *                 "+","-","*","/"
		 * @return ValueWrapper object given as first argument with value set to result
		 *         of the operation
		 */
		private ValueWrapper applyOperator(ValueWrapper val1, ValueWrapper val2, String operator) {
			switch (operator) {
			case "+":
				val1.add(val2.getValue());
				return val1;
			case "-":
				val1.subtract(val2.getValue());
				return val1;
			case "*":
				val1.multiply(val2.getValue());
				return val1;
			case "/":
				val1.divide(val2.getValue());
				return val1;
			}
			throw new IllegalArgumentException("Unsupported operation!");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	};

	/**
	 * SmartScriptEngine constructor.
	 * 
	 * @param documentNode   document node of document to be executed
	 * @param requestContext RequestContext object to be used in execution(all
	 *                       output is being sent to this object's output stream)
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes the document.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
