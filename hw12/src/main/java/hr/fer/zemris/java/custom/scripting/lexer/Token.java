package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A class representing a text Token. A Token is specified by its type and
 * value.
 * 
 * @author Mauro Staver
 *
 */
public class Token {

	private TokenType type;
	private String value;

	/**
	 * Token constructor. Constructs and initializes this Token to the specified
	 * values.
	 * 
	 * @param type  type of token to be set
	 * @param value value to be set
	 */
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns this Tokens value;
	 * 
	 * @return Tokens value;
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns this Tokens type.
	 * 
	 * @return Tokens type.
	 */
	public TokenType getType() {
		return type;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Token) {
			Token otherToken = (Token) other;

			if (type == otherToken.getType() && value.equals(otherToken.getValue())) {
				return true;
			}
		}
		return false;
	}
}
