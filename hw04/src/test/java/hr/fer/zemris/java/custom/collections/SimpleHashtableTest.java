package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests for the SimpleHashtable class.
 * 
 * @author Mauro Staver
 *
 */
class SimpleHashtableTest {

	SimpleHashtable<String, Integer> table = new SimpleHashtable<>();

	@Test
	void putTestEmpty() {
		table.put("One", 1);

		assertEquals(1, table.get("One"));
		assertEquals(true, table.containsKey("One"));
		assertEquals(true, table.containsValue(1));
		assertEquals(1, table.size());
	}

	@Test
	void putTestWithEntries() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);

		assertEquals(3, table.get("Kristina"));
		assertEquals(2, table.get("Jasna"));
		assertEquals(1, table.get("Ivana"));
		assertEquals(true, table.containsKey("Kristina"));
		assertEquals(true, table.containsValue(3));
		assertEquals(3, table.size());
	}

	@Test
	void putTestNull() {
		table.put("Ivana", 1);
		table.put("Jasna", null);

		assertEquals(null, table.get("Jasna"));
		assertEquals(1, table.get("Ivana"));
		assertEquals(true, table.containsKey("Jasna"));
		assertEquals(true, table.containsValue(null));
		assertEquals(2, table.size());
	}

	@Test
	void putTestWithEntriesOverride() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Jasna", 3);

		assertEquals(3, table.get("Jasna"));
		assertEquals(1, table.get("Ivana"));
		assertEquals(true, table.containsValue(3));
		assertEquals(false, table.containsValue(2));
		assertEquals(2, table.size());
	}

	@Test
	void putTestFirstEntryOverride() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Ivana", 3);

		assertEquals(3, table.get("Ivana"));
		assertEquals(false, table.containsValue(1));
		assertEquals(true, table.containsValue(3));
		assertEquals(2, table.size());
	}

	@Test
	void putTestOverrideInMiddleOfList() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Jasna", 4);

		assertEquals(4, table.get("Jasna"));
		assertEquals(true, table.containsValue(4));
		assertEquals(false, table.containsValue(2));
		assertEquals(3, table.size());
	}

	@Test
	void getTest() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", 4);

		assertEquals(1, table.get("Ivana"));
		assertEquals(2, table.get("Jasna"));
		assertEquals(3, table.get("Kristina"));
		assertEquals(4, table.get("Ante"));
		assertEquals(null, table.get("NemaMe"));
	}

	@Test
	void getTestNull() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);

		assertEquals(null, table.get(null));
	}

	@Test
	void getTestWrongType() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);

		assertEquals(null, table.get(Integer.valueOf(5)));
	}

	@Test
	void getTestEmptyTable() {
		assertEquals(null, table.get("Ivana"));
		assertEquals(null, table.get(2));
		assertEquals(null, table.get(null));
	}

	@Test
	void removeTestNull() {
		table.put("Ivana", 1);
		table.remove(null);
		assertEquals(1, table.get("Ivana"));
		assertEquals(1, table.size());
	}

	@Test
	void removeTestEmptyTable() {
		table.remove("Ivana");
		assertEquals(0, table.size());
	}

	@Test
	void removeTestWrongType() {
		table.put("Ivana", 1);
		table.remove(Integer.valueOf(5));
		assertEquals(1, table.get("Ivana"));
		assertEquals(1, table.size());
	}

	@Test
	void removeTestInMiddleOfList() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", 4);
		table.remove("Jasna");

		assertEquals(3, table.get("Kristina"));
		assertEquals(false, table.containsKey("Jasna"));
		assertEquals(false, table.containsValue(2));
		assertEquals(3, table.get("Kristina"));
		assertEquals(3, table.size());
	}

	@Test
	void removeTestBegginingOfList() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", 4);
		table.remove("Ivana");

		assertEquals(false, table.containsKey("Ivana"));
		assertEquals(false, table.containsValue(1));
		assertEquals(3, table.get("Kristina"));
		assertEquals(3, table.size());
	}

	@Test
	void removeTestEndOfList() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", 4);
		table.remove("Kristina");

		assertEquals(false, table.containsKey("Kristina"));
		assertEquals(false, table.containsValue(3));
		assertEquals(2, table.get("Jasna"));
		assertEquals(3, table.size());
	}

	@Test
	void containsKeyTestNull() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		assertEquals(false, table.containsKey(null));
	}

	@Test
	void containsKeyTestWrongType() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		assertEquals(false, table.containsKey(Integer.valueOf(1)));
	}

	@Test
	void containsKeyTestEmpty() {
		assertEquals(false, table.containsKey("Jasna"));
	}

	@Test
	void containsKeyTest() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		assertEquals(true, table.containsKey("Jasna"));
		assertEquals(true, table.containsKey("Kristina"));
		assertEquals(true, table.containsKey("Ivana"));
		assertEquals(false, table.containsKey("Jasna2"));
		assertEquals(false, table.containsKey("Ante"));
	}

	@Test
	void containsValueTestNull() {
		table.put("Ivana", 1);
		assertEquals(false, table.containsValue(null));
		table.put("Jasna", null);
		assertEquals(true, table.containsValue(null));
	}

	@Test
	void containsValueTestWrongType() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		assertEquals(false, table.containsValue("Jasna"));
	}

	@Test
	void containsValueTestEmpty() {
		assertEquals(false, table.containsValue(2));
	}

	@Test
	void containsValueTest() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", null);

		assertEquals(true, table.containsValue(1));
		assertEquals(true, table.containsValue(2));
		assertEquals(true, table.containsValue(3));
		assertEquals(true, table.containsValue(null));
		assertEquals(false, table.containsValue(4));
	}

	@Test
	void toStringTest() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", null);

		String expected = "[Ivana=1, Kristina=3, Ante=null, Jasna=2]";
		assertEquals(expected, table.toString());
	}

	@Test
	void iteratorTestRemoveAll() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", null);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();

		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}

		assertEquals(0, table.size());
	}

	@Test
	void iteratorTestNoSuchElementException() {
		table.put("Ivana", 1);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();

		iter.next();
		assertThrows(NoSuchElementException.class, () -> iter.next());
	}

	@Test
	void iteratorTestModificationException() {
		table.put("Ivana", 1);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();

		iter.next();
		table.put("Jasna", 1);
		assertThrows(ConcurrentModificationException.class, () -> iter.hasNext());
	}

	@Test
	void iteratorTestIllegalState() {
		table.put("Ivana", 1);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();

		iter.next();
		iter.remove();
		assertThrows(IllegalStateException.class, () -> iter.remove());
	}

	@Test
	void iteratorTestRemove() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", null);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();

		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Kristina")) {
				iter.remove();
			}
		}

		assertEquals(3, table.size());
		assertEquals(false, table.containsKey("Kristina"));
	}

	@Test
	void iteratorTest() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.put("Ante", null);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();

		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Kristina") || pair.getKey().equals("Ante")) {
				iter.remove();
			}
		}

		assertEquals(2, table.size());
		assertEquals(false, table.containsKey("Kristina"));
		assertEquals(false, table.containsKey("Ante"));
		assertEquals(true, table.containsKey("Ivana"));
		assertEquals(true, table.containsKey("Jasna"));
	}

	@Test
	void clearTest() {
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		table.put("Kristina", 3);
		table.clear();
		assertEquals(0, table.size());
		assertEquals(false, table.containsKey("Kristina"));
		table.put("Kristina", 2);
		table.put("Ante", null);

		assertEquals(true, table.containsKey("Kristina"));
		assertEquals(false, table.containsKey("Ivana"));
		assertEquals(false, table.containsKey("Jasna"));
		assertEquals(2, table.size());
	}

	@Test
	void isEmptyTest() {
		assertEquals(true, table.isEmpty());
		table.put("Ivana", 23);
		assertEquals(false, table.isEmpty());
	}

	@Test
	void sizeTest() {
		assertEquals(0, table.size());
		table.put("Ivana", 1);
		table.put("Jasna", 2);
		assertEquals(2, table.size());
		table.put("Ivana", 3);
		table.put("Jasna", 4);
		assertEquals(2, table.size());
		table.put("Kristina", 2);
		table.put("Ante", null);
		assertEquals(4, table.size());
		table.remove("Ivana");
		assertEquals(3, table.size());
		table.remove("Jasna");
		assertEquals(2, table.size());
		table.remove("Jasna");
		assertEquals(2, table.size());
	}

	@Test
	void defaultConstructorTest() {
		SimpleHashtable<String, Integer> table2 = new SimpleHashtable<>();
		assertEquals(0, table2.size());
		assertEquals(true, table2.isEmpty());
	}

	@Test
	void constructorTestIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0));
	}

	@Test
	void constructorTest() {
		SimpleHashtable<String, Integer> table2 = new SimpleHashtable<>(17);
		assertEquals(0, table2.size());
		assertEquals(true, table2.isEmpty());
	}

	@Test
	void iteratorGeneralTest() {
		table.put("Ivana", 1); // index 7
		table.put("Jasna", 2); // index 15
		table.put("Kristina", 3); // index 7
		table.put("Kanon", 1); // index 7
		table.put("Ante", null); // index 14

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();

		assertEquals("Ivana",iter.next().getKey()); // Ivana
		assertEquals("Kristina",iter.next().getKey());
		iter.remove(); // remove Kristina
		assertEquals("Kanon",iter.next().getKey());
		iter.remove(); // remove Kanon

		assertEquals(3, table.size());
		assertEquals(false, table.containsKey("Kanon"));
		assertEquals(false, table.containsKey("Kristina"));
		assertEquals(true, table.containsKey("Ante"));
		assertEquals(true, table.containsKey("Ivana"));
		assertEquals(true, table.containsKey("Jasna"));

		assertEquals("Ante",iter.next().getKey());
		assertEquals(true, table.containsKey("Ante"));
		iter.remove();
		assertEquals(false, table.containsKey("Ante"));
		assertEquals("Jasna",iter.next().getKey());
		assertEquals(true, table.containsKey("Jasna"));
		iter.remove();
		assertEquals(false, table.containsKey("Jasna"));

		assertEquals(1, table.size());
		assertEquals("[Ivana=1]", table.toString());
	}
}
