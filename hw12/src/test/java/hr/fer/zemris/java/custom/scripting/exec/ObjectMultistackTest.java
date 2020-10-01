package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests for the class ObjectMultistack.
 * 
 * @author staver
 *
 */
class ObjectMultistackTest {
	
	private ObjectMultistack multistack = new ObjectMultistack();
	private double precision = 0.0000001; //precision for comparing doubles
	
	@Test
	void PopFromEmptyStack() {
		assertThrows(EmptyStackException.class, () -> multistack.pop("nonExistant"));
	}
	
	@Test
	void PopFromEmptyStack2() {
		multistack.push("A", new ValueWrapper(Integer.valueOf(2)));
		assertEquals(2, multistack.pop("A").getValue());
		assertThrows(EmptyStackException.class, () -> multistack.pop("A"));
	}
	
	@Test
	void PeekFromEmptyStack() {
		assertThrows(EmptyStackException.class, () -> multistack.peek("nonExistant"));
	}
	
	@Test
	void PeekFromEmptyStack2() {
		multistack.push("A", new ValueWrapper(Integer.valueOf(2)));
		assertEquals(false, multistack.isEmpty("A"));
		assertEquals(2, multistack.pop("A").getValue());
		assertThrows(EmptyStackException.class, () -> multistack.peek("A"));
	}
	
	@Test
	void PushPopTestSingleStack() {
		multistack.push("A", new ValueWrapper(Integer.valueOf(2)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(3)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(4)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(5)));
		assertEquals(5, multistack.pop("A").getValue());
		assertEquals(4, multistack.pop("A").getValue());
		assertEquals(3, multistack.pop("A").getValue());
		assertEquals(2, multistack.pop("A").getValue());
	}
	
	@Test
	void PushPopTestMultipleStacks() {
		multistack.push("A", new ValueWrapper(Integer.valueOf(2)));
		multistack.push("a", new ValueWrapper(Integer.valueOf(20)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(3)));
		multistack.push("a", new ValueWrapper(Integer.valueOf(30)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(4)));
		multistack.push("a", new ValueWrapper(Integer.valueOf(40)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(5)));
		multistack.push("a", new ValueWrapper(Integer.valueOf(50)));
		assertEquals(50, multistack.pop("a").getValue());
		assertEquals(40, multistack.peek("a").getValue());
		assertEquals(40, multistack.pop("a").getValue());
		assertEquals(30, multistack.pop("a").getValue());
		assertEquals(20, multistack.pop("a").getValue());
		assertEquals(5, multistack.peek("A").getValue());
		assertEquals(5, multistack.pop("A").getValue());
		assertEquals(4, multistack.pop("A").getValue());
		assertEquals(3, multistack.pop("A").getValue());
		assertEquals(2, multistack.pop("A").getValue());
	}
	
	@Test
	void PeekTest() {
		multistack.push("A", new ValueWrapper(Integer.valueOf(2)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(3)));
		assertEquals(3, multistack.peek("A").getValue());
		multistack.pop("A");
		assertEquals(2, multistack.peek("A").getValue());
		multistack.pop("A");
		assertThrows(EmptyStackException.class, () -> multistack.peek("A"));
	}
	
	@Test
	void GeneralTest() {
		assertEquals(true, multistack.isEmpty("A"));
		multistack.push("A", new ValueWrapper(Integer.valueOf(2)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(3)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(4)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(5)));
		assertEquals(false, multistack.isEmpty("A"));
		multistack.push("a", new ValueWrapper(Integer.valueOf(20)));
		multistack.push("a", new ValueWrapper("XXX"));
		multistack.push("a", new ValueWrapper(Double.valueOf(2.3)));
		assertEquals(2.3, (Double)multistack.pop("a").getValue(), precision);
		assertEquals("XXX", multistack.pop("a").getValue());
		assertEquals(20, multistack.pop("a").getValue());
		assertEquals(true, multistack.isEmpty("a"));
		assertEquals(5, multistack.pop("A").getValue());
		assertEquals(4, multistack.pop("A").getValue());
		assertEquals(3, multistack.pop("A").getValue());
		assertEquals(2, multistack.pop("A").getValue());
		assertEquals(true, multistack.isEmpty("A"));
		assertThrows(EmptyStackException.class, () -> multistack.peek("A"));
		assertThrows(EmptyStackException.class, () -> multistack.pop("A"));
		multistack.push("A", new ValueWrapper(Integer.valueOf(2)));
		multistack.push("A", new ValueWrapper(Integer.valueOf(3)));
		assertEquals(3, multistack.pop("A").getValue());
		assertEquals(2, multistack.pop("A").getValue());
		assertEquals(true, multistack.isEmpty("A"));
		assertThrows(EmptyStackException.class, () -> multistack.peek("A"));
		assertThrows(EmptyStackException.class, () -> multistack.pop("A"));
	}
	
	@Test
	void isEmptyTest() {
		assertEquals(true, multistack.isEmpty("A"));		
		multistack.push("A", new ValueWrapper(Integer.valueOf(2)));
		assertEquals(false, multistack.isEmpty("A"));		
		multistack.push("A", new ValueWrapper(Integer.valueOf(3)));
		assertEquals(false, multistack.isEmpty("A"));		
		multistack.pop("A");
		multistack.pop("A");
		assertEquals(true, multistack.isEmpty("A"));
		assertEquals(true, multistack.isEmpty("a"));
		multistack.push("a", new ValueWrapper(Integer.valueOf(20)));
		assertEquals(false, multistack.isEmpty("a"));
		multistack.push("a", new ValueWrapper("XXX"));
		assertEquals(false, multistack.isEmpty("a"));
		multistack.push("a", new ValueWrapper(Double.valueOf(2.3)));
		multistack.pop("a");
		assertEquals(false, multistack.isEmpty("a"));
		multistack.pop("a");
		assertEquals(false, multistack.isEmpty("a"));
		multistack.pop("a");
		assertThrows(EmptyStackException.class, () -> multistack.peek("A"));
		assertEquals(true, multistack.isEmpty("a"));		
	}
	
}
