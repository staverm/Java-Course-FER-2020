package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents a basic element of an expression.
 * 
 * @author Mauro Staver
 *
 */
public class Element {

	/**
	 * Returns this element as a String.
	 * 
	 * @return this element as a String.
	 */
	public String asText() {
		return "";
	}

	@Override
	public boolean equals(Object other) {
		if (this.getClass() == other.getClass()) {
			Element otherElement = (Element) other;

			return this.asText().equals(otherElement.asText());
		}
		return false;
	}
}
