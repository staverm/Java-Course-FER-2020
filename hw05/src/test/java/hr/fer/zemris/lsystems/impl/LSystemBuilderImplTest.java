package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.*;

class LSystemBuilderImplTest {
	
	private LSystemBuilder LBuilder = new LSystemBuilderImpl();
	
	@Test
	void generateTestKoch() {
		LBuilder.setAxiom("F");
		LBuilder.registerProduction('F', "F+F--F+F");
		LSystem L = LBuilder.build();
		
		String expected0 = "F";
		String expected1 = "F+F--F+F";
		String expected2 = "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F";
		
		assertEquals(expected0, L.generate(0));
		assertEquals(expected1, L.generate(1));
		assertEquals(expected2, L.generate(2));
	}
	
	@Test
	void generateTestKoch2() {
		LBuilder.setAxiom("F-F-F-F");
		LBuilder.registerProduction('F', "FF-F-F-F-FF");
		LSystem L = LBuilder.build();
		
		String expected0 = "F-F-F-F";
		String expected1 = "FF-F-F-F-FF-FF-F-F-F-FF-FF-F-F-F-FF-FF-F-F-F-FF";
		
		assertEquals(expected0, L.generate(0));
		assertEquals(expected1, L.generate(1));
	}
	
	@Test
	void multipleProductionsTest() {
		LBuilder.setAxiom("F-F-F-F");
		LBuilder.registerProduction('F', "FF-F-F-F-FF");
		LBuilder.registerProduction('-', "a");
		LSystem L = LBuilder.build();
		
		String expected0 = "F-F-F-F";
		String expected1 = "FF-F-F-F-FFaFF-F-F-F-FFaFF-F-F-F-FFaFF-F-F-F-FF";
		
		assertEquals(expected0, L.generate(0));
		assertEquals(expected1, L.generate(1));
	}
	
	

}
