package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents a variable in an expression.
 * 
 * @author Mauro Staver
 *
 */
public class ElementVariable extends Element {

	private String name;

	/**
	 * Constructor. Constructs and initializes a new ElementVariable object with the
	 * given value.
	 * 
	 * @param value used for initialization.
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ElementVariable) {
			ElementVariable otherElement = (ElementVariable) other;

			return this.asText().equals(otherElement.asText());
		}
		return false;
	}
}
