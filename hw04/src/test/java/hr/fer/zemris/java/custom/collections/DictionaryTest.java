package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Dictionary class.
 * 
 * @author Mauro Staver
 *
 */
class DictionaryTest {

	Dictionary<String, Integer> map = new Dictionary<>();

	@Test
	void putTest() {
		map.put("One", 1);
		map.put("Two", 2);

		assertEquals(1, map.get("One"));
		assertEquals(2, map.get("Two"));
	}

	@Test
	void putTestOverriding() {
		map.put("One", 1);
		map.put("Two", 2);
		map.put("One", 2);

		assertEquals(2, map.get("One"));
		assertEquals(2, map.get("Two"));
		assertEquals(2, map.size());
	}

	@Test
	void putTestNullForKey() {
		assertThrows(IllegalArgumentException.class, () -> map.put(null, 1));
	}

	@Test
	void putTestNullForValue() {
		map.put("One", null);
		map.put("Two", 2);

		assertEquals(null, map.get("One"));
		assertEquals(2, map.get("Two"));
	}

	@Test
	void isEmptyTest() {
		assertEquals(true, map.isEmpty());

		map.put("One", 1);
		map.put("Two", 2);

		assertEquals(false, map.isEmpty());
	}

	@Test
	void sizeTest() {
		assertEquals(0, map.size());

		map.put("One", 1);
		assertEquals(1, map.size());
		map.put("Two", 2);
		assertEquals(2, map.size());
		map.put("Three", 3);
		assertEquals(3, map.size());
	}

	@Test
	void clearTest() {
		map.put("One", 1);
		map.put("Two", 2);

		assertEquals(2, map.size());

		map.clear();

		assertEquals(0, map.size());
	}

	@Test
	void getTest() {
		map.put("One", 1);
		map.put("Two", 2);
		map.put("Four", null);

		assertEquals(1, map.get("One"));
		assertEquals(2, map.get("Two"));
		assertEquals(null, map.get("Five"));
		assertEquals(null, map.get("Four"));
	}

	@Test
	void getTestNull() {
		assertEquals(null, map.get("One"));
		assertEquals(null, map.get(null));
	}

}
