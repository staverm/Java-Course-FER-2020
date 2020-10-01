package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the ConditionalExpression class.
 * 
 * @author Mauro Staver
 *
 */
class ConditionalExpressionTest {
	
	ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Stav*", ComparisonOperators.LIKE);
	StudentRecord record = new StudentRecord("0036514400", "Staver", "Mauro", 2);
	
	@Test
	void generalTest() {
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),  // returns lastName from given record
				expr.getStringLiteral()             // returns "Stav*"
				);
		assertEquals(true, recordSatisfies);
	}
	
	@Test
	void getComparisonOperatorTest() {
		boolean recordSatisfies = expr.getComparisonOperator().satisfied("ABB", "A*B");
		assertEquals(true, recordSatisfies);
	}
	
	@Test
	void getStringLiteralTest() {
		assertEquals("Stav*", expr.getStringLiteral());
	}
	
	@Test
	void getFieldGetterTest() {
		assertEquals("Staver", expr.getFieldGetter().get(record));
	}

}
