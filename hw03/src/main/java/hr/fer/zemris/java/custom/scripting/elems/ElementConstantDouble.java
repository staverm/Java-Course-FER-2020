package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents a double constant in an expression.
 * 
 * @author Mauro Staver
 *
 */
public class ElementConstantDouble extends Element {

	private double value;

	/**
	 * Constructor. Constructs and initializes a new ElementConstantDouble object
	 * with the given value.
	 * 
	 * @param value used for initialization.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Constructor. Constructs and initializes a new ElementConstantDouble object
	 * with the given value.
	 * 
	 * @param String value used for initialization.
	 * @throws NumberFormatException if unable to parse.
	 */
	public ElementConstantDouble(String value) {
		this.value = Double.parseDouble(value);
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ElementConstantDouble) {
			return this.asText().equals(((ElementConstantDouble) other).asText());
		}
		return false;
	}
}
