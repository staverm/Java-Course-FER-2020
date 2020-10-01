package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Parser that processes the input string into a hierarchy of nodes, with the
 * root node of type DocumentNode. The Parser delegates the tokenizing of the
 * input string to the Lexer and then processes those tokens into a hierarchy of
 * nodes.
 * 
 * @author Mauro Staver
 *
 */
public class SmartScriptParser {
	
	private Lexer lexer;
	private DocumentNode document;

	/**
	 * Parser Constructor. Constructs a new Parser and parses the string into a
	 * hierarchy of nodes.
	 * 
	 * @param docBody
	 */
	public SmartScriptParser(String docBody) {
		lexer = new Lexer(docBody);
		this.document = parse();
	}

	/**
	 * Parses the input string into a hierarchy of nodes. It takes tokens that the
	 * Lexer generates and uses them to create nodes.
	 * 
	 * @return DocumentNode - the root node of the hierarchy of nodes.
	 * @throws SmartScriptParserException if unable to parse the input string
	 */
	private DocumentNode parse() {

		try {
			ObjectStack stack = new ObjectStack();
			stack.push(new DocumentNode());

			Token token;
			while ((token = lexer.nextToken()).getType() != TokenType.EOF) {

				if (token.getType() == TokenType.TEXT) { // if TEXT

					TextNode textNode = new TextNode(token.getValue());

					// add as child to the last pushed node on the stack
					Node top = (Node) stack.peek();
					top.addChildNode(textNode);

				} else if (token.getType() == TokenType.OPEN_TAG) { // if open tag

					lexer.setState(LexerState.TAG);
					token = lexer.nextToken();

					// FOR tag
					if (token.getType() == TokenType.VARIABLE && token.getValue().toUpperCase().equals("FOR")) {

						token = lexer.nextToken();

						ElementVariable variable;
						if (token.getType() == TokenType.VARIABLE) {
							variable = new ElementVariable(token.getValue()); // first variable in FOR
						} else {
							throw new SmartScriptParserException(
									"Invalid 1st FOR parameter type. Expected: ElementVariable.");
						}

						Element[] forExpression = new Element[3];
						int i = 0;
						while ((token = lexer.nextToken()).getType() != TokenType.CLOSE_TAG) { // read until closing tag

							// valid types for elems of FOR tags: number, variable or string
							switch (token.getType()) {
							case VARIABLE:
								forExpression[i++] = new ElementVariable(token.getValue());
								break;
							case STRING:
								forExpression[i++] = new ElementString(token.getValue());
								break;
							case INTEGER:
								try {
									forExpression[i++] = new ElementConstantInteger(token.getValue());
								} catch (NumberFormatException ex) {
									throw new SmartScriptParserException("Can't parse number to integer.");
								}
								break;
							case DOUBLE:
								try {
									forExpression[i++] = new ElementConstantDouble(token.getValue());
								} catch (NumberFormatException ex) {
									throw new SmartScriptParserException("Can't parse number to double.");
								}
								break;
							default:
								throw new SmartScriptParserException("Invalid FOR parameter type.");
							}

						}

						if (i < 2 || i > 3) { // wrong number of FOR elements
							throw new SmartScriptParserException("Wrong number of FOR elements.");
						}

						// add as child to the last pushed node on the stack
						ForLoopNode forNode = new ForLoopNode(variable, forExpression[0], forExpression[1],
								forExpression[2]);
						Node top = (Node) stack.peek();
						top.addChildNode(forNode);

						stack.push(forNode); // push on stack

					} else if (token.getType() == TokenType.SYMBOL && token.getValue().equals("=")) { // if = tag

						ArrayIndexedCollection elements = new ArrayIndexedCollection();
						while ((token = lexer.nextToken()).getType() != TokenType.CLOSE_TAG) { // read until closing tag

							createElements(token, elements);

						}

						Element[] elementsArray = Arrays.copyOf(elements.toArray(), elements.size(), Element[].class);
						EchoNode echoNode = new EchoNode(elementsArray);

						// add as child to the last pushed node on the stack
						Node top = (Node) stack.peek();
						top.addChildNode(echoNode);

						// if END of FOR
					} else if (token.getType() == TokenType.VARIABLE && token.getValue().toUpperCase().equals("END")) {
						
						stack.pop();

						token = lexer.nextToken(); // consume closing tag
						if (token.getType() != TokenType.CLOSE_TAG) {
							throw new SmartScriptParserException("Invalid End tag");
						}

					} else { // any other tag name is illegal
						throw new SmartScriptParserException("Invalid tag name."); // if not FOR, END or =
					}

					// processed TAG segment
					lexer.setState(LexerState.TEXT);
				}
			}

			if (stack.size() == 0) {
				throw new SmartScriptParserException("More END tags than FOR");
			}

			try {
				return (DocumentNode) stack.pop();
			} catch (Exception ex) {
				throw new SmartScriptParserException("Uneven tags.");
			}

		} catch (Exception ex) {// mask any exception as SmartScriptParserException
			throw new SmartScriptParserException(ex.getMessage());
		}
	}

	/**
	 * Creates and adds the appropriate Element from the given token to the
	 * specified Collection.
	 * 
	 * @param token    Token used for creating a new Element
	 * @param elements Collection used for storing the created Element.
	 * @throws SmartScriptParserException if unable to parse the token to the
	 *                                    appropriate Element
	 */
	private void createElements(Token token, Collection elements) {
		switch (token.getType()) {
		case VARIABLE:
			elements.add(new ElementVariable(token.getValue()));
			break;
		case STRING:
			elements.add(new ElementString(token.getValue()));
			break;
		case INTEGER:
			try {
				elements.add(new ElementConstantInteger(token.getValue()));
			} catch (NumberFormatException ex) {
				throw new SmartScriptParserException("Can't parse number to integer.");
			}
			break;

		case DOUBLE:
			try {
				elements.add(new ElementConstantDouble(token.getValue()));
			} catch (NumberFormatException ex) {
				throw new SmartScriptParserException("Can't parse number to double.");
			}
			break;

		case FUNCTION:
			elements.add(new ElementFunction(token.getValue()));
			break;
		case SYMBOL:
			elements.add(new ElementOperator(token.getValue()));
			break;
		default:
			throw new SmartScriptParserException("Invalid type.");
		}
	}

	/**
	 * Returns the DocumentNode - the root node of the hierarchy of nodes.
	 * 
	 * @return document node
	 */
	public DocumentNode getDocumentNode() {
		return document;
	}
}
