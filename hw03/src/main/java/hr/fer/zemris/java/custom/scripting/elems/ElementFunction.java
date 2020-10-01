package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents a function in an expression.
 * 
 * @author Mauro Staver
 *
 */
public class ElementFunction extends Element {

	private String name;

	/**
	 * Constructor. Constructs and initializes a new ElementFunction object with the
	 * given value.
	 * 
	 * @param value used for initialization.
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ElementFunction) {
			return this.asText().equals(((ElementFunction) other).asText());
		}
		return false;
	}
}
