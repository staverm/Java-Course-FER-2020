package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents text outside of tags. It's not a part of an expression.
 * 
 * @author Mauro Staver
 *
 */
public class ElementText extends Element {

	private String value;

	/**
	 * Constructor. Constructs and initializes a new ElementText object with the
	 * given value.
	 * 
	 * @param value used for initialization.
	 */
	public ElementText(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return value;
	}

}
