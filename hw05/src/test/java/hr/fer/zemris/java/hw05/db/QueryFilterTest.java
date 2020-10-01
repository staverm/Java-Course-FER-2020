package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests for the class QueryFilter
 * 
 * @author Mauro Staver
 *
 */
class QueryFilterTest {

	List<ConditionalExpression> list = new ArrayList<>();
	StudentRecord record = new StudentRecord("0036514400", "Staver", "Mauro", 2);
	StudentRecord record2 = new StudentRecord("0036614400", "Staver", "Cauro", 3);

	@Test
	void trueTest() {
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "B", ComparisonOperators.GREATER));
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "*r", ComparisonOperators.LIKE));
		QueryFilter filter = new QueryFilter(list);
		assertEquals(true, filter.accepts(record));
		assertEquals(true, filter.accepts(record2));
	}

	@Test
	void falseTest() {
		list.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "B", ComparisonOperators.LESS));
		list.add(new ConditionalExpression(FieldValueGetters.LAST_NAME, "*r", ComparisonOperators.LIKE));
		QueryFilter filter = new QueryFilter(list);
		assertEquals(false, filter.accepts(record));
		assertEquals(false, filter.accepts(record));
	}

	@Test
	void likeTest() {
		list.add(new ConditionalExpression(FieldValueGetters.JMBAG, "00365*", ComparisonOperators.LIKE));
		QueryFilter filter = new QueryFilter(list);
		assertEquals(true, filter.accepts(record));
		assertEquals(false, filter.accepts(record2));
	}

}
