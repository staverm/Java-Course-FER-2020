package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Lexer class.
 * 
 * @author staver
 *
 */
class LexerTest {

	@Test
	void generalTest() {
		String s = "jmbag=\"0036514400\"      AnD  lastName 	> \"Stav\""
				+ "	AND firstName LIKE \"ABC*\" and jmbag <> \"293920\"";

		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "jmbag"), new Token(TokenType.TEXT, "="),
				new Token(TokenType.STRING, "0036514400"), new Token(TokenType.TEXT, "AnD"),
				new Token(TokenType.TEXT, "lastName"), new Token(TokenType.TEXT, ">"),
				new Token(TokenType.STRING, "Stav"), new Token(TokenType.TEXT, "AND"),
				new Token(TokenType.TEXT, "firstName"), new Token(TokenType.TEXT, "LIKE"),
				new Token(TokenType.STRING, "ABC*"), new Token(TokenType.TEXT, "and"),
				new Token(TokenType.TEXT, "jmbag"), new Token(TokenType.TEXT, "<>"),
				new Token(TokenType.STRING, "293920"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	void stringLiteralTest() {
		String s = "\"literal literal\" > \"literal2 literal2\" normalTextLIKE\"literal3\"";

		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.STRING, "literal literal"), new Token(TokenType.TEXT, ">"),
				new Token(TokenType.STRING, "literal2 literal2"), new Token(TokenType.TEXT, "normalText"),
				new Token(TokenType.TEXT, "LIKE"), new Token(TokenType.STRING, "literal3"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	void likeTest() {
		String s = "textLIKELIKEtext LIKE\"Ya\"";

		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "text"), new Token(TokenType.TEXT, "LIKELIKEtext"),
				new Token(TokenType.TEXT, "LIKE"), new Token(TokenType.STRING, "Ya"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	void noSpaceTest() {
		String s = "jmbag=\"0036514400\" AnD lastName>\"Stav\"" + "	AND firstNameLIKE\"ABC*\" and jmbag<>\"293920\"";

		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "jmbag"), new Token(TokenType.TEXT, "="),
				new Token(TokenType.STRING, "0036514400"), new Token(TokenType.TEXT, "AnD"),
				new Token(TokenType.TEXT, "lastName"), new Token(TokenType.TEXT, ">"),
				new Token(TokenType.STRING, "Stav"), new Token(TokenType.TEXT, "AND"),
				new Token(TokenType.TEXT, "firstName"), new Token(TokenType.TEXT, "LIKE"),
				new Token(TokenType.STRING, "ABC*"), new Token(TokenType.TEXT, "and"),
				new Token(TokenType.TEXT, "jmbag"), new Token(TokenType.TEXT, "<>"),
				new Token(TokenType.STRING, "293920"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	void emptyStringTest() {
		String s = "";

		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	void multipleSpacesTest() {
		String s = "jmbag      =	\"0036514400\"      AnD  lastName 	> 			 \"Stav\""
				+ "	AND firstNameLIKE\"ABC*\" 			and  jmbag <>\"293920\"";

		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "jmbag"), new Token(TokenType.TEXT, "="),
				new Token(TokenType.STRING, "0036514400"), new Token(TokenType.TEXT, "AnD"),
				new Token(TokenType.TEXT, "lastName"), new Token(TokenType.TEXT, ">"),
				new Token(TokenType.STRING, "Stav"), new Token(TokenType.TEXT, "AND"),
				new Token(TokenType.TEXT, "firstName"), new Token(TokenType.TEXT, "LIKE"),
				new Token(TokenType.STRING, "ABC*"), new Token(TokenType.TEXT, "and"),
				new Token(TokenType.TEXT, "jmbag"), new Token(TokenType.TEXT, "<>"),
				new Token(TokenType.STRING, "293920"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	void operatorTest() {
		String s = "<>text>=text >text<=\"literal\"";

		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "<>"), new Token(TokenType.TEXT, "text"),
				new Token(TokenType.TEXT, ">="), new Token(TokenType.TEXT, "text"), new Token(TokenType.TEXT, ">"),
				new Token(TokenType.TEXT, "text"), new Token(TokenType.TEXT, "<="),
				new Token(TokenType.STRING, "literal"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.next();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

}
