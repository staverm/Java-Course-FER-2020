package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Program that reads from a given file that contains SmartScript text, parses
 * it into a tree and then reproduces(from a tree) its approximate original form
 * onto standard output using Visitor design pattern.
 * 
 * @author staver
 *
 */
public class TreeWriter {

	/**
	 * INodeVisitor implementation where each method is responsible for producing
	 * it's given nodes output text to standard output(writing contents of given
	 * node to standard output).
	 * 
	 * @author staver
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * Writes contents of given node as text to standard output.
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		/**
		 * Writes contents of given node as text to standard output.
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			StringBuilder sb = new StringBuilder();
			sb.append("{$FOR " + node.getVariable().asText() + " " + node.getStartExpression().asText() + " "
					+ node.getEndExpression().asText());
			if (node.getStepExpression() != null) {
				sb.append(" " + node.getStepExpression().asText());
			}
			sb.append("$}");

			System.out.print(sb.toString());
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$END$}");
		}

		/**
		 * Writes contents of given node as text to standard output.
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print("{$=");
			for (Element e : node.getElements()) {
				if(e instanceof ElementFunction) {
					System.out.print(" @" + e.asText());
				}else {
					System.out.print(" " + e.asText());
				}
			}
			System.out.print("$}");
		}

		/**
		 * Writes contents of given node as text to standard output.
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); ++i) {
				node.getChild(i).accept(this);
			}
		}

	}

	/**
	 * Main method that gets called when the program starts. Reads from a given file
	 * that contains SmartScript text, parses it into a tree and then
	 * reproduces(from a tree) its approximate original form onto standard output
	 * using Visitor design pattern.
	 * 
	 * @param args command line arguments - 1 argument is expected - path to file
	 * @throws IOException if unable to read from given file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("1 argument expected - path to file.");
			return;
		}

		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])));
		} catch (IOException e) {
			System.out.println("Unable to read from given file.");
			return;
		}
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

}
