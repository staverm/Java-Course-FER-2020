package hr.fer.zemris.java.hw05.db;

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
	 * Returns this Tokens type.
	 * 
	 * @return TokenType - this Tokens type.
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Returns this Tokens value;
	 * 
	 * @return String - this Tokens value;
	 */
	public String getValue() {
		return value;
	}

}
