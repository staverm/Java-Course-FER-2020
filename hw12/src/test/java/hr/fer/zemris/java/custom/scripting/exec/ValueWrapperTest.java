package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Tests for the class ValueWrapper.
 * 
 * @author staver
 *
 */
class ValueWrapperTest {
	
	private double precision = 0.0000001; //precision for comparing doubles
	
	@Test
	void StringAsDoubleAndInt() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Double);
		assertEquals(13.0, (Double)v1.getValue(), precision);
	}
	
	@Test
	void StringAsIntAndInt() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Integer);
		assertEquals(13, (Integer)v1.getValue());
	}
	
	@Test
	void StringAsIntAndNull() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Integer);
		assertEquals(12, (Integer)v1.getValue());
	}
	
	@Test
	void StringAsDoubleAndNull() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(23.4));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Double);
		assertEquals(23.4, (Double)v1.getValue(), precision);
	}
	
	@Test
	void NullAndNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Integer);
		assertEquals(0, (Integer)v1.getValue());
	}
	
	@Test
	void NullAndString() {
		ValueWrapper v1 = new ValueWrapper("23.4");
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Double);
		assertEquals(23.4, (Double)v1.getValue(), precision);
	}
	
	@Test
	void NullAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Integer);
		assertEquals(3, (Integer)v1.getValue());
	}
	
	@Test
	void IntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(4));
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Integer);
		assertEquals(7, (Integer)v1.getValue());
	}
	
	@Test
	void StringAsIntegerAndStringAsInteger() {
		ValueWrapper v1 = new ValueWrapper("23");
		ValueWrapper v2 = new ValueWrapper("7");
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Integer);
		assertEquals(30, (Integer)v1.getValue());
	}
	
	@Test
	void DoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(23.4));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Double);
		assertEquals(24.4, (Double)v1.getValue(), precision);
	}
	
	@Test
	void StringAsIntAndDouble() {
		ValueWrapper v1 = new ValueWrapper("7");
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(23));
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Double);
		assertEquals(30, (Double)v1.getValue(), precision);
	}
	
	@Test
	void DoubleAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(23.4));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.2));
		v1.add(v2.getValue());

		assertEquals(true, v1.getValue() instanceof Double);
		assertEquals(25.6, (Double)v1.getValue(), precision);
	}
	
	@Test
	void UnparsableString() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> v1.add(v2.getValue()));

	}
	
	@Test
	void WrappedBoolean() {
		ValueWrapper v1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> v1.add(Integer.valueOf(5)));
	}
	
	@Test
	void IntegerAndBoolean() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class, () -> v1.add(Boolean.valueOf(true)));
	}
	
	@Test
	void compareDoubleAndDoubleSmaller() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(20.7));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(23));
		assertEquals(true, 0 > v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareDoubleAndDoubleLarger() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(28.2));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(23));
		assertEquals(true, 0 < v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareDoubleAndDoubleEqual() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(28.2));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(28.2));
		assertEquals(true, 0 == v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndIntegerLarger() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(28));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(23));
		assertEquals(true, 0 < v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndIntegerSmaller() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(21));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(23));
		assertEquals(true, 0 > v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndIntegerEqual() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(21));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(21));
		assertEquals(true, 0 == v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndDoubleSmaller() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(12.3));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(21));
		assertEquals(true, 0 > v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndDoubleLarger() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(53.3));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(21));
		assertEquals(true, 0 < v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndDoubleEqual() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(53));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(53));
		assertEquals(true, 0 == v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndStringAsDoubleSmaller() {
		ValueWrapper v1 = new ValueWrapper("12.2");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(21));
		assertEquals(true, 0 > v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndStringAsDoubleLarger() {
		ValueWrapper v1 = new ValueWrapper("50.3");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(21));
		assertEquals(true, 0 < v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareIntegerAndStringEqual() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));
		assertEquals(true, 0 == v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareStringAndStringSmaller() {
		ValueWrapper v1 = new ValueWrapper("12.2");
		ValueWrapper v2 = new ValueWrapper("21");
		assertEquals(true, 0 > v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareStringAndStringLarger() {
		ValueWrapper v1 = new ValueWrapper("123");
		ValueWrapper v2 = new ValueWrapper("21");
		assertEquals(true, 0 < v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareStringAndStringEqual() {
		ValueWrapper v1 = new ValueWrapper("12.2");
		ValueWrapper v2 = new ValueWrapper("12.2");
		assertEquals(true, 0 == v1.numCompare(v2.getValue()));
	}
	
	@Test
	void compareUnparsableString() {
		ValueWrapper v1 = new ValueWrapper("12.2.3.2");
		ValueWrapper v2 = new ValueWrapper("21");
		assertThrows(RuntimeException.class, () -> v1.numCompare(v2.getValue()));
	}
}
