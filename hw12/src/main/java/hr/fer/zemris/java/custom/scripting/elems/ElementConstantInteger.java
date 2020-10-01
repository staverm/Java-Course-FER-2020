package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents an integer constant in an expression.
 * 
 * @author Mauro Staver
 *
 */
public class ElementConstantInteger extends Element {

	private int value;

	/**
	 * Constructor. Constructs and initializes a new ElementConstantInteger object
	 * with the given value.
	 * 
	 * @param value used for initialization.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Constructor. Constructs and initializes a new ElementConstantInteger object
	 * with the given value.
	 * 
	 * @param String value used for initialization.
	 * @throws NumberFormatException if unable to parse.
	 */
	public ElementConstantInteger(String value) {
		this.value = Integer.parseInt(value);
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ElementConstantInteger) {
			return this.asText().equals(((ElementConstantInteger) other).asText());
		}
		return false;
	}
}
