package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Tests for the SmartScriptParser. Some tests were meant to generate an error.
 * 
 * @author Mauro Staver
 *
 */
class SmartScriptParserTest {

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
	void illegalOperatorTest() {
		String text = "{$= ? 2 3 $}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	void moreEndTagsThanForTest() {
		String text = "{$FOR i 2 3 $}{$END$}{$END}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void BadTagFormatTest() {
		String text = "{$= ? 2 3 $}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void illegalOperatorTestDot() {
		String text = "{$= . 2 3 $}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void tooManyForArgumentsTest() {
		String text = "{$ FOR year 1 10 1 \"3\" $}{$END$}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void tooFewForArgumentsTest() {
		String text = "{$ FOR year $}{$END$}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void IllegalvariableNameTest() {
		String text = "{$= _a21 2 3 $}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void invalidTagNameNumberTest() {
		String text = "{$1 2 3 $}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void invalidFirstForArgumentTestString() {
		String text = "{$FOR \"ILLEGAL\" 2 3 $}{$END$}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void invalidFirstForArgumentTestNumber() {
		String text = "{$FOR 3 2 3 $}{$END$}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void invalidFirstForArgumentTestOperator() {
		String text = "{$FOR * 2 3 $}{$END$}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void invalidFirstForArgumentTestFunction() {
		String text = "{$FOR @ill 2 3 $}{$END$}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void invalidForArgumentTest() {
		String text = "{$ i 2 @illegal_function 4$}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void nodeStructureTest() {
		String text = "Tekst {$FOR i 1 2 3$} tekst{$= i $} {$END$} Tekst";
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode doc = parser.getDocumentNode();
		assertEquals(3, doc.numberOfChildren());
	}

	@Test
	void illegalTagNameTextTest() {
		String text = "Ovo je tekst{$Frr i 1 2 $}{$END$}";
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	void primjer10testTextEscaping() {
		String text = readExample(10);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();// now document and document2 should be structurally
															// identical trees
		// System.out.println(document.toString());
		boolean same = document.equals(document2); // ==> "same" must be true

		assertEquals(true, same);
	}

	@Test
	void primjer12testComplex() {
		String text = readExample(12);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();// now document and document2 should be structurally
															// identical trees
		//System.out.println(document.toString());
		boolean same = document.equals(document2); // ==> "same" must be true
		
		assertEquals(true, same);
	}

	@Test
	void primjer11testComplexExample() {
		String text = readExample(11);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();// now document and document2 should be structurally
															// identical trees
		// System.out.println(document.toString());
		boolean same = document.equals(document2); // ==> "same" must be true

		assertEquals(true, same);
	}

	@Test
	void primjer1test() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}

	@Test
	void primjer2test() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}

	@Test
	void primjer3test() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}

	@Test
	void primjer4test() { // error
		String text = readExample(4);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}

	@Test
	void primjer5test() { // error
		String text = readExample(5);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}

	@Test
	void primjer6test() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(3, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(1).getClass());
	}

	@Test
	void primjer7test() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(3, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(1).getClass());
	}

	@Test
	void primjer8test() { // error
		String text = readExample(8);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(3, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}

	@Test
	void primjer9test() { // error
		String text = readExample(9);
		SmartScriptParser parser = new SmartScriptParser(text);

		DocumentNode document = parser.getDocumentNode();

		assertEquals(3, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}

}
