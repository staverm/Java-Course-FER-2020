package hr.fer.zemris.math;

/**
 * Class that represents a 2D vector. It implements some basic vector
 * operations.
 * 
 * @author Mauro Staver
 *
 */
public class Vector2D {

	private double x;
	private double y;

	/**
	 * Constructs and initializes a new Vector2D with the specified values.
	 * 
	 * @param x - x value of the vector
	 * @param y - y value of the vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Translates this vector using the specified offset vector.
	 * 
	 * @param offset vector used for translation
	 */
	public void translate(Vector2D offset) {
		x += offset.x;
		y += offset.y;
	}

	/**
	 * Creates and returns a new vector by taking this vector and translating it
	 * using the specified offset vector.
	 * 
	 * @param offset vector used for translation.
	 * @return a new vector created by taking this vector and translating it using
	 *         the specified offset vector.
	 */
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(x + offset.x, y + offset.y);
	}

	/**
	 * Rotates this Vector by the specified angle value.
	 * 
	 * @param angle angle to rotate the vector, in degrees.
	 */
	public void rotate(double angle) {
		angle = Math.toRadians(angle); // convert to radians
		double oldX = x;

		x = x * Math.cos(angle) - y * Math.sin(angle);
		y = oldX * Math.sin(angle) + y * Math.cos(angle);
	}

	/**
	 * Creates and returns a new Vector by taking this vector and rotating it by the
	 * specified angle value.
	 * 
	 * @param angle angle to rotate the vector, in degrees.
	 * @return a new Vector created by taking this vector and rotating it by the
	 *         specified angle value.
	 */
	public Vector2D rotated(double angle) {
		angle = Math.toRadians(angle); // convert to radians
		
		return new Vector2D(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
	}

	/**
	 * Scales this vector with the specified scaler value.
	 * 
	 * @param scaler scaler value to scale the vector.
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Creates and returns a new Vector by taking this vector and scaling it with
	 * the specified scaler value.
	 * 
	 * @param scaler scaler value to scale the vector.
	 * @return a new Vector created by taking this vector and scaling it with the
	 *         specified scaler value.
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(x * scaler, y * scaler);
	}

	/**
	 * Returns a new Vector which is a copy of this vector.
	 * 
	 * @return a new Vector which is a copy of this vector.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/**
	 * Returns the x value of this vector.
	 * 
	 * @return the x value of this vector.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y value of this vector.
	 * 
	 * @return the y value of this vector.
	 */
	public double getY() {
		return y;
	}
}
