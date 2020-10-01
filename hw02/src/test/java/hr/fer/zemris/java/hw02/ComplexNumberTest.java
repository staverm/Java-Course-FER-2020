package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for class ComplexNumber
 * @author Mauro Staver
 *
 */
class ComplexNumberTest {
	
	private double precision = 0.0000001; //precision for comparing doubles
	
	@Test
	void fromRealTest() {
		ComplexNumber c = ComplexNumber.fromReal(3.2);
		double expectedReal = 3.2;
		double expectedImaginary = 0;
		assertEquals(expectedReal,c.getReal(),precision);
		assertEquals(expectedImaginary,c.getImaginary(),precision);
	}
	@Test
	void fromImaginaryTest() {
		ComplexNumber c = ComplexNumber.fromImaginary(3.2);
		double expectedReal = 0;
		double expectedImaginary = 3.2;
		assertEquals(expectedReal,c.getReal(),precision);
		assertEquals(expectedImaginary,c.getImaginary(),precision);
	}
	@Test
	void fromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(8,Math.PI/3);
		double expectedReal = 4;
		double expectedImaginary = 6.92820323;
		assertEquals(expectedReal,c.getReal(),precision);
		assertEquals(expectedImaginary,c.getImaginary(),precision);
	}
	
	
	@Test
	void addTest() {
		ComplexNumber expected = new ComplexNumber(3,1);
		
		ComplexNumber c = new ComplexNumber(2,3);
		ComplexNumber result = c.add(new ComplexNumber(1,-2));
		
		assertEquals(expected.toString(),result.toString());
	}
	
	@Test
	void subTest() {
		ComplexNumber expected = new ComplexNumber(1,5);
		
		ComplexNumber c = new ComplexNumber(2,3);
		ComplexNumber result = c.sub(new ComplexNumber(1,-2));
		
		assertEquals(expected.toString(),result.toString());
	}
	
	@Test
	void mulTest() {
		ComplexNumber expected = new ComplexNumber(8,-1);
		
		ComplexNumber c = new ComplexNumber(2,3);
		ComplexNumber result = c.mul(new ComplexNumber(1,-2));
		
		assertEquals(expected.toString(),result.toString());
	}
	
	@Test
	void divTest() {
		ComplexNumber expected = new ComplexNumber(-1,1);
		
		ComplexNumber c = new ComplexNumber(1,3);
		ComplexNumber result = c.div(new ComplexNumber(1,-2));
		
		assertEquals(expected.getReal(),result.getReal(),precision);
		assertEquals(expected.getImaginary(),result.getImaginary(),precision);
	}
	
	@Test
	void powerTest() {
		ComplexNumber expected = new ComplexNumber(-26,-18);
		
		ComplexNumber c = new ComplexNumber(1,3);
		ComplexNumber result = c.power(3);
		
		assertEquals(expected.getReal(),result.getReal(),precision);
		assertEquals(expected.getImaginary(),result.getImaginary(),precision);
	}
	
	@Test
	void powerTestZero() {
		ComplexNumber expected = new ComplexNumber(1,0);
		
		ComplexNumber c = new ComplexNumber(41,23);
		ComplexNumber result = c.power(0);
		
		assertEquals(expected.getReal(),result.getReal(),precision);
		assertEquals(expected.getImaginary(),result.getImaginary(),precision);
	}
	
	@Test
	void powerTestException() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = new ComplexNumber(2,3);
			c.power(-1);
		  });
	}
	
	@Test
	void rootTest() {
		ComplexNumber expected = new ComplexNumber(-1,1);
		
		ComplexNumber c = new ComplexNumber(2,2);
		ComplexNumber result[] = c.root(3);
		
		assertEquals(expected.getReal(),result[1].getReal(),precision);
		assertEquals(expected.getImaginary(),result[1].getImaginary(),precision);
	}
	
	@Test
	void rootTestException() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = new ComplexNumber(2,3);
			c.root(0);
		  });
	}
	
	@Test
	void rootTestOne() {
		ComplexNumber expected = new ComplexNumber(2,2);
		
		ComplexNumber c = new ComplexNumber(2,2);
		ComplexNumber result[] = c.root(1);
		
		assertEquals(expected.getReal(),result[0].getReal(),precision);
		assertEquals(expected.getImaginary(),result[0].getImaginary(),precision);
	}
	
	@Test
	void getRealTest() {
		ComplexNumber c = new ComplexNumber(2,3);
		assertEquals(c.getReal(),2);
	}
	@Test
	void getImaginaryTest() {
		ComplexNumber c = new ComplexNumber(2,3);
		double expected = 3;
		assertEquals(expected,c.getImaginary(),precision);
	}
	@Test
	void getMagnitudeTest() {
		ComplexNumber c = new ComplexNumber(2,3);
		double expected = 3.605551275;
		assertEquals(expected,c.getMagnitude(),precision);
	}
	@Test
	void getAngleTestFirstQuadrant() {
		ComplexNumber c = new ComplexNumber(17,42);
		double expected = 1.186191612;
		assertEquals(expected,c.getAngle(),precision);
	}
	@Test
	void getAngleTestSecondQuadrant() {
		ComplexNumber c = new ComplexNumber(-1,1);
		double expected = 2.35619449;
		assertEquals(expected,c.getAngle(),precision);
	}
	@Test
	void getAngleTestThirdQuadrant() {
		ComplexNumber c = new ComplexNumber(-1,-1);
		double expected = 3.926990817;
		assertEquals(expected,c.getAngle(),precision);
	}
	@Test
	void getAngleTestFourthQuadrant() {
		ComplexNumber c = new ComplexNumber(1,-1);
		double expected = 5.49778714;
		assertEquals(expected,c.getAngle(),precision);
	}
	@Test
	void getAngleTestZero() {
		ComplexNumber c = new ComplexNumber(100,0);
		double expected = 0;
		assertEquals(expected,c.getAngle(),precision);
	}
	
	
	@Test
	void toStringTestNegativeImaginary() {
		ComplexNumber c = new ComplexNumber(17,-32);
		String expected = "17.0-32.0i";
		assertEquals(expected,c.toString());
	}
	@Test
	void toStringTestPositiveImaginary() {
		ComplexNumber c = new ComplexNumber(17,32);
		String expected = "17.0+32.0i";
		assertEquals(expected,c.toString());
	}
	@Test
	void toStringTestOnlyImaginary() {
		ComplexNumber c = new ComplexNumber(0,-32);
		String expected = "-32.0i";
		assertEquals(expected,c.toString());
	}
	@Test
	void toStringTestOnlyReal() {
		ComplexNumber c = new ComplexNumber(23.3,0);
		String expected = "23.3";
		assertEquals(expected,c.toString());
	}
	@Test
	void toStringTestOneForImaginary() {
		ComplexNumber c = new ComplexNumber(0,-1);
		String expected = "-1.0i";
		assertEquals(expected,c.toString());
	}
	@Test
	void toStringTestZero() {
		ComplexNumber c = new ComplexNumber(0,0);
		String expected = "0";
		assertEquals(expected,c.toString());
	}
	
	@Test
	void parseTestRealAndImaginary() {
		String s = "3.15+4.15i";
		ComplexNumber expected = new ComplexNumber(3.15,4.15);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestRealAndImaginaryWithPlusAtBeginning() {
		String s = "+3.15-4.15i";
		ComplexNumber expected = new ComplexNumber(3.15,-4.15);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOneDigitRealAndImaginary() {
		String s = "5-4i";
		ComplexNumber expected = new ComplexNumber(5,-4);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOneDigitRealAndOnly_i_Imaginary() {
		String s = "5-i";
		ComplexNumber expected = new ComplexNumber(5,-1);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestRealAndImaginaryWithMinusAtBeginning() {
		String s = "-3.15+4.15i";
		ComplexNumber expected = new ComplexNumber(-3.15,4.15);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestRealAndImaginaryWithOnlyNegative_i_() {
		String s = "+3.15-i";
		ComplexNumber expected = new ComplexNumber(3.15,-1.0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestRealAndImaginaryWithOnlyPositive_i_() {
		String s = "3.15+i";
		ComplexNumber expected = new ComplexNumber(3.15,1.0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnlyRealWithSignAtBeginning() {
		String s = "+3.15";
		ComplexNumber expected = new ComplexNumber(3.15,0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnlyReal() {
		String s = "3.15";
		ComplexNumber expected = new ComplexNumber(3.15,0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnlyRealOneDigit() {
		String s = "3";
		ComplexNumber expected = new ComplexNumber(3,0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnlyRealOneDigitWithSign() {
		String s = "-3";
		ComplexNumber expected = new ComplexNumber(-3,0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnlyImaginary() {
		String s = "3.15i";
		ComplexNumber expected = new ComplexNumber(0,3.15);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnlyImaginaryWithSignAtBeginning() {
		String s = "+3.15i";
		ComplexNumber expected = new ComplexNumber(0,3.15);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnlyImaginaryOneDigit() {
		String s = "3i";
		ComplexNumber expected = new ComplexNumber(0,3);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnlyImaginaryOneDigitWithSign() {
		String s = "-3i";
		ComplexNumber expected = new ComplexNumber(0,-3);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnly_i_WithMinusSign() {
		String s = "-i";
		ComplexNumber expected = new ComplexNumber(0,-1.0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnly_i_WithPlusSign() {
		String s = "+i";
		ComplexNumber expected = new ComplexNumber(0,1.0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestOnly_i_() {
		String s = "i";
		ComplexNumber expected = new ComplexNumber(0,1.0);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	@Test
	void parseTestWhitespace() {
		String s = " - 3.4 +2. 5 i ";
		ComplexNumber expected = new ComplexNumber(-3.4,2.5);
		ComplexNumber actual = ComplexNumber.parse(s);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	void parseTestTwoSignsExceptionOnlyReal() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "-+2.71";
			ComplexNumber.parse(s);
		  });
	}
	
	@Test
	void parseTestTwoSignsExceptionOnlyImaginary() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "-+2.71i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTestTwoSignsExceptionRealAndImaginary1() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "-+2.71+i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTestTwoSignsExceptionRealAndImaginary2() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "+2.71+-i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTest_i_BeforeImaginaryPartWithSignException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "-i3.16i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTest_i_BeforeImaginaryPartOneDigitException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "i3i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTest_i_BeforeImaginaryPartException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "i3.16i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTest_i_BeforeRealpartWithSignException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "-i3.16";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTest_i_BeforeRealPartOneDigitException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "i3";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTest_i_BeforeRealPartException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "i3.16";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTestRealAndImaginary_i_BeforeImaginaryPartException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "2+i3.16";
			ComplexNumber.parse(s);
		  });
	}
	
	@Test
	void parseTestTwoDotsInRealPartException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "2.4.3+3i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTest_i_InFrontOfRealPartException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "i23+23i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTest_i_InFrontOfRealPartNoImaginaryPartException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "i351";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTestTwoDotsInImaginaryPartException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "2+3.2.i";
			ComplexNumber.parse(s);
		  });
	}
	
	@Test
	void parseTestRandomTextException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "234ki";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTestRandomTextOnlyLettersException() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "abcd";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTestTwoSignsBefore_i_Exception() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "++i";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTestTwoSignsAfter_i_Exception() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "i++";
			ComplexNumber.parse(s);
		  });
	}
	@Test
	void parseTestOneSignAfter_i_Exception() {
		assertThrows(IllegalArgumentException.class, () -> {
			String s = "i+";
			ComplexNumber.parse(s);
		  });
	}
	
}
