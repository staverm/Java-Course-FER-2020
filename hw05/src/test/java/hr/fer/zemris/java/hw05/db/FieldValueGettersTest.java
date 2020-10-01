package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the class FieldValueGetters
 * 
 * @author Mauro Staver
 *
 */
class FieldValueGettersTest {
	
	StudentRecord record = new StudentRecord("0036514400", "Staver", "Mauro", 2);
	@Test
	void firstNameTest() {
		assertEquals("Mauro",FieldValueGetters.FIRST_NAME.get(record));
	}
	@Test
	void lastNameTest() {
		assertEquals("Staver",FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	void jmbagTest() {
		assertEquals("0036514400",FieldValueGetters.JMBAG.get(record));
	}

}
