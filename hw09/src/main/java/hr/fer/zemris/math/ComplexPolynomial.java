package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that models a complex polynomial with the form: zn*z^n + ... + z2*z^2 +
 * z1*z +z0, where z0 to zn are coefficients that stand with the corresponding
 * powers of z.
 * 
 * @author staver
 *
 */
public class ComplexPolynomial {
	
	/**
	 * Factors(or coefficients) of this polynomial.
	 */
	private List<Complex> factors = new ArrayList<>();

	/**
	 * Constructs and initializes a new ComplexRootedPolynom to the specified
	 * values. The polynomial has the form: zn*z^n + ... + z2*z^2 + z1*z +z0, where
	 * z0 to zn are coefficients that stand with the corresponding powers of z.
	 * 
	 * @param factors coefficients used to initialize the polynomial (z0 to zn)
	 * @throws IllegalArgumentException if no factors are given
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors.length == 0) {
			throw new IllegalArgumentException("Can't initialize empty polynomial.");
		}
		this.factors = Arrays.asList(factors);
	}
	
	/**
	 * Returns the order of this polynomial.
	 * 
	 * @return order of this polynomial
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Computes a new polynomial that is this polynomial multiplied by the given
	 * polynomial (this*p). Both this and given polynomial remain unchanged.
	 * 
	 * @param p polynomial used to multiply
	 * @return a new polynomial that is this polynomial multiplied by the given
	 *         polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int n = this.factors.size();
		int m = p.factors.size();
		Complex[] resultFactors = new Complex[n + m - 1];
		Arrays.fill(resultFactors, Complex.ZERO);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				resultFactors[i + j] = resultFactors[i + j].add(this.factors.get(i).mul(p.factors.get(j)));
			}
		}
		return new ComplexPolynomial(resultFactors);
	}

	/**
	 * Computes first derivative of this polynomial and returns it. This polynomial
	 * object remains unchanged.
	 * 
	 * @return first derivative of this polynomial
	 */
	public ComplexPolynomial derive() {
		int n = factors.size();
		Complex[] resultFactors = new Complex[n - 1];
		for (int i = 1; i < n; i++) {
			resultFactors[i - 1] = factors.get(i).mul(new Complex(i, 0));
		}
		return new ComplexPolynomial(resultFactors);
	}

	/**
	 * Computes polynomial value at given point z and returns it.
	 * 
	 * @param z point at which to compute the polynomial value.
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex result = factors.get(0);
		for (int i = 1; i < factors.size(); i++) {
			result = result.add(factors.get(i).mul(z.power(i)));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.size() - 1; i >= 0; i--) {
			if (i == 0) {
				sb.append(factors.get(0));
			} else {
				sb.append(factors.get(i) + "*z^" + i + "+");
			}
		}

		return sb.toString();
	}

}
