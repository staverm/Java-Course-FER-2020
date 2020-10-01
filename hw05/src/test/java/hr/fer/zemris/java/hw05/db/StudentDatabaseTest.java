package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests for the StudentDatabase class.
 * 
 * @author Mauro Staver
 *
 */
class StudentDatabaseTest {
	
	List<String> records = Arrays.asList("0000000001	Akšamović	Marin	2", "0000000002	Bakamović	Petra	3",
			"0000000003	Bosnić	Andrea	4", "0000000031	Krušelj Posavec	Bojan	4");
	StudentDatabase db = new StudentDatabase(records);

	@Test
	void forJMBAGTest() {
		StudentRecord expected1 = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		assertEquals(expected1, db.forJMBAG("0000000001"));
		assertEquals(expected1.getFirstName(), db.forJMBAG("0000000001").getFirstName());
		assertEquals(expected1.getLastName(), db.forJMBAG("0000000001").getLastName());
		assertEquals(expected1.getFinalGrade(), db.forJMBAG("0000000001").getFinalGrade());
		assertEquals(expected1.getJmbag(), db.forJMBAG("0000000001").getJmbag());
		assertEquals(null, db.forJMBAG("1002003002"));
	}
	
	@Test
	void filterTestCustomFilter() {
		List<StudentRecord> list = db.filter((record) -> {
			if(record.getJmbag().equals("0000000001")) {
				return true;
			}
			return false;
		});

		assertEquals(1, list.size());
		assertEquals("0000000001", list.get(0).getJmbag());
	}

	@Test
	void filterTestAcceptEverything() {
		List<StudentRecord> list = db.filter((record) -> {
			return true;
		});

		assertEquals(4, list.size());
	}

	@Test
	void filterTestAcceptNothing() {
		List<StudentRecord> list = db.filter((record) -> {
			return false;
		});

		assertEquals(0, list.size());
	}

	@Test
	void ConstructorTestSpaceInLastName() {
		List<String> records = Arrays.asList("0000000005   	Akšamović  	Balde   	Marin 	 	2");
		db = new StudentDatabase(records);
		StudentRecord expected1 = new StudentRecord("0000000005", "Akšamović Balde", "Marin", 2);
		compare(expected1, "0000000005");
	}

	@Test
	void ConstructorTestMultipleWhitespace() {
		List<String> records = Arrays.asList("0000000001   	Akšamović	 	Marin 		2");
		db = new StudentDatabase(records);
		StudentRecord expected1 = new StudentRecord("0000000001", "Akšamović", "Marin", 2);

		compare(expected1, "0000000001");
	}

	@Test
	void ConstructorTestGeneral() {
		List<String> records = Arrays.asList("0000000001	Akšamović	Marin	2", "0000000002	Bakamović	Petra	3",
				"0000000003	Bosnić	Andrea	4", "0000000031	Krušelj Posavec	Bojan	4");
		db = new StudentDatabase(records);
		StudentRecord expected1 = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		StudentRecord expected2 = new StudentRecord("0000000002", "Bakamović", "Petra", 3);
		StudentRecord expected3 = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		StudentRecord expected4 = new StudentRecord("0000000031", "Krušelj Posavec", "Bojan", 4);

		compare(expected1, "0000000001");
		compare(expected2, "0000000002");
		compare(expected3, "0000000003");
		compare(expected4, "0000000031");	
	}
	
	/**
	 * Helper method for comparing and testing StudentRecords.
	 * @param expected expected StudentRecord
	 * @param jmbag jmbag of the StudentRecord which will be retrieved from the database.
	 */
	private void compare(StudentRecord expected, String jmbag) {
		assertEquals(expected, db.forJMBAG(jmbag));
		assertEquals(expected.getFirstName(), db.forJMBAG(jmbag).getFirstName());
		assertEquals(expected.getLastName(), db.forJMBAG(jmbag).getLastName());
		assertEquals(expected.getFinalGrade(), db.forJMBAG(jmbag).getFinalGrade());
		assertEquals(expected.getJmbag(), db.forJMBAG(jmbag).getJmbag());
	}
}
