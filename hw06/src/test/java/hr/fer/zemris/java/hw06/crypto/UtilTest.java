package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for class Util
 * 
 * @author Mauro Staver
 *
 */
class UtilTest {

	@Test
	void byteToHexTest() {
		byte[] bytes = { 1, -82, 34 };
		String expected = "01ae22";
		assertEquals(expected, Util.byteToHex(bytes));
	}

	@Test
	void byteToHexTestOneDigitBytes() {
		byte[] bytes = { 1, 2, 3 };
		String expected = "010203";
		assertEquals(expected, Util.byteToHex(bytes));
	}

	@Test
	void byteToHexTestEmpty() {
		byte[] bytes = {};
		String expected = "";
		assertEquals(expected, Util.byteToHex(bytes));
	}

	@Test
	void byteToHexTestLong() {
		byte[] bytes = { 20, -68, 35, -92, -66, -1, 33, 42, 59, 62, 63 };
		String expected = "14bc23a4beff212a3b3e3f";
		assertEquals(expected, Util.byteToHex(bytes));
	}

	@Test
	void hexToByteTest() {
		byte[] expected = { 1, -82, 34 };
		byte[] actual = Util.hexToByte("01aE22");
		compareBytes(expected, actual);
	}

	@Test
	void hexToByteTestEmpty() {
		byte[] expected = {};
		byte[] actual = Util.hexToByte("");
		compareBytes(expected, actual);
	}

	@Test
	void hexToByteTestLong() {
		byte[] expected = { 20, -68, 35, -92, -66, -1, 33, 42, 59, 62, 63 };
		byte[] actual = Util.hexToByte("14BC23a4bEFF212a3b3E3F");
		compareBytes(expected, actual);
	}

	@Test
	void hexToByteTestOddSizedString() {
		assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("123"));
	}

	/**
	 * Helper method for comparing byte arrays. It runs assertEquals on the
	 * corresponding array elements.
	 * 
	 * @param expected Expected byte array.
	 * @param actual   actual byte array.
	 */
	private void compareBytes(byte[] expected, byte[] actual) {
		assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}
	}

}
