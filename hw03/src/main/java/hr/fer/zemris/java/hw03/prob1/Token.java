package hr.fer.zemris.java.hw03.prob1;

/**
 * A class representing a text Token.
 * A Token is specified by its type and value.
 * @author Mauro Staver
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * Token constructor.
	 * Constructs and initializes this Token to the given objects.
	 * @param type type of token to be set
	 * @param value value to be set
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns this Tokens value;
	 * @return Tokens value;
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns this Tokens type.
	 * @return Tokens type.
	 */
	public TokenType getType() {
		return type;
	}
}
