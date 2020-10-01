package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that models a complex polynomial with the form:
 * z0*(z-z1)*(z-z2)*...*(z-zn) where z1 to zn are the roots of the polynomial
 * and z0 is a constant.
 * 
 * @author staver
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Roots of this polynomial. (z1 to zn)
	 */
	private List<Complex> roots = new ArrayList<>();
	/**
	 * Constant that multiplies the polynomial (z0)
	 */
	private Complex constant;

	/**
	 * Constructs and initializes a new ComplexRootedPolynom to the specified
	 * values. The polynomial has the form: z0*(z-z1)*(z-z2)*...*(z-zn) where z1 to
	 * zn are the roots of the polynomial and z0 is a constant.
	 * 
	 * @param constant complex number to be used as a constant that multiplies the
	 *                 polynomial (z0)
	 * @param roots    roots of the polynomial (z1 to zn)
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = Arrays.asList(roots);
	}

	/**
	 * Computes polynomial value at given point z and returns it.
	 * 
	 * @param z point at which to compute the polynomial value.
	 * @return polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (Complex c : roots) {
			result = result.mul((z.sub(c)));
		}
		return result;
	}

	/**
	 * Converts this representation to ComplexPolynomial type.
	 * 
	 * @return ComplexPolynomial representation of this polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[roots.size() + 1];
		Arrays.fill(factors, Complex.ZERO);
		factors[0] = constant;

		for (int i = 0; i < roots.size(); i++) {
			Complex[] scaled = scale(factors, roots.get(i));

			for (int j = factors.length - 1; j > 0; j--) {
				factors[j] = factors[j - 1].sub(scaled[j]);
			}
			factors[0] = scaled[0].negate();
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Scales the specified array of complex numbers with the given scaler.
	 * Multiplies each element of the array with the scaler. The given array remains
	 * unchanged.
	 * 
	 * @param factors
	 * @param scaler
	 * @return
	 */
	private Complex[] scale(Complex[] array, Complex scaler) {
		Complex[] scaled = array.clone();

		for (int i = 0; i < scaled.length; i++) {
			scaled[i] = scaled[i].mul(scaler);
		}

		return scaled;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant);
		for (int i = 0; i < roots.size(); i++) {
			sb.append("*(z-" + roots.get(i) + ")");
		}
		return sb.toString();
	}

	/**
	 * Returns the index of closest root for given complex number z that is within
	 * the specified treshold. If there is no such root return -1. First root has
	 * index 0, second index 1, etc.
	 * 
	 * @param z        Complex number whose closest root is to be found
	 * @param treshold distance treshold
	 * @return index of closest root for given complex number z or -1 if there is no
	 *         such root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int indexOfClosestRoot = -1;
		double minModule = treshold;
		for (int i = 0; i < roots.size(); i++) {
			double module = z.sub(roots.get(i)).module();

			if (module < treshold && module < minModule) {
				indexOfClosestRoot = i;
				minModule = module;
			}
		}
		return indexOfClosestRoot;
	}

}
