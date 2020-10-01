package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum with the possible token types.
 * 
 * @author Mauro Staver
 *
 */
public enum TokenType {
	EOF,
	TEXT,
	FUNCTION,
	STRING,
	SYMBOL,
	VARIABLE,
	DOUBLE,
	INTEGER,
	OPEN_TAG,
	CLOSE_TAG,
}
