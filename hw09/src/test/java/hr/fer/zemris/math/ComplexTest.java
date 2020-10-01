package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexTest {

	private double precision = 0.0000001; //precision for comparing doubles
	
	@Test
	void divTest() {
		Complex expected = new Complex(-1,1);
		
		Complex c = new Complex(1,3);
		Complex result = c.div(new Complex(1,-2));
		
		assertEquals(expected.getReal(),result.getReal(),precision);
		assertEquals(expected.getImaginary(),result.getImaginary(),precision);
	}
	
	@Test 
	void divTest2(){
		Complex expected = new Complex(2.540540541, 0.7567567568);
		
		Complex c = new Complex(32,4);
		Complex result = c.div(new Complex(12,-2));
		
		assertEquals(expected.getReal(),result.getReal(),precision);
		assertEquals(expected.getImaginary(),result.getImaginary(),precision);
	}

}
