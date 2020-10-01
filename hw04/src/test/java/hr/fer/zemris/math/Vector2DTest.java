package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Vector2D class.
 * 
 * @author Mauro Staver
 *
 */
class Vector2DTest {

	Vector2D vector = new Vector2D(2, 3); // vector used for tests
	private double precision = 0.0000001; // precision for comparing doubles

	@Test
	void rotateTest90deg() {
		double oldMagnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
		vector.rotate(Math.PI / 2); // rotate for 90 deg
		double newMagnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());

		assertEquals(-3, vector.getX(), precision);
		assertEquals(2, vector.getY(), precision);
		assertEquals(oldMagnitude, newMagnitude, precision);
	}

	@Test
	void rotateTest135deg() {
		double oldMagnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
		vector.rotate(2.35619449); // rotate for 135 deg
		double newMagnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());

		assertEquals(-3.53553390593, vector.getX(), precision);
		assertEquals(-0.707106781187, vector.getY(), precision);
		assertEquals(oldMagnitude, newMagnitude, precision);
	}

	@Test
	void rotateTest360deg() {
		vector.rotate(2 * Math.PI); // rotate for 135 deg

		assertEquals(2, vector.getX(), precision);
		assertEquals(3, vector.getY(), precision);
	}

	@Test
	void rotatedTest90deg() {
		Vector2D vector2 = vector.rotated(Math.PI / 2); // rotate for 90 deg

		assertEquals(-3, vector2.getX(), precision);
		assertEquals(2, vector2.getY(), precision);
	}

	@Test
	void rotatedTest135deg() {
		Vector2D vector2 = vector.rotated(2.35619449); // rotate for 135 deg

		assertEquals(-3.53553390593, vector2.getX(), precision);
		assertEquals(-0.707106781187, vector2.getY(), precision);
	}

	@Test
	void scaleTest() {
		vector.scale(2.5); // rotate for 135 deg

		assertEquals(5, vector.getX(), precision);
		assertEquals(7.5, vector.getY(), precision);

		double magnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
		assertEquals(9.013878189, magnitude, precision);
	}

	@Test
	void scaleTestNegative() {
		vector.scale(-3.24); // rotate for 135 deg

		assertEquals(-6.48, vector.getX(), precision);
		assertEquals(-9.72, vector.getY(), precision);

		double magnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
		assertEquals(11.68198613, magnitude, precision);
	}

	@Test
	void scaledTest() {
		Vector2D vector2 = vector.scaled(2.5); // rotate for 135 deg

		assertEquals(5, vector2.getX(), precision);
		assertEquals(7.5, vector2.getY(), precision);

		double magnitude = Math.sqrt(vector2.getX() * vector2.getX() + vector2.getY() * vector2.getY());
		assertEquals(9.013878189, magnitude, precision);
	}

	@Test
	void scaledTestNegative() {
		Vector2D vector2 = vector.scaled(-3.24); // rotate for 135 deg

		assertEquals(-6.48, vector2.getX(), precision);
		assertEquals(-9.72, vector2.getY(), precision);

		double magnitude = Math.sqrt(vector2.getX() * vector2.getX() + vector2.getY() * vector2.getY());
		assertEquals(11.68198613, magnitude, precision);
	}

	@Test
	void copyTest() {
		Vector2D vector2 = vector.copy();
		assertEquals(vector2.getX(), vector.getX(), precision);
		assertEquals(vector2.getY(), vector.getY(), precision);
	}

	@Test
	void constructorTest() {
		Vector2D vector2 = new Vector2D(1, 5);
		assertEquals(1, vector2.getX(), precision);
		assertEquals(5, vector2.getY(), precision);
	}

	@Test
	void translateTest() {
		vector.translate(new Vector2D(-1,1));

		assertEquals(1, vector.getX(), precision);
		assertEquals(4, vector.getY(), precision);
	}

	@Test
	void translatedTest() {
		Vector2D vector2 = vector.translated(new Vector2D(-1,1));

		assertEquals(1, vector2.getX(), precision);
		assertEquals(4, vector2.getY(), precision);
	}

}
