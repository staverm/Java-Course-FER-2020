package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class QueryParserTest {

	QueryParser parser;
	
	@Test
	void getQueryTest() {
		String s = "jmbag=\"0036514400\"      AnD  lastName 	>\"Stav\""
				+ "	AND firstName LIKE \"ABC*\" and jmbag<>\"293920\" and jmbagLIKE\"B*\"";
		parser = new QueryParser(s);
		List<ConditionalExpression> list = parser.getQuery();

		compare(FieldValueGetters.JMBAG, ComparisonOperators.EQUALS, "0036514400", list.get(0));
		compare(FieldValueGetters.LAST_NAME, ComparisonOperators.GREATER, "Stav", list.get(1));
		compare(FieldValueGetters.FIRST_NAME, ComparisonOperators.LIKE, "ABC*", list.get(2));
		compare(FieldValueGetters.JMBAG, ComparisonOperators.NOT_EQUALS, "293920", list.get(3));
		compare(FieldValueGetters.JMBAG, ComparisonOperators.LIKE, "B*", list.get(4));
		assertEquals(5, list.size());
	}
	
	@Test
	void getQueryTestInvalidOperator() {
		String s = "jmbag==\"0036514400\"      AnD  lastName 	> \"Stav\""
				+ "	AND firstName LIKE \"ABC*\" and jmbag<>\"293920\"";
		assertThrows(IllegalArgumentException.class, () -> new QueryParser(s));
	}
	
	@Test
	void getQueryTestInvalidAttribute() {
		String s = "jmbag=\"0036514400\"      AnD  lasttName 	> \"Stav\""
				+ "	AND firstName LIKE \"ABC*\" and jmbag<>\"293920\"";
		assertThrows(IllegalArgumentException.class, () -> new QueryParser(s));
	}
	
	@Test
	void getQueryTestInvalidStringLiteral() {
		String s = "jmbag=\"0036514400\"      AnD  lasttName 	> abc"
				+ "	AND firstName LIKE \"ABC*\" and jmbag<>\"293920\"";
		assertThrows(IllegalArgumentException.class, () -> new QueryParser(s));
	}
	

	@Test
	void isDirectQueryTestTrue() {
		String s = "jmbag= 	\"0036514400\" ";
		parser = new QueryParser(s);
		assertEquals(true, parser.isDirectQuery());
	}
	
	@Test
	void isDirectQueryTestFalse() {
		String s = "jmbag= 	\"0036514400\" and lastName LIKE\"B*\" ";
		parser = new QueryParser(s);
		assertEquals(false, parser.isDirectQuery());
	}
	
	@Test
	void getQueriedJMBAGtest() {
		String s = "jmbag= 	\"0036514400\" ";
		parser = new QueryParser(s);
		assertEquals("0036514400", parser.getQueriedJMBAG());
	}
	
	@Test
	void getQueriedJMBAGtestException() {
		String s = "jmbag= 	\"0036514400\" and lastName LIKE\"B*\" ";
		parser = new QueryParser(s);
		assertThrows(IllegalStateException.class, () -> parser.getQueriedJMBAG());
	}
	
	/**
	 * Helper function for comparing and testing Conditional Expressions.
	 * 
	 * @param valueGetter expected IFieldValueGetter
	 * @param operator expected IComparisonOperator
	 * @param literal expected string literal
	 * @param expr actual ConditionalExpression
	 */
	void compare(IFieldValueGetter valueGetter, IComparisonOperator operator, String literal, ConditionalExpression expr) {
		assertEquals(valueGetter.getClass(), expr.getFieldGetter().getClass());
		assertEquals(operator.getClass(), expr.getComparisonOperator().getClass());
		assertEquals(literal, expr.getStringLiteral());
	}
	

}
