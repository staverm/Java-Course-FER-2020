package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that models a complex number. It implements methods for all basic
 * operations with complex numbers.
 * 
 * @author staver
 *
 */
public class Complex {

	private double real; // real part 
	private double imaginary; // imaginary part

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	public Complex() {
		real = ZERO.real;
		imaginary = ZERO.imaginary;
	}

	/**
	 * Constructor. Creates a new Complex number object initialized with the given
	 * values.
	 * 
	 * @param real      real part of the complex number
	 * @param imaginary imaginary part of the complex number
	 */
	public Complex(double re, double im) {
		real = re;
		imaginary = im;
	}

	/**
	 * Returns the module of this complex number
	 * 
	 * @return module of this complex number
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Adds the given complex number to this complex number and returns the result.
	 * 
	 * @param c Complex number to be added.
	 * @return the resulting Complex number.
	 */
	public Complex add(Complex c) {
		return new Complex(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Subtracts the given complex number from this complex number and returns the
	 * result.
	 * 
	 * @param c complex number to be subtracted.
	 * @return the resulting complex number.
	 */
	public Complex sub(Complex c) {
		return new Complex(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * Returns the additive inverse of this complex number. The new complex numbers
	 * real and imaginary part are the result of the real and imaginary components
	 * of this complex number multiplied by -1.
	 * 
	 * @return the additive inverse of this complex number.
	 */
	public Complex negate() {
		return new Complex(real * (-1) + 0.0, imaginary * (-1) + 0.0);
		// adding 0.0 so the method doesn't return negative zero (-0.0)
	}

	/**
	 * Multiplies the given complex number with this complex number and returns the
	 * result.
	 * 
	 * @param c complex number to be multiplied.
	 * @return the resulting complex number.
	 */
	public Complex mul(Complex c) {
		return new Complex(real * c.getReal() - imaginary * c.getImaginary(),
				real * c.getImaginary() + imaginary * c.getReal());
	}

	/**
	 * Divides the given complex number with this complex number and returns the
	 * result. The given complex number is used as a divisor, and this complex
	 * number as a dividend.
	 * 
	 * @param c complex number to be used as a divisor.
	 * @return the resulting complex number.
	 */
	public Complex div(Complex c) {
		double sumOfSquares = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
		double re = c.getReal() / sumOfSquares;
		double im = - (c.getImaginary() / sumOfSquares);

		return mul(new Complex(re, im)); // multiply by 1/c
	}

	/**
	 * Calculates and returns this complex number raised to the given power.
	 * 
	 * @param n power
	 * @return this complex number raised to the given power.
	 * @throws IllegalArgumentException if given power is less than 0
	 */
	public Complex power(int n) throws IllegalArgumentException {
		if (n < 0) {
			throw new IllegalArgumentException("Argument has to be >= 0");
		}
		double rPowN = Math.pow(this.module(), n);
		double re = rPowN * Math.cos(n * this.getAngle());
		double im = rPowN * Math.sin(n * this.getAngle());

		return new Complex(re, im); // DeMoivre's Theorem z^n = r^n(cos(nθ), isin(nθ))
	}

	/**
	 * Calculates and returns n-th roots of this complex number.
	 * 
	 * @param n root value
	 * @return List of complex numbers which contains the n-th roots of this
	 *         Complex.
	 * @throws IllegalArgumentException if root value is <= 0
	 */
	public List<Complex> root(int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException("Argument has to be > 0");
		}
		List<Complex> roots = new ArrayList<>();
		double rPowOneOverN = Math.pow(this.module(), 1.0 / n); // r^(1/n)

		for (int k = 0; k < n; ++k) {
			double re = rPowOneOverN * Math.cos((this.getAngle() + 2 * k * Math.PI) / n);
			double im = rPowOneOverN * Math.sin((this.getAngle() + 2 * k * Math.PI) / n);

			roots.add(new Complex(re, im)); // DeMoivre's Theorem
		}
		return roots;
	}

	/**
	 * Returns the angle of the polar form of this complex number in radians.
	 * 
	 * @return double - angle in radians in range [0,2PI] of the polar form of this
	 *         complex number
	 */
	private double getAngle() {
		double angle = Math.atan2(imaginary, real);
		if (angle < 0)
			angle += 2 * Math.PI;
		return angle;
	}

	/**
	 * Returns this Complex in String format.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + String.format("%.1f", real));
		if (imaginary < 0.0) {
			sb.append("-i" + String.format("%.1f", Math.abs(imaginary)) + ")");
		} else {
			sb.append("+i" + String.format("%.1f", imaginary) + ")");
		}
		return sb.toString();
	}

	/**
	 * Returns the real part of the ComplexNumber.
	 * 
	 * @return double - real part of the ComplexNumber.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of the ComplexNumber.
	 * 
	 * @return double - imaginary part of the ComplexNumber.
	 */
	public double getImaginary() {
		return imaginary;
	}

}
