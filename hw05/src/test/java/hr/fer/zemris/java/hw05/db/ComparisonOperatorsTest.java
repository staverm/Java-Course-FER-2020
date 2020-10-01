package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the class ComparisonOperators
 * 
 * @author Mauro Staver
 *
 */
class ComparisonOperatorsTest {

	private IComparisonOperator oper;

	@Test
	void likeTest() {
		oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
		assertEquals(true, oper.satisfied("AA", "AA*"));
		assertEquals(true, oper.satisfied("AAAAA", "AA*"));
		assertEquals(true, oper.satisfied("A", "*"));
		assertEquals(false, oper.satisfied("AAA", "AAAA*"));
		assertEquals(true, oper.satisfied("ABCDEFHB", "A*B"));
		assertEquals(true, oper.satisfied("ABCDEFHAAB", "*AAB"));
		assertEquals(true, oper.satisfied("A", "*A"));
		assertEquals(true, oper.satisfied("AA", "*A"));
		assertEquals(true, oper.satisfied("BADA", "*A"));
		assertEquals(false, oper.satisfied("LIKER", "LIK*E"));
		assertEquals(true, oper.satisfied("LIKERRRE", "LIK*E"));
		assertEquals(true, oper.satisfied("AB", "*AB"));
		assertEquals(false, oper.satisfied("AB", "AAAB"));
		assertEquals(true, oper.satisfied("AB", "AB"));
	}
	
	

	@Test
	void likeTestMultipleWildcards() {
		oper = ComparisonOperators.LIKE;
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("AB", "AB**"));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("ABBA", "AB*A*"));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("ABBBBA", "A*B*A"));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("ABBA", "*AB*"));
	}

	@Test
	void lessTest() {
		oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
		assertEquals(false, oper.satisfied("", ""));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
	}

	@Test
	void lessOrEqualsTest() {
		oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
		assertEquals(true, oper.satisfied("", ""));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}

	@Test
	void greaterTest() {
		oper = ComparisonOperators.GREATER;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
		assertEquals(false, oper.satisfied("", ""));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
	}

	@Test
	void greaterOrEqualsTest() {
		oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
		assertEquals(true, oper.satisfied("", ""));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}

	@Test
	void equalsTest() {
		oper = ComparisonOperators.EQUALS;
		assertEquals(false, oper.satisfied("Ana", "Jasna"));
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
		assertEquals(true, oper.satisfied("", ""));
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}

	@Test
	void notEqualsTest() {
		oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
		assertEquals(true, oper.satisfied("Jasna", "Ana"));
		assertEquals(false, oper.satisfied("", ""));
		assertEquals(false, oper.satisfied("Ana", "Ana"));
	}

}
