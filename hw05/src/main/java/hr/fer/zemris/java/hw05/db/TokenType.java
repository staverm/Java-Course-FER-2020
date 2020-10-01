package hr.fer.zemris.java.hw05.db;

/**
 * Types of tokens that the Lexer generates.
 * 
 * @author Mauro Staver
 *
 */
public enum TokenType {
	TEXT, // for regular text
	STRING, // for text inside quotation marks
	EOF // for end of string 
}
