package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Tests for the Lexer class from problem 3.
 * 
 * @author Mauro Staver
 *
 */
class LexerTest {

	/**
	 * Helper method for reading the example inputs. Inputs are of type primjerN.txt
	 * where N is a number.
	 * 
	 * @param n number of example in the extra/ folder.
	 * @return A string read from the file
	 */
	private String readExample(int n) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
			if (is == null)
				throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
			byte[] data = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")
					.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch (IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}

	@Test
	void testTextEscape() {
		String text = readExample(10);
		Lexer lexer = new Lexer(text);

		Token correctData[] = { new Token(TokenType.TEXT, "Jedan {$Dva Gdje \\Sam\\Ja  "),
				new Token(TokenType.OPEN_TAG, null) };

		// System.out.println(lexer.nextToken().getValue().asText());
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	void exampleTest2() {
		String s = "This is sample text.{$ FOR i 1 10 1 $}{$END$}";
		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "This is sample text."),
				new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData);

		lexer.setState(LexerState.TAG);

		Token correctData2[] = { new Token(TokenType.VARIABLE, "FOR"),
				new Token(TokenType.VARIABLE, "i"),
				new Token(TokenType.INTEGER, "1"),
				new Token(TokenType.INTEGER, "10"),
				new Token(TokenType.INTEGER, "1"),
				new Token(TokenType.CLOSE_TAG, null)
				};
		checkTokenStream(lexer, correctData2);

		lexer.setState(LexerState.TEXT);

		Token correctData3[] = { new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData3);
		lexer.setState(LexerState.TAG);

		Token correctData4[] = { new Token(TokenType.VARIABLE, "END"),
				new Token(TokenType.CLOSE_TAG, null) };
		checkTokenStream(lexer, correctData4);

		lexer.setState(LexerState.TEXT);

		Token correctData5[] = { new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData5);
	}

	
	@Test
	void EchoNestedInForTest() {
		String s = "This is sample text. {$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. {$END$}";
		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "This is sample text. "),
				new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData);

		lexer.setState(LexerState.TAG);

		Token correctData2[] = { new Token(TokenType.VARIABLE, "FOR"),
				new Token(TokenType.VARIABLE, "i"),
				new Token(TokenType.INTEGER, "1"),
				new Token(TokenType.INTEGER, "10"),
				new Token(TokenType.INTEGER, "1"),
				new Token(TokenType.CLOSE_TAG, null) };
		checkTokenStream(lexer, correctData2);

		lexer.setState(LexerState.TEXT);
		Token correctData3[] = { new Token(TokenType.TEXT, " This is "),
				new Token(TokenType.OPEN_TAG, null) };
		checkTokenStream(lexer, correctData3);

		lexer.setState(LexerState.TAG);

		Token correctData4[] = { new Token(TokenType.SYMBOL, "="),
				new Token(TokenType.VARIABLE, "i"), new Token(TokenType.CLOSE_TAG, null) };
		checkTokenStream(lexer, correctData4);

		lexer.setState(LexerState.TEXT);

		Token correctData5[] = { new Token(TokenType.TEXT, "-th time this message is generated. "),
				new Token(TokenType.OPEN_TAG, null) };
		checkTokenStream(lexer, correctData5);

		lexer.setState(LexerState.TAG);

		Token correctData6[] = { new Token(TokenType.VARIABLE, "END"),
				new Token(TokenType.CLOSE_TAG, null) };
		checkTokenStream(lexer, correctData6);

		lexer.setState(LexerState.TEXT);
		Token correctData7[] = { new Token(TokenType.EOF, null) };
		checkTokenStream(lexer, correctData7);

		lexer.setState(LexerState.TAG);
	}

	@Test
	void onlyTextTest() {
		String s = "Lorem ${ ipsum $}";
		Lexer lexer = new Lexer(s);

		Token expected = new Token(TokenType.TEXT, "Lorem ${ ipsum $}");
		Token actual = lexer.nextToken();
		assertEquals(expected.getValue(), actual.getValue());
		assertEquals(expected.getType(), actual.getType());

		actual = lexer.nextToken();
		Token expected2 = new Token(TokenType.EOF, null);
		assertEquals(expected2.getValue(), actual.getValue());
		assertEquals(expected2.getType(), actual.getType());
	}

	@Test
	void forAndTextTest() {
		String s = "Text {$  FOR i 1 2 3 $} text2 abc {$END$}";
		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "Text "),
				new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData);

		lexer.setState(LexerState.TAG);

		Token correctData2[] = { new Token(TokenType.VARIABLE, "FOR"),
				new Token(TokenType.VARIABLE, "i"),
				new Token(TokenType.INTEGER, "1"),
				new Token(TokenType.INTEGER, "2"),
				new Token(TokenType.INTEGER, "3"),
				new Token(TokenType.CLOSE_TAG, null) };

		checkTokenStream(lexer, correctData2);

		lexer.setState(LexerState.TEXT);

		Token correctData3[] = { new Token(TokenType.TEXT, " text2 abc "),
				new Token(TokenType.OPEN_TAG, null)

		};

		checkTokenStream(lexer, correctData3);

		lexer.setState(LexerState.TAG);

		Token correctData4[] = { new Token(TokenType.VARIABLE, "END"),
				new Token(TokenType.CLOSE_TAG, null) };

		checkTokenStream(lexer, correctData4);
	}

	@Test
	void echoAndTextTest() {
		String s = "Text {$ = i * @sin \"0.5\"3.32 $} 3.2";
		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.TEXT, "Text "),
				new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData);

		lexer.setState(LexerState.TAG);

		Token correctData2[] = { new Token(TokenType.SYMBOL, "="),
				new Token(TokenType.VARIABLE, "i"),
				new Token(TokenType.SYMBOL, "*"),
				new Token(TokenType.FUNCTION, "sin"),
				new Token(TokenType.STRING, "0.5"),
				new Token(TokenType.DOUBLE, "3.32"), new Token(TokenType.CLOSE_TAG, null), };

		checkTokenStream(lexer, correctData2);

	}

	@Test
	void numberTokenizingTestTwoDots() {
		String s = "{$= -23.3.4 $}";
		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData);

		lexer.setState(LexerState.TAG);

		lexer.nextToken(); // reads "=" token

		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	void invalidEscapeTestString() {
		String s = "{$= \" \\s string 23 \" $}";
		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData);

		lexer.setState(LexerState.TAG);

		lexer.nextToken(); // reads "=" token

		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	void invalidEscapeTestText() {
		String s = "randomText \\b";
		Lexer lexer = new Lexer(s);

		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	void numberTokenizingTest() {
		String s = "{$= -23.45 23 24.67 -a 34.5b \"34.2\"$}";
		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData);

		lexer.setState(LexerState.TAG);

		Token correctData2[] = { new Token(TokenType.SYMBOL, "="),
				new Token(TokenType.DOUBLE, "-23.45"),
				new Token(TokenType.INTEGER, "23"),
				new Token(TokenType.DOUBLE, "24.67"),
				new Token(TokenType.SYMBOL, "-"),
				new Token(TokenType.VARIABLE, "a"),
				new Token(TokenType.DOUBLE, "34.5"),
				new Token(TokenType.VARIABLE, "b"),
				new Token(TokenType.STRING, "34.2"),
				new Token(TokenType.CLOSE_TAG, null) };
		checkTokenStream(lexer, correctData2);

		lexer.setState(LexerState.TEXT);

		Token correctData3[] = { new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData3);

	}

	@Test
	void noSpacesTokenizingTest() {
		String s = "{$= abc-27.3def@func $}";
		Lexer lexer = new Lexer(s);

		Token correctData[] = { new Token(TokenType.OPEN_TAG, null) };

		checkTokenStream(lexer, correctData);

		lexer.setState(LexerState.TAG);

		Token correctData2[] = { new Token(TokenType.SYMBOL, "="),
				new Token(TokenType.VARIABLE, "abc"),
				new Token(TokenType.DOUBLE, "-27.3"),
				new Token(TokenType.VARIABLE, "def"),
				new Token(TokenType.FUNCTION, "func"), new Token(TokenType.CLOSE_TAG, null) };
		checkTokenStream(lexer, correctData2);

		lexer.setState(LexerState.TEXT);

		Token correctData3[] = { new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData3);

	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}
}
