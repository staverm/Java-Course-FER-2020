package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the class ObjectStack
 * @author Mauro Staver
 *
 */
class ObjectStackTest {
	
	private ObjectStack stack = new ObjectStack();
	private String object = "Test object";
	
	@Test
	void constructorTest() {
		ObjectStack stack = new ObjectStack();
		assertEquals(0, stack.size());
	}
	
	@Test
	void isEmptyTest() {
		assertEquals(true, stack.isEmpty());
		stack.push(new Object());
		assertEquals(false, stack.isEmpty());
	}
	
	@Test
	void sizeTest() {
		assertEquals(0, stack.size());
		stack.push(new Object());
		assertEquals(1, stack.size());
	}
	
	@Test
	void pushTest() {
		stack.push(new Object());
		stack.push(object);
		
		assertEquals(object, stack.peek());
		assertEquals(2, stack.size());
	}
	
	@Test
	void pushTestNullException() {
		assertThrows(NullPointerException.class, () -> {
			stack.push(null);
		});
	}

	@Test
	void popTest() {
		stack.push(object);
		
		assertEquals(object, stack.pop());
		assertEquals(0, stack.size());
	}
	
	@Test
	void popTestEmptyStackException() {
		assertThrows(EmptyStackException.class, () -> {
			stack.pop();
		});
	}
	
	@Test
	void peekTest() {
		stack.push(object);
		
		assertEquals(object, stack.peek());
	}
	
	@Test
	void clearTest() {
		stack.push(object);
		stack.push(new Object());
		
		stack.clear();
		assertEquals(0, stack.size());
	}
	
}
