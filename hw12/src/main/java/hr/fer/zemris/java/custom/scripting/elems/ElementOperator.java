package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents an operator in an expression.
 * 
 * @author Mauro Staver
 *
 */
public class ElementOperator extends Element {

	private String symbol;

	/**
	 * Constructor. Constructs and initializes a new ElementOperator object with the
	 * given value.
	 * 
	 * @param value used for initialization.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof ElementOperator) {
			return this.asText().equals(((ElementOperator) other).asText());
		}
		return false;
	}
}
