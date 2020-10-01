package hr.fer.zemris.math;

/**
 * Class that models a 3D vector. It implements basic vector operations.
 * 
 * @author staver
 *
 */
public class Vector3 {

	private final double x;
	private final double y;
	private final double z;

	/**
	 * Constructs and initializes a new 3D vector to the specified values.
	 * 
	 * @param x x value of the vector
	 * @param y y value of the vector
	 * @param z z value of the vector
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the norm(length) of this vector.
	 * 
	 * @return the norm(length) of this vector.
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns a new vector that is this vector normalized. This vector object
	 * remains unchanged.
	 * 
	 * @return a new vector that is this vector normalized.
	 */
	public Vector3 normalized() {
		double norm = this.norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Adds this vector and the vector given as argument and returns the resulting
	 * vector as a new vector object. This vector object remains unchanged.
	 * 
	 * @param other vector to add
	 * @return a new vector that is a result of adding this vector and the vector
	 *         given as argument
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Subtracts the vector given as argument from this vector and returns the
	 * resulting vector as a new vector object. This vector object remains
	 * unchanged.
	 * 
	 * @param other vector used as subtrahend
	 * @return a new vector that is a result of subtracting the vector given as
	 *         argument from this vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Returns a dot product of this vector and the vector given as argument.
	 * 
	 * @param other vector used a second operand for the dot product
	 * @return a dot product of this vector and the vector given as argument
	 */
	public double dot(Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Returns a cross product of this vector and the vector given as argument.
	 * 
	 * @param other vector used a second operand for the cross product
	 * @return a cross product of this vector and the vector given as argument
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	/**
	 * Returns a new vector that is a result of scaling this vector by the value
	 * given as argument. This vector object remains unchanged.
	 * 
	 * @param s scaler value to scale the vector.
	 * @return a new vector that is a result of scaling this vector by the value
	 *         given as argument
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Returns cosine of the angle between this vector and the vector given as
	 * argument.
	 * 
	 * @param other second vector
	 * @return cosine of the angle between this vector and the vector given as
	 *         argument
	 */
	public double cosAngle(Vector3 other) {
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Returns the x component of this vector.
	 * 
	 * @return the x component of this vector.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y component of this vector.
	 * 
	 * @return the y component of this vector.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the z component of this vector.
	 * 
	 * @return the z component of this vector.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns this vector as a 3 element array.
	 * 
	 * @return this vector as a 3 element array.
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	/**
	 * Returns this vector as a String.
	 * 
	 * @return this vector as a String
	 */
	@Override
	public String toString() {
		return "(" + String.format("%.6f", x) + ", " + String.format("%.6f", y) + ", " + String.format("%.6f", z) + ")";
	}
}
