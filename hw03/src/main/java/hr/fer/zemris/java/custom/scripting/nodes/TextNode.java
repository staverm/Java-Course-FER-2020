package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node that holds text.
 * 
 * @author Mauro Staver
 *
 */
public class TextNode extends Node {

	private String text;

	/**
	 * Constructs and initializes a new TextNode with the specified string
	 * 
	 * @param text string used to initialize
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns the String with the text of the TextNode.
	 * 
	 * @return String with the text of the TextNode.
	 */
	public String getText() {
		return text;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof TextNode) {
			TextNode otherNode = (TextNode) other;
			return this.getText().equals(otherNode.getText());
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < text.length(); ++i) {
			if (text.charAt(i) == '{') {
				sb.append("\\{");
			} else if (text.charAt(i) == '\\') {
				sb.append("\\\\");
			} else {
				sb.append(text.charAt(i));
			}
		}
		return sb.toString();
	}
}
