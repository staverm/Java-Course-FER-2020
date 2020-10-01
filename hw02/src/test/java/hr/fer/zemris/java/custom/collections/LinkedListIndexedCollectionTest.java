package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the class LinkedListIndexedCollection.
 * @author Mauro Staver
 *
 */
class LinkedListIndexedCollectionTest {
	
	private LinkedListIndexedCollection list = new LinkedListIndexedCollection(); //list used for testing

	//sample objects
	private String object1 = "Test object 1";
	private String object2 = "Test object 2";
	private String object3 = "Test object 3";
	private String object4 = "Test object 4";
	
	/**
	 * Method that adds four objects to the given linked list.
	 * @param list LinkedListIndexedCollection to which we want to add values.
	 */
	private void addFourObjects(LinkedListIndexedCollection list) {
		list.add(object1);
		list.add(object2);
		list.add(object3);
		list.add(object4);
	}
	
	@Test
	void defaultConstructorTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
	}
	
	@Test
	void CollectionConstructorTest() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		addFourObjects(other);
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(other);
		assertEquals(4, list.size());
		assertEquals(object1, list.get(0));
	}
	
	@Test
	void CollectionConstructorTestEmptyCollection() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(other);
		assertEquals(0, list.size());
	}
	
	@Test
	void CollectionConstructorTestNullException() {
		assertThrows(NullPointerException.class, () -> {				
			new LinkedListIndexedCollection(null);
		  });
	}
	
	@Test
	void addTest() {
		assertEquals(0, list.size());
		addFourObjects(list);
		assertEquals(4, list.size());
		assertEquals(object4, list.get(3));
	}
	
	@Test
	void addTestNullException() {
		assertThrows(NullPointerException.class, () -> {				
			list.add(null);
		  });
	}
	@Test
	void getTestTooLargeIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {				
			addFourObjects(list);
			list.get(4);
		  });
	}
	@Test
	void getTestNegativeIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {				
			addFourObjects(list);
			list.get(-1);
		  });
	}
	@Test
	void getTestOddNumberOfItems() {
		String object5 = "Test object 5";
		addFourObjects(list);
		list.add(object5);
		
		assertEquals(object1, list.get(0));
		assertEquals(object2, list.get(1));
		assertEquals(object3, list.get(2));
		assertEquals(object4, list.get(3));
		assertEquals(object5, list.get(4));		
	}
	@Test
	void getTestEvenNumberOfItems() {
		addFourObjects(list);
		
		assertEquals(object1, list.get(0));
		assertEquals(object2, list.get(1));
		assertEquals(object3, list.get(2));
		assertEquals(object4, list.get(3));	
	}
	
	@Test
	void clearTestSize() {
		addFourObjects(list);
		
		list.clear();
		assertEquals(0, list.size());
	}

	@Test
	void clearTest() {
		addFourObjects(list);
		list.clear();
		assertEquals(false, list.contains(object1));	
		assertEquals(false, list.contains(object3));	
	}
	
	@Test
	void insertTestAtMiddle() {
		addFourObjects(list);
		String newObject = "new object";
		
		list.insert(newObject, 1);

		assertEquals(list.size(), 5);
		assertEquals(list.get(3), object3);
		assertEquals(list.get(4), object4);
		assertEquals(list.get(1), newObject);
	}

	@Test
	void insertTestAtBeggining() {
		String newObject = "new object";
		addFourObjects(list);
		
		list.insert(newObject, 0);

		assertEquals(list.size(), 5);
		assertEquals(list.get(0), newObject);
	}

	@Test
	void insertTestAtEnd() {
		String newObject = "new object";
		addFourObjects(list);

		list.insert(newObject, 4);

		assertEquals(list.size(), 5);
		assertEquals(list.get(4), newObject);
	}

	@Test
	void insertTestEmpty() {
		String newObject = "new object";

		list.insert(newObject, 0);

		assertEquals(list.size(), 1);
		assertEquals(list.get(0), newObject);
	}

	@Test
	void insertTestPositiveIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(new Object());
			list.insert(new Object(), 2);
		});
	}

	@Test
	void insertTestNegativeIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.insert(new Object(), -1);
		});
	}
	
	@Test
	void indexOfTestGeneral() {
		String newObject = "new object";
		addFourObjects(list);
		
		assertEquals(2, list.indexOf(object3));
		assertEquals(3, list.indexOf(object4));
		assertEquals(-1, list.indexOf(newObject));
	}
	
	@Test
	void indexOfTestAtMiddle() {
		addFourObjects(list);
		
		assertEquals(2, list.indexOf(object3));
	}
	
	@Test
	void indexOfTestUnexistingObject() {
		String newObject = "new object";
		addFourObjects(list);

		assertEquals(-1, list.indexOf(newObject));
	}
	@Test
	void indexOfTestAtBeginning() {
		addFourObjects(list);
		
		assertEquals(0, list.indexOf(object1));
		
	}
	@Test
	void indexOfTestAtEnd() {
		addFourObjects(list);
		
		assertEquals(3, list.indexOf(object4));
	}

	@Test
	void indexOfTestNull() {
		assertEquals(-1, list.indexOf(null));
	}
	
	@Test
	void removeTestFromMiddle() {
		addFourObjects(list);

		list.remove(2);

		assertEquals(3, list.size());
		assertEquals(object4, list.get(2));
	}
	
	@Test
	void removeTestFromBeginning() {
		addFourObjects(list);

		list.remove(0);

		assertEquals(3, list.size());
		assertEquals(object4, list.get(2));
	}
	
	@Test
	void removeTestFromEnd() {
		addFourObjects(list);

		list.remove(3);

		assertEquals(3, list.size());
		assertEquals(object3, list.get(2));
	}

	@Test
	void removeTestEmptyListException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(0);
		});
	}

	@Test
	void removeTestTooLargeIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			addFourObjects(list);
			list.remove(4);
		});
	}
	
	@Test
	void removeTestIndexBeforeException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(-1);
		});
	}
	
	@Test
	void addAllTest() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		addFourObjects(other);
		
		list.addAll(other);
		
		assertEquals(object1, list.get(0));
		assertEquals(object4, list.get(3));
	}
	
	@Test
	void toArrayTest() {
		addFourObjects(list);
		Object[] objects = list.toArray();
		
		assertEquals(objects[0], list.get(0));
		assertEquals(objects[3], list.get(3));
	}
	
	@Test
	void removeWithReferenceTestForReturnValue(){
		addFourObjects(list);
		
		assertEquals(false, list.remove(new Object()));
		assertEquals(true, list.remove(object1));
	}
	@Test
	void removeWithReferenceTestFromMiddle(){
		addFourObjects(list);
		list.remove(object2);
		
		assertEquals(3, list.size());
		assertEquals(object4, list.get(2));
	}
	@Test
	void removeWithReferenceTestFromBeginning(){
		addFourObjects(list);
		list.remove(object1);
		
		assertEquals(3, list.size());
		assertEquals(object4, list.get(2));
	}
	@Test
	void removeWithReferenceTestFromEnd(){
		addFourObjects(list);
		list.remove(object4);
		
		assertEquals(3, list.size());
		assertEquals(object3, list.get(2));
		
	}
	
	@Test
	void containsTest() {
		addFourObjects(list);
		assertEquals(true, list.contains(object2));
		assertEquals(false, list.contains(new Object()));
	}
	@Test
	void sizeTest() {
		assertEquals(0, list.size());
		addFourObjects(list);
		assertEquals(4, list.size());
	}
	
	@Test
	void isEmptyTest() {
		assertEquals(true, list.isEmpty());
		addFourObjects(list);
		assertEquals(false, list.isEmpty());
	}
	
	
}
