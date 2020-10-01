package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the class ArrayIndexedCollection.
 * @author Mauro Staver
 *
 */
class ArrayIndexedCollectionTest {
	
	private ArrayIndexedCollection array = new ArrayIndexedCollection(); //array collection used for testing
	
	//sample objects
	private String object1 = "Test object 1";
	private String object2 = "Test object 2";
	private String object3 = "Test object 3";
	private String object4 = "Test object 4";
	
	/**
	 * Method that adds four objects to the given array collection.
	 * @param list ArrayIndexedCollection to which we want to add values.
	 */
	private void addFourObjects(ArrayIndexedCollection array) {
		array.add(object1);
		array.add(object2);
		array.add(object3);
		array.add(object4);
	}
	
	@Test
	void defaultConstructorTest() {
		assertEquals(0, array.size());
		assertEquals(16, array.capacity());
	}
	
	@Test
	void IntialCapacityConstructorTestException() {
		assertThrows(IllegalArgumentException.class, () -> {				
			new ArrayIndexedCollection(0);
		  });
	}
	
	@Test
	void CollectionAndCapacityConstructorTestNullException() {
		assertThrows(NullPointerException.class, () -> {				
			new ArrayIndexedCollection(null, 3);
		  });
	}
	
	@Test
	void CollectionAndCapacityConstructorTestIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {				
			ArrayIndexedCollection other = new ArrayIndexedCollection(); //has size 0
			
			new ArrayIndexedCollection(other, 0);
		  });
	}
	
	@Test
	void CollectionAndCapacityConstructorTest() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		addFourObjects(other);
		ArrayIndexedCollection array = new ArrayIndexedCollection(other, 3);
			
		assertEquals(4, array.size());
		assertEquals(4, array.capacity());
	}
	
	@Test
	void CollectionAndCapacityConstructorTest2() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		addFourObjects(other);
		ArrayIndexedCollection array = new ArrayIndexedCollection(other, 5);
			
		assertEquals(4, array.size());
		assertEquals(5, array.capacity());
	}
	
	@Test
	void CollectionConstructorTest() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		addFourObjects(other);
		ArrayIndexedCollection array = new ArrayIndexedCollection(other);
			
		assertEquals(4, array.size());
		assertEquals(16, array.capacity());
	}
	
	@Test
	void CollectionConstructorTestNullException() {
		assertThrows(NullPointerException.class, () -> {				
			new ArrayIndexedCollection(null);
		  });
	}
	
	@Test
	void addTest() {
		addFourObjects(array);

		assertEquals(array.size(), 4);
		assertEquals(array.get(0), object1);
		assertEquals(array.get(1), object2);
	}

	@Test
	void addTestNull() {
		assertThrows(NullPointerException.class, () -> {
			array.add(null);
		});
	}

	@Test
	void addTestWithReallocation() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(3);
		addFourObjects(array);

		assertEquals(4,array.size());
		assertEquals(object1, array.get(0));
		assertEquals(object2,array.get(1));
		assertEquals(6,array.capacity());
	}

	@Test
	void getTest() {
		addFourObjects(array);
		assertEquals(array.get(0), object1);
	}

	@Test
	void getTestIndexLargerThanSize() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.add(new Object());
			array.get(1);
		});
	}

	@Test
	void getTestIndexSmallerThanZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.add(new Object());
			array.get(-1);
		});
	}

	@Test
	void clearTest() {
		addFourObjects(array);

		array.clear();

		assertEquals(0, array.size());
	}
	
	@Test
	void clearTestCapacity() {
		array = new ArrayIndexedCollection(5);
		addFourObjects(array);

		array.clear();

		assertEquals(5, array.capacity());
	}

	@Test
	void insertTestMiddle() {
		addFourObjects(array);
		String newObject = "new object";
		
		array.insert(newObject, 1);

		assertEquals(array.size(), 5);
		assertEquals(array.get(3), object3);
		assertEquals(array.get(1), newObject);
	}

	@Test
	void insertTestBeggining() {
		String newObject = "new object";
		addFourObjects(array);
		
		array.insert(newObject, 0);

		assertEquals(array.size(), 5);
		assertEquals(array.get(0), newObject);
	}

	@Test
	void insertTestEnd() {
		String newObject = "new object";
		addFourObjects(array);

		array.insert(newObject, 4);

		assertEquals(array.size(), 5);
		assertEquals(array.get(4), newObject);
	}

	@Test
	void insertTestEmpty() {
		String newObject = "new object";

		array.insert(newObject, 0);

		assertEquals(array.size(), 1);
		assertEquals(array.get(0), newObject);
	}

	@Test
	void insertTestPositiveIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.add(new Object());
			array.insert(new Object(), 2);
		});
	}

	@Test
	void insertTestNegativeIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.insert(new Object(), -1);
		});
	}

	@Test
	void indexOfTestAtMiddle() {
		addFourObjects(array);
		
		assertEquals(2, array.indexOf(object3));
	}
	
	@Test
	void indexOfTestUnexistingObject() {
		String newObject = "new object";
		addFourObjects(array);

		assertEquals(-1, array.indexOf(newObject));
	}
	@Test
	void indexOfTestAtBeginning() {
		addFourObjects(array);
		
		assertEquals(0, array.indexOf(object1));
		
	}
	@Test
	void indexOfTestAtEnd() {
		addFourObjects(array);
		
		assertEquals(3, array.indexOf(object4));
	}

	@Test
	void indexOfTestNull() {
		assertEquals(-1, array.indexOf(null));
	}

	@Test
	void removeTestFromMiddle() {
		addFourObjects(array);

		array.remove(2);

		assertEquals(3, array.size());
		assertEquals(object4, array.get(2));
	}
	
	@Test
	void removeTestFromBeginning() {
		addFourObjects(array);

		array.remove(0);

		assertEquals(3, array.size());
		assertEquals(object4, array.get(2));
	}
	
	@Test
	void removeTestFromEnd() {
		addFourObjects(array);

		array.remove(3);

		assertEquals(3, array.size());
		assertEquals(object3, array.get(2));
	}

	@Test
	void removeTestEmptyArrayException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.remove(0);
		});
	}
	
	@Test
	void removeTestTooLargeIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			addFourObjects(array);
			array.remove(4);
		});
	}

	@Test
	void removeTestNegativeIndexException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.remove(-1);
		});
	}
	
	@Test
	void addAllTest() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		addFourObjects(other);
		
		array.addAll(other);
		
		assertEquals(object1, array.get(0));
		assertEquals(object4, array.get(3));
	}
	
	@Test
	void toArrayTest() {
		addFourObjects(array);
		Object[] objects = array.toArray();
		
		assertEquals(objects[0], array.get(0));
		assertEquals(objects[3], array.get(3));
	}
	
	@Test
	void removeWithReferenceTestForReturnValue(){
		addFourObjects(array);
		
		assertEquals(false, array.remove(new Object()));
		assertEquals(true, array.remove(object1));
	}
	@Test
	void removeWithReferenceTestFromMiddle(){
		addFourObjects(array);
		array.remove(object2);
		
		assertEquals(3, array.size());
		assertEquals(object4, array.get(2));
	}
	@Test
	void removeWithReferenceTestFromBeginning(){
		addFourObjects(array);
		array.remove(object1);
		
		assertEquals(3, array.size());
		assertEquals(object4, array.get(2));
	}
	@Test
	void removeWithReferenceTestFromEnd(){
		addFourObjects(array);
		array.remove(object4);
		
		assertEquals(3, array.size());
		assertEquals(object3, array.get(2));
		
	}
	
	@Test
	void containsTest() {
		addFourObjects(array);
		assertEquals(true, array.contains(object2));
		assertEquals(false, array.contains(new Object()));
	}
	@Test
	void sizeTest() {
		assertEquals(0, array.size());
		addFourObjects(array);
		assertEquals(4, array.size());
	}
	
	@Test
	void isEmptyTest() {
		assertEquals(true, array.isEmpty());
		addFourObjects(array);
		assertEquals(false, array.isEmpty());
	}
	
}

